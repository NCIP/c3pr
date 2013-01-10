/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.dao.query;


/**
 * @author Saurabh Agrawal
 */
public class ResearchStaffQuery extends AbstractQuery {

    private static String queryString = "SELECT distinct rs from PersonUser rs left join fetch rs.healthcareSites order by rs.id";

    private static String FIRST_NAME = "firstName";

    private static String ORGANIZATION_NAME = "name";

    private static String LAST_NAME = "lastName";

    private static String EMAIL_ADDRESS = "emailAddress";

    private static String ASSIGNED_IDENTIFIER = "assignedIdentifier";
    
    public ResearchStaffQuery() {

        super(queryString);
    }

    public void filterByOrganizationName(final String name) {
        String searchString = "%" + name.toLowerCase() + "%";
        andWhere("lower(rs.organization.name) LIKE :" + ORGANIZATION_NAME);
        setParameter(ORGANIZATION_NAME, searchString);
    }

    public void filterByFirstName(final String firstName) {
        String searchString = "%" + firstName.toLowerCase() + "%";
        andWhere("lower(rs.firstName) LIKE :" + FIRST_NAME);
        setParameter(FIRST_NAME, searchString);
    }

    public void filterByLastName(final String lastName) {
        String searchString = "%" + lastName.toLowerCase() + "%";
        andWhere("lower(rs.lastName) LIKE :" + LAST_NAME);
        setParameter(LAST_NAME, searchString);
    }

    public void filterByEmailAddress(final String emailAddress) {
        String searchString = "%" + emailAddress.trim().toLowerCase() + "%";
        andWhere("lower(rs.contactMechanisms.value) LIKE :" + EMAIL_ADDRESS);
        setParameter(EMAIL_ADDRESS, searchString);
    }

    public void filterByAssignedIdentifier(final String assignedIdentifier) {
        String searchString = "%" + assignedIdentifier.toLowerCase() + "%";
        andWhere("lower(rs.assignedIdentifier) LIKE :" + ASSIGNED_IDENTIFIER);
        setParameter(ASSIGNED_IDENTIFIER, searchString);
    }
}
