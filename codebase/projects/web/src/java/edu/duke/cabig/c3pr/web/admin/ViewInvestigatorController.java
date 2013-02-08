/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.admin;

//Spring imports
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

public class ViewInvestigatorController extends ParameterizableViewController {

    public ViewInvestigatorController() {
        setViewName("admin/investigator_confirmation");
    }

    public ModelAndView handleRequestInternal(HttpServletRequest request,
                    HttpServletResponse response) throws Exception {

        setViewName("admin/investigator_confirmation");
        ModelAndView mav = new ModelAndView("admin/investigator_confirmation");

        return mav;
    }
}
