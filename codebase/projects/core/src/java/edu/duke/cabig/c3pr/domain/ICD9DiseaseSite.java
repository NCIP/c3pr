/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.constants.ICD9DiseaseSiteCodeDepth;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * The Class ICD9DiseaseSite.
 * @author Ramakrishna
 */
@Entity
@Table(name = "icd9_disease_sites")
@GenericGenerator(name = "id-generator", strategy = "assigned")
public class ICD9DiseaseSite extends AbstractMutableDomainObject{
	
	/** The code. */
	private String code;
	
	/** The description text. */
	private String descriptionText;
	
	/** The level. */
	private ICD9DiseaseSiteCodeDepth level;
	
	/** The selectable. */
	private Boolean selectable;
	
	/** The name. */
	private String name;
	
	/**
	 * Gets the selectable.
	 * 
	 * @return the selectable
	 */
	public Boolean getSelectable() {
		return selectable;
	}

	/**
	 * Sets the selectable.
	 * 
	 * @param selectable the new selectable
	 */
	public void setSelectable(Boolean selectable) {
		this.selectable = selectable;
	}

	/** The summary3 report disease site. */
	private Summary3ReportDiseaseSite summary3ReportDiseaseSite;
	
	
	/**
	 * Sets the summary3 report disease site.
	 * 
	 * @param summary3ReportDiseaseSite the new summary3 report disease site
	 */
	public void setSummary3ReportDiseaseSite(
			Summary3ReportDiseaseSite summary3ReportDiseaseSite) {
		this.summary3ReportDiseaseSite = summary3ReportDiseaseSite;
	}

	/** The parent site. */
	private ICD9DiseaseSite parentSite;
	
	/** The child sites. */
	private List<ICD9DiseaseSite> childSites;

	/**
	 * Gets the parent site.
	 * 
	 * @return the parent site
	 */
	@ManyToOne
	@JoinColumn(name="parent_id")
	public ICD9DiseaseSite getParentSite() {
		return parentSite;
	}

	/**
	 * Sets the parent site.
	 * 
	 * @param parentSite the new parent site
	 */
	public void setParentSite(ICD9DiseaseSite parentSite) {
		this.parentSite = parentSite;
	}
	
	/**
	 * Gets the child sites.
	 * 
	 * @return the child sites
	 */
	@OneToMany (mappedBy="parentSite")
	public List<ICD9DiseaseSite> getChildSites() {
		return childSites;
	}

	/**
	 * Sets the child sites.
	 * 
	 * @param childSites the new child sites
	 */
	public void setChildSites(List<ICD9DiseaseSite> childSites) {
		this.childSites = childSites;
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the code.
	 * 
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code.
	 * 
	 * @param code the new code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Gets the description text.
	 * 
	 * @return the description text
	 */
	public String getDescriptionText() {
		return descriptionText;
	}

	/**
	 * Sets the description text.
	 * 
	 * @param descriptionText the new description text
	 */
	public void setDescriptionText(String descriptionText) {
		this.descriptionText = descriptionText;
	}

	/**
	 * Gets the level.
	 * 
	 * @return the level
	 */
	@Column(name = "depth")
	@Enumerated(EnumType.STRING)
	public ICD9DiseaseSiteCodeDepth getLevel() {
		return level;
	}

	/**
	 * Sets the level.
	 * 
	 * @param level the new level
	 */
	public void setLevel(ICD9DiseaseSiteCodeDepth level) {
		this.level = level;
	}

	/**
	 * Gets the summary3 report disease site.
	 * 
	 * @return the summary3 report disease site
	 */
	@ManyToOne(targetEntity=Summary3ReportDiseaseSite.class)
	@JoinColumn(name = "summ3_rep_disease_site_id" )
	public Summary3ReportDiseaseSite getSummary3ReportDiseaseSite() {
		return summary3ReportDiseaseSite;
	}
	
}
