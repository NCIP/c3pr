package edu.duke.cabig.c3pr.xml;

import java.util.List;

import edu.duke.cabig.c3pr.domain.Identifier;

public class IdentifiersHolder {

	private List<Identifier> identifiers;

	public IdentifiersHolder() {
		
	}
	
	public IdentifiersHolder(List<Identifier> identifiers) {
		this.setIdentifiers(identifiers);
	}
	
	public List<Identifier> getIdentifiers() {
		return identifiers;
	}

	public void setIdentifiers(List<Identifier> identifiers) {
		this.identifiers = identifiers;
	}
}
