package edu.duke.cabig.c3pr.web.security;

import edu.duke.cabig.c3pr.utils.web.navigation.Section;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Apr 23, 2007
 * Time: 5:39:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class SectionPrivilegeAndObjectIdGenerator extends AbstractPrivilegeAndObjectIdGenerator {

	protected String getKeyValue(Object object){
		return ((Section)object).getMainUrl();
	}

	protected boolean supports(Object object){
		return object instanceof Section;
	}
}