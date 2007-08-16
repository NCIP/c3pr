package edu.duke.cabig.c3pr.utils.web.spring.tabbedflow;

/**
 * @author Rhett Sutphin
*/
public abstract class WorkFlowTab<C> extends ReflexiveAjaxableTab<C>{

    private String display="true";
    private String showSummary="true";
    private String showLink="true";
    
    public WorkFlowTab(){
    	
    }
    
	public WorkFlowTab(String longTitle, String shortTitle, String viewName) {
		super(longTitle, shortTitle, viewName);
    }

	public WorkFlowTab(String longTitle, String shortTitle, String viewName, Class[] params) {
		super(longTitle, shortTitle, viewName, params);
    }

	public WorkFlowTab(String longTitle, String shortTitle, String viewName,String display) {
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
}
