package edu.duke.cabig.c3pr.utils.web.spring.tabbedflow;

import gov.nih.nci.cabig.ctms.web.tabs.Tab;

/**
 * @author Rhett Sutphin
*/
public abstract class SubFlowTab<C> extends AjaxableTab<C>{

    private String display="true";
    private String showSummary="true";
    private String showLink="true";
    private String subFlow="false";

    
	public SubFlowTab() {
		super();
	}
	public SubFlowTab(String longTitle, String shortTitle, String viewName) {
		super(longTitle, shortTitle, viewName);
    }
    public SubFlowTab(String longTitle, String shortTitle) {
    	super(longTitle, shortTitle, "");
    }
	public SubFlowTab(String longTitle, String shortTitle, String viewName,String display) {
		super(longTitle, shortTitle, viewName);
		this.display=display;
    }
    ////// BEAN PROPERTIES
    public String getShowSummary() {
		return showSummary;
	}
	public void setShowSummary(String showSummary) {
		this.showSummary = showSummary;
	}

	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public String getShowLink() {
		return showLink;
	}
	public void setShowLink(String showLink) {
		this.showLink = showLink;
	}
    public String getSubFlow() {
		return subFlow;
	}
	public void setSubFlow(String subFlow) {
		this.subFlow = subFlow;
	}
}
