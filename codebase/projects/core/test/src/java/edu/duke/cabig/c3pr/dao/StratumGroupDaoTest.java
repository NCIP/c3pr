/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import edu.duke.cabig.c3pr.domain.StratumGroup;
import edu.duke.cabig.c3pr.utils.ContextDaoTestCase;

public class StratumGroupDaoTest extends ContextDaoTestCase<StratumGroupDao> {
 
	/**
	 * Test domain class.
	 */
	public void testDomainClass() {
		assertEquals("Wrong Domain Class", StratumGroup.class, getDao().domainClass());
	}
    
    
    /**
     * Test merge.
     */
    public void testMerge() {
    	StratumGroup stratumGroup = getDao().getById(1000);
    	stratumGroup = getDao().merge(stratumGroup);
    	int savedId = stratumGroup.getId();
    	interruptSession();

    	StratumGroup savedStratumGroup = getDao().getById(savedId);
        assertEquals(8, savedStratumGroup.getStratumGroupNumber().intValue());
	}

}
