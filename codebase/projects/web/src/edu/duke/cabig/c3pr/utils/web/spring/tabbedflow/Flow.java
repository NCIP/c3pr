package edu.duke.cabig.c3pr.utils.web.spring.tabbedflow;

import java.util.List;
import java.util.LinkedList;

/**
 * @author Rhett Sutphin
 */
public class Flow<C> {
    private String name;
    private List<Tab<C>> tabs;

    public Flow(String name) {
        this.name = name;
        this.tabs = new LinkedList<Tab<C>>();
    }

    public void addTab(Tab<C> tab) {
        tab.setNumber(tabs.size());
        this.tabs.add(tab);
    }

    public int getTabCount() {
        return getTabs().size();
    }

    public Tab<C> getTab(int number) {
        return getTabs().get(number);
    }

    public List<Tab<C>> getTabs() {
        return tabs;
    }

    public String getName() {
        return name;
    }

    ////// OBJECT METHODS

    public String toString() {
        return new StringBuilder(getClass().getSimpleName())
            .append('[').append(getName()).append(']')
            .toString();
    }
    public Flow createAlternateFlow(List order){
    	Flow ret=new Flow(name);
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
