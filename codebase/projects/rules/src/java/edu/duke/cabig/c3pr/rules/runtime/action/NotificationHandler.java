package edu.duke.cabig.c3pr.rules.runtime.action;

import edu.duke.cabig.c3pr.rules.runtime.RuleContext;

/**
 *
 * @author Sujith Vellat Thayyilthodi
 */
public interface NotificationHandler {

    public void performNotify(ActionContext actionContext, RuleContext ruleContext);

}