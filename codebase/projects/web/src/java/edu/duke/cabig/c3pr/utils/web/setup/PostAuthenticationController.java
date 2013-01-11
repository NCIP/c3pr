/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.utils.web.setup;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.setup.SetupStatus;
import edu.duke.cabig.c3pr.web.admin.ConfigurationController;

/**
 * @author Kruttik Aggarwal
 */
public class PostAuthenticationController extends ConfigurationController {
	
	private SetupStatus setupStatus;
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Object o, BindException e)
			throws Exception {
		ModelAndView mv = super.onSubmit(httpServletRequest, httpServletResponse, o, e); 
        if(e.hasErrors())
        	return mv;
        setupStatus.recheck();
		return new ModelAndView("/user/setup_complete");
	}

	@Required
	public void setSetupStatus(SetupStatus setupStatus) {
		this.setupStatus = setupStatus;
	}
}
