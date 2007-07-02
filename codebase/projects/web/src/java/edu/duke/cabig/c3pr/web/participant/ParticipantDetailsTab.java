package edu.duke.cabig.c3pr.web.participant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.duke.cabig.c3pr.utils.Lov;

public class ParticipantDetailsTab extends ParticipantTab {

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
		;

		return refdata;
	}

}
