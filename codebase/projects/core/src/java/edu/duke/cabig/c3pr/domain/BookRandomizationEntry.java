package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "book_rndm_entry")
@GenericGenerator(name = "id-generator", strategy = "native",
        parameters = {
        @Parameter(name = "sequence", value = "book_rndm_entry_ID_SEQ")
                }
)
public class BookRandomizationEntry extends AbstractMutableDeletableDomainObject{
	
	private Integer position;
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
	
	@ManyToOne
	@JoinColumn(name = "str_grp_id", nullable=false)
	@Cascade(value = { CascadeType.SAVE_UPDATE, CascadeType.MERGE})
	@OrderBy("stratumGroupNumber")
	public StratumGroup getStratumGroup() {
		return stratumGroup;
	}
	public void setStratumGroup(StratumGroup stratumGroup) {
		this.stratumGroup = stratumGroup;
	} 
	
	@Override
	@Transient
	public void setRetiredIndicatorAsTrue(){
		super.setRetiredIndicatorAsTrue();		
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	public Integer getPosition() {
		return position;
	}
}
