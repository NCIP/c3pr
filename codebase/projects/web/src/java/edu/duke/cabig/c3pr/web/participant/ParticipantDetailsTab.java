/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.participant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.constants.FamilialRelationshipName;
import edu.duke.cabig.c3pr.constants.RaceCodeEnum;
import edu.duke.cabig.c3pr.constants.RelationshipCategory;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.RaceCodeAssociation;
import edu.duke.cabig.c3pr.domain.validator.ParticipantValidator;
import edu.duke.cabig.c3pr.service.PersonnelService;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.web.WebUtils;
import edu.duke.cabig.c3pr.web.RaceCodeHolder;

public class ParticipantDetailsTab extends ParticipantTab {

    private ParticipantValidator participantValidator;
    
    private PersonnelService personnelService;
    
	public void setPersonnelService(PersonnelService personnelService) {
		this.personnelService = personnelService;
	}

	public ParticipantDetailsTab() {
        super("Details", "Details", "participant/participant");
        setShowSummary("false");
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> referenceData(HttpServletRequest request, ParticipantWrapper command) {
		
		if(request.getParameter("fromCreateRegistration")!=null && request.getParameter("fromCreateRegistration").equals("true")){
			request.getSession().setAttribute("fromCreateRegistration", true);
		
			if(request.getParameter("studySiteStudyVersionIdFromCreateReg")!=null && !request.getParameter("studySiteStudyVersionIdFromCreateReg").equals("")){
				request.getSession().setAttribute("studySiteStudyVersionIdFromCreateReg", request.getParameter("studySiteStudyVersionIdFromCreateReg"));
			} else if(request.getParameter("searchedForStudy").equals("true")){
				request.getSession().setAttribute("searchedForStudy", request.getParameter("searchedForStudy"));
				request.getSession().setAttribute("studySearchType", request.getParameter("studySearchType"));
				request.getSession().setAttribute("studySearchText", request.getParameter("studySearchText"));
			}
		}
		
		 Map<String, List<Lov>> configMap = configurationProperty.getMap();

	        Map<String, Object> refdata = new HashMap<String, Object>();

	        refdata.put("administrativeGenderCode", configMap.get("administrativeGenderCode"));
	        refdata.put("ethnicGroupCode", configMap.get("ethnicGroupCode"));
	        refdata.put("searchTypeRefData", configMap.get("participantSearchType"));
	        
	        List<Lov> sysParticipantIdentifiersType = new ArrayList<Lov>();
	        
	        // remove Household Identifier type from sysIdentifiers as it should be included only as a part of organization identifiers
	        for(Lov partOrgIdType : configMap.get("participantIdentifiersType")){
	        	if(!partOrgIdType.getCode().equalsIgnoreCase("HOUSEHOLD_IDENTIFIER")){
	        		sysParticipantIdentifiersType.add(partOrgIdType);
	        	}
	        }
	        
	        refdata.put("orgIdentifiersTypeRefData", configMap.get("participantIdentifiersType"));
	        refdata.put("sysIdentifiersTypeRefData", sysParticipantIdentifiersType);
	        refdata.put("mandatory", "true");
	        refdata.put("raceCodes", WebUtils.collectOptions(RaceCodeEnum.values()));
	        
	        Map<String,Object> familialRelationshipNames = new HashMap<String,Object>();
	        for(FamilialRelationshipName familialRelationshipName : FamilialRelationshipName.values()){
	        	familialRelationshipNames.put(familialRelationshipName.getName(), familialRelationshipName.getCode());
	        }
	        refdata.put("familialRelationshipNames",familialRelationshipNames);
	        
	        
	        Map<String,Object> relationshipCategories = new HashMap<String,Object>();
	        for(RelationshipCategory relationshipCategory : RelationshipCategory.values()){
	        	relationshipCategories.put(relationshipCategory.getName(), relationshipCategory.getCode());
	        }
	        refdata.put("relationshipCategories",relationshipCategories);


	        Participant participant = command.getParticipant();
	        for(Object object : WebUtils.collectOptions(RaceCodeEnum.values())){
	        	RaceCodeEnum raceCode = (RaceCodeEnum) object ;
	        	RaceCodeHolder holder = new RaceCodeHolder();
	        	if(participant.getRaceCodes().contains(raceCode)){
	        		holder.setRaceCode(raceCode);
	        	}else{
	        		holder.setRaceCode(null);
	        	}
	        	command.addRaceCodeHolder(holder);
	        }
	        return refdata;
	}

    @Override
    public void validate(ParticipantWrapper participantWrapper, Errors errors) {
        super.validate(participantWrapper, errors);
        participantValidator.validateParticipantFamilialRelationships(participantWrapper.getParticipant(), errors);
        participantValidator.validateIdentifiers(participantWrapper.getParticipant(), errors);
    }

    public ParticipantValidator getParticipantValidator() {
        return participantValidator;
    }

    public void setParticipantValidator(ParticipantValidator participantValidator) {
        this.participantValidator = participantValidator;
    }
    
    @Override
    public void postProcess(HttpServletRequest request, ParticipantWrapper command, Errors errors) {
    	Participant participant = command.getParticipant();
    	for(RaceCodeEnum raceCode : command.getRaceCodesFromHolder()){
    		if(!participant.getRaceCodes().contains(raceCode)){
    			RaceCodeAssociation raceCodeAssociation = new RaceCodeAssociation();
				raceCodeAssociation.setRaceCode(raceCode);
				participant.addRaceCodeAssociation(raceCodeAssociation);
    		}
    	}
    	for(RaceCodeEnum raceCode : participant.getRaceCodes()){
    		if(!command.getRaceCodesFromHolder().contains(raceCode)){
    			participant.removeRaceCodeAssociation(participant.getRaceCodeAssociation(raceCode));
    		}
    	}
    	if(command.getParticipant().getId() == null){
    		gov.nih.nci.security.authorization.domainobjects.User user = (gov.nih.nci.security.authorization.domainobjects.User) request
    		.getSession().getAttribute("userObject");
        	command.getParticipant().getHealthcareSites().addAll(personnelService.getUserOrganizations(user));
    	}
    }

}
