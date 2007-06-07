package edu.duke.cabig.c3pr.utils.web.spring.tabbedflow;

import org.springframework.validation.Errors;

import java.util.Map;
import java.util.HashMap;

/**
 * @author Rhett Sutphin
*/
public class Tab<C> {
    private Integer number;
    private String longTitle;
    private String shortTitle;
    private String viewName;
    private String display="true";
    private String showSummary="true";
    private String showLink="true";
    private String subFlow="false";

    public String getSubFlow() {
		return subFlow;
	}
	public void setSubFlow(String subFlow) {
		this.subFlow = subFlow;
	}
	public Tab(String longTitle, String shortTitle, String viewName) {
        this.longTitle = longTitle;
        this.shortTitle = shortTitle;
        this.viewName = viewName;
    }
    public Tab(String longTitle, String shortTitle) {
        this.longTitle = longTitle;
        this.shortTitle = shortTitle;
    }
    public String getShowSummary() {
		return showSummary;
	}
	public void setShowSummary(String showSummary) {
		this.showSummary = showSummary;
	}
	public Tab(String longTitle, String shortTitle, String viewName,String display) {
        this.longTitle = longTitle;
        this.shortTitle = shortTitle;
        this.viewName = viewName;
        this.display=display;
    }

    ////// TEMPLATE METHODS

    public void validate(C command, Errors errors) {
    }

    public Map<String, Object> referenceData() {
        return new HashMap<String, Object>();
    }

    public Map<String, Object> referenceData(C command) {
        return referenceData();
    }

    public boolean isAllowDirtyForward() {
        return true;
    }

    public boolean isAllowDirtyBack() {
        return true;
    }

    public int getTargetNumber() {
        return getNumber() + 1;
    }

    ////// BEAN PROPERTIES

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getLongTitle() {
        return longTitle;
    }

    public void setLongTitle(String longTitle) {
        this.longTitle = longTitle;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
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
