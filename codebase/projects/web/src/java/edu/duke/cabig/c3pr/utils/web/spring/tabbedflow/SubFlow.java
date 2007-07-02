package edu.duke.cabig.c3pr.utils.web.spring.tabbedflow;

import gov.nih.nci.cabig.ctms.web.tabs.Flow;

import java.util.List;

/**
 * @author Rhett Sutphin
 */
public class SubFlow<C> extends Flow<C>{
    public SubFlow(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public SubFlow createAlternateFlow(List order){
    	SubFlow ret=new SubFlow(getName());
    	for(int i=0 ; i<order.size() ; i++){
    		for(int j=0 ; j<getTabs().size() ; j++){
    			if(((String)order.get(i)).equalsIgnoreCase(getTab(j).getShortTitle())){
    				ret.addTab(getTab(j));
    			}
    		}
    	}
    	return ret;
    }
}
