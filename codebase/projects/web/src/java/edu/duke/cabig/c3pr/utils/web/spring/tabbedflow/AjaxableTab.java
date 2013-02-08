/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.utils.web.spring.tabbedflow;

import gov.nih.nci.cabig.ctms.web.tabs.Tab;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Rhett Sutphin
 */
public abstract class AjaxableTab<C> extends Tab<C> {

    public AjaxableTab() {
        super();
    }

    public AjaxableTab(String longTitle, String shortTitle, String viewName) {
        super(longTitle, shortTitle, viewName);
    }

    public AjaxableTab(String longTitle, String shortTitle) {
        super(longTitle, shortTitle, "");
    }

    protected ModelAndView postProcessAsynchronous(HttpServletRequest request, C command,
                    Errors error) throws Exception {
        return new ModelAndView(AjaxableUtils.getAjaxViewName(request));
    }

}
