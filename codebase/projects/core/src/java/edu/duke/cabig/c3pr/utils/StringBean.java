package edu.duke.cabig.c3pr.utils;

/**
 * A Bean wrapped around a String
 * @author priyatam
 *
 */
public class StringBean {
	String str;
		
	public StringBean(){}
	
	public StringBean(String str) {this.str = str;}
	
	public String getStr(){
		return str;
	}
	
	public void setStr(String str){
		this.str=str;
	}
}
