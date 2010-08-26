package edu.duke.cabig.c3pr.web.participant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.domain.validator.ParticipantValidator;
import edu.duke.cabig.c3pr.service.PersonnelService;
import edu.duke.cabig.c3pr.utils.Lov;

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
	        // refdata.put("source", healthcareSiteDao.getAll());
	        refdata.put("searchTypeRefData", configMap.get("participantSearchType"));
	        refdata.put("identifiersTypeRefData", configMap.get("participantIdentifiersType"));
	        refdata.put("mandatory", "true");
	        
	        return refdata;
	}

    @Override
    public void validate(ParticipantWrapper participantWrapper, Errors errors) {
        super.validate(participantWrapper, errors);
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
    	if(command.getParticipant().getId() == null){
    		gov.nih.nci.security.authorization.domainobjects.User user = (gov.nih.nci.security.authorization.domainobjects.User) request
    		.getSession().getAttribute("userObject");
        	command.getParticipant().getHealthcareSites().addAll(personnelService.getUserOrganizations(user));
    	}
    }

}
