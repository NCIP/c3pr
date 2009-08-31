package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.constants.ICD9DiseaseSiteCodeDepth;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "icd9_disease_sites")
@GenericGenerator(name = "id-generator", strategy = "assigned")
public class ICD9DiseaseSite extends AbstractMutableDomainObject{
	
	private String code;
	private String descriptionText;
	private ICD9DiseaseSiteCodeDepth level;
	private boolean selectable;
	private String name;
	
	
	private ICD9DiseaseSite parentSite;
	private List<ICD9DiseaseSite> childSites;

	@ManyToOne
	@JoinColumn(name="parent_id")
	public ICD9DiseaseSite getParentSite() {
		return parentSite;
	}

	public void setParentSite(ICD9DiseaseSite parentSite) {
		this.parentSite = parentSite;
	}
	@OneToMany (mappedBy="parentSite")
	public List<ICD9DiseaseSite> getChildSites() {
		return childSites;
	}

	public void setChildSites(List<ICD9DiseaseSite> childSites) {
		this.childSites = childSites;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescriptionText() {
		return descriptionText;
	}

	public void setDescriptionText(String descriptionText) {
		this.descriptionText = descriptionText;
	}

	@Enumerated(EnumType.STRING)
	public ICD9DiseaseSiteCodeDepth getLevel() {
		return level;
	}

	public void setLevel(ICD9DiseaseSiteCodeDepth level) {
		this.level = level;
	}

	public boolean isSelectable() {
		return selectable;
	}

	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}

}
