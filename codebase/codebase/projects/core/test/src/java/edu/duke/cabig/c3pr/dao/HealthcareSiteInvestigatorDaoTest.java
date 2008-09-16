package edu.duke.cabig.c3pr.dao;

import static edu.nwu.bioinformatics.commons.testing.CoreTestCase.assertContains;

import java.util.List;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.InvestigatorGroup;
import edu.duke.cabig.c3pr.domain.SiteInvestigatorGroupAffiliation;
import edu.duke.cabig.c3pr.utils.ContextDaoTestCase;

/**
 * JUnit Tests for HealthcareSiteInvestigatorDao
 * 
 * @author Priyatam
 * @testType unit
 */
public class HealthcareSiteInvestigatorDaoTest extends
                ContextDaoTestCase<HealthcareSiteInvestigatorDao> {

    private HealthcareSiteInvestigatorDao dao = (HealthcareSiteInvestigatorDao) getApplicationContext()
                    .getBean("healthcareSiteInvestigatorDao");

    private HealthcareSiteDao healthcareSiteDao = (HealthcareSiteDao) getApplicationContext()
                    .getBean("healthcareSiteDao");

    private InvestigatorDao investigatorDao = (InvestigatorDao) getApplicationContext().getBean(
                    "investigatorDao");

    public void testAddSiteInvestigatorGroupAffiliationsSiteInvestigator() throws Exception {
        {
            HealthcareSiteInvestigator siteInvestigator = getDao().getById(1002);
            assertEquals("Wrong number of matches", 0, siteInvestigator
                            .getSiteInvestigatorGroupAffiliations().size());
            HealthcareSite org = healthcareSiteDao.getById(1001);
            InvestigatorGroup invGroup = new InvestigatorGroup();
            invGroup.setName("New investigator group");
            invGroup.setHealthcareSite(org);
            org.getInvestigatorGroups().add(invGroup);
            SiteInvestigatorGroupAffiliation siteInvGrAffiliation = new SiteInvestigatorGroupAffiliation();
            siteInvGrAffiliation.setHealthcareSiteInvestigator(siteInvestigator);
            siteInvGrAffiliation.setInvestigatorGroup(invGroup);
            invGroup.getSiteInvestigatorGroupAffiliations().add(siteInvGrAffiliation);
            healthcareSiteDao.save(org);
        }
        interruptSession();

        HealthcareSiteInvestigator loadedSiteInvestigator = getDao().getById(1002);
        assertEquals("Wrong number of matches", 1, loadedSiteInvestigator
                        .getSiteInvestigatorGroupAffiliations().size());
    }

    /**
     * Test for loading a Site Investigator by Id
     * 
     * @throws Exception
     */
    public void testGetById() throws Exception {
        HealthcareSiteInvestigator loaded = getDao().getById(1000);
        assertNotNull("HealthcareSiteInvestigatorDao not found", loaded);
        assertEquals("ACTIVE", loaded.getStatusCode());
    }

    /**
     * Test for loading all Site Investigators
     * 
     * @throws Exception
     */
    public void testGetAll() throws Exception {
        List<HealthcareSiteInvestigator> actual = dao.getAll();
        assertEquals(4, actual.size());
        List<Integer> ids = collectIds(actual);
        assertContains("Wrong study found", ids, 1000);
        assertContains("Wrong study found", ids, 1001);
        assertContains("Wrong study found", ids, 1002);
        assertContains("Wrong study found", ids, 1003);
    }

    /**
     * Test for loading of Site Investigators based on mathing pattern on Investigator name
     * 
     * @throws Exception
     */
    public void testGetBySubnameMatchesShortTitle() throws Exception {
        List<HealthcareSiteInvestigator> actual = getDao().getBySubnames(new String[] { "Bi" },
                        1000);
        assertEquals("Wrong number of matches", 1, actual.size());
        assertEquals("Wrong match", 1000, (int) actual.get(0).getId());
    }

    /**
     * Test for loading of Site Investigators based on mathing pattern on Investigator name
     * 
     * @throws Exception
     */
    public void testGetBySubnameMatchesIntersectionOfSubnames() throws Exception {
        List<HealthcareSiteInvestigator> actual = getDao().getBySubnames(
                        new String[] { "Investig", "Geo" }, 1000);
        assertEquals("Wrong number of matches", 1, actual.size());
        assertEquals("Wrong match", 1001, (int) actual.get(0).getId());
    }

    public void testFailureAddDuplicateInvestigatorToOrganization() throws Exception {

        HealthcareSite loadedHealthcareSite = healthcareSiteDao.getById(1000);
        Investigator inv1 = new Investigator();
        inv1.setFirstName("Brad");
        inv1.setLastName("Johnson");
        inv1.setMaidenName("Bradster");
        inv1.setNciIdentifier("NCI-123");

        Investigator inv2 = new Investigator();
        inv2.setFirstName("Brad");
        inv2.setLastName("Johnson");
        inv2.setMaidenName("Bradster");
        inv2.setNciIdentifier("NCI-123");

        HealthcareSiteInvestigator hcsInv1 = new HealthcareSiteInvestigator();
        hcsInv1.setInvestigator(inv1);

        HealthcareSiteInvestigator hcsInv2 = new HealthcareSiteInvestigator();
        hcsInv2.setInvestigator(inv2);

        loadedHealthcareSite.addHealthcareSiteInvestigator(hcsInv1);
        loadedHealthcareSite.addHealthcareSiteInvestigator(hcsInv2);

        try {
            healthcareSiteDao.save(loadedHealthcareSite);
            interruptSessionForceNewSession();
            fail("it should fail because of duplicate data entry");
        }
        catch (Exception e) {
            assertTrue(true);
        }

    }

    public void testGetSiteInvestigatorGroupAffiliations() throws Exception {
        HealthcareSiteInvestigator siteInvestigator = getDao().getById(1000);
        assertEquals("Wrong number of matches", 1, siteInvestigator
                        .getSiteInvestigatorGroupAffiliations().size());
    }

}