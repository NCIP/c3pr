package edu.duke.cabig.c3pr.tools;

import gov.nih.nci.cabig.ctms.tools.configuration.ConfigurationEntry;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
* @author Eric Wyles
*/
@Entity
@Table(name = "configuration")
@AttributeOverride(name = "key", column = @Column(name = "prop"))
public class C3prConfigurationEntry extends ConfigurationEntry { }