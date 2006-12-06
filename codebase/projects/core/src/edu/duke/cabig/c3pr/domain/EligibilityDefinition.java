package edu.duke.cabig.c3pr.domain;

import java.io.Serializable;




public class EligibilityDefinition implements Serializable
{
	private Integer id;
	private Amendment amendment;
	private EligibilityStatus status;
	private String crfId;
	
	public Amendment getAmendment() {
		return amendment;
	}
	public void setAmendment(Amendment amendment) {
		this.amendment = amendment;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public EligibilityStatus getStatus() {
		return status;
	}
	public void setStatus(EligibilityStatus status) {
		this.status = status;
	}
	public String getCrfId() {
		return crfId;
	}
	public void setCrfId(String crfId) {
		this.crfId = crfId;
	}

	
}