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
public class ProtocolIdentifier implements Serializable
{
	private Integer id;
	private String protocolIdentifierCode;
    /**
     * Simple constructor of ProtocolStatus instances.
     */
    public ProtocolIdentifier()
    {
    }
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getProtocolIdentifierCode() {
		return protocolIdentifierCode;
	}
	public void setProtocolIdentifierCode(String protocolIdentifierCode) {
		this.protocolIdentifierCode = protocolIdentifierCode;
	}
}
