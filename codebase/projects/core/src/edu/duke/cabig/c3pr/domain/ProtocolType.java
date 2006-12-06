/*
 * Created Thu Apr 20 17:45:32 EDT 2006 by MyEclipse Hibernate Tool.
 */ 
package edu.duke.cabig.c3pr.domain;

import java.io.Serializable;

/**
 * A class that represents a row in the 'CODE_PROT_TYPE' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class ProtocolType extends AbstractDomainObject implements Comparable<ProtocolType>, Serializable
{
    private String code;
    private String description;

    /**
     * Simple constructor of ProtocolRole instances.
     */
    public ProtocolType()
    {
    }
 
    
    public int compareTo(ProtocolType o) {
        // by type first
       // int typeDiff = getType().compareTo(o.getType());
       // if (typeDiff != 0) return typeDiff;
        // then by name
       // return ComparisonUtils.nullSafeCompare(toLower(getName()), toLower(o.getName()));
        return 1;
    }
    
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


}
