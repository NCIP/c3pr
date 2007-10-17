package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue(value = "CR")
public class CalloutRandomization extends Randomization {

	private String calloutUrl;

	public String getCalloutUrl() {
		return calloutUrl;
	}

	public void setCalloutUrl(String calloutUrl) {
		this.calloutUrl = calloutUrl;
	}
	
	@Override
	@Transient
	public void setRetiredIndicatorAsTrue(){
		super.setRetiredIndicatorAsTrue();
	}
	
}
