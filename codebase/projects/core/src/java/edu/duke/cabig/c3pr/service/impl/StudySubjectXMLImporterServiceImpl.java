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
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.RandomizationType;
import edu.duke.cabig.c3pr.domain.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.domain.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledNonTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.SiteStudyStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.TreatmentEpoch;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.exception.StudyValidationException;
import edu.duke.cabig.c3pr.service.ParticipantService;
import edu.duke.cabig.c3pr.service.StudyService;
import edu.duke.cabig.c3pr.service.StudySubjectService;
import edu.duke.cabig.c3pr.service.StudySubjectXMLImporterService;
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

    private HealthcareSiteDao healthcareSiteDao;
    private StudySubjectDao studySubjectDao;
	private C3PRExceptionHelper exceptionHelper;
	private MessageSource c3prErrorMessages;
	private ParticipantDao participantDao;
    private final String identifierTypeValueStr = "Coordinating Center Identifier";
    private StudyService studyService;
    private final String prtIdentifierTypeValueStr = "MRN";
    private ParticipantService participantService;
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
        } catch (Exception e) {
            throw new C3PRBaseRuntimeException("Could not import study",e);
        }
        return studySubjectList;
    }
    
    public StudySubject importStudySubject(String registrationXml) throws C3PRCodedException {
    	StudySubject studySubject=null;
		try {
			studySubject = (StudySubject)marshaller.fromXML(new StringReader(registrationXml));
		} catch (XMLUtilityException e) {
			throw this.exceptionHelper.getException(getCode("C3PR.EXCEPTION.REGISTRATION.IMPORT.ERROR_MARSHALLING"),e);
		}
        this.validate(studySubject);
        log.debug("Saving study subject with grid ID" + studySubject.getGridId());
        return importStudySubject(studySubject);
    }

    public StudySubject importStudySubject(StudySubject deserialedStudySubject) throws C3PRCodedException{
    	StudySubject studySubject=buildStudySubject(deserialedStudySubject);
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
        log.debug("Study saved with grid ID" + studySubject.getGridId());
        return studySubject;
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


    public HealthcareSiteDao getHealthcareSiteDao() {
        return healthcareSiteDao;
    }

    public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
        this.healthcareSiteDao = healthcareSiteDao;
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

	private StudySubject buildStudySubject(StudySubject studySubject) throws C3PRCodedException{
		StudySubject built=new StudySubject();
		Participant participant=buildParticipant(studySubject.getParticipant());
		StudySite studySite=buildStudySite(studySubject.getStudySite(),buildStudy(studySubject.getStudySite().getStudy()));
        if (participant.getId() != null) {
            StudySubject exampleSS = new StudySubject(true);
            exampleSS.setParticipant(participant);
            exampleSS.setStudySite(studySite);
            List<StudySubject> registrations = studySubjectDao.searchBySubjectAndStudySite(exampleSS);
            if (registrations.size() > 0) {
                throw this.exceptionHelper.getException(getCode("C3PR.EXCEPTION.REGISTRATION.IMPORT.STUDYSUBJECTS_ALREADY_EXISTS.CODE"));
            }
        } else {
            participantDao.save(participant);
        }
        built.setStudySite(studySite);
        built.setParticipant(participant);
        Epoch epoch = buildEpoch(studySite.getStudy().getEpochs(), studySubject.getScheduledEpoch());
        ScheduledEpoch scheduledEpoch=buildScheduledEpoch(studySubject.getScheduledEpoch(), epoch);
        built.getScheduledEpochs().add(0, scheduledEpoch);
        fillStudySubjectDetails(built, studySubject);
        return built;
	}

	public void setParticipantDao(ParticipantDao participantDao) {
		this.participantDao = participantDao;
	}
	
	private Participant buildParticipant(Participant participant)throws C3PRCodedException{
		if (participant.getIdentifiers()==null || participant.getIdentifiers().size() == 0) {
            throw this.exceptionHelper.getException(getCode("C3PR.EXCEPTION.REGISTRATION.IMPORT.MISSING.SUBJECT_IDENTIFIER.CODE"));
        }
        for (OrganizationAssignedIdentifier organizationAssignedIdentifier: participant.getOrganizationAssignedIdentifiers()) {
            if (organizationAssignedIdentifier.getType().equals(this.prtIdentifierTypeValueStr)) {
                List<Participant> paList = participantService.searchByMRN(organizationAssignedIdentifier);
                if (paList.size() > 1) {
                    throw this.exceptionHelper.getException(getCode("C3PR.EXCEPTION.REGISTRATION.IMPORT.MULTIPLE.SUBJECTS_SAME_MRN.CODE")
                            ,new String[]{organizationAssignedIdentifier.getValue()});
                }else if (paList.size() == 1) {
                    System.out.println("Participant with the same MRN found in the database");
                    Participant temp = paList.get(0);
                    if (temp.getFirstName().equals(participant.getFirstName())
                            && temp.getLastName().equals(participant.getLastName())
                            && temp.getBirthDate().getTime()==participant.getBirthDate().getTime()) {
                        return temp;
                    }
                }
            }
        }
        return participant;
	}
	
	private Study buildStudy(Study study) throws C3PRCodedException{
        if(study.getIdentifiers()==null){
            throw this.exceptionHelper.getException(getCode("C3PR.EXCEPTION.REGISTRATION.IMPORT.MISSING.STUDY_IDENTIFIER.CODE"));
        }
        List<Study> studies = null;
        OrganizationAssignedIdentifier identifier=null;
        for (OrganizationAssignedIdentifier organizationAssignedIdentifier: study.getOrganizationAssignedIdentifiers()) {
            if (organizationAssignedIdentifier.getType().equals(this.identifierTypeValueStr)) {
            	identifier=organizationAssignedIdentifier;
            	studies=studyService.searchByCoOrdinatingCenterId(organizationAssignedIdentifier);
                break;
            }
        }
        if(studies==null){
            throw this.exceptionHelper.getException(getCode("C3PR.EXCEPTION.REGISTRATION.IMPORT.MISSING.STUDY_IDENTIFIER.CODE"));
        }
        if (studies.size() == 0) {
            throw this.exceptionHelper.getException(getCode("C3PR.EXCEPTION.REGISTRATION.IMPORT.NOTFOUND.STUDY_WITH_IDENTIFIER.CODE")
                    ,new String[]{identifier.getHealthcareSite().getNciInstituteCode(),this.identifierTypeValueStr});
        }
        if (studies.size() > 1) {
            throw this.exceptionHelper.getException(getCode("C3PR.EXCEPTION.REGISTRATION.IMPORT.MULTIPLE.STUDY_SAME_CO_IDENTIFIER.CODE")
                    ,new String[]{identifier.getHealthcareSite().getNciInstituteCode(),this.identifierTypeValueStr});
        }
        if(studies.get(0).getCoordinatingCenterStudyStatus()!=CoordinatingCenterStudyStatus.ACTIVE){
        	throw this.exceptionHelper.getException(getCode("C3PR.EXCEPTION.REGISTRATION.IMPORT.STUDY_NOT_ACTIVE")
                    ,new String[]{identifier.getHealthcareSite().getNciInstituteCode(),this.identifierTypeValueStr});
        }
        return studies.get(0);
	}

	private StudySite buildStudySite(StudySite studySite, Study study) throws C3PRCodedException{
		for (StudySite temp : study.getStudySites()) {
            if (temp.getHealthcareSite().getNciInstituteCode().equals(studySite.getHealthcareSite().getNciInstituteCode())) {
            	if(temp.getSiteStudyStatus()!=SiteStudyStatus.ACTIVE){
            		throw this.exceptionHelper.getException(getCode("C3PR.EXCEPTION.REGISTRATION.IMPORT.STUDYSITE_NOT_ACTIVE")
                            ,new String[]{temp.getHealthcareSite().getNciInstituteCode()});
            	}
                return temp;
            }
        }
        throw this.exceptionHelper.getException(getCode("C3PR.EXCEPTION.REGISTRATION.IMPORT.NOTFOUND.STUDYSITE_WITH_NCICODE.CODE")
                ,new String[]{studySite.getHealthcareSite().getNciInstituteCode()});
	}
	
	private Epoch buildEpoch(List<Epoch> epochs,ScheduledEpoch scheduledEpoch)throws C3PRCodedException{
		for (Epoch epochCurr : epochs) {
            if (epochCurr.getName().equalsIgnoreCase(scheduledEpoch.getEpoch().getName())) {
                return epochCurr;
            }
        }
        throw this.exceptionHelper.getException(getCode("C3PR.EXCEPTION.REGISTRATION.IMPORT.NOTFOUND.EPOCH_NAME.CODE")
                ,new String[]{scheduledEpoch.getEpoch().getName()});
	}
	
	private ScheduledEpoch buildScheduledEpoch(ScheduledEpoch source, Epoch epoch) throws C3PRCodedException{
		ScheduledEpoch scheduledEpoch=null;
        if (epoch instanceof TreatmentEpoch) {
            ScheduledTreatmentEpoch scheduledTreatmentEpochSource=(ScheduledTreatmentEpoch) source;
            scheduledEpoch=new ScheduledTreatmentEpoch();
            ScheduledTreatmentEpoch scheduledTreatmentEpoch=(ScheduledTreatmentEpoch) scheduledEpoch;
//            scheduledTreatmentEpoch.setEligibilityIndicator(scheduledTreatmentEpochSource.getEligibilityIndicator());
            scheduledTreatmentEpoch.setEligibilityIndicator(true);
            if(epoch.getRequiresArm()){
	            if(scheduledTreatmentEpochSource.getScheduledArm()!=null
	                    &&scheduledTreatmentEpochSource.getScheduledArm().getArm()!=null
	                    &&scheduledTreatmentEpochSource.getScheduledArm().getArm().getName()!=null){
	/*            	if(epoch.getRequiresRandomization()){
	                    throw this.exceptionHelper.getException(getCode("C3PR.EXCEPTION.REGISTRATION.IMPORT.RANDOMIZEDSTUDY.ARM_PROVIDED.CODE"));
	            	}
	*/                Arm arm=null;
	                for(Arm a: ((TreatmentEpoch)epoch).getArms()){
	                    if(a.getName().equals(scheduledTreatmentEpochSource.getScheduledArm().getArm().getName())){
	                        arm=a;
	                    }
	                }
	                if(arm==null){
	                    throw this.exceptionHelper.getException(getCode("C3PR.EXCEPTION.REGISTRATION.IMPORT.NOTFOUND.ARM_NAME.CODE")
	                            ,new String[]{scheduledTreatmentEpochSource.getScheduledArm().getArm().getName(),scheduledTreatmentEpochSource.getEpoch().getName()});
	                }
	                scheduledTreatmentEpoch.getScheduledArms().get(0).setArm(arm);
	
	            }else{
                    throw this.exceptionHelper.getException(getCode("C3PR.EXCEPTION.REGISTRATION.IMPORT.NOTFOUND.ARM.CODE"));
	            }
            }
        } else {
        	scheduledEpoch=new ScheduledNonTreatmentEpoch();
        }
        scheduledEpoch.setEpoch(epoch);
        scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.APPROVED);
        return scheduledEpoch;

	}
	private void fillStudySubjectDetails(StudySubject studySubject, StudySubject source) {
		studySubject.setInformedConsentSignedDate(source.getInformedConsentSignedDate());
		studySubject.setInformedConsentVersion(source.getInformedConsentVersion());
		studySubject.setStartDate(source.getStartDate());
		studySubject.setStratumGroupNumber(source.getStratumGroupNumber());
		studySubject.getIdentifiers().addAll(source.getIdentifiers());
	}
	public void setParticipantService(ParticipantService participantService) {
		this.participantService = participantService;
	}

	public void setStudyService(StudyService studyService) {
		this.studyService = studyService;
	}

	public void setStudySubjectService(StudySubjectService studySubjectService) {
		this.studySubjectService = studySubjectService;
	}
}
