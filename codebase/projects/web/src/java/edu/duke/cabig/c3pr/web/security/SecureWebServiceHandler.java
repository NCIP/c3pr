package edu.duke.cabig.c3pr.web.security;

import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPHeader;
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
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import edu.duke.cabig.c3pr.utils.web.AuditInfoFilter;

/**
 * @author dkrylov
 * 
 */
public final class SecureWebServiceHandler implements
		SOAPHandler<SOAPMessageContext> {

	public static final String AUTHENTICATION_MANAGER = "authenticationManager";
	public static final String CSM_USER_DETAILS_SERVICE = "csmUserDetailsService";
	public static final String TOKEN_NS = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
	private static Log log = LogFactory.getLog(SecureWebServiceHandler.class);

	public Set<QName> getHeaders() {
		return null;
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
			// Handle the SOAP only if it's incoming.
			if (!response_p) {				
				SOAPEnvelope env = msg.getSOAPPart().getEnvelope();
				SOAPHeader hdr = env.getHeader();
				// Ensure that the SOAP message has a header.
				if (hdr == null) {
					generateSecurityFault(msg, "No SOAP message header.");
				}

				Element security = getRequiredElement(msg, hdr, "Security");
				Element usernameToken = getRequiredElement(msg, security,
						"UsernameToken");
				Element username = getRequiredElement(msg, usernameToken,
						"Username");
				Element password = getRequiredElement(msg, usernameToken,
						"Password");

				ApplicationContext springCtx = WebApplicationContextUtils
						.getWebApplicationContext(servletContext);
				AuthenticationManager authenticationManager = (ProviderManager) springCtx
						.getBean(AUTHENTICATION_MANAGER);
				Authentication auth = authenticationManager
						.authenticate(new UsernamePasswordAuthenticationToken(
								username.getTextContent().trim(), password
										.getTextContent().trim()));
				SecurityContextHolder.getContext().setAuthentication(auth);

				AuditInfoFilter.setAuditInfo(request);

			}
		} catch (AuthenticationException e) {
			generateSecurityFault(msg, e.getMessage());
		} catch (SOAPException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new RuntimeException(e);
		}
		return true;
	}

	/**
	 * @param node
	 * @param name
	 * @return
	 */
	private Element getRequiredElement(SOAPMessage msg, Element node,
			String name) {
		NodeList nodeList = node.getElementsByTagNameNS(TOKEN_NS, name);
		if (nodeList == null || nodeList.getLength() == 0) {
			generateSecurityFault(msg,
					"Message header does not contain security-related information. "
							+ name + " element is missing.");
		}
		return (Element) nodeList.item(0);
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
			// wrapper for a SOAP 1.1 or SOAP 1.2 fault
			throw new SOAPFaultException(fault);
		} catch (SOAPException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new RuntimeException(e);
		}
	}

}
