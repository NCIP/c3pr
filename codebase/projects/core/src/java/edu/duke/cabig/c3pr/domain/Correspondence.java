package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.constants.AMPMEnum;
import edu.duke.cabig.c3pr.constants.CorrespondencePurpose;
import edu.duke.cabig.c3pr.constants.CorrespondenceType;
import edu.duke.cabig.c3pr.constants.TimeZoneEnum;
import edu.duke.cabig.c3pr.domain.factory.ParameterizedInstantiateFactory;
import edu.duke.cabig.c3pr.utils.DateUtil;
import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

@Entity
@Table(name="correspondences")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "correspondences_id_seq") })
public class Correspondence extends AbstractMutableDeletableDomainObject{
	
	private String action;
	private String notes;
	private CorrespondencePurpose purpose;
	private String text;
	private Date time;
	private CorrespondenceType type;
	private Boolean resolved;
	private Boolean followUpNeeded;
	private PersonUser personSpokenTo;
	private TimeZoneEnum timeZone = TimeZoneEnum.EASTERN;
	private int startTimeHours;
	private int startTimeMinutes;
	private AMPMEnum startTimeAmPm;
	private int endTimeHours;
	private int endTimeMinutes;
	private AMPMEnum endTimeAmPm;
	private List<PersonUser> notifiedStudyPersonnel = new ArrayList<PersonUser>();
	private LazyListHelper lazyListHelper;

	public Correspondence(){
	lazyListHelper = new LazyListHelper();
	lazyListHelper.add(PersonUser.class,new ParameterizedInstantiateFactory<PersonUser>(PersonUser.class));
		
	}
	
	@OneToMany
	@Cascade(value={CascadeType.LOCK})
	@JoinTable(name="corr_per_users_assoc",joinColumns=@JoinColumn(name="corr_id"),
			inverseJoinColumns=@JoinColumn(name="pers_user_id"))
	public List<PersonUser> getNotifiedStudyPersonnelInternal() {
		return lazyListHelper.getInternalList(PersonUser.class);
	}
	
	@Transient
	public List<PersonUser> getNotifiedStudyPersonnel() {
		return lazyListHelper.getLazyList(PersonUser.class);
	}
	
	public void setNotifiedStudyPersonnelInternal(List<PersonUser> notifiedStudyPersonnel) {
		lazyListHelper.setInternalList(PersonUser.class, notifiedStudyPersonnel);
	}

	public void setNotifiedStudyPersonnel(List<PersonUser> notifiedStudyPersonnel) {
		setNotifiedStudyPersonnelInternal(notifiedStudyPersonnel);
	}

	@Enumerated(EnumType.STRING)
	public TimeZoneEnum getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(TimeZoneEnum timeZone) {
		this.timeZone = timeZone;
	}

	public int getStartTimeHours() {
		return startTimeHours;
	}

	public void setStartTimeHours(int startTimeHours) {
		this.startTimeHours = startTimeHours;
	}

	public int getStartTimeMinutes() {
		return startTimeMinutes;
	}

	public void setStartTimeMinutes(int startTimeMinutes) {
		this.startTimeMinutes = startTimeMinutes;
	}

	@Enumerated(EnumType.STRING)
	public AMPMEnum getStartTimeAmPm() {
		return startTimeAmPm;
	}

	public void setStartTimeAmPm(AMPMEnum startTimeAmPm) {
		this.startTimeAmPm = startTimeAmPm;
	}

	public int getEndTimeHours() {
		return endTimeHours;
	}

	public void setEndTimeHours(int endTimeHours) {
		this.endTimeHours = endTimeHours;
	}

	public int getEndTimeMinutes() {
		return endTimeMinutes;
	}

	public void setEndTimeMinutes(int endTimeMinutes) {
		this.endTimeMinutes = endTimeMinutes;
	}

	@Enumerated(EnumType.STRING)
	public AMPMEnum getEndTimeAmPm() {
		return endTimeAmPm;
	}

	public void setEndTimeAmPm(AMPMEnum endTimeAmPm) {
		this.endTimeAmPm = endTimeAmPm;
	}

	
	public Boolean getResolved() {
		return resolved;
	}

	public void setResolved(Boolean resolved) {
		this.resolved = resolved;
	}

	public Boolean getFollowUpNeeded() {
		return followUpNeeded;
	}

	public void setFollowUpNeeded(Boolean followUpNeeded) {
		this.followUpNeeded = followUpNeeded;
	}

	@ManyToOne
	@JoinColumn(name="person_spoken_to_id")
	public PersonUser getPersonSpokenTo() {
		return personSpokenTo;
	}

	public void setPersonSpokenTo(PersonUser personSpokenTo) {
		this.personSpokenTo = personSpokenTo;
	}

	
	public String getAction() {
		return action;
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	@Enumerated(EnumType.STRING)
	public CorrespondencePurpose getPurpose() {
		return purpose;
	}
	
	public void setPurpose(CorrespondencePurpose purpose) {
		this.purpose = purpose;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getTime() {
		return time;
	}
	
	@Transient
	public String getTimeStr() {
		if (time == null ||time.equals("") ) {
			return "";
		}
		return DateUtil.formatDate(time, "MM/dd/yyyy");
	}
	
	public void setTime(Date time) {
		this.time = time;
	}
	
	@Enumerated(EnumType.STRING)
	public CorrespondenceType getType() {
		return type;
	}
	
	public void setType(CorrespondenceType type) {
		this.type = type;
	}
	
	@Transient
	public String getStartTimeStr(){
		StringBuilder sb = new StringBuilder();
		if(this.getStartTimeHours() < 10){
			sb.append("0");
		}
		sb.append(this.getStartTimeHours());
    	sb.append(":");
    	if(this.getStartTimeMinutes() < 10){
			sb.append("0");
		}
    	sb.append(this.getStartTimeMinutes());
    	sb.append(" ");
    	sb.append(this.getStartTimeAmPm().getCode());
    	sb.append(" ");
    	sb.append(this.getTimeZone().getDisplayName());
		return sb.toString();
	}
	
	@Transient
	public String getEndTimeStr(){
		StringBuilder sb = new StringBuilder();
		if(this.getEndTimeHours() < 10){
			sb.append("0");
		}
		sb.append(this.getEndTimeHours());
    	sb.append(":");
    	if(this.getEndTimeMinutes() < 10){
			sb.append("0");
		}
    	sb.append(this.getEndTimeMinutes());
    	sb.append(" ");
    	sb.append(this.getEndTimeAmPm().getCode());
    	sb.append(" ");
    	sb.append(this.getTimeZone().getDisplayName());
		return sb.toString();
	}
	
}
