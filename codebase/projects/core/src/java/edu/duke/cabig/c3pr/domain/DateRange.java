package edu.duke.cabig.c3pr.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateRange {

	private Date startDate;
	
	private Date endDate;

	public DateRange(Date startDate, Date endDate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Override
	public String toString() {
		String dateStr = "";
		if(startDate != null){
			dateStr = new SimpleDateFormat("MM/dd/yyyy").format(startDate);
		}
		if(endDate != null){
			if(dateStr.equals("")){
				dateStr = new SimpleDateFormat("MM/dd/yyyy").format(endDate);
			}else{
				dateStr += " - " + new SimpleDateFormat("MM/dd/yyyy").format(endDate);				
			}
		}
		return dateStr;
	}
}
