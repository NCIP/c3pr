/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.report;

public class AdvancedSearchColumn {
	
private String columnHeader;
	
	private Object value;
	
	public void setValue(Object value){
		this.value = value;
	}
	
	public Object getValue(){
		return value;
	}
	
	public void setColumnHeader(String columnHeader){
		this.columnHeader = columnHeader;
	}
	
	public String getColumnHeader(){
		return columnHeader;
	}


}
