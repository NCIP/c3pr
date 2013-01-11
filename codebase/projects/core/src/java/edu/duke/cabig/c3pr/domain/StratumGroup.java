/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

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
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

/**
 * The Class StratumGroup.
 */
@Entity
@Table(name = "stratum_groups")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "STRATUM_GROUPS_ID_SEQ") })
public class StratumGroup extends AbstractMutableDeletableDomainObject implements Comparable {

    /** The current position. */
    private Integer currentPosition;

    /** The stratum group number. */
    private Integer stratumGroupNumber;

    /** The lazy list helper. */
    private LazyListHelper lazyListHelper;
    
    /** The c3 pr exception helper. */
    private C3PRExceptionHelper c3PRExceptionHelper;

	/** The c3pr error messages. */
	private MessageSource c3prErrorMessages;

    /**
     * Instantiates a new stratum group.
     */
    public StratumGroup() {
        lazyListHelper = new LazyListHelper();
        lazyListHelper
                        .add(BookRandomizationEntry.class,
                                        new InstantiateFactory<BookRandomizationEntry>(
                                                        BookRandomizationEntry.class));
        lazyListHelper.add(StratificationCriterionAnswerCombination.class,
                        new InstantiateFactory<StratificationCriterionAnswerCombination>(
                                        StratificationCriterionAnswerCombination.class));
        currentPosition = new Integer(0);
        
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
		resourceBundleMessageSource.setBasename("error_messages_multisite");
		ResourceBundleMessageSource resourceBundleMessageSource1 = new ResourceBundleMessageSource();
		resourceBundleMessageSource1.setBasename("error_messages_c3pr");
		resourceBundleMessageSource1
				.setParentMessageSource(resourceBundleMessageSource);
		this.c3prErrorMessages = resourceBundleMessageSource1;
		this.c3PRExceptionHelper = new C3PRExceptionHelper(c3prErrorMessages);
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject#setRetiredIndicatorAsTrue()
     */
    @Override
    @Transient
    public void setRetiredIndicatorAsTrue() {
        super.setRetiredIndicatorAsTrue();
        List<StratificationCriterionAnswerCombination> scacList = this
                        .getStratificationCriterionAnswerCombinations();
        StratificationCriterionAnswerCombination scac;
        Iterator scacIter = scacList.iterator();
        while (scacIter.hasNext()) {
            scac = (StratificationCriterionAnswerCombination) scacIter.next();
            scac.setRetiredIndicatorAsTrue();
        }
    }

    /**
     * Gets the stratification criterion answer combination internal.
     * 
     * @return the stratification criterion answer combination internal
     */
    @OneToMany(fetch = FetchType.LAZY)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @JoinColumn(name = "STR_GRP_ID")
    public List<StratificationCriterionAnswerCombination> getStratificationCriterionAnswerCombinationInternal() {
        return lazyListHelper.getInternalList(StratificationCriterionAnswerCombination.class);
    }
    
    public void addStratificationCriterionAnswerCombination(StratificationCriterionAnswerCombination stratificationCriterionAnswerCombination){
    	getStratificationCriterionAnswerCombinations().add(stratificationCriterionAnswerCombination);
    }

    /**
     * Sets the stratification criterion answer combination internal.
     * 
     * @param combinationAnswers the new stratification criterion answer combination internal
     */
    public void setStratificationCriterionAnswerCombinationInternal(
                    List<StratificationCriterionAnswerCombination> combinationAnswers) {
        lazyListHelper.setInternalList(StratificationCriterionAnswerCombination.class,
                        combinationAnswers);
    }

    /**
     * Gets the stratification criterion answer combination.
     * 
     * @return the stratification criterion answer combination
     */
    @Transient
    public List<StratificationCriterionAnswerCombination> getStratificationCriterionAnswerCombinations() {
        return lazyListHelper.getLazyList(StratificationCriterionAnswerCombination.class);
    }


    /**
     * Gets the book randomization entry internal.
     * 
     * @return the book randomization entry internal
     */
    @OneToMany(mappedBy = "stratumGroup", fetch = FetchType.LAZY)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public List<BookRandomizationEntry> getBookRandomizationEntryInternal() {
        return lazyListHelper.getInternalList(BookRandomizationEntry.class);
    }
    
    public void addBookRandomizationEntry(BookRandomizationEntry bookRandomizationEntry){
    	getBookRandomizationEntry().add(bookRandomizationEntry);
    	bookRandomizationEntry.setStratumGroup(this);
    }

    /**
     * Sets the book randomization entry internal.
     * 
     * @param bookRandomizationEntry the new book randomization entry internal
     */
    public void setBookRandomizationEntryInternal(
                    List<BookRandomizationEntry> bookRandomizationEntry) {
        lazyListHelper.setInternalList(BookRandomizationEntry.class, bookRandomizationEntry);
    }

    /**
     * Gets the book randomization entry.
     * 
     * @return the book randomization entry
     */
    @Transient
    public List<BookRandomizationEntry> getBookRandomizationEntry() {
        return lazyListHelper.getLazyList(BookRandomizationEntry.class);
    }


    /**
     * Gets the current position.
     * 
     * @return the current position
     */
    public Integer getCurrentPosition() {
        return currentPosition;
    }

    /**
     * Sets the current position.
     * 
     * @param currentPosition the new current position
     */
    public void setCurrentPosition(Integer currentPosition) {
        this.currentPosition = currentPosition;
    }

    /**
     * Gets the stratum group number.
     * 
     * @return the stratum group number
     */
    public Integer getStratumGroupNumber() {
        return stratumGroupNumber;
    }

    /**
     * Sets the stratum group number.
     * 
     * @param stratumGroupNumber the new stratum group number
     */
    public void setStratumGroupNumber(Integer stratumGroupNumber) {
        this.stratumGroupNumber = stratumGroupNumber;
    }

    /**
     * Gets the answer combinations.
     * 
     * @return the answer combinations
     */
    @Transient
    public String getAnswerCombinations() {
        String result = "";
        for(StratificationCriterionAnswerCombination stratificationCriterionAnswerCombination : this.getStratificationCriterionAnswerCombinations()){
        	result = result + ", " + stratificationCriterionAnswerCombination.getStratificationCriterionPermissibleAnswer().getPermissibleAnswer() ; 
        }
        //removes the extra leading comma sign.
        if(result.length() != 0 ){
        	result = result.substring(2);
        }
        return result;
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject#hashCode()
     */
    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        int computedSum = 0;
        for(StratificationCriterionAnswerCombination stratificationCriterionAnswerCombination : this.getStratificationCriterionAnswerCombinations()){
        	 computedSum += stratificationCriterionAnswerCombination.hashCode(); 
        }
        result = PRIME * result + computedSum;
        return result;
    }

    /*
     * NOTE: As per this method two Stratum Groups are considered equal if they have the same
     * question/answer combination. In other words if they have the same
     * stratification_cri_ans_combination.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (getClass() != obj.getClass()) return false;
        if (obj instanceof StratumGroup) {
            StratumGroup sg = (StratumGroup) obj;
            List<StratificationCriterionAnswerCombination> scacList;
            StratificationCriterionAnswerCombination scac;

            scacList = sg.getStratificationCriterionAnswerCombinations();
            Iterator iter = scacList.iterator();
            while (iter.hasNext()) {
                scac = (StratificationCriterionAnswerCombination) iter.next();
                if (this.getStratificationCriterionAnswerCombinations().contains(scac)) {
                    continue;
                }
                else {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /*
     * Basic toString method which is open to modifications.
     */
    @Override
    public String toString() {
        return this.getStratumGroupNumber() + ":" + this.getAnswerCombinations();
    }

    /**
     * Gets the next arm.
     * 
     * @return the next arm
     */
    @Transient
    public Arm getNextArm() {
        Iterator<BookRandomizationEntry> iter = getBookRandomizationEntry().iterator();
        BookRandomizationEntry breTemp;
        Arm arm = null;
        while (iter.hasNext()) {
            breTemp = iter.next();
            if (breTemp.getPosition().equals(this.currentPosition)) {
                synchronized (this) {
                    this.currentPosition++;
                    arm = breTemp.getArm();
                    break;
                }
            }
        }
        if (arm == null) {
        	throw getC3PRExceptionHelper().getRuntimeException(
                    getCode("C3PR.EXCEPTION.REGISTRATION.NO.ARM.AVAILABLE.BOOK.EXHAUSTED.CODE"));
        }
        return arm;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Transient
    public StratumGroup clone() {
        StratumGroup sgClone = new StratumGroup();
        sgClone.getStratificationCriterionAnswerCombinations().addAll(
                        this.getStratificationCriterionAnswerCombinations());
        sgClone.setStratumGroupNumber(this.getStratumGroupNumber());
        return sgClone;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Transient
    public int compareTo(Object obj) throws ClassCastException {
        StratumGroup sg = (StratumGroup) obj;
        return this.stratumGroupNumber - sg.getStratumGroupNumber();
    }
    
    /**
     * Gets the c3pr exception helper.
     * 
     * @return the c3pr exception helper
     */
    @Transient
	public C3PRExceptionHelper getC3PRExceptionHelper() {
		return c3PRExceptionHelper;
	}

	/**
	 * Sets the c3 pr exception helper.
	 * 
	 * @param exceptionHelper the new c3 pr exception helper
	 */
	public void setC3PRExceptionHelper(C3PRExceptionHelper exceptionHelper) {
		this.c3PRExceptionHelper = exceptionHelper;
	}

	/**
	 * Gets the code.
	 * 
	 * @param errortypeString the errortype string
	 * 
	 * @return the code
	 */
	@Transient
	public int getCode(String errortypeString) {
		return Integer.parseInt(this.c3prErrorMessages.getMessage(
				errortypeString, null, null));
	}

	/**
	 * Gets the c3pr error messages.
	 * 
	 * @return the c3pr error messages
	 */
	@Transient
	public MessageSource getC3prErrorMessages() {
		return c3prErrorMessages;
	}

	/**
	 * Sets the c3pr error messages.
	 * 
	 * @param errorMessages the new c3pr error messages
	 */
	public void setC3prErrorMessages(MessageSource errorMessages) {
		c3prErrorMessages = errorMessages;
	}

}
