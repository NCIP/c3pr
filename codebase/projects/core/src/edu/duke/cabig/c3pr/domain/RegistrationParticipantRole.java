/*
 * Created Thu Apr 20 17:45:32 EDT 2006 by MyEclipse Hibernate Tool.
 */ 
package edu.duke.cabig.c3pr.domain;


import java.io.Serializable;


public class RegistrationParticipantRole implements Serializable
{
    private Integer id;
    private RegistrationRole registrationRole;
    private Participant participant;
    private Registration registration;

    /**
     * Simple constructor of RegistrationRole instances.
     */
    public RegistrationParticipantRole()
    {
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public RegistrationRole getRegistrationRole() {
		return registrationRole;
	}

	public void setRegistrationRole(RegistrationRole registrationRole) {
		this.registrationRole = registrationRole;
	}

	public Participant getParticipant() {
		return participant;
	}

	public void setParticipant(Participant person) {
		this.participant = person;
	}

	public Registration getRegistration() {
		return registration;
	}

	public void setRegistration(Registration registration) {
		this.registration = registration;
	}

}
