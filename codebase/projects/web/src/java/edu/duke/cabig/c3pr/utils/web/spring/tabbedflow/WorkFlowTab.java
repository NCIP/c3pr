/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.utils.web.spring.tabbedflow;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rhett Sutphin
 */
public abstract class WorkFlowTab<C> extends RowManagableTab<C> {

    private String display = "true";

    private String showSummary = "true";

    private String showLink = "true";
    
    private Boolean willSave = true;

    public Boolean getWillSave() {
        return willSave;
    }

    public void setWillSave(Boolean willSave) {
        this.willSave = willSave;
    }

    public WorkFlowTab() {

    }

    public WorkFlowTab(String longTitle, String shortTitle, String viewName) {
        super(longTitle, shortTitle, viewName);
    }
    
    public WorkFlowTab(String longTitle, String shortTitle, String viewName, Boolean willSave) {
        super(longTitle, shortTitle, viewName);
        this.willSave=willSave;
    }

    public WorkFlowTab(String longTitle, String shortTitle, String viewName, Class[] params) {
        super(longTitle, shortTitle, viewName, params);
    }

    public WorkFlowTab(String longTitle, String shortTitle, String viewName, String display) {
        super(longTitle, shortTitle, viewName);
        this.display = display;
    }

    // //// BEAN PROPERTIES
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

    public Map referenceData(HttpServletRequest request, C command) {
        return referenceData();
    }
}
