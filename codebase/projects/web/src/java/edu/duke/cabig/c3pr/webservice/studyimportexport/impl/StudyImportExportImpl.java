/**
 * 
 */
package edu.duke.cabig.c3pr.webservice.studyimportexport.impl;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Detail;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.validation.SchemaFactory;
import javax.xml.ws.BindingType;
import javax.xml.ws.Provider;
import javax.xml.ws.Service.Mode;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceProvider;
import javax.xml.ws.soap.SOAPFaultException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.input.DOMBuilder;
import org.jdom.output.XMLOutputter;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.repository.StudyRepository;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.service.StudyXMLImporterService;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import edu.emory.mathcs.backport.java.util.Arrays;
import gov.nih.nci.common.exception.XMLUtilityException;

/**
 * Implementation of the Study Import Export Web service. It's done on the XML
 * level, because the underlying import processing is also done on XML level.
 * 
 * @author Denis G. Krylov
 * 
 */
@WebServiceProvider(wsdlLocation = "/WEB-INF/wsdl/StudyImportExport.wsdl", portName = "StudyImportExport", serviceName = "StudyImportExportService", targetNamespace = "http://enterpriseservices.nci.nih.gov/StudyImportExportService")
@ServiceMode(Mode.MESSAGE)
@BindingType(value = javax.xml.ws.soap.SOAPBinding.SOAP11HTTP_BINDING)
public class StudyImportExportImpl implements Provider<SOAPMessage> {

	public static final String C3PR_DOMAIN_XSD_URL = "/c3pr-domain.xsd";

	private static final String STUDY_ELEMENT = "study";

	private static final String IDENTIFIER_ELEMENT = "identifier";

	public static final String C3PR_NS = edu.duke.cabig.c3pr.utils.XMLUtils.CCTS_DOMAIN_NS;

	private static final String IMPORT_STUDY_REQUEST = "ImportStudyRequest";

	private static final String EXPORT_STUDY_REQUEST = "ExportStudyRequest";

	private static final String FAULT_MESSAGE = "message";

	private static final String SOAP_FAULT_CODE = "Server";

	private static final String STUDY_IMPORT_FAULT = "StudyImportExportFault";

	public static final String SERVICE_NS = "http://enterpriseservices.nci.nih.gov/StudyImportExportService";

	public static final String SOAP_NS = "http://schemas.xmlsoap.org/soap/envelope/";

	@Resource
	protected WebServiceContext ctx;

	private StudyXMLImporterService studyXMLImporterService;

	private XmlMarshaller marshaller;

	private StudyRepository studyRepository;

	private static final Log log = LogFactory
			.getLog(StudyImportExportImpl.class);

	public SOAPMessage invoke(SOAPMessage request) {
		try {
			SOAPBody body = request.getSOAPBody();
			RequestType type = determineRequestType(body);
			if (type == RequestType.IMPORT_STUDY) {
				return processStudyImportRequest(body);
			} else {
				return processStudyExportRequest(body);
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new WebServiceException(
					createSOAPFaultException(e.getMessage()));
		}
	}

	/**
	 * Determines the operation we need to perform based on the wrapper element.
	 * 
	 * @param body
	 * @return
	 */
	private RequestType determineRequestType(SOAPBody body) {
		if (body.getElementsByTagNameNS(SERVICE_NS, IMPORT_STUDY_REQUEST)
				.getLength() == 1) {
			return RequestType.IMPORT_STUDY;
		}
		if (body.getElementsByTagNameNS(SERVICE_NS, EXPORT_STUDY_REQUEST)
				.getLength() == 1) {
			return RequestType.EXPORT_STUDY;
		}
		throw new RuntimeException(
				"Malformed SOAP request. Please check the WSDL.");
	}

	private SOAPMessage createImportStudyResponse() throws SOAPException {
		MessageFactory mf = MessageFactory.newInstance();
		SOAPMessage response = mf.createMessage();
		SOAPBody body = response.getSOAPBody();
		body.addChildElement(new QName(SERVICE_NS, "ImportStudyResponse"));
		response.saveChanges();
		return response;
	}

	private SOAPMessage processStudyExportRequest(SOAPBody body)
			throws DOMException, RuntimeException,
			ParserConfigurationException, C3PRCodedException, SOAPException,
			XMLUtilityException, SAXException, IOException {
		NodeList nodes = body.getElementsByTagNameNS(SERVICE_NS,
				EXPORT_STUDY_REQUEST);
		Node exportStudyRequestNode = nodes.item(0);
		Element studyId = (Element) getFirstChild(exportStudyRequestNode,
				IDENTIFIER_ELEMENT, C3PR_NS);
		if (studyId == null) {
			throw new RuntimeException(
					"Malformed SOAP request. Please check the WSDL.");
		}
		Identifier oai = convertToOAI(studyId);
		Study study = studyRepository.getUniqueStudy(java.util.Arrays
				.asList(oai));
		return createExportStudyResponse(study);

	}

	private Node getFirstChild(Node node, String name, String ns) {
		NodeList children = node.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (name.equals(child.getLocalName()) && ns.equals(child.getNamespaceURI())) {
				return child;
			}
		}
		return null;
	}

