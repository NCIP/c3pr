package edu.duke.cabig.c3pr.rules.author;

import edu.duke.cabig.c3pr.rules.exception.RuleException;
import gov.nih.nci.cabig.c3pr.rules.brxml.Category;
import gov.nih.nci.cabig.c3pr.rules.brxml.Rule;
import gov.nih.nci.cabig.c3pr.rules.brxml.RuleSet;

import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 * Remote interface for Rule Provisioning. This interface can be exposed as a webservice.
 * 
 * @author Sujith Vellat Thayyilthodi
 */

public interface RuleAuthoringService extends java.rmi.Remote {

    public void createCategory(Category category) throws RemoteException;

    public Category getCategory(String categoryPath) throws RemoteException;

    public void createRuleSet(RuleSet ruleSet) throws RemoteException;

    public String createRule(Rule rule) throws RemoteException, RuleException;

    public void updateRule(Rule rule) throws RemoteException, RuleException;

    public void updateRuleSet(RuleSet ruleSet) throws RemoteException;

    public RuleSet getRuleSet(String ruleSetId, boolean cached) throws RemoteException, RuleException;

    public Rule getRule(String ruleId) throws RemoteException, RuleException;

    public List<Rule> getRulesByCategory(String categoryPath) throws RemoteException, RuleException;

    public List<RuleSet> getAllRuleSets() throws RemoteException;

    public void addRuleExecutionSet(final String bindUri, final InputStream resourceAsStream,
                    final Map properties) throws RuleException;

    // REVISIT: Should be removed once testing is done
    public void listPackages() throws RuleException;

    public boolean containsRuleSet(String ruleSetName);

    public List<RuleSet> findRuleSetsForSponsor(String sponsorName);

    public List<RuleSet> findRuleSetsForStudy(String sponsorName, String studyName);
}