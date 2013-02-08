/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.participant;

import edu.duke.cabig.c3pr.dao.ParticipantDao;

public class ParticipantSummaryTab extends ParticipantTab {
    private ParticipantDao participantDao;

    public ParticipantSummaryTab() {
        super("Summary", "Summary", "participant/participant_summary_view");
    }

    public ParticipantDao getParticipantDao() {
        return participantDao;
    }

    public void setParticipantDao(ParticipantDao participantDao) {
        this.participantDao = participantDao;
    }

}
