package edu.duke.cabig.c3pr.rules.common.adapter;

import edu.duke.cabig.c3pr.rules.exception.RuleException;
import gov.nih.nci.cabig.c3pr.rules.brxml.RuleSet;
import org.drools.rule.Package;

public interface RuleAdapter {
    public Package adapt(RuleSet ruleSet) throws RuleException;
}
