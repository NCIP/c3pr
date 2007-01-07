package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Temporary domain object to hold values of static data loaded from CADsr
 * @author Priyatam
 *
 */
//TODO Remove this class when a service for loading static data from CADsr exists
public class CaDSRData {
	
	private String code;
	private String desc;
	List<CaDSRData> data = new ArrayList();
	
	public CaDSRData() {}
	

	public CaDSRData(String code, String desc)
	{
		this.code=code;
		this.desc=desc;		
	}
	
	public void addData(String code, String desc)
	{
		data.add(new CaDSRData(code, desc));
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getDesc(){
		return desc;
	}
		
	public void setDesc(String desc){
		this.desc=desc;
	}
	
	public List<CaDSRData> getData() {
		return data;
	}

	public void setData(List<CaDSRData> data) {
		this.data = data;
	}
}