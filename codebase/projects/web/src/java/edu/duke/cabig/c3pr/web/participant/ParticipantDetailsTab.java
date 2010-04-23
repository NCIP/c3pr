package edu.duke.cabig.c3pr.web.participant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.validator.ParticipantValidator;
import edu.duke.cabig.c3pr.utils.Lov;

public class ParticipantDetailsTab extends ParticipantTab {

    private ParticipantValidator participantValidator;

    public ParticipantDetailsTab() {
        super("Details", "Details", "participant/participant");
        setShowSummary("false");
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> referenceData(HttpServletRequest request, Participant command) {
		
		if(request.getParameter("fromCreateRegistration")!=null && request.getParameter("fromCreateRegistration").equals("true")){
			request.getSession().setAttribute("fromCreateRegistration", true);
		}
		
		if(request.getParameter("studySiteStudyVersionIdFromCreateReg")!=null){
			request.getSession().setAttribute("studySiteStudyVersionIdFromCreateReg", request.getParameter("studySiteStudyVersionIdFromCreateReg"));
		}
		request.getSession().getAttribute("studySiteStudyVersionIdFromCreateReg");
		
		 Map<String, List<Lov>> configMap = configurationProperty.getMap();

	        Map<String, Object> refdata = new HashMap<String, Object>();

	        refdata.put("administrativeGenderCode", configMap.get("administrativeGenderCode"));
	        refdata.put("ethnicGroupCode", configMap.get("ethnicGroupCode"));
	        refdata.put("raceCode", configMap.get("raceCode"));
	        // refdata.put("source", healthcareSiteDao.getAll());
	        refdata.put("searchTypeRefData", configMap.get("participantSearchType"));
	        refdata.put("identifiersTypeRefData", configMap.get("participantIdentifiersType"));
	        refdata.put("mandatory", "true");
	        
	        return refdata;
	}

    @Override
    public void validate(Participant participant, Errors errors) {
        super.validate(participant, errors);
        participantValidator.validateParticipantMRN(participant, errors);
        participantValidator.validateIdentifiers(participant, errors);
    }

    public ParticipantValidator getParticipantValidator() {
        return participantValidator;
    }

    public void setParticipantValidator(ParticipantValidator participantValidator) {
        this.participantValidator = participantValidator;
    }

}
