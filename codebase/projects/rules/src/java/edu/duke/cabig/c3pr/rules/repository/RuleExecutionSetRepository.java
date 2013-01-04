/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.rules.repository;

import java.util.List;

import javax.rules.admin.RuleExecutionSet;
import javax.rules.admin.RuleExecutionSetRegisterException;

import edu.duke.cabig.c3pr.rules.exception.RuleException;

public interface RuleExecutionSetRepository {

    public List getRegistrations() throws RuleException;

    public RuleExecutionSet getRuleExecutionSet(final String bindUri) throws RuleException;

    public void registerRuleExecutionSet(final String bindUri, final RuleExecutionSet ruleSet)
                    throws RuleExecutionSetRegisterException, RuleException;

    public void unregisterRuleExecutionSet(final String bindUri) throws RuleException;
}
