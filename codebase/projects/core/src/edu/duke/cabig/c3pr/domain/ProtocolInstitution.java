/*
 * Created Thu Apr 20 17:45:40 EDT 2006 by MyEclipse Hibernate Tool.
 */ 
package edu.duke.cabig.c3pr.domain;


import java.io.Serializable;

/**
 * A class that represents a row in the 'PROTOCOL_STATUS' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class ProtocolInstitution implements Serializable
{
	private Integer id;
	private ProtocolIdentifier protocolIdentifier;
	private ProtocolInstitutionRole protocolInstitutionRole;
	private Institution institution;
	private Protocol protocol;
    /**
     * Simple constructor of ProtocolStatus instances.
     */
    public ProtocolInstitution()
    {
    }
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Institution getInstitution() {
		return institution;
	}
	public void setInstitution(Institution institution) {
		this.institution = institution;
	}
	public ProtocolIdentifier getProtocolIdentifier() {
		return protocolIdentifier;
	}
	public void setProtocolIdentifier(ProtocolIdentifier protocolIdentifier) {
		this.protocolIdentifier = protocolIdentifier;
	}
	public ProtocolInstitutionRole getProtocolInstitutionRole() {
		return protocolInstitutionRole;
	}
	public void setProtocolInstitutionRole(
			ProtocolInstitutionRole protocolInstitutionRole) {
		this.protocolInstitutionRole = protocolInstitutionRole;
	}
	public Protocol getProtocol() {
		return protocol;
	}
	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}
	
}
