package edu.duke.cabig.c3pr.api;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.Study;

/**
 * @author Ram Chilukuri
 */

public interface RegistrationService {
    public void addStudySubjectIdentifier(String studySubjectGridIdentifier, Identifier newIdentifier);
    public void addStudySubjectIdentifier(Study study, Participant subject, HealthcareSite site, Identifier newIdentifier);
    
}
