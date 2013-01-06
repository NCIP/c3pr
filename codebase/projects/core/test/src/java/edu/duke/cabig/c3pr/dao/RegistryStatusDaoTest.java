package edu.duke.cabig.c3pr.dao;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.duke.cabig.c3pr.domain.RegistryStatus;
import edu.duke.cabig.c3pr.domain.RegistryStatusReason;
import edu.duke.cabig.c3pr.utils.ContextDaoTestCase;

/**
 * JUnit Tests for ArmDao
 * 
 * @author Priyatam
 * @testType unit
 */
public class RegistryStatusDaoTest extends ContextDaoTestCase<RegistryStatusDao> {

    /**
     * Test for loading a Registry Status by Id
     * 
     * @throws Exception
     */
    public void testGetById() throws Exception {
        RegistryStatus registryStatus = getDao().getById(1002);
        assertEquals("Screen Failed", registryStatus.getCode());
        assertEquals("Screen Failed", registryStatus.getDescription());
        assertEquals(3, registryStatus.getPrimaryReasons().size());
        Collections.sort(registryStatus.getPrimaryReasons(), new Comparator<RegistryStatusReason>(){
        	public int compare(RegistryStatusReason o1, RegistryStatusReason o2) {
        		return o1.getId().compareTo(o2.getId());
        	}});
    	assertEquals(1000, registryStatus.getPrimaryReasons().get(0).getId().intValue());
    	assertEquals("FAILED INCLUSION", registryStatus.getPrimaryReasons().get(0).getCode());
    	assertEquals("FAILED INCLUSION", registryStatus.getPrimaryReasons().get(0).getDescription());
    	assertEquals(1001, registryStatus.getPrimaryReasons().get(1).getId().intValue());
    	assertEquals("FAILED EXCLUSION", registryStatus.getPrimaryReasons().get(1).getCode());
    	assertEquals("FAILED EXCLUSION", registryStatus.getPrimaryReasons().get(1).getDescription());
    	assertEquals(1002, registryStatus.getPrimaryReasons().get(2).getId().intValue());
    	assertEquals("DIED", registryStatus.getPrimaryReasons().get(2).getCode());
    	assertEquals("DIED", registryStatus.getPrimaryReasons().get(2).getDescription());
    }

    /**
     * Test for loading all Registry Status
     * 
     * @throws Exception
     */
    public void testGetAll() throws Exception {
        List<RegistryStatus> registryStatuses = getDao().getAll();
        Collections.sort(registryStatuses, new Comparator<RegistryStatus>(){
        	public int compare(RegistryStatus o1, RegistryStatus o2) {
        		return o1.getId().compareTo(o2.getId());
        	}});
        assertEquals(1000, registryStatuses.get(0).getId().intValue());
    	assertEquals("Pre-Enrolled", registryStatuses.get(0).getCode());
    	assertEquals("Pre-Enrolled", registryStatuses.get(0).getDescription());
    	assertEquals(1001, registryStatuses.get(1).getId().intValue());
    	assertEquals("Enrolled", registryStatuses.get(1).getCode());
    	assertEquals("Enrolled", registryStatuses.get(1).getDescription());
    	assertEquals(1002, registryStatuses.get(2).getId().intValue());
    	assertEquals("Screen Failed", registryStatuses.get(2).getCode());
    	assertEquals("Screen Failed", registryStatuses.get(2).getDescription());
    	assertEquals(1003, registryStatuses.get(3).getId().intValue());
    	assertEquals("Accrued", registryStatuses.get(3).getCode());
    	assertEquals("Accrued", registryStatuses.get(3).getDescription());
    	assertEquals(1004, registryStatuses.get(4).getId().intValue());
    	assertEquals("Active Intervention", registryStatuses.get(4).getCode());
    	assertEquals("Active Intervention", registryStatuses.get(4).getDescription());
    }
    
    /**
     * Test for loading all Registry Status
     * 
     * @throws Exception
     */
    public void testSave() throws Exception {
    	RegistryStatusReason registryStatusReason = new RegistryStatusReason();
    	registryStatusReason.setCode("Some Code");
    	registryStatusReason.setDescription("Some Desc");
        RegistryStatus registryStatus = new RegistryStatus("Code", "Desc", Arrays.asList(new RegistryStatusReason[]{registryStatusReason}));
        getDao().save(registryStatus);
        int id = registryStatus.getId();
        interruptSession();
        registryStatus = getDao().getById(id);
    	assertEquals("Code", registryStatus.getCode());
    	assertEquals("Desc", registryStatus.getDescription());
    	assertEquals(1, registryStatus.getPrimaryReasons().size());
    }

}