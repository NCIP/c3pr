package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import gov.nih.nci.cabig.ctms.collections.LazyListHelper;
import edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject;

import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections15.functors.InstantiateFactory;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "stratum_groups")
@GenericGenerator(name = "id-generator", strategy = "native",
        parameters = {@Parameter(name = "sequence", value = "STRATUM_GROUPS_ID_SEQ")})
public class StratumGroup extends AbstractMutableDeletableDomainObject implements Comparable{
	
	private Integer currentPosition;
	private Integer stratumGroupNumber;
	private LazyListHelper lazyListHelper;

	public StratumGroup(){
		lazyListHelper = new LazyListHelper();
		lazyListHelper.add(BookRandomizationEntry.class,
				new InstantiateFactory<BookRandomizationEntry>(BookRandomizationEntry.class));
    	lazyListHelper.add(StratificationCriterionAnswerCombination.class,new InstantiateFactory<StratificationCriterionAnswerCombination>(
    			StratificationCriterionAnswerCombination.class));
    	currentPosition = new Integer(0);
	}
	 
	@Override
	@Transient
	public void setRetiredIndicatorAsTrue(){
		super.setRetiredIndicatorAsTrue();
		List<StratificationCriterionAnswerCombination> scacList = this.getStratificationCriterionAnswerCombination();
		StratificationCriterionAnswerCombination scac;
		Iterator scacIter = scacList.iterator();
		while(scacIter.hasNext()){
			scac = (StratificationCriterionAnswerCombination)scacIter.next();
			scac.setRetiredIndicatorAsTrue();
		}
	}
	
	@OneToMany (fetch=FetchType.LAZY)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN})	
    @JoinColumn(name = "STR_GRP_ID")
	public List<StratificationCriterionAnswerCombination> getStratificationCriterionAnswerCombinationInternal() {
		return lazyListHelper.getInternalList(StratificationCriterionAnswerCombination.class);
	}

	public void setStratificationCriterionAnswerCombinationInternal(
			List<StratificationCriterionAnswerCombination> combinationAnswers) {
		lazyListHelper.setInternalList(StratificationCriterionAnswerCombination.class, combinationAnswers);
	}
	
	@Transient
	public List<StratificationCriterionAnswerCombination> getStratificationCriterionAnswerCombination() {
		return lazyListHelper.getLazyList(StratificationCriterionAnswerCombination.class);
	}

	public void setStratificationCriterionAnswerCombination(
			List<StratificationCriterionAnswerCombination> combinationAnswers) {
	}
	
    @OneToMany (mappedBy = "stratumGroup", fetch=FetchType.LAZY)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN})	
	public List<BookRandomizationEntry> getBookRandomizationEntryInternal() {
		return lazyListHelper.getInternalList(BookRandomizationEntry.class);
	}

	public void setBookRandomizationEntryInternal(
			List<BookRandomizationEntry> bookRandomizationEntry) {
		lazyListHelper.setInternalList(BookRandomizationEntry.class, bookRandomizationEntry);
	}
	
	@Transient
	public List<BookRandomizationEntry> getBookRandomizationEntry() {
		return lazyListHelper.getLazyList(BookRandomizationEntry.class);
	}

	public void setBookRandomizationEntry(
			List<BookRandomizationEntry> bookRandomizationEntry) {
	
	}	

	public Integer getCurrentPosition() {
		return currentPosition;
	}


	public void setCurrentPosition(Integer currentPosition) {
		this.currentPosition = currentPosition;
	}


	public Integer getStratumGroupNumber() {
		return stratumGroupNumber;
	}


	public void setStratumGroupNumber(Integer stratumGroupNumber) {
		this.stratumGroupNumber = stratumGroupNumber;
	}


	@Transient
	public String getAnswerCombinations(){
		String result = "";
		Iterator iter = getStratificationCriterionAnswerCombination().iterator();
		while(iter.hasNext()){
			result+=" - "+((StratificationCriterionAnswerCombination)iter.next()).getStratificationCriterionPermissibleAnswer().getPermissibleAnswer();			
		}
		result=result.substring(3);
		return result;		
	}
	
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		int computedSum = 0;
		Iterator iter = getStratificationCriterionAnswerCombination().iterator();
		while(iter.hasNext()){
			computedSum += ((StratificationCriterionAnswerCombination)iter.next()).hashCode();
		}
		result = PRIME
				* result
				+ computedSum;
		return result;
	}

	/*
	 * NOTE: As per this method two Stratum Groups are considered equal if they have the same question/answer combination.
	 * In other words if they have the same stratification_cri_ans_combination.
	 */
	@Override	
	public boolean equals(Object obj){
		if (this == obj)
			return true;
//		if (!super.equals(obj))
//			return false;
		if (getClass() != obj.getClass())
			return false;
		if(obj instanceof StratumGroup){
			StratumGroup sg = (StratumGroup)obj;
			List <StratificationCriterionAnswerCombination>scacList;
			StratificationCriterionAnswerCombination scac;
			
			scacList = sg.getStratificationCriterionAnswerCombination();
			Iterator iter = scacList.iterator();
			while(iter.hasNext()){
				scac = (StratificationCriterionAnswerCombination)iter.next();
				if(this.getStratificationCriterionAnswerCombination().contains(scac)){
					continue;
				} else {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	/*
	 * very basic toString method which is open to modifications.
	 */
	@Override
	public String toString(){
		return this.getStratumGroupNumber() +":"+ this.getAnswerCombinations();
	}
	
	/*
	 * 
	 */
	@Transient
	public Arm getNextArm() throws C3PRBaseException{
		Iterator <BookRandomizationEntry>iter = getBookRandomizationEntry().iterator();
		BookRandomizationEntry breTemp;
		Arm arm=null;
		while(iter.hasNext()){
			breTemp = iter.next(); 
			if(breTemp.getPosition().equals(this.currentPosition)){
				synchronized(this){
					this.currentPosition++;
					arm= breTemp.getArm();
					break;
				}
			}
		}
		if(arm==null){
			throw new C3PRBaseException("No Arm avalable for this stratum group. Maybe the Randomization Book is exhausted");
		}
		return arm;		
	}
	
	@Transient
	public StratumGroup clone(){
		StratumGroup sgClone = new StratumGroup();
		sgClone.getStratificationCriterionAnswerCombination().addAll(this.getStratificationCriterionAnswerCombination());
		sgClone.setStratumGroupNumber(this.getStratumGroupNumber());
		return sgClone;
	}
	
	@Transient
	public int compareTo(Object obj) throws ClassCastException{
		StratumGroup sg = (StratumGroup)obj;		
		return this.stratumGroupNumber - sg.getStratumGroupNumber();		
	}

}
