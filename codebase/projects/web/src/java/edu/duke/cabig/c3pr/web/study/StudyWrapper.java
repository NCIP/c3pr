package edu.duke.cabig.c3pr.web.study;

import javax.persistence.Transient;

import edu.duke.cabig.c3pr.domain.Study;

public class StudyWrapper {
	
	private Study study ;
	private String file;

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
