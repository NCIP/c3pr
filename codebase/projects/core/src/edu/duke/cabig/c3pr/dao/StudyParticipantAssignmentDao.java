package edu.duke.cabig.c3pr.dao;

import edu.duke.cabig.c3pr.domain.StudyParticipantAssignment;

public class StudyParticipantAssignmentDao extends AbstractBaseDao<StudyParticipantAssignment> {
    public StudyParticipantAssignmentDao() {
    }
    
    @Override
    public Class<StudyParticipantAssignment> domainClass() {
            return StudyParticipantAssignment.class;
     }
}
