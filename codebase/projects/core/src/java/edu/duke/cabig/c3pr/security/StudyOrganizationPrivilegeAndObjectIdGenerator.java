package edu.duke.cabig.c3pr.security;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.StudyOrganization;

/**
 * WIll generate the objectId and privilege for HealthcareSite domain object. THis is needed to make
 * authorization desicisions
 * 
 * Created by IntelliJ IDEA. User: kherm Date: Sep 13, 2007 Time: 3:05:32 PM To change this template
 * use File | Settings | File Templates.
 */
public final class StudyOrganizationPrivilegeAndObjectIdGenerator extends
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
        StudyOrganization studyOrganization = (StudyOrganization) object;
        
        return HealthcareSite.class.getName() + getPathSeperator() + studyOrganization.getHealthcareSite().getNciInstituteCode();
    }

    @Override
    protected boolean supports(Object object) {
        return object instanceof StudyOrganization;
    }

}
