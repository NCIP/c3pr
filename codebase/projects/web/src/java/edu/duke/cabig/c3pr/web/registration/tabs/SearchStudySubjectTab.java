/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.constants.FamilialRelationshipName;
import edu.duke.cabig.c3pr.constants.RaceCodeEnum;
import edu.duke.cabig.c3pr.constants.RelationshipCategory;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudySiteStudyVersionDao;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySiteStudyVersion;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.validator.ParticipantValidator;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.web.WebUtils;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AjaxableUtils;
import edu.duke.cabig.c3pr.web.registration.StudySubjectWrapper;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To change this template
 * use File | Settings | File Templates.
 */
public class SearchStudySubjectTab extends RegistrationTab<StudySubjectWrapper> {

    private static final Logger logger = Logger.getLogger(SearchStudySubjectTab.class);
    
    private StudyDao studyDao;
    
    private ParticipantDao participantDao;
    
    private ParticipantValidator participantValidator;
    
    public void setParticipantValidator(ParticipantValidator participantValidator) {
		this.participantValidator = participantValidator;
	}

	public void setParticipantDao(ParticipantDao participantDao) {
		this.participantDao = participantDao;
	}

	private StudySiteStudyVersionDao studySiteStudyVersionDao;

    public void setStudySiteStudyVersionDao(
			StudySiteStudyVersionDao studySiteStudyVersionDao) {
		this.studySiteStudyVersionDao = studySiteStudyVersionDao;
	}

	public void setStudyDao(StudyDao studyDao) {
		this.studyDao = studyDao;
	}

	public SearchStudySubjectTab() {
        super("Subject & Study", "Subject & Study",
                        "registration/select_study_or_subject");
        setShowSummary("false");
    }
   
    @Override
    public Map referenceData(HttpServletRequest request,
    		StudySubjectWrapper command) {
    	Map refdata=super.referenceData(request, command);
    	Map<String, List<Lov>> configMap = configurationProperty.getMap();
        refdata.put("searchTypeRefDataPrt", configMap.get("participantSearchType"));
        refdata.put("searchTypeRefDataStudy", configMap.get("studySearchTypeForRegistration"));
        refdata.put("administrativeGenderCode", configMap.get("administrativeGenderCode"));
        refdata.put("ethnicGroupCode", configMap.get("ethnicGroupCode"));
        refdata.put("raceCodes", WebUtils.collectOptions(RaceCodeEnum.values()));
        
        List<Lov> sysParticipantIdentifiersType = new ArrayList<Lov>();
        // remove Household Identifier type from sysIdentifiers as it should be included only as a part of organization identifiers
        for(Lov partOrgIdType : configMap.get("participantIdentifiersType")){
        	if(!partOrgIdType.getCode().equalsIgnoreCase("HOUSEHOLD_IDENTIFIER")){
        		sysParticipantIdentifiersType.add(partOrgIdType);
        	}
        }
        
        refdata.put("orgIdentifiersTypeRefData", configMap.get("participantIdentifiersType"));
        refdata.put("sysIdentifiersTypeRefData", sysParticipantIdentifiersType);
        
        List<Lov> familialRelationshipNames = new ArrayList<Lov>();
        Lov lov = new Lov();
        for(FamilialRelationshipName familialRelationship : FamilialRelationshipName.values()){
        	lov = new Lov();
        	lov.setCode(familialRelationship.getName());
        	lov.setDesc(familialRelationship.getCode());
        	familialRelationshipNames.add(lov);
        }
        refdata.put("familialRelationshipNames",familialRelationshipNames);
        
        
        if (command.getStudySubject().getSystemAssignedIdentifiers()!= null && command.getStudySubject().getSystemAssignedIdentifiers().size()>0) {
            refdata.put("disableForm", new Boolean(true));
        }
        refdata.put("mandatory", "true"); 
        
        if(request.getParameter("from_reg_confirmation")!=null && request.getParameter("from_reg_confirmation").equals("true")){
        	Study study = null;
        	
        	refdata.put("from_reg_confirmation", request.getParameter("from_reg_confirmation"));
        	
	        if(request.getParameter("create_studyId")!= null){
	        	study = studyDao.getById(Integer.parseInt(request.getParameter("create_studyId")));
	        	refdata.put("create_studyId", request.getParameter("create_studyId"));
	        	refdata.put("create_study_name", study.getShortTitleText());
	        	refdata.put("create_study_identifier", study.getPrimaryIdentifier());
	        }
	        if(request.getParameter("create_studySiteName")!= null){
	        	refdata.put("create_studySiteName", request.getParameter("create_studySiteName"));
	        }
	        if(request.getParameter("create_studySiteStudyVersionId")!= null){
	        	refdata.put("create_studySiteStudyVersionId", request.getParameter("create_studySiteStudyVersionId"));
	        }
        }
        
        if(request.getParameter("fromStudyRegistrations")!=null && request.getParameter("fromStudyRegistrations").equals("true")){
        	refdata.put("fromStudyRegistrations", request.getParameter("fromStudyRegistrations"));
	        if(request.getParameter("createRegistration_studyId")!= null){
	        	refdata.put("createRegistration_studyId", request.getParameter("createRegistration_studyId"));
	        }
	        
        }
        
        if (request.getParameter("fromEditParticipant") != null && request.getParameter("fromEditParticipant").equals("true")) { 
			if (request.getSession().getAttribute("fromCreateRegistration") != null) {
				refdata.put("fromUpdateParticipant", request.getSession()
						.getAttribute("fromCreateRegistration"));
				request.getSession().removeAttribute("fromCreateRegistration");
			}
			if (request.getSession().getAttribute(
					"studySiteStudyVersionIdFromCreateReg") != null
					&& !request.getSession().getAttribute(
							"studySiteStudyVersionIdFromCreateReg").equals("")) {
				String StudySiteStudyVersionId = (String) request.getSession().getAttribute("studySiteStudyVersionIdFromCreateReg");
				StudySiteStudyVersion studySiteStudyVersion = studySiteStudyVersionDao.getById(Integer.parseInt(StudySiteStudyVersionId));
				refdata.put("studyName", studySiteStudyVersion.getStudyVersion().getShortTitleText());
				refdata.put("siteName", studySiteStudyVersion.getStudySite().getHealthcareSite().getName());
				refdata.put("studyPrimaryIdentifier", studySiteStudyVersion.getStudyVersion().getStudy().getPrimaryIdentifier());
				refdata.put("studySiteStudyVersionIdFromUpdateParticipant",StudySiteStudyVersionId);
				request.getSession().removeAttribute("studySiteStudyVersionIdFromCreateReg");
			} else if (request.getSession().getAttribute("searchedForStudy") != null && request.getSession().getAttribute("searchedForStudy")
							.equals("true")) {
				refdata.put("searchedForStudy", request.getSession().getAttribute("searchedForStudy"));
				request.getSession().removeAttribute("searchedForStudy");
				refdata.put("studySearchType", request.getSession().getAttribute("studySearchType"));
				request.getSession().removeAttribute("studySearchType");
				refdata.put("studySearchText", request.getSession().getAttribute("studySearchText"));
				request.getSession().removeAttribute("studySearchText");

			}
			if (request.getParameter("participantId") != null) {
				refdata.put("participantId", request.getParameter("participantId"));
				Participant participant = participantDao.getById(Integer.parseInt(request.getParameter("participantId")));
				refdata.put("participantName", participant.getLastName() + ""+ participant.getFirstName());
				refdata.put("participantIdentifier", participant.getOrganizationAssignedIdentifiers().get(0).getType().getCode()+ " - "	
						+ participant.getOrganizationAssignedIdentifiers().get(0).getValue());
			}
		}
		return refdata;
    }
    
