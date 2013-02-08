/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.rules.runtime.action;

import edu.duke.cabig.c3pr.rules.runtime.RuleContext;

/**
 *
 * @author Sujith Vellat Thayyilthodi
 */
public interface NotificationHandler {

    public void performNotify(ActionContext actionContext, RuleContext ruleContext);

}
