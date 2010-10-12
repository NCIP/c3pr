/**
 * 
 */
package edu.duke.cabig.c3pr.webservice.integration;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.Service;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.PortInfo;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * Provides helper methods mostly for dealing with inserting test SAML tokens
 * into SOAP header.
 * 
 * @author dkrylov
 * 
 */
public final class SOAPUtils {

	public static final String WSS_NS = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
	public static final String PATH_TO_SAML_TOKEN = "/edu/duke/cabig/c3pr/webservice/integration/testdata/SAMLToken.xml";

	/**
	 * @param service
	 */
	public static void installSecurityHandler(Service service) {
		service.setHandlerResolver(new HandlerResolver() {
			public List<Handler> getHandlerChain(PortInfo arg0) {
				List<Handler> list = new ArrayList<Handler>();
				list.add(getSecurityHandler());
				return list;
			}
		});
	}

	/**
	 * We need a handler to insert a SAML token into SOAP header.
	 * 
	 * @return
	 */
	private static SOAPHandler<SOAPMessageContext> getSecurityHandler() {
		return new SOAPHandler<SOAPMessageContext>() {
			public void close(MessageContext arg0) {
			}

			public boolean handleFault(SOAPMessageContext arg0) {
				return true;
			}

			public boolean handleMessage(SOAPMessageContext ctx) {
				// Is this an outbound message, i.e., a request?
				Boolean isOut = (Boolean) ctx
						.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
				if (isOut) {
					SOAPMessage msg = ctx.getMessage();
					try {
						addSAMLToken(msg);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
				return true;
			}

			public Set<QName> getHeaders() {
				return new HashSet<QName>();
			}
		};
	}

	private static void addSAMLToken(SOAPMessage msg) throws SOAPException,
			SAXException, IOException, ParserConfigurationException {
		InputStream xml = SOAPUtils.class
				.getResourceAsStream(PATH_TO_SAML_TOKEN);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		org.w3c.dom.Document doc = dbf.newDocumentBuilder().parse(xml);
		xml.close();

		Node samlToken = doc.getChildNodes().item(0);

		SOAPEnvelope env = msg.getSOAPPart().getEnvelope();
		SOAPHeader hdr = env.getHeader();
		if (hdr == null) {
			hdr = env.addHeader();
		}

		Name qname = env.createName("Security", "wsse", WSS_NS);
		SOAPHeaderElement security = hdr.addHeaderElement(qname);
		security.appendChild(security.getOwnerDocument().importNode(samlToken,
				true));
		msg.saveChanges();
	}

}
