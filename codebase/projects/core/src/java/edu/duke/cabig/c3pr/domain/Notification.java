package edu.duke.cabig.c3pr.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections15.functors.InstantiateFactory;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Where;

import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

@Entity
@Table(name = "notifications")
@GenericGenerator(name = "id-generator", strategy = "native",
        parameters = {@Parameter(name = "sequence", value = "NOTIFICATIONS_ID_SEQ")})
public class Notification extends AbstractMutableDeletableDomainObject{
	
	private Integer threshold;
	private LazyListHelper lazyListHelper;

	public Notification(){
		lazyListHelper = new LazyListHelper();
		lazyListHelper.add(EmailBasedRecipient.class,
				new InstantiateFactory<EmailBasedRecipient>(EmailBasedRecipient.class));
		lazyListHelper.add(RoleBasedRecipient.class,
				new InstantiateFactory<RoleBasedRecipient>(RoleBasedRecipient.class));
	}
	 
	@Override
	@Transient
	public void setRetiredIndicatorAsTrue(){
		super.setRetiredIndicatorAsTrue();		
	}
	
	@OneToMany (fetch=FetchType.LAZY)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN})	
    @JoinColumn(name = "NOTIFICATIONS_ID", nullable=false)
    @Where(clause = "DTYPE = 'ER' and retired_indicator  = 'false'")
	public List<EmailBasedRecipient> getEmailBasedRecipientInternal() {
		return lazyListHelper.getInternalList(EmailBasedRecipient.class);
	}

	public void setEmailBasedRecipientInternal(
			List<EmailBasedRecipient> emailBasedRecipient) {
		lazyListHelper.setInternalList(EmailBasedRecipient.class, emailBasedRecipient);
	}
	
	@Transient
	public List<EmailBasedRecipient> getEmailBasedRecipient() {
		return lazyListHelper.getLazyList(EmailBasedRecipient.class);
	}

	public void setEmailBasedRecipient(
			List<EmailBasedRecipient> emailBasedRecipient) {
	}
	
	
	@OneToMany (fetch=FetchType.LAZY)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN})	
    @JoinColumn(name = "NOTIFICATIONS_ID", nullable=false)
    @Where(clause = "DTYPE = 'RR' and retired_indicator  = 'false'")
	public List<RoleBasedRecipient> getRoleBasedRecipientInternal() {
		return lazyListHelper.getInternalList(RoleBasedRecipient.class);
	}

	public void setRoleBasedRecipientInternal(
			List<RoleBasedRecipient> roleBasedRecipient) {
		lazyListHelper.setInternalList(RoleBasedRecipient.class, roleBasedRecipient);
	}
	
	@Transient
	public List<RoleBasedRecipient> getRoleBasedRecipient() {
		return lazyListHelper.getLazyList(RoleBasedRecipient.class);
	}

	public void setRoleBasedRecipient(
			List<RoleBasedRecipient> roleBasedRecipient) {
	}

	public Integer getThreshold() {
		return threshold;
	}

	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}

	@Transient
	public String getEmailAddresses(){
		String emailAddresses = "";
		for(EmailBasedRecipient eRec : getEmailBasedRecipient() ){
			emailAddresses += eRec.getEmailAddress() + " <br/> ";
		}
		return emailAddresses;
	}
	
	@Transient
	public String getRoles(){
		String roles = "";
		for(RoleBasedRecipient rRec : getRoleBasedRecipient() ){
			roles += rRec.getRole() + " <br/> ";
		}
		return roles;
	}	
	
}
