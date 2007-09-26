package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author Kulasekaran
 * @version 1.0
 */
@Entity
@Table (name="anatomic_sites")
@GenericGenerator(name="id-generator", strategy = "native",
    parameters = {
        @Parameter(name="sequence", value="anatomic_sites_id_seq")
    }
)
public class AnatomicSite extends AbstractMutableDeletableDomainObject
{			
	private String name;
	private String category;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}	
}
