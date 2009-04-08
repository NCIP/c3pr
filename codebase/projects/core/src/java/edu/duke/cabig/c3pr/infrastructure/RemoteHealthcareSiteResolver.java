package edu.duke.cabig.c3pr.infrastructure;

import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;
import edu.duke.cabig.c3pr.esb.CCTSMessageBroadcaster;
import edu.duke.cabig.c3pr.esb.Metadata;
import edu.duke.cabig.c3pr.esb.OperationNameEnum;
import edu.duke.cabig.c3pr.esb.ServiceTypeEnum;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import gov.nih.nci.cagrid.caxchange.client.CaXchangeRequestProcessorClient;
import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.caxchange.ResponseMessage;
import gov.nih.nci.coppa.iso.AddressPartType;
import gov.nih.nci.coppa.iso.Adxp;
import gov.nih.nci.coppa.iso.Enxp;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.client.CoreServicesClient;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.iso._21090.AD;
import org.iso._21090.CD;
import org.iso._21090.DSETTEL;
import org.springframework.context.MessageSource;

import com.semanticbits.coppa.infrastructure.service.RemoteResolver;

/**
 * The Class RemoteHealthcareSiteResolver.
 * @author Vinay Gangoli, Ramakrishna Gundala
 */
public class RemoteHealthcareSiteResolver implements RemoteResolver{

	/** The organization entity service remote. */
	private OrganizationEntityServiceRemote organizationEntityServiceRemote;
	
	/** The identified organization correlation service remote. */
	private IdentifiedOrganizationCorrelationServiceRemote identifiedOrganizationCorrelationServiceRemote;
	
	/** The message broadcaster. */
	private edu.duke.cabig.c3pr.esb.CCTSMessageBroadcaster coppaMessageBroadcaster;
	
	/** The exception helper. */
	protected C3PRExceptionHelper exceptionHelper;

	/** The c3pr error messages. */
	private MessageSource c3prErrorMessages;
	
