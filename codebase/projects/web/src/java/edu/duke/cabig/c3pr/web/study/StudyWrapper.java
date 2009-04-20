package edu.duke.cabig.c3pr.web.study;

import javax.persistence.Transient;

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

}
