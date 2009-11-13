package edu.duke.cabig.c3pr.web.study;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.Study;

public class StudyWrapper {

	private Study study ;
	private String file;
	private boolean hasStratifiedEpoch ;

	public boolean getHasStratifiedEpoch() {
		hasStratifiedEpoch = false;
		if (this.study != null) {
			if (study.getStratificationIndicator()) {
				for (Epoch epoch : this.study.getEpochs()) {
					if (epoch.getStratificationIndicator()) {
						return true;
					}
				}
			}
		}
		return hasStratifiedEpoch;
	}

	public void setHasStratifiedEpoch(boolean hasStratifiedEpoch) {
		this.hasStratifiedEpoch = hasStratifiedEpoch;
	}

	public Study getStudy() {
		return study;
	}

	public void setStudy(Study study) {
		this.study = study;
	}

	@Transient
	public String getFile() {
		if (file != null) {
			return file;
		}
		return null;
	}

	public void setFile(String file) {
		this.file = file;
	}

     public boolean canAmendStudy(){
    	Study study = this.getStudy();
    	if(study.getIsEmbeddedCompanionStudy()){
    		// we do not support amendment of embedded companion studies.
    		return false ;
    	}
    	List<CoordinatingCenterStudyStatus> permissibleStatus = new ArrayList<CoordinatingCenterStudyStatus>();
        permissibleStatus.add(CoordinatingCenterStudyStatus.OPEN);
        permissibleStatus.add(CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL);
        permissibleStatus.add(CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT);

        return permissibleStatus.contains(study.getCoordinatingCenterStudyStatus()) && study.getStudyVersion() == study.getLatestStudyVersion();

    }

     public boolean resumeAmendment(){
    	 if(canAmendStudy() && getStudy().getCurrentStudyAmendment() != null ){
             return true;
         }
         return false;
     }

     public boolean applyAmendment(){
     	return resumeAmendment();
     }

}
