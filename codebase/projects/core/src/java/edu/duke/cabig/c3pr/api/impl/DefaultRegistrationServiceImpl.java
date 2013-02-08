/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.api.impl;

import edu.duke.cabig.c3pr.api.RegistrationService;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;

/**
 * @author Ram Chilukuri
 */
public class DefaultRegistrationServiceImpl implements RegistrationService {
    private StudySubjectDao studySubjectDao;

    public DefaultRegistrationServiceImpl() {
    }

    public void addStudySubjectIdentifier(String studySubjectGridIdentifier,
                    Identifier newIdentifier) {
        StudySubject registration = (StudySubject) studySubjectDao
                        .getByGridId(studySubjectGridIdentifier);
        if (registration == null) {
            throw new IllegalArgumentException("Registration with grid identifier: "
                            + studySubjectGridIdentifier + " could not be found in the database");
        }
        if (this.validateIdentifier(newIdentifier)) {
            registration.addIdentifier(newIdentifier);
            studySubjectDao.save(registration);
        }

    }

    public void addStudySubjectIdentifier(Study study, Participant subject, HealthcareSite site,
                    Identifier newIdentifier) {
        // TODO: Implement this method in next release
    }

    public void setRegistrationDao(StudySubjectDao studySubjectDao) {
        this.studySubjectDao = studySubjectDao;
    }

    private boolean validateIdentifier(Identifier newIdentifier) {
    	Object type = null;
    	if(newIdentifier instanceof SystemAssignedIdentifier){
    		type= ((SystemAssignedIdentifier)newIdentifier).getType();
    	}
    	if(newIdentifier instanceof OrganizationAssignedIdentifier){
    		type= ((OrganizationAssignedIdentifier)newIdentifier).getType();
    	}
        if (type == null) {
            throw new IllegalArgumentException(
                            "New Identifier does not have a type. Please specify a type.");
        }
        if (newIdentifier.getValue() == null) {
            throw new IllegalArgumentException(
                            "New Identifier does not have a value. Please specify a value.");
        }
        return true;
    }
}