    @Override
    public void postProcess(HttpServletRequest request, StudySubjectWrapper command, Errors errors) {
    	
    	StudySubject studySubject = ((StudySubjectWrapper)command).getStudySubject();
    	
    	command.setParticipant(studySubject.getParticipant());
    	
    	if(WebUtils.hasSubmitParameter(request, "studySiteStudyVersionId") && !StringUtils.isBlank(request.getParameter("studySiteStudyVersionId"))){
    		StudySiteStudyVersion studySiteStudyVersion = studySiteStudyVersionDao.getById(Integer.parseInt(request.getParameter("studySiteStudyVersionId")));
    		if(studySiteStudyVersion == null){
    			logger.debug("Study Site Study Version is not set to Study Subject Study Version");
    		}
    		studySubject.getStudySubjectStudyVersion().setStudySiteStudyVersion(studySiteStudyVersion);
    		studySubject.setStudySite(studySiteStudyVersion.getStudySite());
    	}else{
    		logger.debug("Study Site Study Version is not set to Study Subject Study Version");
    	} 
    	if(studySubject.getScheduledEpoch()== null && WebUtils.hasSubmitParameter(request, "epoch") && !StringUtils.isBlank(request.getParameter("epoch"))){
        	Epoch epoch = epochDao.getById(Integer.parseInt(request.getParameter("epoch")));
        	ScheduledEpoch scheduledEpoch = new ScheduledEpoch();
        	scheduledEpoch.setEpoch(epoch);
        	studySubject.addScheduledEpoch(scheduledEpoch);
        }
    	
    	
		if(studySubject.getId()==null){
    			List<StudySubject> studySubjects = new ArrayList<StudySubject>();
    			studySubjects=studySubjectRepository.findRegistrations(studySubject);
    			if (studySubjects.size() > 0) {
    				errors.reject("duplicateRegistration","Subject already registered on this study");
    	        }
		}
		studySubjectDao.initialize(studySubject);
		if(!errors.hasErrors()){
			registrationControllerUtils.buildCommandObject(command.getStudySubject());
			registrationControllerUtils.addConsents(command.getStudySubject());
		}
    }

    @Override
	public void validate(StudySubjectWrapper command, Errors errors) {
		super.validate(command, errors);
		participantValidator.validateParticipantFamilialRelationships(command.getStudySubject().getParticipant(), errors);
		participantValidator.validateIdentifiers(command.getStudySubject().getParticipant(), errors);
	}

	public ModelAndView checkEpochAccrualCeiling(HttpServletRequest request, Object commandObj,
                    Errors error) {
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        int id = Integer.parseInt(request.getParameter("epochId"));
        map.put("alertForCeiling",
                        new Boolean(studySubjectRepository.isEpochAccrualCeilingReached(id)));
        return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
    }
}
