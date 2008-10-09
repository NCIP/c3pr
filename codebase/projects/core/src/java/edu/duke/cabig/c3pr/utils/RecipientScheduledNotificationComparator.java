package edu.duke.cabig.c3pr.utils;

import java.util.Comparator;

import edu.duke.cabig.c3pr.domain.RecipientScheduledNotification;

public class RecipientScheduledNotificationComparator implements Comparator {
	
	public int compare(Object o1, Object o2){
		
		RecipientScheduledNotification rsn1 = (RecipientScheduledNotification)o1;
		RecipientScheduledNotification rsn2 = (RecipientScheduledNotification)o2;
	    // the following line will return a negative value, if one is smaller in size than two
	    if(rsn1.getScheduledNotification().getDateSent().compareTo(rsn2.getScheduledNotification().getDateSent()) < 0){
	    	return 1;
	    } else{
	    	return -1;
	    }
	  }

}
