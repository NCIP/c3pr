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
		lazyListHelper.add(EmailBasedRecepient.class,
				new InstantiateFactory<EmailBasedRecepient>(EmailBasedRecepient.class));
		lazyListHelper.add(RoleBasedRecepient.class,
				new InstantiateFactory<RoleBasedRecepient>(RoleBasedRecepient.class));
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
	public List<EmailBasedRecepient> getEmailBasedRecepientInternal() {
		return lazyListHelper.getInternalList(EmailBasedRecepient.class);
	}

	public void setEmailBasedRecepientInternal(
			List<EmailBasedRecepient> emailBasedRecepient) {
		lazyListHelper.setInternalList(EmailBasedRecepient.class, emailBasedRecepient);
	}
	
	@Transient
	public List<EmailBasedRecepient> getEmailBasedRecepient() {
		return lazyListHelper.getLazyList(EmailBasedRecepient.class);
	}

	public void setEmailBasedRecepient(
			List<EmailBasedRecepient> emailBasedRecepient) {
	}
	
	
	@OneToMany (fetch=FetchType.LAZY)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN})	
    @JoinColumn(name = "NOTIFICATIONS_ID", nullable=false)
    @Where(clause = "DTYPE = 'RR' and retired_indicator  = 'false'")
	public List<RoleBasedRecepient> getRoleBasedRecepientInternal() {
		return lazyListHelper.getInternalList(RoleBasedRecepient.class);
	}

	public void setRoleBasedRecepientInternal(
			List<RoleBasedRecepient> roleBasedRecepient) {
		lazyListHelper.setInternalList(RoleBasedRecepient.class, roleBasedRecepient);
	}
	
	@Transient
	public List<RoleBasedRecepient> getRoleBasedRecepient() {
		return lazyListHelper.getLazyList(RoleBasedRecepient.class);
	}

	public void setRoleBasedRecepient(
			List<RoleBasedRecepient> roleBasedRecepient) {
	}

	public Integer getThreshold() {
		return threshold;
	}

	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}

}
