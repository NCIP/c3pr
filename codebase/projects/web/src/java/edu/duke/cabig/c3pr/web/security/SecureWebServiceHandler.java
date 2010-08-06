package edu.duke.cabig.c3pr.web.security;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.jws.WebService;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.jaxws.handler.soap.SOAPMessageContextImpl;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.WSSecurityEngineResult;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.apache.ws.security.handler.WSHandlerResult;
import org.apache.ws.security.util.WSSecurityUtil;
import org.opensaml.SAMLAssertion;
import org.opensaml.SAMLNameIdentifier;
import org.opensaml.SAMLStatement;
import org.opensaml.SAMLSubject;
import org.opensaml.SAMLSubjectStatement;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.w3c.dom.Element;

import edu.duke.cabig.c3pr.utils.web.AuditInfoFilter;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.InsufficientPrivilegesExceptionFault;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.SubjectManagement;

/**
 * @author dkrylov
 * 
 */
public final class SecureWebServiceHandler extends AbstractSoapInterceptor {

	private static final String SAML_TOKEN_HAS_NOT_BEEN_PRODUCED_BY_WSS4J_INTERCEPTOR = "SAMLToken has not been produced by WSS4J interceptor";
	public static final String AUTHENTICATION_MANAGER = "authenticationManager";
	public static final String CSM_USER_DETAILS_SERVICE = "csmUserDetailsService";
	private static Log log = LogFactory.getLog(SecureWebServiceHandler.class);

	public SecureWebServiceHandler() {
		super(Phase.PRE_PROTOCOL);
		addAfter(WSS4JInInterceptor.class.getName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.cxf.interceptor.Interceptor#handleMessage(org.apache.cxf.message
	 * .Message)
	 */
	public void handleMessage(SoapMessage message) throws Fault {
		SOAPMessageContext ctx = new SOAPMessageContextImpl(message);
		try {
			ServletContext servletContext = (ServletContext) ctx
					.get(MessageContext.SERVLET_CONTEXT);
			HttpServletRequest request = (HttpServletRequest) ctx
					.get(MessageContext.SERVLET_REQUEST);

			SAMLAssertion samlAssertion = extractSAMLAssertion(message);
			samlAssertion.verify();

			authenticateSubject(servletContext, samlAssertion);

			if (SecurityContextHolder.getContext().getAuthentication() == null) {
				throw new RuntimeException(
						"Unable to authenticate service caller: perhaps, invalid SAML assertion?");
			}
			AuditInfoFilter.setAuditInfo(request);
		} catch (Exception e) {
			log.error(e, e);
			generateSecurityFault(e);
		}
	}

	/**
	 * @param servletContext
	 * @param samlAssertion
	 * @throws RuntimeException
	 * @throws BeansException
	 * @throws UsernameNotFoundException
	 * @throws DataAccessException
	 */
	private void authenticateSubject(ServletContext servletContext,
			SAMLAssertion samlAssertion) throws RuntimeException,
			BeansException, UsernameNotFoundException, DataAccessException {
		Iterator<SAMLStatement> it = samlAssertion.getStatements();
		while (it.hasNext()) {
			SAMLStatement st = it.next();
			if (st instanceof SAMLSubjectStatement) {
				SAMLSubjectStatement attrSt = (SAMLSubjectStatement) st;
				SAMLSubject subject = attrSt.getSubject();
				SAMLNameIdentifier nameID = subject.getName();
				String loginId = nameID.getName();
				if (StringUtils.isBlank(loginId)) {
					throw new RuntimeException(
							"SAML subject identifier contains an empty name.");
				}
				ApplicationContext springCtx = WebApplicationContextUtils
						.getWebApplicationContext(servletContext);
				UserDetailsService userDetailsService = (UserDetailsService) springCtx
						.getBean(CSM_USER_DETAILS_SERVICE);
				UserDetails user = userDetailsService
						.loadUserByUsername(loginId.trim());
				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
						user, user.getPassword(), user.getAuthorities());
				// token.setAuthenticated(true);
				SecurityContextHolder.getContext().setAuthentication(token);
				break;
			}
		}
	}

	/**
	 * @param message
	 * @return
	 * @throws RuntimeException
	 */
	private SAMLAssertion extractSAMLAssertion(SoapMessage message)
			throws RuntimeException {
		List<Object> results = CastUtils.cast((List) message
				.get(WSHandlerConstants.RECV_RESULTS));
		if (CollectionUtils.isEmpty(results)) {
			throw new RuntimeException(
					SAML_TOKEN_HAS_NOT_BEEN_PRODUCED_BY_WSS4J_INTERCEPTOR);
		}
		WSHandlerResult wsHandlerResult = (WSHandlerResult) results.get(0);

		// Note: when the outbound action is ST_SIGNED
		// (SamlTokenSigned), the
		// results list is a ST_UNSIGNED because the SAML processor and
		// signature processors don't indicate if the assertion was used
		// to
		// sign the message or not so you get signature results and
		// ST_UNSIGNED
		// results even if the assertion was used to sign the message.
		final Vector<WSSecurityEngineResult> samlResults = new Vector<WSSecurityEngineResult>();
		WSSecurityUtil.fetchAllActionResults(wsHandlerResult.getResults(),
				WSConstants.ST_UNSIGNED, samlResults);
		if (CollectionUtils.isEmpty(samlResults)) {
			throw new RuntimeException(
					SAML_TOKEN_HAS_NOT_BEEN_PRODUCED_BY_WSS4J_INTERCEPTOR);
		}

		final WSSecurityEngineResult result = samlResults.get(0);
		SAMLAssertion samlAssertion = (SAMLAssertion) result
				.get(WSSecurityEngineResult.TAG_SAML_ASSERTION);
		if (samlAssertion == null) {
			throw new RuntimeException(
					SAML_TOKEN_HAS_NOT_BEEN_PRODUCED_BY_WSS4J_INTERCEPTOR);
		}
		return samlAssertion;
	}

	/**
	 * @param msg
	 * @param reason
	 */
	private void generateSecurityFault(Throwable ex) {

		Fault fault = new Fault(ex);
		fault.setMessage(ex.getMessage());

		Element detail = fault.getOrCreateDetail();
		final Element detailEntry = detail.getOwnerDocument().createElementNS(
				getNameSpace(SubjectManagement.class),
				InsufficientPrivilegesExceptionFault.class.getSimpleName());
		detail.appendChild(detailEntry);

		final Element detailMsg = detail.getOwnerDocument().createElementNS(
				getNameSpace(SubjectManagement.class), "message");
		detailMsg.setTextContent(ex.getMessage());
		detailEntry.appendChild(detailMsg);

		throw fault;

	}

	/**
	 * @param cls
	 * @return
	 */
	private String getNameSpace(Class<SubjectManagement> cls) {
		String ns = "";
		for (Annotation ann : cls.getAnnotations()) {
			if (WebService.class.equals(ann.annotationType())) {
				ns = ((WebService) ann).targetNamespace();
			}
		}
		return ns;
	}

}
