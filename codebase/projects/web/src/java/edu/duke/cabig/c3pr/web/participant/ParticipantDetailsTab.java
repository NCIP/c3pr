package edu.duke.cabig.c3pr.web.participant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.validator.ParticipantValidator;
import edu.duke.cabig.c3pr.utils.Lov;

public class ParticipantDetailsTab extends ParticipantTab {
	
	private ParticipantValidator participantValidator;

	public ParticipantDetailsTab() {
		super("Details", "Details", "participant/participant");
	}

	public Map<String, Object> referenceData() {
		Map<String, List<Lov>> configMap = configurationProperty
				.getMap();

		Map<String, Object> refdata = new HashMap<String, Object>();

		refdata.put("administrativeGenderCode", configMap
				.get("administrativeGenderCode"));
		refdata
				.put("ethnicGroupCode", configMap
						.get("ethnicGroupCode"));
		refdata.put("raceCode", configMap.get("raceCode"));
		refdata.put("source", healthcareSiteDao.getAll());
		refdata.put("searchTypeRefData", configMap
				.get("participantSearchType"));
		refdata.put("identifiersTypeRefData", configMap
				.get("participantIdentifiersType"));
		refdata.put("mandatory", "true");
		;

		return refdata;
	}

	@Override
	public void validate(Participant participant, Errors errors) {
		// TODO Auto-generated method stub
		super.validate(participant, errors);
		participantValidator.validateParticipantMRN(participant,errors);
		
	}

	public ParticipantValidator getParticipantValidator() {
		return participantValidator;
	}

	public void setParticipantValidator(ParticipantValidator participantValidator) {
		this.participantValidator = participantValidator;
	}

}