	private Logger log = Logger.getLogger(RemoteHealthcareSiteResolver.class);
    
	
	/**
	 * Convert to organization.
	 * 
	 * @param organizationDto the organization dto
	 * 
	 * @return the list< object>
	 */
	
	
	public RemoteHealthcareSite populateRemoteOrganization(OrganizationDTO organizationDto){
		IdentifiedOrganizationDTO identifiedOrganizationDTO = null;
		try {
			identifiedOrganizationDTO = identifiedOrganizationCorrelationServiceRemote.getCorrelation(organizationDto.getIdentifier());
		} catch (Exception e) {
			log.error("identifiedOrganizationCorrelationServiceRemote.getCorrelation failed" +e);
		}
		RemoteHealthcareSite remoteHealthcareSite = new RemoteHealthcareSite();
		Iterator<Enxp> enxpItr = organizationDto.getName().getPart().iterator();
		Enxp next = enxpItr.next();
		List<Adxp> postalAddress = organizationDto.getPostalAddress().getPart();
		Iterator<Adxp> adxpItr = postalAddress.iterator();
		String city = "";
		String country = "";
		while(adxpItr.hasNext()){
			Adxp adXp = adxpItr.next();
			if(AddressPartType.CTY.equals(adXp.getType())){
				city = adXp.getValue();
			}

			if(AddressPartType.CNT.equals(adXp.getType())){
				country = adXp.getCode();
			}
		}
		
		Address remoteOrgAddress = new Address();

		remoteHealthcareSite.setName(next.getValue().toString());
		remoteOrgAddress.setCity(city);
		remoteOrgAddress.setCountryCode(country);	
		remoteHealthcareSite.setAddress(remoteOrgAddress);
		remoteHealthcareSite.setNciInstituteCode(identifiedOrganizationDTO.getAssignedId().getExtension());	
		remoteHealthcareSite.setExternalId(organizationDto.getIdentifier().getExtension());
		return remoteHealthcareSite;
	}
	
	
	/* (non-Javadoc)
	 * @see com.semanticbits.coppa.infrastructure.service.RemoteResolver#getRemoteEntityByUniqueId(java.lang.String)
	 */
	public Object getRemoteEntityByUniqueId(String externalId) {
		OrganizationDTO organizationDto = null;
		Ii ii = new Ii();
		ii.setExtension(externalId);
		try {
			organizationDto = organizationEntityServiceRemote.getOrganization(ii);
			return populateRemoteOrganization(organizationDto);
		} catch (Exception e) {
			log.error("getRemoteEntityByUniqueId failed" +e);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.semanticbits.coppa.infrastructure.service.RemoteResolver#find(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public List<Object> find(Object example) {	
		edu.duke.cabig.c3pr.domain.Organization remoteOrgExample = (RemoteHealthcareSite)example;
//		 build Organization DTO based on remoteOrgExample
		OrganizationDTO dtoExample = new OrganizationDTO();
		List<OrganizationDTO> organizationDTOs = organizationEntityServiceRemote.search(dtoExample);
		
		
		List<Object> remoteOrganizations = new ArrayList<Object>();
		for (OrganizationDTO organizationDTO:organizationDTOs) {
			edu.duke.cabig.c3pr.domain.Organization remoteOrganization = populateRemoteOrganization(organizationDTO);
			remoteOrganizations.add(remoteOrganization);
		}
		return remoteOrganizations;
	}
	
	/**
	 * De serialize the response xml from caXchange to get ResponseMessage which will
	 * get us the Coppa payload.
	 * 
	 * @param inputXMLString the input xml string
	 * @return the object
	 */
	public Object deSerializeFromCaXchange(String inputXMLString) {
		try {
			FileReader fr = getFileReaderForString(inputXMLString);
			InputStream wsddIs = CaXchangeRequestProcessorClient.class.getResourceAsStream("client-config.wsdd");
			Object deserializedObject = Utils.deserializeObject(fr,ResponseMessage.class, wsddIs);
			return deserializedObject;
		} catch (Exception e) {
			log.error("deSerializeFromCaXchange Failed" + e);
		}
		return null;
	}

	/**
	 * De serialize the coppa xml to get the coppa organization object.
	 * 
	 * @param inputXMLString the input xml string
	 * @return the object(gov.nih.nci.coppa.po.Organization)
	 */
	public Object deSerializeFromCoppa(String inputXMLString) {
		try {
			FileReader fr = getFileReaderForString(inputXMLString);
			InputStream wsddIs = getClass().getResourceAsStream("/gov/nih/nci/coppa/services/client/client-config.wsdd");
			Object deserializedObject = Utils.deserializeObject(fr, gov.nih.nci.coppa.po.Organization.class, wsddIs);
			return deserializedObject;
		} catch (Exception e) {
			log.error("deSerializeFromCoppa Failed" + e);
		}
		return null;
	}
	
	
	/**
	 * Gets the file reader for string.
	 * 
	 * @param inputXMLString the input xml string
	 * @return the file reader for string
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private FileReader getFileReaderForString(String inputXMLString) throws FileNotFoundException, IOException{
		File f = new File("response.xml");
		OutputStream out = new FileOutputStream(f);
		out.write(inputXMLString.getBytes());
		out.close();
		return new FileReader(f);
	}
	
	/**
	 * Serialize the Coppa org into xml.
	 * 
	 * @param org the coppa org
	 * @return the xml as string
	 */
	public String serialize(gov.nih.nci.coppa.po.Organization org) {
		if (org.getPostalAddress()== null){
			org.setPostalAddress(new AD());
		}
		if (org.getStatusCode()== null){
			org.setStatusCode(new CD());
		}
		if (org.getTelecomAddress()== null){
			org.setTelecomAddress(new DSETTEL());
		}
		QName idQname = new QName("http://po.coppa.nci.nih.gov", "Organization");
		StringWriter writer = new StringWriter();
		InputStream wsddIs = getClass().getResourceAsStream(
				"/gov/nih/nci/coppa/services/client/client-config.wsdd");
		try {
			Utils.serializeObject(org, idQname, writer, wsddIs);
			log.debug(writer.toString());
			return writer.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Broadcast organization search.
	 * 
	 * @param healthcareSiteXml the healthcare site xml
	 * @return the string
	 * @throws C3PRCodedException the c3 pr coded exception
	 */
	public String broadcastOrganizationSearch(String healthcareSiteXml) throws C3PRCodedException {
		try {
            //build metadata with operation name and the external Id and pass it to the broadcast method.
            Metadata mData = new Metadata(OperationNameEnum.search.getName(), "extId", ServiceTypeEnum.ORGANIZATION.getName());
            return coppaMessageBroadcaster.broadcastCoppaMessage(healthcareSiteXml, mData);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw this.exceptionHelper.getException(
                            getCode("C3PR.EXCEPTION.ORGANIZATION.SEARCH.BROADCAST.SEND_ERROR"), e);
        }
	}
	
	/**
	 * Broadcast organization search. overloaded method (used by test classes).
	 * 
	 * @param healthcareSiteXml the healthcare site xml
	 * @return the string
	 * @throws C3PRCodedException the c3 pr coded exception
	 */
	public String broadcastOrganizationSearch(CCTSMessageBroadcaster coppaMessageBroadcaster, String healthcareSiteXml) throws C3PRCodedException {
		try {
            //build metadata with operation name and the external Id and pass it to the broadcast method.
            Metadata mData = new Metadata(OperationNameEnum.search.getName(), "extId", ServiceTypeEnum.ORGANIZATION.getName());
            return coppaMessageBroadcaster.broadcastCoppaMessage(healthcareSiteXml, mData);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw this.exceptionHelper.getException(
                            getCode("C3PR.EXCEPTION.ORGANIZATION.SEARCH.BROADCAST.SEND_ERROR"), e);
        }
	}
	
	
	/**
	 * Sets the identified organization correlation service remote.
	 * 
	 * @param identifiedOrganizationCorrelationServiceRemote the new identified organization correlation service remote
	 */
	public void setIdentifiedOrganizationCorrelationServiceRemote(
			IdentifiedOrganizationCorrelationServiceRemote identifiedOrganizationCorrelationServiceRemote) {
		this.identifiedOrganizationCorrelationServiceRemote = identifiedOrganizationCorrelationServiceRemote;
	}

	/**
	 * Sets the organization entity service remote.
	 * 
	 * @param organizationEntityServiceRemote the new organization entity service remote
	 */
	public void setOrganizationEntityServiceRemote(
			OrganizationEntityServiceRemote organizationEntityServiceRemote) {
		this.organizationEntityServiceRemote = organizationEntityServiceRemote;
	}


    /**
     * Gets the error code which is used to retrieve the exception message.
     * 
     * @param errortypeString the errortype string
     * @return the code
     */
    public int getCode(String errortypeString) {
        return Integer.parseInt(this.c3prErrorMessages.getMessage(errortypeString, null, null));
    }
    
	public C3PRExceptionHelper getExceptionHelper() {
		return exceptionHelper;
	}


	public void setExceptionHelper(C3PRExceptionHelper exceptionHelper) {
		this.exceptionHelper = exceptionHelper;
	}


	public MessageSource getC3prErrorMessages() {
		return c3prErrorMessages;
	}


	public void setC3prErrorMessages(MessageSource errorMessages) {
		c3prErrorMessages = errorMessages;
	}


	public edu.duke.cabig.c3pr.esb.CCTSMessageBroadcaster getMessageBroadcaster() {
		return coppaMessageBroadcaster;
	}


	public void setMessageBroadcaster(
			edu.duke.cabig.c3pr.esb.CCTSMessageBroadcaster coppaMessageBroadcaster) {
		this.coppaMessageBroadcaster = coppaMessageBroadcaster;
	}

}



