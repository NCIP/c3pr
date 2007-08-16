package edu.duke.cabig.c3pr.domain;

import gov.nih.nci.cabig.ctms.collections.LazyListHelper;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

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
@Table(name = "stratum_group")
@GenericGenerator(name = "id-generator", strategy = "native",
        parameters = {@Parameter(name = "sequence", value = "STRATUM_GROUP_ID_SEQ")})
public class StratumGroup extends AbstractMutableDomainObject{
	
	private int currentPosition;
	private int stratumGroupNumber;
	private LazyListHelper lazyListHelper;

	public StratumGroup(){
		lazyListHelper = new LazyListHelper();
		lazyListHelper.add(BookRandomizationEntry.class,
				new InstantiateFactory<BookRandomizationEntry>(BookRandomizationEntry.class));
    	lazyListHelper.add(StratificationCriterionAnswerCombination.class,new InstantiateFactory<StratificationCriterionAnswerCombination>(
    			StratificationCriterionAnswerCombination.class));
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

	public int getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(int currentPosition) {
		this.currentPosition = currentPosition;
	}

	public int getStratumGroupNumber() {
		return stratumGroupNumber;
	}

	public void setStratumGroupNumber(int stratumGroupNumber) {
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

}
