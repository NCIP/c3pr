/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.security;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalStudy;
import edu.duke.cabig.c3pr.domain.RemoteStudy;
import edu.duke.cabig.c3pr.domain.Study;
import gov.nih.nci.cabig.ctms.suite.authorization.ScopeType;

/**
 * WIll generate the objectId and privilege for HealthcareSite domain object. THis is needed to make
 * authorization desicisions
 * 
 * Created by IntelliJ IDEA. User: kherm Date: Sep 13, 2007 Time: 3:05:32 PM To change this template
 * use File | Settings | File Templates.
 */
public final class StudyPrivilegeAndObjectIdGenerator extends
                DomainObjectPrivilegeAndObjectIdGenerator {

    /**
     * Returns a CSM objectId, given an object.
     * 
     * @param object
     *                from which ID should be generated
     * @return CSM objectId
     */
    @Override
    public String generateId(Object object) {
        assertSupports(object);
        Study study = (Study) object;

        return ScopeType.STUDY.getScopeCsmNamePrefix() + study.getCoordinatingCenterAssignedIdentifier().getValue();
    }

    @Override
    protected boolean supports(Object object) {
        return object instanceof RemoteStudy || object instanceof LocalStudy;
    }

}
