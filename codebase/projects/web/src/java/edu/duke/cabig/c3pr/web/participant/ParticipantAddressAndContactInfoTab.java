/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.participant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.duke.cabig.c3pr.utils.Lov;

public class ParticipantAddressAndContactInfoTab extends ParticipantTab {

    public ParticipantAddressAndContactInfoTab() {
        super("Address & Contact Information", "Address & Contact Information", "participant/participant_address");
    }

    public Map<String, Object> referenceData() {
        Map<String, List<Lov>> configMap = configurationProperty.getMap();

        Map<String, Object> refdata = new HashMap<String, Object>();
        refdata.put("searchTypeRefData", configMap.get("participantSearchType"));
        return refdata;
    }

}
