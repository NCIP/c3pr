package edu.duke.cabig.c3pr.domain;




public class NotCountTowardsAccrualIndicator
{

	private Integer id;
	private String comments;
	private NotCountTowardsAccrualReason reason;
	private Boolean notCountTowardsAccrualFlag;
	
	
	
	public NotCountTowardsAccrualIndicator() {
	}

	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public NotCountTowardsAccrualReason getReason() {
		return reason;
	}
	public void setReason(NotCountTowardsAccrualReason reason) {
		this.reason = reason;
	}
	public Boolean isNotCountTowardsAccrualFlag()
	{
		return notCountTowardsAccrualFlag;
	}
	public void setNotCountTowardsAccrualFlag(Boolean notCountTowardsAccrualFlag)
	{
		this.notCountTowardsAccrualFlag = notCountTowardsAccrualFlag;
	}
}