/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.rules.common.adapter;

import edu.duke.cabig.c3pr.rules.exception.RuleException;
import gov.nih.nci.cabig.c3pr.rules.brxml.RuleSet;
import org.drools.rule.Package;

public interface RuleAdapter {
    public Package adapt(RuleSet ruleSet) throws RuleException;
}
