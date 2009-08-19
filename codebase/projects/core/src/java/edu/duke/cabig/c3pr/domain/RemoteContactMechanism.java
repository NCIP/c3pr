package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.semanticbits.coppa.domain.annotations.RemoteEntity;

@Entity
@DiscriminatorValue("Remote")
@RemoteEntity
public class RemoteContactMechanism extends ContactMechanism {

}
