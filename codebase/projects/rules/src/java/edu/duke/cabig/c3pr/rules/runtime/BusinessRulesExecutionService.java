/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.rules.runtime;

import edu.duke.cabig.c3pr.rules.exception.RuleException;

import java.util.List;

public interface BusinessRulesExecutionService {

    public List<Object> fireRules(String bindingURI, List<Object> objects) throws RuleException;
}
