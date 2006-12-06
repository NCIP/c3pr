package edu.duke.cabig.c3pr.domain;

import java.io.Serializable;

;

/**
 * The persistent class for the CODE_TYPE database table.
 * 
 * @author BEA Workshop Studio
 */
public class ReportType implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private String id;
	private String description;

    public ReportType(String id) {
    	this.id = id;
    }

	public ReportType() {
    }

	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getLOVDescription(Object param) {
		return this.getDescription();
	}

	public String getLOVId() {
		return this.getId();
	}

	public void setLOVFilter(Object param, Object filter) {
	}

}