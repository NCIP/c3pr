package edu.duke.cabig.c3pr.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "consent_questions")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "CONSENT_QUESTIONS_ID_SEQ") })
public class ConsentQuestion extends AbstractMutableDeletableDomainObject {
	
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
