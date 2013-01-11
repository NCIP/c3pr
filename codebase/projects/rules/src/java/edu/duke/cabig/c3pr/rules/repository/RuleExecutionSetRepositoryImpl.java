/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.rules.repository;

import edu.duke.cabig.c3pr.rules.common.RuleServiceContext;
import edu.duke.cabig.c3pr.rules.exception.RuleException;
import gov.nih.nci.cabig.c3pr.rules.deploy.sxml.RuleSetInfo;
import edu.duke.cabig.c3pr.rules.runtime.cache.RulesCache;

import java.util.ArrayList;
import java.util.List;

import javax.rules.admin.RuleExecutionSet;
import javax.rules.admin.RuleExecutionSetRegisterException;

/**
 * 
 * @author Sujith Vellat Thayyilthodi
 */
public class RuleExecutionSetRepositoryImpl implements RuleExecutionSetRepository {

    RulesCache rc = RulesCache.getInstance();

    public RuleExecutionSetRepositoryImpl() {
    }

    /**
     * Retrieves a <code>List</code> of the URIs that currently have <code>RuleExecutionSet</code>s
     * associated with them.
     * 
     * An empty list is returned is there are no associations.
     * 
     * @return a <code>List</code> of the URIs that currently have <code>RuleExecutionSet</code>s
     *         associated with them.
     */
    public List getRegistrations() throws RuleException{
        RuleSetInfo[] ruleSetInfos = getRepositoryService().listRegistrations();
        final List<String> list = new ArrayList<String>();
        for (int i = 0; i < ruleSetInfos.length; i++) {
            list.add(ruleSetInfos[i].getBindUri());
        }
        return list;
    }

    /**
     * Get the <code>RuleExecutionSet</code> bound to this URI, or return <code>null</code>.
     * 
     * @param bindUri
     *                the URI associated with the wanted <code>RuleExecutionSet</code>.
     * 
     * @return the <code>RuleExecutionSet</code> bound to the given URI.
     */
    public RuleExecutionSet getRuleExecutionSet(final String bindUri) throws RuleException{
        RuleExecutionSet res = null;

        // if (rc.getRuleExecutionSet(bindUri)!=null){
        // res= rc.getRuleExecutionSet(bindUri);
        // } else {
        RuleSetInfo ruleSetInfo = getRepositoryService().getRegisteredRuleset(bindUri);
        res = (RuleExecutionSet) ruleSetInfo.getContent();
        // rc.putRuleExecutionSet(bindUri, res);
        // }
        return res;
    }

    /**
     * Register a <code>RuleExecutionSet</code> under the given URI.
     * 
     * @param bindUri
     *                the URI to associate with the <code>RuleExecutionSet</code>.
     * @param ruleExecutionSet
     *                the <code>RuleExecutionSet</code> to associate with the URI
     * 
     * @throws RuleExecutionSetRegisterException
     *                 if an error occurred that prevented registration (i.e. if bindUri or ruleSet
     *                 are <code>null</code>)
     */
    public void registerRuleExecutionSet(final String bindUri,
                    final RuleExecutionSet ruleExecutionSet)
                    throws RuleExecutionSetRegisterException, RuleException {
        if (bindUri == null) {
            throw new RuleExecutionSetRegisterException("bindUri cannot be null");
        }
        if (ruleExecutionSet == null) {
            throw new RuleExecutionSetRegisterException("ruleSet cannot be null");
        }
        RuleSetInfo ruleSetInfo = new RuleSetInfo();
        ruleSetInfo.setContent(ruleExecutionSet);
        ruleSetInfo.setBindUri(bindUri);
        getRepositoryService().registerRuleSet(bindUri, ruleSetInfo);
    }

    /**
     * Unregister a <code>RuleExecutionSet</code> from the given URI.
     * 
     * @param bindUri
     *                the URI to disassociate with the <code>RuleExecutionSet</code>.
     */
    public void unregisterRuleExecutionSet(final String bindUri) throws RuleException{
        if (bindUri == null) {
            throw new NullPointerException("bindUri cannot be null");
        }
        getRepositoryService().deregisterRuleExecutionSet(bindUri);
    }

    public RepositoryService getRepositoryService() throws RuleException{
        return RuleServiceContext.getInstance().repositoryService;
    }

}