	private SOAPMessage createExportStudyResponse(Study study)
			throws SOAPException, XMLUtilityException,
			ParserConfigurationException, SAXException, IOException {
		MessageFactory mf = MessageFactory.newInstance();
		SOAPMessage response = mf.createMessage();
		SOAPBody body = response.getSOAPBody();
		SOAPElement exportStudyResponse = body.addChildElement(new QName(
				SERVICE_NS, "ExportStudyResponse"));
		String xml = marshaller.toXML(study);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setSchema(SchemaFactory.newInstance(
				XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(
				XmlMarshaller.class.getResource(C3PR_DOMAIN_XSD_URL)));
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(IOUtils.toInputStream(xml));
		Element studyEl = (Element) doc.getFirstChild();
		exportStudyResponse.appendChild(body.getOwnerDocument().importNode(
				studyEl, true));
		response.saveChanges();
		return response;
	}

	private OrganizationAssignedIdentifier convertToOAI(Element studyId)
			throws XMLUtilityException, ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();
		doc.appendChild(doc.importNode(studyId, true));

		DOMBuilder jdomBuilder = new DOMBuilder();
		org.jdom.Document jdomDoc = jdomBuilder.build(doc);
		String xmlRep = new XMLOutputter().outputString(jdomDoc);
		OrganizationAssignedIdentifier oai = (OrganizationAssignedIdentifier) marshaller
				.fromXML(new StringReader(xmlRep));
		return oai;
	}

	/**
	 * @param body
	 * @return
	 * @throws DOMException
	 * @throws RuntimeException
	 * @throws ParserConfigurationException
	 * @throws C3PRCodedException
	 * @throws SOAPException
	 */
	private SOAPMessage processStudyImportRequest(SOAPBody body)
			throws DOMException, RuntimeException,
			ParserConfigurationException, C3PRCodedException, SOAPException {
		NodeList nodes = body.getElementsByTagNameNS(SERVICE_NS,
				IMPORT_STUDY_REQUEST);
		Node importStudyRequestNode = nodes.item(0);
		NodeList studyNodes = ((Element) importStudyRequestNode)
				.getElementsByTagNameNS(C3PR_NS, STUDY_ELEMENT);
		if (studyNodes.getLength() != 1) {
			throw new RuntimeException(
					"Malformed SOAP request. Please check the WSDL.");
		}
		Element study = (Element) studyNodes.item(0);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();
		doc.appendChild(doc.importNode(study, true));
		List<Study> studies = studyXMLImporterService.importStudies(doc,
				new ErrorsImpl());
		if (CollectionUtils.isEmpty(studies)) {
			throw new RuntimeException("No studies have been imported.");
		}
		return createImportStudyResponse();

	}

	private SOAPFaultException createSOAPFaultException(String msg) {
		return new SOAPFaultException(createSOAPFault(msg));
	}

	private SOAPFault createSOAPFault(String msg) {
		try {
			SOAPFactory factory = SOAPFactory.newInstance();
			SOAPFault fault = factory.createFault();
			fault.setFaultString(msg);
			fault.setFaultCode(new QName(SOAP_NS, SOAP_FAULT_CODE));
			Detail detail = fault.addDetail();
			final Element detailEntry = detail.getOwnerDocument()
					.createElementNS(SERVICE_NS, STUDY_IMPORT_FAULT);
			detail.appendChild(detailEntry);

			final Element detailMsg = detail.getOwnerDocument()
					.createElementNS(SERVICE_NS, FAULT_MESSAGE);
			detailMsg.setTextContent(msg);
			detailEntry.appendChild(detailMsg);
			return fault;
		} catch (SOAPException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new WebServiceException(e);
		}

	}

