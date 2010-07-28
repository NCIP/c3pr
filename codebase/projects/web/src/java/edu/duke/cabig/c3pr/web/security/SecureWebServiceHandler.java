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

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import edu.duke.cabig.c3pr.utils.web.AuditInfoFilter;

/**
 * @author dkrylov
 * 
 */
public final class SecureWebServiceHandler implements
		SOAPHandler<SOAPMessageContext> {

	public static final String CSM_USER_DETAILS_SERVICE = "csmUserDetailsService";
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
		try {
			Boolean response_p = (Boolean) ctx
					.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
			ServletContext servletContext = (ServletContext) ctx
					.get(MessageContext.SERVLET_CONTEXT);
			HttpServletRequest request = (HttpServletRequest) ctx
					.get(MessageContext.SERVLET_REQUEST);
			// Handle the SOAP only if it's incoming.
			if (!response_p) {
				SOAPMessage msg = ctx.getMessage();
				SOAPEnvelope env = msg.getSOAPPart().getEnvelope();
				SOAPHeader hdr = env.getHeader();
				// Ensure that the SOAP message has a header.
				if (hdr == null) {
					generateSOAPFault(msg, "No message header.");
				}

				ApplicationContext springCtx = WebApplicationContextUtils
						.getWebApplicationContext(servletContext);
				UserDetailsService userDetailsService = (UserDetailsService) springCtx
						.getBean(CSM_USER_DETAILS_SERVICE);
				UserDetails user = userDetailsService
						.loadUserByUsername("jdoe01");
				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
						user, user.getPassword(), user.getAuthorities());
				// token.setAuthenticated(true);
				SecurityContextHolder.getContext().setAuthentication(token);

				AuditInfoFilter.setAuditInfo(request);

			}
		} catch (SOAPException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new RuntimeException(e);
		}
		return true;
	}

	/**
	 * @param msg
	 * @param reason
	 */
	private void generateSOAPFault(SOAPMessage msg, String reason) {
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
