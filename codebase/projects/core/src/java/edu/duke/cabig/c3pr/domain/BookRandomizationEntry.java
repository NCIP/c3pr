package edu.duke.cabig.c3pr.domain;

import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "book_randomization_entry")
@GenericGenerator(name = "id-generator", strategy = "native",
        parameters = {
        @Parameter(name = "sequence", value = "BOOK_RANDOMIZATION_ENTRY_ID_SEQ")
                }
)
public class BookRandomizationEntry extends AbstractMutableDomainObject{
	
	private int position;
	private Arm arm;
	private StratumGroup stratumGroup;
	
	@ManyToOne
	@JoinColumn(name = "ARMS_ID")
	@Cascade(value = { CascadeType.SAVE_UPDATE, CascadeType.MERGE})	
	public Arm getArm() {
		return arm;
	}
	public void setArm(Arm arm) {
		this.arm = arm;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	
	@ManyToOne
	@JoinColumn(name = "str_grp_id", nullable=false)
	@Cascade(value = { CascadeType.SAVE_UPDATE, CascadeType.MERGE})
	public StratumGroup getStratumGroup() {
		return stratumGroup;
	}
	public void setStratumGroup(StratumGroup stratumGroup) {
		this.stratumGroup = stratumGroup;
	} 
}
