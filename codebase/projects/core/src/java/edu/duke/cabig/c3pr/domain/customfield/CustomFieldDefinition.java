package edu.duke.cabig.c3pr.domain.customfield;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.log4j.Logger;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject;
import edu.duke.cabig.c3pr.domain.Organization;
import edu.duke.cabig.c3pr.domain.ParameterizedBiDirectionalInstantiateFactory;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

@Entity
@Table(name = "cust_field_defns")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "CUST_FIELD_DEFNS_ID_SEQ") })
public abstract class CustomFieldDefinition extends AbstractMutableDeletableDomainObject{
	
	private String displayName;
	private String displayLabel;
	private boolean mandatoryIndicator;
	private int maxLength ;
	private String fieldType ;
	private String dataType;	
	private String displayPage ;
	private Study study ;
	private StudyOrganization studyOrganization ;
	
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getDisplayLabel() {
		return displayLabel;
	}
	public void setDisplayLabel(String displayLabel) {
		this.displayLabel = displayLabel;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public boolean isMandatoryIndicator() {
		return mandatoryIndicator;
	}
	public void setMandatoryIndicator(boolean mandatoryIndicator) {
		this.mandatoryIndicator = mandatoryIndicator;
	}
	public String getDisplayPage() {
		return displayPage;
	}
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public int getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}
	public void setStudyOrganization(StudyOrganization studyOrganization) {
		this.studyOrganization = studyOrganization;
	}
	
	@ManyToOne
    @JoinColumn(name = "org_id")
    @Cascade( { CascadeType.LOCK, CascadeType.SAVE_UPDATE, CascadeType.MERGE})
	public StudyOrganization getStudyOrganization() {
		return studyOrganization;
	}
	public void setStudy(Study study) {
		this.study = study;
	}
	
	@ManyToOne
    @JoinColumn(name = "stu_id")
    @Cascade( { CascadeType.LOCK, CascadeType.SAVE_UPDATE, CascadeType.MERGE})
	public Study getStudy() {
		return study;
	}
	
	@OneToMany(mappedBy = "customFieldDefinition", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	public List<CustomFieldPermissibleValue> getCustomFieldPermissibleValuesInternal() {
		return lazyListHelper.getInternalList(CustomFieldPermissibleValue.class);
	}

	@Transient
	public List<CustomFieldPermissibleValue> getCustomFieldPermissibleValues() {
		return lazyListHelper.getLazyList(CustomFieldPermissibleValue.class);
	}

	public void setCustomFieldPermissibleValuesInternal(List<CustomFieldPermissibleValue> customFieldPermissibleValues) {
		lazyListHelper.setInternalList(CustomFieldPermissibleValue.class,customFieldPermissibleValues);
	}

	public void addCustomFieldPermissibleValue(CustomFieldPermissibleValue customFieldPermissibleValue) {
		this.getCustomFieldPermissibleValues().add(customFieldPermissibleValue);
		customFieldPermissibleValue.setCustomFieldDefinition(this);
	}
	
	
	@OneToMany(mappedBy = "customFieldDefinition", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	public List<CustomField> getCustomFieldsInternal() {
		return lazyListHelper.getInternalList(CustomField.class);
	}

	@Transient
	public List<CustomField> getCustomFields() {
		return lazyListHelper.getLazyList(CustomField.class);
	}

	public void setCustomFieldsInternal(List<CustomField> customFields) {
		lazyListHelper.setInternalList(CustomField.class,customFields);
	}

	public void addCustomField(CustomField customField) {
		this.getCustomFields().add(customField);
		customField.setCustomFieldDefinition(this);
	}
	
	private Logger log = Logger.getLogger(CustomFieldDefinition.class);
	private LazyListHelper lazyListHelper;
	public CustomFieldDefinition(){
		lazyListHelper = new LazyListHelper();
		lazyListHelper.add(CustomFieldPermissibleValue.class,new ParameterizedBiDirectionalInstantiateFactory<CustomFieldPermissibleValue>(CustomFieldPermissibleValue.class, this));
		lazyListHelper.add(CustomField.class,new ParameterizedBiDirectionalInstantiateFactory<CustomField>(CustomField.class, this));
	}
	
}
