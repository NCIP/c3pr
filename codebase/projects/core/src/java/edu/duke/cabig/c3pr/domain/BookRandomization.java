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

@Entity
@DiscriminatorValue(value = "BR")
public class BookRandomization extends Randomization {
	
	private LazyListHelper lazyListHelper;

	public BookRandomization(){
		lazyListHelper = new LazyListHelper();
		lazyListHelper.add(BookRandomizationEntry.class,
				new InstantiateFactory<BookRandomizationEntry>(BookRandomizationEntry.class));
	}

	@OneToMany (fetch=FetchType.LAZY)
	@JoinColumn(name = "rndm_id", nullable=false)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN})
    @OrderBy("stratumGroup, position")
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
	
	@Override
	@Transient
	public void setRetiredIndicatorAsTrue(){
		super.setRetiredIndicatorAsTrue();
		List<BookRandomizationEntry> breList = this.getBookRandomizationEntry();;
		BookRandomizationEntry bre;
		Iterator breIter = breList.iterator();
		while(breIter.hasNext()){
			bre = (BookRandomizationEntry)breIter.next();
			bre.setRetiredIndicatorAsTrue();
		}		
	}

}
