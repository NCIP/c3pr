package edu.duke.cabig.c3pr.rules.runtime;

import edu.duke.cabig.c3pr.rules.exception.RuleException;

import java.util.List;

public interface BusinessRulesExecutionService {

    public List<Object> fireRules(String bindingURI, List<Object> objects) throws RuleException;
}
