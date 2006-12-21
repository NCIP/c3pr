package edu.duke.cabig.c3pr.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author Kulasekaran
 * @version 1.0 
 * 
 */
@Entity 
@Table (name = "PARTICIPANT")
@GenericGenerator (name="id-generator", strategy = "native",
		parameters = {
			@Parameter(name="sequence", value="C3PR_GENERAL_SEQ")
		}
)
public class Participant extends Person implements Serializable
{
	/**
     * Simple constructor of Participant instances.
     */
    public Participant()
    {
    }    
}

