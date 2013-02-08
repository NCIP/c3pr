/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;
import edu.duke.cabig.c3pr.domain.LocalStudy;
import edu.duke.cabig.c3pr.domain.Study;


public class AccrualCountComparatorTest extends TestCase{
	
	public void testCompare(){
		LocalStudy study1 = new LocalStudy();
		study1.setShortTitleText("study1");
		study1.setAccrualCount(10);
		
		LocalStudy study2 = new LocalStudy();
		study2.setShortTitleText("study2");
		study2.setAccrualCount(10);
		
		LocalStudy study3 = new LocalStudy();
		study3.setShortTitleText("study3");
		study3.setAccrualCount(11);
		
		
		LocalStudy study4 = new LocalStudy();
		study4.setShortTitleText("study4");
		study4.setAccrualCount(9);
		
		List<Study> studies = new ArrayList<Study>();
		studies.add(study1);
		studies.add(study2);
		studies.add(study3);
		studies.add(study4);
		
		assertEquals("before sorting First study should be with short title study1", "study1", studies.get(0).getShortTitleText());
		assertEquals("before sorting Last study should be with short title study4", "study4", studies.get(3).getShortTitleText());
		
		Collections.sort(studies, new AccrualCountComparator());
		
		assertEquals("First study should be with short title study4", "study4", studies.get(0).getShortTitleText());
		assertEquals("Last study should be with short title study3", "study3", studies.get(3).getShortTitleText());
		
		
	}
	
}
