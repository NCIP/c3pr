package edu.duke.cabig.c3pr.xml;

import edu.duke.cabig.c3pr.constants.RandomizationType;

public class RandomizationHolder {

	private RandomizationType randomizationType;
	
	private String phoneNumber;

	public RandomizationType getRandomizationType() {
		return randomizationType;
	}

	public void setRandomizationType(RandomizationType randomizationType) {
		this.randomizationType = randomizationType;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
