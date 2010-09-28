package edu.duke.cabig.c3pr.web.security;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.nio.charset.Charset;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
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
import org.apache.commons.lang.SystemUtils;
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
import org.apache.ws.security.WSSecurityException;
import org.apache.ws.security.components.crypto.Crypto;
import org.apache.ws.security.components.crypto.CryptoFactory;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.apache.ws.security.handler.WSHandlerResult;
import org.apache.ws.security.util.WSSecurityUtil;
import org.opensaml.SAMLAssertion;
import org.opensaml.SAMLAttribute;
import org.opensaml.SAMLAttributeStatement;
import org.opensaml.SAMLException;
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

	public static final String ADFS_NS = "http://schemas.microsoft.com/ws/2008/06/identity/claims";
	public static final String ADFS_WINDOWSACCOUNTNAME = "windowsaccountname";
	private static final String SAML_TOKEN_HAS_NOT_BEEN_PRODUCED_BY_WSS4J_INTERCEPTOR = "SAMLToken has not been produced by WSS4J interceptor";
	public static final String AUTHENTICATION_MANAGER = "authenticationManager";
	public static final String CSM_USER_DETAILS_SERVICE = "csmUserDetailsService";
	private static Log log = LogFactory.getLog(SecureWebServiceHandler.class);

	private String cryptoPropFile;

	private Crypto crypto;

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
			verifyAssertion(samlAssertion);

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
	 * @param samlAssertion
	 * @throws SAMLException
	 * @throws WSSecurityException
	 */
	private void verifyAssertion(SAMLAssertion samlAssertion)
			throws SAMLException, WSSecurityException {
		samlAssertion.verify();
		Iterator<X509Certificate> it = samlAssertion.getX509Certificates();
		while (it.hasNext()) {
			X509Certificate cert = (X509Certificate) it.next();
			Crypto crypto = getCrypto();
			String alias = crypto.getAliasForX509Cert(cert);
			if (alias == null) {
				store(cert, true);
				throw new WSSecurityException(
						"The issuer's certificate found in the SAML token is not trusted: "
								+ cert);
			} else {
				log.debug("Certificate is trusted: " + cert);
			}
		}
	}

	/**
	 * Store the certificate in a temporary file for further investigation.
	 * 
	 * @param cert
	 */
	private void store(X509Certificate cert, boolean binary) {
		try {
			byte[] buf = cert.getEncoded();
			final File file = new File(SystemUtils.JAVA_IO_TMPDIR,
					"Certificate_" + System.currentTimeMillis() + ".crt");
			log.error("Storing X.509 certificate in the file: "
					+ file.getCanonicalPath());
			OutputStream os = new FileOutputStream(file);
			if (binary) {
				os.write(buf);
				os.flush();
			} else {
				Writer wr = new OutputStreamWriter(os, Charset.forName("UTF-8"));
				wr.write("-----BEGIN CERTIFICATE-----\n");
				wr.write(new sun.misc.BASE64Encoder().encode(buf));
				wr.write("\n-----END CERTIFICATE-----\n");
				wr.flush();
			}
			os.close();
		} catch (Exception e) {
			log.info(e.toString(), e);
		}
	}

	/**
	 * <pre>
	 * &lt;bean class="edu.duke.cabig.c3pr.web.security.SecureWebServiceHandler" p:cryptoPropFile="server_sign.properties"/&gt;
	 * </pre>
	 * 
	 * @return
	 */
	private synchronized Crypto getCrypto() {
		if (crypto == null) {
			crypto = CryptoFactory.getInstance(getCryptoPropFile());
		}
		return crypto;
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

		String loginId = extractLoginId(samlAssertion);
		if (StringUtils.isBlank(loginId)) {
			throw new RuntimeException(
					"Unable to determine login ID from the SAML assertion.");
		}

		ApplicationContext springCtx = WebApplicationContextUtils
				.getWebApplicationContext(servletContext);
		UserDetailsService userDetailsService = (UserDetailsService) springCtx
				.getBean(CSM_USER_DETAILS_SERVICE);
		UserDetails user = userDetailsService
				.loadUserByUsername(loginId.trim());
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				user, user.getPassword(), user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(token);
	}

	private String extractLoginId(SAMLAssertion samlAssertion) {
		String loginId = "";
		Iterator<SAMLStatement> it = samlAssertion.getStatements();
		while (it.hasNext()) {
			SAMLStatement st = it.next();
			if (st instanceof SAMLSubjectStatement) {
				SAMLSubjectStatement attrSt = (SAMLSubjectStatement) st;
				SAMLSubject subject = attrSt.getSubject();
				SAMLNameIdentifier nameID = subject.getNameIdentifier();
				if (nameID != null) {
					loginId = nameID.getName();
				} else if (attrSt instanceof SAMLAttributeStatement) {
					SAMLAttributeStatement attributeStatement = (SAMLAttributeStatement) attrSt;
					Iterator<SAMLAttribute> attrIt = attributeStatement
							.getAttributes();
					while (attrIt.hasNext()) {
						SAMLAttribute attr = attrIt.next();
						String loginIdFromAttr = extractLoginId(attr);
						if (StringUtils.isNotBlank(loginIdFromAttr)) {
							loginId = loginIdFromAttr;
						}
					}
				}
			}
		}
		return loginId;
	}

	private String extractLoginId(SAMLAttribute attr) {
		// Microsoft ADFS specifics go here.
		String loginId = "";
		if (ADFS_WINDOWSACCOUNTNAME.equalsIgnoreCase(attr.getName())
				&& ADFS_NS
						.equals(attr.getNamespace())
				&& attr.getValues().hasNext()) {
			loginId = attr.getValues().next().toString();
		}
		return loginId;
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

	public String getCryptoPropFile() {
		return cryptoPropFile;
	}

	public void setCryptoPropFile(String cryptoPropFile) {
		this.cryptoPropFile = cryptoPropFile;
	}

}
