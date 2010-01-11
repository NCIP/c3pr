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
