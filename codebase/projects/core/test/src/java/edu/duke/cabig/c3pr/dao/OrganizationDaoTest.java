package edu.duke.cabig.c3pr.dao;

import static edu.duke.cabig.c3pr.C3PRUseCase.CREATE_ORGANIZATION;
import edu.duke.cabig.c3pr.C3PRUseCases;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.InvestigatorGroup;
import edu.duke.cabig.c3pr.utils.ContextDaoTestCase;

@C3PRUseCases({CREATE_ORGANIZATION})
public class OrganizationDaoTest extends ContextDaoTestCase<OrganizationDao> {
	/**
	 * Test for loading an Organization by Id
	 * 
	 * @throws Exception
	 */
	public void testGetById() throws Exception {
		HealthcareSite org = getDao().getById(1000);
		assertEquals("Duke Comprehensive Cancer Center", org.getName());
	}

	public void testSaveNewOrganization() {
		Integer savedId;
		{
			HealthcareSite healthcaresite = new HealthcareSite();

			Address address = new Address();
			address.setCity("Chicago");
			address.setCountryCode("USA");
			address.setPostalCode("83929");
			address.setStateCode("IL");
			address.setStreetAddress("123 Lake Shore Dr");

			healthcaresite.setAddress(address);
			healthcaresite.setName("Northwestern Memorial Hospital");
			healthcaresite.setDescriptionText("NU healthcare");
			healthcaresite.setNciInstituteCode("NCI northwestern");
			this.getDao().save(healthcaresite);

			savedId = healthcaresite.getId();
			assertNotNull("The saved organization didn't get an id", savedId);
		}
		
		interruptSession();
		{
			HealthcareSite loaded = this.getDao().getById(savedId);
			assertNotNull("Could not reload organization with id " + savedId, loaded);
			// assertNotNull("GridId not updated", loaded.getGridId());
			assertEquals("Wrong name", "Northwestern Memorial Hospital", loaded.getName());
			assertEquals("Wrong city", "Chicago", loaded.getAddress().getCity());
		}
	}
	public void testNumberOfInvestigatorGroups() throws Exception {
		HealthcareSite org = getDao().getById(1000);
		assertEquals("Expected 2 investigator groups",2, org.getInvestigatorGroups().size());
	}
	public void testAddInvestigatorGroupToHealthcareSite() throws Exception {
		{
		HealthcareSite org = getDao().getById(1000);
	//	InvestigatorGroup invGroup = new InvestigatorGroup();
	//	invGroup.setName("Physicians Group");
	//	invGroup.setHealthcareSite(org);
		org.getInvestigatorGroups().get(2).setName("Physicians Group");
		}
		interruptSession();
		HealthcareSite loadedOrg = getDao().getById(1000);
		
		assertEquals("Expected 3 investigator groups",3, loadedOrg.getInvestigatorGroups().size());
	}
}
