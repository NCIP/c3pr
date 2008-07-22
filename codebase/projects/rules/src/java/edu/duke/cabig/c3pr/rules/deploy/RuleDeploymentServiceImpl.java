package edu.duke.cabig.c3pr.rules.deploy;

import gov.nih.nci.cabig.c3pr.rules.brxml.RuleSet;
import edu.duke.cabig.c3pr.rules.common.RuleServiceContext;
import edu.duke.cabig.c3pr.rules.common.XMLUtil;
import edu.duke.cabig.c3pr.rules.common.adapter.RuleAdapter;
import gov.nih.nci.cabig.c3pr.rules.deploy.sxml.RepositoryConfiguration;
import gov.nih.nci.cabig.c3pr.rules.deploy.sxml.RuleSetInfo;
import edu.duke.cabig.c3pr.rules.exception.RuleException;
import edu.duke.cabig.c3pr.rules.repository.RepositoryService;
import edu.duke.cabig.c3pr.rules.repository.RepositoryServiceImpl;
import edu.duke.cabig.c3pr.rules.runtime.cache.RulesCache;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import javax.rules.admin.RuleAdministrator;
import javax.rules.admin.RuleExecutionSet;
import javax.rules.admin.RuleExecutionSetCreateException;
import javax.rules.admin.RuleExecutionSetRegisterException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.drools.rule.Package;

/**
 * 
 * @author Sujith Vellat Thayyilthodi
 */

public class RuleDeploymentServiceImpl implements java.rmi.Remote, RuleDeploymentService {

    private static final Log log = LogFactory.getLog(RuleDeploymentServiceImpl.class);

    RulesCache rc = RulesCache.getInstance();

    public RuleDeploymentServiceImpl() {
        super();
    }

    public void login(String userName, String password) throws RemoteException {
        throw new RemoteException("Not Implemented");
    }

    /**
     * Read this configuration and make sure we have a JCR Repository implementation to support
     * that.
     * 
     * @throws RemoteException
     */
    public void configureRepository(RepositoryConfiguration repositoryConfiguration)
                    throws RemoteException {
        throw new RemoteException("Not Implemented");
    }

    public void registerRuleXml(String bindUri, String ruleXml) throws RemoteException {
        try {
            Package rulePackage = XMLUtil.unmarshalToPackage(ruleXml);
            registerPackage(bindUri, rulePackage);
        } catch (Exception e) {
            log.info("Error occured while registering the rules [bindUri :" + bindUri
                            + ", ruleXml :\r\n" + ruleXml + "\r\n]", e);
            throw new RemoteException("Error while registering rules", e);
        }

    }

    public void registerRulePackage(String bindUri, Package rulePackage) throws RuleException {
        try {
            registerPackage(bindUri, rulePackage);
        } catch (Exception e) {
            log.info("Error occured while registering the rules [bindUri :" + bindUri
                            + ", rulePackage :\r\n" + rulePackage + "\r\n]", e);
            throw new RuleException("Error while registering rules", e);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.duke.cabig.c3pr.rules.runtime.RuleDeploymentService#registerPackage(java.lang.String,
     *      java.lang.String)
     */
    public void registerRuleSet(String bindUri, String ruleSetName) throws RemoteException, RuleException {

        rc.ruleSetDeployed(bindUri);
        rc.ruleSetModified(bindUri);

        // obtain the rule xml from repository
        RuleSet ruleSet = getRepositoryService().getRuleSet(ruleSetName, false);

        // transform the rule xml to a Package , then register the Package to rule exceution service
        try {
            RuleAdapter ruleAdapter = null;
            ruleAdapter = (RuleAdapter) Class
                            .forName("edu.duke.cabig.c3pr.rules.common.adapter.CaAERSJBossXSLTRuleAdapter")
                            .newInstance();

            Object ruleSetObj = ruleAdapter.adapt(ruleSet);
            registerPackage(bindUri, ruleSetObj);
            
            
            

        } catch (Exception e) {
            log.info("Error while registering ruleSet, with name :" + ruleSetName + ", bindUri :"
                            + bindUri, e);
            throw new RemoteException(e.getMessage(), e);
        }

        rc.putRuleSet(bindUri, ruleSet);

    }

    public void deregisterRuleSet(String bindUri) throws RemoteException {
        try {
            RuleServiceContext.getInstance().ruleAdministrator.deregisterRuleExecutionSet(bindUri,
                            null);
        } catch (Exception e) {
            log.info("Error while undeploying rules", e);
            throw new RemoteException("Error while undeploying rules," + e.getMessage(), e);
        }

        rc.ruleSetDeployed(bindUri);
        rc.ruleSetModified(bindUri);

    }

    public RuleSetInfo[] listRegistrations() throws RemoteException, RuleException {
        return getRepositoryService().listRegistrations();
    }

    private RepositoryService getRepositoryService() throws RuleException{
        return (RepositoryServiceImpl) RuleServiceContext.getInstance().repositoryService;
    }

    protected void registerPackage(String bindUri, Object rulePackage)
                    throws RuleExecutionSetRegisterException, RuleExecutionSetCreateException, RuleException,
                    RemoteException {
        final Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("source", "xml");

        // Please note that we can only pass the Package here to the RuleExecution set.
        // Since we still use drools implementation of LocalRuleExecutionSetProvider
        final RuleExecutionSet ruleExecutionSet = RuleServiceContext.getInstance().ruleSetProvider
                        .createRuleExecutionSet(rulePackage, properties);
        
        
        RuleAdministrator ra = RuleServiceContext.getInstance().ruleAdministrator;
        ra.registerRuleExecutionSet(bindUri,ruleExecutionSet, properties);

    }

}