	/**
	 * @return the studyRepository
	 */
	public final StudyRepository getStudyRepository() {
		return studyRepository;
	}

	/**
	 * @param studyRepository
	 *            the studyRepository to set
	 */
	public final void setStudyRepository(StudyRepository studyRepository) {
		this.studyRepository = studyRepository;
	}

	/**
	 * @return the marshaller
	 */
	public final XmlMarshaller getMarshaller() {
		return marshaller;
	}

	/**
	 * @param marshaller
	 *            the marshaller to set
	 */
	public final void setMarshaller(XmlMarshaller marshaller) {
		this.marshaller = marshaller;
	}

	/**
	 * @return the studyXMLImporterService
	 */
	public final StudyXMLImporterService getStudyXMLImporterService() {
		return studyXMLImporterService;
	}

	/**
	 * @param studyXMLImporterService
	 *            the studyXMLImporterService to set
	 */
	public final void setStudyXMLImporterService(
			StudyXMLImporterService studyXMLImporterService) {
		this.studyXMLImporterService = studyXMLImporterService;
	}

	private static final class ErrorsImpl implements Errors {

		public String getObjectName() {
			// TODO Auto-generated method stub
			return null;
		}

		public void setNestedPath(String nestedPath) {
			// TODO Auto-generated method stub

		}

		public String getNestedPath() {
			// TODO Auto-generated method stub
			return null;
		}

		public void pushNestedPath(String subPath) {
			// TODO Auto-generated method stub

		}

		public void popNestedPath() throws IllegalStateException {
			// TODO Auto-generated method stub

		}

		public void reject(String errorCode) {
			throw new RuntimeException(errorCode);
		}

		public void reject(String errorCode, String defaultMessage) {
			throw new RuntimeException(defaultMessage);

		}

		public void reject(String errorCode, Object[] errorArgs,
				String defaultMessage) {
			throw new RuntimeException(defaultMessage);

		}

		public void rejectValue(String field, String errorCode) {
			throw new RuntimeException(errorCode);

		}

		public void rejectValue(String field, String errorCode,
				String defaultMessage) {
			throw new RuntimeException(defaultMessage);

		}

		public void rejectValue(String field, String errorCode,
				Object[] errorArgs, String defaultMessage) {
			throw new RuntimeException(defaultMessage);

		}

		public void addAllErrors(Errors errors) {
			// TODO Auto-generated method stub

		}

		public boolean hasErrors() {
			// TODO Auto-generated method stub
			return false;
		}

		public int getErrorCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		public List getAllErrors() {
			// TODO Auto-generated method stub
			return null;
		}

		public boolean hasGlobalErrors() {
			// TODO Auto-generated method stub
			return false;
		}

		public int getGlobalErrorCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		public List getGlobalErrors() {
			// TODO Auto-generated method stub
			return null;
		}

		public ObjectError getGlobalError() {
			// TODO Auto-generated method stub
			return null;
		}

		public boolean hasFieldErrors() {
			// TODO Auto-generated method stub
			return false;
		}

		public int getFieldErrorCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		public List getFieldErrors() {
			// TODO Auto-generated method stub
			return null;
		}

		public FieldError getFieldError() {
			// TODO Auto-generated method stub
			return null;
		}

		public boolean hasFieldErrors(String field) {
			// TODO Auto-generated method stub
			return false;
		}

		public int getFieldErrorCount(String field) {
			// TODO Auto-generated method stub
			return 0;
		}

		public List getFieldErrors(String field) {
			// TODO Auto-generated method stub
			return null;
		}

		public FieldError getFieldError(String field) {
			// TODO Auto-generated method stub
			return null;
		}

		public Object getFieldValue(String field) {
			// TODO Auto-generated method stub
			return null;
		}

		public Class getFieldType(String field) {
			// TODO Auto-generated method stub
			return null;
		}

	}

	private static enum RequestType {
		IMPORT_STUDY, EXPORT_STUDY;
	}

}
