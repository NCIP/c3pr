/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.utils;

import java.util.Comparator;

import edu.duke.cabig.c3pr.domain.Study;


public class AccrualCountComparator implements Comparator<Study> {
	public int compare(Study study, Study anotherStudy) {
		Integer accrualCount1 = study.getAccrualCount();
		Integer accrualCount2 = anotherStudy.getAccrualCount();
		if (accrualCount1 != accrualCount2)
			return accrualCount1.compareTo(accrualCount2);
		else
			return 0 ;
	}
}
