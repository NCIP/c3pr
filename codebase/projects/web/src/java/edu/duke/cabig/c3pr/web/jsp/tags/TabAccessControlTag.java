/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.jsp.tags;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.HttpSessionContextIntegrationFilter;
import org.acegisecurity.context.SecurityContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.util.ExpressionEvaluationUtils;

import edu.duke.cabig.c3pr.web.security.TabAuthroizationCheck;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;

public class TabAccessControlTag extends TagSupport {

	protected static final Log logger = LogFactory
			.getLog(TabAccessControlTag.class);

	private Object domainObject;
	
	private String scope;
	
	private Tab tab;

	private String hasPrivilege = "";

	private String tabAuthorizationCheckName = "";

	public int doStartTag() throws JspException {
		if (tabAuthorizationCheckName == null
				|| tabAuthorizationCheckName.trim().length() == 0) {
			throw new JspException("authorizationCheckName is required");
		}

		String evaledAuthorizationCheckName = ExpressionEvaluationUtils
				.evaluateString("authorizationCheckName",
						tabAuthorizationCheckName, pageContext);

		String evaledPrivilegesString = hasPrivilege;
		if (evaledPrivilegesString != null
				&& evaledPrivilegesString.trim().length() > 0) {
			evaledPrivilegesString = ExpressionEvaluationUtils.evaluateString(
					"hasPrivileges", hasPrivilege, pageContext);
		}

		String[] requiredPrivileges = evaledPrivilegesString.split(",");

		Object resolvedDomainObject = null;

		if (domainObject instanceof String) {
			resolvedDomainObject = ExpressionEvaluationUtils.evaluate(
					"domainObject", (String) domainObject, Object.class,
					pageContext);
		} else {
			resolvedDomainObject = domainObject;
		}

		if (resolvedDomainObject == null) {

			logger
					.debug("domainObject resolved to null, so including tag body");

			return Tag.EVAL_BODY_INCLUDE;
		}

		Authentication auth = getAuthentication();

		ApplicationContext context = getContext(pageContext);

		TabAuthroizationCheck tabAutzCheck = (TabAuthroizationCheck) context
				.getBean(evaledAuthorizationCheckName);
		if (tabAutzCheck == null) {
			throw new JspException(
					"No authorization check found for bean name '"
							+ evaledAuthorizationCheckName + "'.");
		}

		for (String requiredPrivilege : requiredPrivileges) {

			if (tabAutzCheck.checkAuthorization(auth, requiredPrivilege,
					tab, resolvedDomainObject, scope)) {
				logger.debug("Authorization succeeded, evaluating body");
				return Tag.EVAL_BODY_INCLUDE;
			}

		}

		logger.debug("No permission, so skipping tag body");

		return Tag.SKIP_BODY;
	}

	private Authentication getAuthentication() {
		Authentication auth = null;
		SecurityContext securityContext = (SecurityContext) this.pageContext.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY);
		if(securityContext != null){
			auth = securityContext.getAuthentication();
		}
		return auth;
	}

	/**
	 * Allows test cases to override where application context obtained from.
	 * 
	 * @param pageContext
	 *            so the <code>ServletContext</code> can be accessed as
	 *            required by Spring's <code>WebApplicationContextUtils</code>
	 * 
	 * @return the Spring application context (never <code>null</code>)
	 */
	protected ApplicationContext getContext(PageContext pageContext) {
		ServletContext servletContext = pageContext.getServletContext();

		return WebApplicationContextUtils
				.getRequiredWebApplicationContext(servletContext);
	}

	public Object getDomainObject() {
		return domainObject;
	}

	public void setDomainObject(Object domainObject) {
		this.domainObject = domainObject;
	}

	public Tab getTab() {
		return tab;
	}

	public void setTab(Tab tab) {
		this.tab = tab;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getHasPrivilege() {
		return hasPrivilege;
	}

	public void setHasPrivilege(String hasPrivilege) {
		this.hasPrivilege = hasPrivilege;
	}

	public String getTabAuthorizationCheckName() {
		return tabAuthorizationCheckName;
	}

	public void setTabAuthorizationCheckName(String tabAuthorizationCheckName) {
		this.tabAuthorizationCheckName = tabAuthorizationCheckName;
	}

}
