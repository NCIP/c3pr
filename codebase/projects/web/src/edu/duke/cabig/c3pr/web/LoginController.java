package edu.duke.cabig.c3pr.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.semanticbits.security.grid.GridLoginContext;
import com.semanticbits.security.grid.exception.GridLoginException;
import com.semanticbits.security.grid.impl.GridAuthenticationManagerImpl;

public class LoginController extends SimpleFormController {

	private static final Log log = LogFactory.getLog(LoginController.class);

	private GridAuthenticationManagerImpl gridAuthenticationManager;

	public GridAuthenticationManagerImpl getGridAuthenticationManager() {
		return gridAuthenticationManager;
	}

	public void setGridAuthenticationManager(
			GridAuthenticationManagerImpl gridAuthenticationManager) {
		this.gridAuthenticationManager = gridAuthenticationManager;
	}

	@Override
	protected Map referenceData(HttpServletRequest arg0) throws Exception {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("msg", "");
		return map;
	}
	protected ModelAndView showForm(HttpServletRequest request,
			HttpServletResponse response, BindException errors)
			throws Exception {
		return new ModelAndView(getFormView());
	}

	@SuppressWarnings("unchecked")
	protected ModelAndView processFormSubmission(HttpServletRequest request,
			HttpServletResponse response, Object oCommand, BindException errors)
			throws Exception {
		LoginCommand loginCommand = (LoginCommand) oCommand;
		String username = loginCommand.getUsername();
		String password = loginCommand.getPassword();
		log.debug("Username: " + loginCommand.getUsername());
		System.out.println("Usename : " + username
				+ "---------------Password : " + password);
		System.out.println("idpURL : {" + gridAuthenticationManager.getIdpUrl()
				+ "}");
		System.out.println("ifsURL : {" + gridAuthenticationManager.getIfsUrl()
				+ "}");
		log.debug("System Config file is: "
				+ System.getProperty("gov.nih.nci.security.configFile"));

		System.out
				.println("------------Authenticating Against Grid Service-----------------------");
		GridLoginContext gridLoginContext = null;
		;
		boolean loginSuccess = false;
		try {
			gridLoginContext = gridAuthenticationManager.login(username,
					password);
		} catch (GridLoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (gridLoginContext != null) {
			String proxyString = gridLoginContext.getGridProxyAsString();
			if (proxyString != null && !proxyString.equals("")) {
				request.getSession().setAttribute("login-context",
						gridLoginContext);
				loginSuccess = true;
				System.out.println(gridLoginContext.getGridProxyAsString());
			}
		}
		if (loginSuccess) {
			System.out
			.println("------------Authentication Successfull-----------------------");
			System.out.println("First Name: "+gridLoginContext.getFirstName());
			System.out.println("Last Name: "+gridLoginContext.getLastName());
			System.out.println("Email Id: "+gridLoginContext.getEmailId());
			response.sendRedirect("/c3pr/SearchAndRegister.do");
		} else {
			System.out
			.println("------------Authentication Complete-----------------------");
			Map<String, Object> model = errors.getModel();
			model.put("failed", true);
			model.put("msg", "Authentication Invalid. Please try again");
			return new ModelAndView("login", model);
		}
		return null;
	}
}
