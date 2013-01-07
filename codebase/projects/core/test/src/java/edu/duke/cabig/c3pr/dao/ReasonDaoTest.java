package edu.duke.cabig.c3pr.dao;

import edu.duke.cabig.c3pr.domain.OffReservingReason;
import edu.duke.cabig.c3pr.domain.OffScreeningReason;
import edu.duke.cabig.c3pr.domain.OffTreatmentReason;
import edu.duke.cabig.c3pr.utils.ContextDaoTestCase;

/**
 * JUnit Tests for ReasonDao
 * 
 * @author Ramakrishna
 * @testType unit
 */
public class ReasonDaoTest extends ContextDaoTestCase<ReasonDao> {

    public void testGetReasonByCode() throws Exception{
    	assertEquals(1001, getDao().getReasonByCode("ABC").getId().intValue());
    	assertEquals(1000, getDao().getReasonByCode("ABCD").getId().intValue());
    	assertEquals(1002, getDao().getReasonByCode("EFG").getId().intValue());
    	assertEquals(1003, getDao().getReasonByCode("EFGH").getId().intValue());
    	assertEquals(1004, getDao().getReasonByCode("PQR").getId().intValue());
    }
    
    public void testGetReasonByCodeAndType() throws Exception{
    	OffTreatmentReason offTreatmentReason = new OffTreatmentReason();
    	offTreatmentReason.setCode("ABCD");
    	assertEquals(1000, getDao().getReasonByCodeAndType(offTreatmentReason.getCode(), offTreatmentReason.getClass().getName()).getId().intValue());
    	
    	OffScreeningReason offScreeningReason = new OffScreeningReason();
    	offScreeningReason.setCode("EFG");
    	assertEquals(1002, getDao().getReasonByCodeAndType(offScreeningReason.getCode(), offScreeningReason.getClass().getName()).getId().intValue());
    	
    	OffReservingReason offReservingReason = new OffReservingReason();
    	offReservingReason.setCode("EFGH");
    	assertEquals(1003, getDao().getReasonByCodeAndType(offReservingReason.getCode(), offReservingReason.getClass().getName()).getId().intValue());
    }
    
    
}