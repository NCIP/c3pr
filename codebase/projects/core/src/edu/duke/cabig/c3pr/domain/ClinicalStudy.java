package edu.duke.cabig.c3pr.domain;


import java.io.Serializable;



public class ClinicalStudy implements Serializable
{
	private Integer id;
	private String isC3dProtocol;
	private String studyId;
	private Protocol protocol;
	
	
	public Protocol getProtocol() {
		return protocol;
	}
	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getIsC3dProtocol() {
		return isC3dProtocol;
	}
	public void setIsC3dProtocol(String isC3dProtocol) {
		this.isC3dProtocol = isC3dProtocol;
	}
	public String getStudyId() {
		return studyId;
	}
	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}

}