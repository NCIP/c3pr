package edu.duke.cabig.c3pr.domain;

import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * 
 * @author Priyatam
 */



@Entity 
@Table (name = "IDENTIFIERS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@GenericGenerator (name="id-generator", strategy = "native",
		parameters = {
			@Parameter(name="sequence", value="IDENTIFIERS_ID_SEQ")
		}
)
public abstract class Identifier extends AbstractMutableDomainObject
{			
//	private String source;
	private String type;
	private String value;
	private Boolean primaryIndicator = false;
				
	/**
     * Null-safe conversion from primaryIndicator property to simple boolean.
     * TODO: switch the db field to not-null, default false so this isn't necessary.
     */
    @Transient
    public boolean isPrimary() {
        return getPrimaryIndicator() == null ? false : getPrimaryIndicator();
    }
   
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Boolean getPrimaryIndicator() {
		return primaryIndicator;
	}

	public void setPrimaryIndicator(Boolean primaryIndicator) {
		if (primaryIndicator == null)
		{
			primaryIndicator = false;
		}
		this.primaryIndicator = primaryIndicator;
	}
/*
	public String getSource() {
		if(true)throw new UnsupportedOperationException("Not supported");
		return source;
	}

	public void setSource(String source) {
		if(true)throw new UnsupportedOperationException("Not supported");
		this.source = source;
	}*/
		
}
