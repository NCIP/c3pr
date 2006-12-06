/*
 * Created Thu Apr 20 17:45:32 EDT 2006 by MyEclipse Hibernate Tool.
 */ 
package edu.duke.cabig.c3pr.domain;


import java.io.Serializable;


public class ProtocolParticipantRole implements Serializable
{
    private Integer id;
    private ProtocolRole protocolRole;
    private Participant participant;
    private Protocol protocol;

    /**
     * Simple constructor of ProtocolParticipantRole instances.
     */
    public ProtocolParticipantRole()
    {
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ProtocolRole getProtocolRole() {
		return protocolRole;
	}

	public void setProtocolRole(ProtocolRole protocolRole) {
		this.protocolRole = protocolRole;
	}

	public Participant getParticipant() {
		return participant;
	}

	public void setParticipant(Participant participant) {
		this.participant = participant;
	}

	public Protocol getProtocol() {
		return protocol;
	}

	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}


}
