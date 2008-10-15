package edu.duke.cabig.c3pr.utils;

import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;

public class StudyStatusHelper {
	
	public static void setStatus(Study study,CoordinatingCenterStudyStatus targetStatus) throws C3PRCodedRuntimeException{
		
		// TODO Should be using Switch Statement to do this
		
		if (targetStatus.equals(CoordinatingCenterStudyStatus.ACTIVE)){
			study.open();
		} else if (targetStatus.equals(CoordinatingCenterStudyStatus.OPEN)){
			study.open();
		} else if (targetStatus.equals(CoordinatingCenterStudyStatus.PENDING)){
			study.putInPending();
		} else if (targetStatus.equals(CoordinatingCenterStudyStatus.AMENDMENT_PENDING)){
			study.putInAmendmentPending();
		} else if (targetStatus.equals(CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL)){
			study.temporarilyCloseToAccrual();
		} else if (targetStatus.equals(CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT)){
			study.temporarilyCloseToAccrualAndTreatment();
		} else if (targetStatus.equals(CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL)){
			study.closeToAccrual();
		} else if (targetStatus.equals(CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT)){
			study.closeToAccrualAndTreatment();
		}
		
	}

}
