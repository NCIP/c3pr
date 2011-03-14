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

	/** The Constant C3PR_DOMAIN_XSD_URL. */
	public static final String C3PR_DOMAIN_XSD_URL = "/c3pr-domain.xsd";

	/** The Constant STUDY_ELEMENT. */
	private static final String STUDY_ELEMENT = "study";

	/** The Constant IDENTIFIER_ELEMENT. */
	private static final String IDENTIFIER_ELEMENT = "identifier";

	/** The Constant C3PR_NS. */
	public static final String C3PR_NS = edu.duke.cabig.c3pr.utils.XMLUtils.CCTS_DOMAIN_NS;

	/** The Constant IMPORT_STUDY_REQUEST. */
	private static final String IMPORT_STUDY_REQUEST = "ImportStudyRequest";

	/** The Constant EXPORT_STUDY_REQUEST. */
	private static final String EXPORT_STUDY_REQUEST = "ExportStudyRequest";

	/** The Constant FAULT_MESSAGE. */
	private static final String FAULT_MESSAGE = "message";

	/** The Constant SOAP_FAULT_CODE. */
	private static final String SOAP_FAULT_CODE = "Server";

	/** The Constant STUDY_IMPORT_FAULT. */
	private static final String STUDY_IMPORT_FAULT = "StudyImportExportFault";

	/** The Constant SERVICE_NS. */
	public static final String SERVICE_NS = "http://enterpriseservices.nci.nih.gov/StudyImportExportService";

	/** The Constant SOAP_NS. */
	public static final String SOAP_NS = "http://schemas.xmlsoap.org/soap/envelope/";

	/** The ctx. */
	@Resource
	protected WebServiceContext ctx;

	/** The study xml importer service. */
	private StudyXMLImporterService studyXMLImporterService;

	/** The marshaller. */
	private XmlMarshaller marshaller;

	/** The study repository. */
	private StudyRepository studyRepository;

	/** The Constant log. */
	private static final Log log = LogFactory
			.getLog(StudyImportExportImpl.class);

	/* (non-Javadoc)
	 * @see javax.xml.ws.Provider#invoke(java.lang.Object)
	 */
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
	 * @param body the body
	 * @return the request type
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

	/**
	 * Creates the import study response.
	 *
	 * @return the sOAP message
	 * @throws SOAPException the sOAP exception
	 */
	private SOAPMessage createImportStudyResponse() throws SOAPException {
		MessageFactory mf = MessageFactory.newInstance();
		SOAPMessage response = mf.createMessage();
		SOAPBody body = response.getSOAPBody();
		body.addChildElement(new QName(SERVICE_NS, "ImportStudyResponse"));
		response.saveChanges();
		return response;
	}

	/**
	 * Process study export request.
	 *
	 * @param body the body
	 * @return the sOAP message
	 * @throws DOMException the dOM exception
	 * @throws RuntimeException the runtime exception
	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws C3PRCodedException the c3 pr coded exception
	 * @throws SOAPException the sOAP exception
	 * @throws XMLUtilityException the xML utility exception
	 * @throws SAXException the sAX exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
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

	/**
	 * Gets the first child.
	 *
	 * @param node the node
	 * @param name the name
	 * @param ns the ns
	 * @return the first child
	 */
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

	/**
	 * Creates the export study response.
	 *
	 * @param study the study
	 * @return the sOAP message
	 * @throws SOAPException the sOAP exception
	 * @throws XMLUtilityException the xML utility exception
	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws SAXException the sAX exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
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

	/**
	 * Convert to oai.
	 *
	 * @param studyId the study id
	 * @return the organization assigned identifier
	 * @throws XMLUtilityException the xML utility exception
	 * @throws ParserConfigurationException the parser configuration exception
	 */
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
	 * Process study import request.
	 *
	 * @param body the body
	 * @return the sOAP message
	 * @throws DOMException the dOM exception
	 * @throws RuntimeException the runtime exception
	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws C3PRCodedException the c3 pr coded exception
	 * @throws SOAPException the sOAP exception
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

	/**
	 * Creates the soap fault exception.
	 *
	 * @param msg the msg
	 * @return the sOAP fault exception
	 */
	private SOAPFaultException createSOAPFaultException(String msg) {
		return new SOAPFaultException(createSOAPFault(msg));
	}

	/**
	 * Creates the soap fault.
	 *
	 * @param msg the msg
	 * @return the sOAP fault
	 */
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
	 * Gets the study repository.
	 *
	 * @return the studyRepository
	 */
	public final StudyRepository getStudyRepository() {
		return studyRepository;
	}

	/**
	 * Sets the study repository.
	 *
	 * @param studyRepository the studyRepository to set
	 */
	public final void setStudyRepository(StudyRepository studyRepository) {
		this.studyRepository = studyRepository;
	}

	/**
	 * Gets the marshaller.
	 *
	 * @return the marshaller
	 */
	public final XmlMarshaller getMarshaller() {
		return marshaller;
	}

	/**
	 * Sets the marshaller.
	 *
	 * @param marshaller the marshaller to set
	 */
	public final void setMarshaller(XmlMarshaller marshaller) {
		this.marshaller = marshaller;
	}

	/**
	 * Gets the study xml importer service.
	 *
	 * @return the studyXMLImporterService
	 */
	public final StudyXMLImporterService getStudyXMLImporterService() {
		return studyXMLImporterService;
	}

	/**
	 * Sets the study xml importer service.
	 *
	 * @param studyXMLImporterService the studyXMLImporterService to set
	 */
	public final void setStudyXMLImporterService(
			StudyXMLImporterService studyXMLImporterService) {
		this.studyXMLImporterService = studyXMLImporterService;
	}

	/**
	 * The Class ErrorsImpl.
	 */
	private static final class ErrorsImpl implements Errors {

		/* (non-Javadoc)
		 * @see org.springframework.validation.Errors#getObjectName()
		 */
		public String getObjectName() {
			// TODO Auto-generated method stub
			return null;
		}

		/* (non-Javadoc)
		 * @see org.springframework.validation.Errors#setNestedPath(java.lang.String)
		 */
		public void setNestedPath(String nestedPath) {
			// TODO Auto-generated method stub

		}

		/* (non-Javadoc)
		 * @see org.springframework.validation.Errors#getNestedPath()
		 */
		public String getNestedPath() {
			// TODO Auto-generated method stub
			return null;
		}

		/* (non-Javadoc)
		 * @see org.springframework.validation.Errors#pushNestedPath(java.lang.String)
		 */
		public void pushNestedPath(String subPath) {
			// TODO Auto-generated method stub

		}

		/* (non-Javadoc)
		 * @see org.springframework.validation.Errors#popNestedPath()
		 */
		public void popNestedPath() throws IllegalStateException {
			// TODO Auto-generated method stub

		}

		/* (non-Javadoc)
		 * @see org.springframework.validation.Errors#reject(java.lang.String)
		 */
		public void reject(String errorCode) {
			throw new RuntimeException(errorCode);
		}

		/* (non-Javadoc)
		 * @see org.springframework.validation.Errors#reject(java.lang.String, java.lang.String)
		 */
		public void reject(String errorCode, String defaultMessage) {
			throw new RuntimeException(defaultMessage);

		}

		/* (non-Javadoc)
		 * @see org.springframework.validation.Errors#reject(java.lang.String, java.lang.Object[], java.lang.String)
		 */
		public void reject(String errorCode, Object[] errorArgs,
				String defaultMessage) {
			throw new RuntimeException(defaultMessage);

		}

		/* (non-Javadoc)
		 * @see org.springframework.validation.Errors#rejectValue(java.lang.String, java.lang.String)
		 */
		public void rejectValue(String field, String errorCode) {
			throw new RuntimeException(errorCode);

		}

		/* (non-Javadoc)
		 * @see org.springframework.validation.Errors#rejectValue(java.lang.String, java.lang.String, java.lang.String)
		 */
		public void rejectValue(String field, String errorCode,
				String defaultMessage) {
			throw new RuntimeException(defaultMessage);

		}

		/* (non-Javadoc)
		 * @see org.springframework.validation.Errors#rejectValue(java.lang.String, java.lang.String, java.lang.Object[], java.lang.String)
		 */
		public void rejectValue(String field, String errorCode,
				Object[] errorArgs, String defaultMessage) {
			throw new RuntimeException(defaultMessage);

		}

		/* (non-Javadoc)
		 * @see org.springframework.validation.Errors#addAllErrors(org.springframework.validation.Errors)
		 */
		public void addAllErrors(Errors errors) {
			// TODO Auto-generated method stub

		}

		/* (non-Javadoc)
		 * @see org.springframework.validation.Errors#hasErrors()
		 */
		public boolean hasErrors() {
			// TODO Auto-generated method stub
			return false;
		}

		/* (non-Javadoc)
		 * @see org.springframework.validation.Errors#getErrorCount()
		 */
		public int getErrorCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		/* (non-Javadoc)
		 * @see org.springframework.validation.Errors#getAllErrors()
		 */
		public List getAllErrors() {
			// TODO Auto-generated method stub
			return null;
		}

		/* (non-Javadoc)
		 * @see org.springframework.validation.Errors#hasGlobalErrors()
		 */
		public boolean hasGlobalErrors() {
			// TODO Auto-generated method stub
			return false;
		}

		/* (non-Javadoc)
		 * @see org.springframework.validation.Errors#getGlobalErrorCount()
		 */
		public int getGlobalErrorCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		/* (non-Javadoc)
		 * @see org.springframework.validation.Errors#getGlobalErrors()
		 */
		public List getGlobalErrors() {
			// TODO Auto-generated method stub
			return null;
		}

		/* (non-Javadoc)
		 * @see org.springframework.validation.Errors#getGlobalError()
		 */
		public ObjectError getGlobalError() {
			// TODO Auto-generated method stub
			return null;
		}

		/* (non-Javadoc)
		 * @see org.springframework.validation.Errors#hasFieldErrors()
		 */
		public boolean hasFieldErrors() {
			// TODO Auto-generated method stub
			return false;
		}

		/* (non-Javadoc)
		 * @see org.springframework.validation.Errors#getFieldErrorCount()
		 */
		public int getFieldErrorCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		/* (non-Javadoc)
		 * @see org.springframework.validation.Errors#getFieldErrors()
		 */
		public List getFieldErrors() {
			// TODO Auto-generated method stub
			return null;
		}

		/* (non-Javadoc)
		 * @see org.springframework.validation.Errors#getFieldError()
		 */
		public FieldError getFieldError() {
			// TODO Auto-generated method stub
			return null;
		}

		/* (non-Javadoc)
		 * @see org.springframework.validation.Errors#hasFieldErrors(java.lang.String)
		 */
		public boolean hasFieldErrors(String field) {
			// TODO Auto-generated method stub
			return false;
		}

		/* (non-Javadoc)
		 * @see org.springframework.validation.Errors#getFieldErrorCount(java.lang.String)
		 */
		public int getFieldErrorCount(String field) {
			// TODO Auto-generated method stub
			return 0;
		}

		/* (non-Javadoc)
		 * @see org.springframework.validation.Errors#getFieldErrors(java.lang.String)
		 */
		public List getFieldErrors(String field) {
			// TODO Auto-generated method stub
			return null;
		}

		/* (non-Javadoc)
		 * @see org.springframework.validation.Errors#getFieldError(java.lang.String)
		 */
		public FieldError getFieldError(String field) {
			// TODO Auto-generated method stub
			return null;
		}

		/* (non-Javadoc)
		 * @see org.springframework.validation.Errors#getFieldValue(java.lang.String)
		 */
		public Object getFieldValue(String field) {
			// TODO Auto-generated method stub
			return null;
		}

		/* (non-Javadoc)
		 * @see org.springframework.validation.Errors#getFieldType(java.lang.String)
		 */
		public Class getFieldType(String field) {
			// TODO Auto-generated method stub
			return null;
		}

	}

	/**
	 * The Enum RequestType.
	 */
	private static enum RequestType {
		
		/** The IMPOR t_ study. */
		IMPORT_STUDY, 
 /** The EXPOR t_ study. */
 EXPORT_STUDY;
	}

}
