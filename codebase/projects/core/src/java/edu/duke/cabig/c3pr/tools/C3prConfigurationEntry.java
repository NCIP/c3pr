/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
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
