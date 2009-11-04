package edu.duke.cabig.c3pr.rules.common.adapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.drools.rule.Package;

import gov.nih.nci.cabig.c3pr.rules.brxml.RuleSet;

public class JBossXSLTRuleAdapter implements RuleAdapter {
    private static final Log log = LogFactory.getLog(JBossXSLTRuleAdapter.class);

    public Package adapt(RuleSet ruleSet) {
        if (true) {
            throw new RuntimeException(
                            "Deprecated, dont use JBossXSLTRuleAdapter, instead use CaAERSJBossXSLTRuleAdapter");
        }
        return null;
    }

}