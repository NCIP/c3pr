package edu.duke.cabig.c3pr.web.report;

public class ViewColumn {
	
	private Object value;
	
	private String columnTitle;
	
	
	public String getColumnTitle() {
		return columnTitle;
	}

	public void setColumnTitle(String columnTitle) {
		this.columnTitle = columnTitle;
	}

	public void setValue(Object value){
		this.value = value;
	}
	
	public Object getValue(){
		return value;
	}
}
