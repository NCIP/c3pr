/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.participant;

import java.util.Map;

import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.domain.Participant;

public class ParticipantRegistrationsTab extends ParticipantTab {
    private ParticipantDao participantDao;

    public ParticipantRegistrationsTab() {
        super("Registrations", "Registrations", "participant/participant_registrations");
    }

    @Override
    public Map<String, Object> referenceData(ParticipantWrapper participantWrapper) {
    	Participant participant = participantWrapper.getParticipant();
        Map<String, Object> refdata = super.referenceData(participantWrapper);
        participant = participantDao.getById(participant.getId());
        participantDao.initializeStudySubjects(participant);
        refdata.put("participantAssignments",participant.getStudySubjects());
        return refdata;
    }

    public void setParticipantDao(ParticipantDao participantDao) {
        this.participantDao = participantDao;
    }

}
