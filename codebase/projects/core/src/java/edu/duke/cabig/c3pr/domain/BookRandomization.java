/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import java.util.Iterator;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.apache.commons.collections15.functors.InstantiateFactory;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

/**
 * The Class BookRandomization.
 */
@Entity
@DiscriminatorValue(value = "BR")
public class BookRandomization extends Randomization {

    /** The lazy list helper. */
    private LazyListHelper lazyListHelper;

    /**
     * Instantiates a new book randomization.
     */
    public BookRandomization() {
        lazyListHelper = new LazyListHelper();
        lazyListHelper.add(BookRandomizationEntry.class,
                            new InstantiateFactory<BookRandomizationEntry>(
                                        BookRandomizationEntry.class));
    }

    /**
     * Gets the book randomization entry internal.
     * 
     * @return the book randomization entry internal
     */
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval=true)
    @JoinColumn(name = "rndm_id", nullable = false)
    @Cascade(value = { CascadeType.ALL})
    @OrderBy("stratumGroup, position")
    public List<BookRandomizationEntry> getBookRandomizationEntryInternal() {
        return lazyListHelper.getInternalList(BookRandomizationEntry.class);
    }
    
    public void addBookRandomizationEntry(BookRandomizationEntry bookRandomizationEntry){
    	getBookRandomizationEntry().add(bookRandomizationEntry);
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
     * @see edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject#setRetiredIndicatorAsTrue()
     * sets the retiredIndicator for the bookEntries also.
     * 
     */
    @Override
    @Transient
    public void setRetiredIndicatorAsTrue() {
        super.setRetiredIndicatorAsTrue();
        List<BookRandomizationEntry> breList = this.getBookRandomizationEntry();
        
        BookRandomizationEntry bre;
        Iterator breIter = breList.iterator();
        while (breIter.hasNext()) {
            bre = (BookRandomizationEntry) breIter.next();
            bre.setRetiredIndicatorAsTrue();
        }
    }

}
