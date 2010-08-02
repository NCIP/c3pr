package edu.duke.cabig.c3pr.web.security;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import javax.jws.WebService;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;
import javax.xml.soap.Detail;
import javax.xml.soap.DetailEntry;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.soap.SOAPFaultException;

import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.AuthenticationManager;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.ProviderManager;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sun.xml.wss.ProcessingContext;
import com.sun.xml.wss.XWSSProcessor;
import com.sun.xml.wss.XWSSProcessorFactory;
import com.sun.xml.wss.XWSSecurityException;
import com.sun.xml.wss.impl.callback.PasswordValidationCallback;

import edu.duke.cabig.c3pr.utils.web.AuditInfoFilter;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.InsufficientPrivilegesExceptionFault;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.SubjectManagement;

/**
 * @author dkrylov
 * 
 */
public final class SecureWebServiceHandler implements
		SOAPHandler<SOAPMessageContext> {

	private static final String NS = "http://docs.oasis-open.org/wss/2004/01/"
					+ "oasis-200401-wss-wssecurity-secext-1.0.xsd";
	public static final String AUTHENTICATION_MANAGER = "authenticationManager";
	public static final String CSM_USER_DETAILS_SERVICE = "csmUserDetailsService";
	private static Log log = LogFactory.getLog(SecureWebServiceHandler.class);

	private static final ThreadLocal<ServletContext> servletContextHolder = new ThreadLocal<ServletContext>();

	private XWSSProcessor xwssProcessor = null;

	public SecureWebServiceHandler() {

		XWSSProcessorFactory fact = null;
		InputStream config = null;
		try {
			fact = XWSSProcessorFactory.newInstance();
			config = SecureWebServiceHandler.class
					.getResourceAsStream("/xws-config.xml");
			xwssProcessor = fact.createProcessorForSecurityConfiguration(
					config, new Verifier());
		} catch (Exception e) {
			log.error(e);
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(config);
		}

	}

	public Set<QName> getHeaders() {
		String uri = NS;
		QName security_hdr = new QName(uri, "Security", "wsse");
		Set<QName> headers = new HashSet<QName>();
		headers.add(security_hdr);
		return headers;
	}

	public void close(MessageContext ctx) {
		AuditInfoFilter.unsetAuditInfo();
	}

	public boolean handleFault(SOAPMessageContext ctx) {
		return true;
	}

	public boolean handleMessage(SOAPMessageContext ctx) {
		SOAPMessage msg = ctx.getMessage();
		try {
			Boolean response_p = (Boolean) ctx
					.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
			ServletContext servletContext = (ServletContext) ctx
					.get(MessageContext.SERVLET_CONTEXT);
			HttpServletRequest request = (HttpServletRequest) ctx
					.get(MessageContext.SERVLET_REQUEST);
			servletContextHolder.set(servletContext);
			// Handle the SOAP only if it's incoming.
			if (!response_p) {
				ProcessingContext p_ctx = xwssProcessor
						.createProcessingContext(msg);
				p_ctx.setSOAPMessage(msg);
				SOAPMessage verified_msg = xwssProcessor
						.verifyInboundMessage(p_ctx);
				ctx.setMessage(verified_msg);
				AuditInfoFilter.setAuditInfo(request);
			}
		} catch (AuthenticationException e) {
			generateSecurityFault(msg, e.getMessage());
		} catch (XWSSecurityException e) {
			generateSecurityFault(msg, e.getMessage());
		} finally {
			servletContextHolder.set(null);
		}
		return true;
	}

	/**
	 * @param msg
	 * @param reason
	 */
	private void generateSecurityFault(SOAPMessage msg, String reason) {
		try {
			SOAPBody body = msg.getSOAPPart().getEnvelope().getBody();
			SOAPFault fault = body.addFault();
			fault.setFaultString(reason);
			Detail detail = fault.addDetail();
			DetailEntry detailEntry = detail.addDetailEntry(new QName(
					getNameSpace(SubjectManagement.class),
					InsufficientPrivilegesExceptionFault.class.getSimpleName(),
					"ent"));
			SOAPElement message = detailEntry.addChildElement(new QName(
					getNameSpace(SubjectManagement.class), "message"));
			message.setValue(reason);
			// wrapper for a SOAP 1.1 or SOAP 1.2 fault
			throw new SOAPFaultException(fault);
		} catch (SOAPException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new RuntimeException(e);
		}
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

	/**
	 * @author dkrylov
	 * 
	 */
	private static final class Verifier implements CallbackHandler {

		// For password validation, set the validator to the inner class below.
		public void handle(Callback[] callbacks)
				throws UnsupportedCallbackException {
			for (int i = 0; i < callbacks.length; i++) {
				if (callbacks[i] instanceof PasswordValidationCallback) {
					PasswordValidationCallback cb = (PasswordValidationCallback) callbacks[i];
					if (cb.getRequest() instanceof PasswordValidationCallback.PlainTextPasswordRequest)
						cb.setValidator(new PlainTextPasswordVerifier());
				} else
					throw new UnsupportedCallbackException(null, "Not needed");
			}
		}

		// Encapsulated validate method verifies the username/password.
		private class PlainTextPasswordVerifier implements
				PasswordValidationCallback.PasswordValidator {
			public boolean validate(PasswordValidationCallback.Request req)
					throws PasswordValidationCallback.PasswordValidationException {
				PasswordValidationCallback.PlainTextPasswordRequest plainPwd = (PasswordValidationCallback.PlainTextPasswordRequest) req;
				final String username = plainPwd.getUsername();
				final String password = plainPwd.getPassword();
				ApplicationContext springCtx = WebApplicationContextUtils
						.getWebApplicationContext(servletContextHolder.get());
				AuthenticationManager authenticationManager = (ProviderManager) springCtx
						.getBean(AUTHENTICATION_MANAGER);
				Authentication auth = authenticationManager
						.authenticate(new UsernamePasswordAuthenticationToken(
								username.trim(), password.trim()));
				SecurityContextHolder.getContext().setAuthentication(auth);
				return true;
			}
		}
	}

}
