package edu.duke.cabig.c3pr.service.impl;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.acegisecurity.AccessDeniedException;
import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.domain.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.exception.StudyValidationException;
import edu.duke.cabig.c3pr.service.StudySubjectService;
import edu.duke.cabig.c3pr.service.StudySubjectXMLImporterService;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import gov.nih.nci.common.exception.XMLUtilityException;


/**
 * Utility class to import XML extracts of study
 *
 * Created by IntelliJ IDEA.
 * User: kherm
 * @author kherm manav.kher@semanticbits.com
 * Date: Jun 4, 2007
 * Time: 1:18:10 PM
 * To change this template use File | Settings | File Templates.
 */

public class StudySubjectXMLImporterServiceImpl implements StudySubjectXMLImporterService {

    private StudySubjectDao studySubjectDao;
	private C3PRExceptionHelper exceptionHelper;
	private MessageSource c3prErrorMessages;
	private ParticipantDao participantDao;
    private StudySubjectService studySubjectService;
    private XmlMarshaller marshaller;
    private  Logger log = Logger.getLogger(StudySubjectXMLImporterServiceImpl.class.getName());

    /**
     * Will parse an xml stream and create 1..many study subjects
     * XML should have one or many registration elements
     * <registration>
     * //registration serialization
     * </registration>
     *
     * Container to the <registration/> element is not important
     * @param xmlStream
     * @return
     * @throws Exception
     */
    @Transactional
    public List<StudySubject> importStudySubjects(InputStream xmlStream) throws C3PRCodedException {
        List<StudySubject> studySubjectList = null;
        try {
            studySubjectList = new ArrayList<StudySubject>();
            Document studyDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlStream);
            NodeList studySubjects = studyDoc.getElementsByTagName("registration");

            for(int i=0;i<studySubjects.getLength(); i++)
            {
                Node studySubjectNode = studySubjects.item(i);
                if(studySubjectNode.getNodeType()  == Node.ELEMENT_NODE){
                    StringWriter sw = new StringWriter();
                    StreamResult sr = new StreamResult( sw );
                    TransformerFactory.newInstance().newTransformer().transform(new DOMSource(studySubjectNode),sr);
                    try {
                        StudySubject studySubject = importStudySubject(sr.getWriter().toString());
                        //once saved retreive persisted study
                        studySubjectList.add(studySubject);
                    } catch(AccessDeniedException e){
                    	throw this.exceptionHelper.getException(getCode("C3PR.EXCEPTION.REGISTRATION.IMPORT.ACCESSDENIED"),e);
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.debug(e.getMessage());
                    }
                }

            }
        }catch (C3PRCodedException e) {
        	throw e;
        }catch (Exception e) {
        	throw this.exceptionHelper.getException(getCode("C3PR.EXCEPTION.REGISTRATION.IMPORT.ERROR_UNMARSHALLING"),e);
        }
        return studySubjectList;
    }
    
    public StudySubject importStudySubject(String registrationXml) throws C3PRCodedException {
    	StudySubject studySubject=null;
		try {
			studySubject = (StudySubject)marshaller.fromXML(new StringReader(registrationXml));
		} catch (XMLUtilityException e) {
			throw this.exceptionHelper.getException(getCode("C3PR.EXCEPTION.REGISTRATION.IMPORT.ERROR_UNMARSHALLING"),e);
		}
        this.validate(studySubject);
        return importStudySubject(studySubject);
    }

    public StudySubject importStudySubject(StudySubject deserialedStudySubject) throws C3PRCodedException{
    	StudySubject studySubject=studySubjectService.buildStudySubject(deserialedStudySubject);
        if (studySubject.getParticipant().getId() != null) {
            StudySubject exampleSS = new StudySubject(true);
            exampleSS.setParticipant(studySubject.getParticipant());
            exampleSS.setStudySite(studySubject.getStudySite());
            List<StudySubject> registrations = studySubjectDao.searchBySubjectAndStudySite(exampleSS);
            if (registrations.size() > 0) {
                throw this.exceptionHelper.getException(getCode("C3PR.EXCEPTION.REGISTRATION.STUDYSUBJECTS_ALREADY_EXISTS.CODE"));
            }
        } else {
        	if(validateParticipant(studySubject.getParticipant()))
        		participantDao.save(studySubject.getParticipant());
        	else{
        		throw this.exceptionHelper.getException(getCode("C3PR.EXCEPTION.REGISTRATION.SUBJECTS_INVALID_DETAILS.CODE"));
        	}
        }
    	/*if(studySubject.getScheduledEpoch().getRequiresRandomization()){
            throw this.exceptionHelper.getException(getCode("C3PR.EXCEPTION.REGISTRATION.IMPORT.RANDOMIZEDSTUDY.ARM_PROVIDED.CODE"));
    	}*/
        if(studySubject.getScheduledEpoch().getEpoch().getRequiresArm()){
        	ScheduledTreatmentEpoch scheduledTreatmentEpoch=(ScheduledTreatmentEpoch)studySubject.getScheduledEpoch();
        	if(scheduledTreatmentEpoch.getScheduledArm()==null || 
        			scheduledTreatmentEpoch.getScheduledArm().getArm()==null ||
        			scheduledTreatmentEpoch.getScheduledArm().getArm().getId()==null)
        	throw this.exceptionHelper.getException(getCode("C3PR.EXCEPTION.REGISTRATION.IMPORT.REQUIRED.ARM.NOTFOUND.CODE"));
        }
		studySubject.setRegDataEntryStatus(studySubjectService.evaluateRegistrationDataEntryStatus(studySubject));
		studySubject.getScheduledEpoch().setScEpochDataEntryStatus(studySubjectService.evaluateScheduledEpochDataEntryStatus(studySubject));
		if(studySubject.getRegDataEntryStatus()==RegistrationDataEntryStatus.INCOMPLETE){
			throw this.exceptionHelper.getException(getCode("C3PR.EXCEPTION.REGISTRATION.DATA_ENTRY_INCOMPLETE.CODE"));
		}
		if(studySubject.getScheduledEpoch().getScEpochDataEntryStatus()==ScheduledEpochDataEntryStatus.INCOMPLETE){
			throw this.exceptionHelper.getException(getCode("C3PR.EXCEPTION.REGISTRATION.SCHEDULEDEPOCH.DATA_ENTRY_INCOMPLETE.CODE"));
		}
		if(studySubject.getScheduledEpoch().isReserving()){
			studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.RESERVED);
		}else if(studySubject.getScheduledEpoch().getEpoch().isEnrolling()){
			studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.REGISTERED);
		}else{
			studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.UNREGISTERED);
		}
		studySubjectDao.save(studySubject);
        log.debug("Registration saved with grid ID" + studySubject.getGridId());
        return studySubject;
    }

    public boolean validateParticipant(Participant participant){
    	if(StringUtils.getBlankIfNull(participant.getFirstName()).equals("")
    			|| StringUtils.getBlankIfNull(participant.getLastName()).equals("")
    			|| participant.getBirthDate()==null
    			|| StringUtils.getBlankIfNull(participant.getAdministrativeGenderCode()).equals("")
    			|| !(participant.getAdministrativeGenderCode().equalsIgnoreCase("Male")||participant.getAdministrativeGenderCode().equalsIgnoreCase("Female")))
    		return false;
    	return true;
    }
    /**
     * Validate a study against a set of validation rules
     * @param study
     * @throws StudyValidationException
     */
    public void validate(StudySubject studySubject) throws C3PRCodedException {
        
    }


//setters for spring

    public XmlMarshaller getMarshaller() {
        return marshaller;
    }

    public void setMarshaller(XmlMarshaller marshaller) {
        this.marshaller = marshaller;
    }


	public void setC3prErrorMessages(MessageSource errorMessages) {
		c3prErrorMessages = errorMessages;
	}

	public void setExceptionHelper(C3PRExceptionHelper exceptionHelper) {
		this.exceptionHelper = exceptionHelper;
	}
	private int getCode(String errortypeString){
		return Integer.parseInt(this.c3prErrorMessages.getMessage(errortypeString, null, null));
	}

	public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
		this.studySubjectDao = studySubjectDao;
	}


	public void setStudySubjectService(StudySubjectService studySubjectService) {
		this.studySubjectService = studySubjectService;
	}

	public void setParticipantDao(ParticipantDao participantDao) {
		this.participantDao = participantDao;
	}
}
