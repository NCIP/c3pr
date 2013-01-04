/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.rules.common;

import java.rmi.RemoteException;

import javax.rules.ConfigurationException;
import javax.rules.RuleServiceProvider;
import javax.rules.RuleServiceProviderManager;
import javax.rules.admin.LocalRuleExecutionSetProvider;
import javax.rules.admin.RuleAdministrator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.duke.cabig.c3pr.rules.exception.RuleException;
import edu.duke.cabig.c3pr.rules.repository.RepositoryService;
import edu.duke.cabig.c3pr.rules.repository.RepositoryServiceImpl;
import edu.duke.cabig.c3pr.rules.runtime.RuleExecutionServiceImpl;

/**
 *
 *
 * @author Sujith Vellat Thayyilthodi
 */
public class RuleServiceContext {

    private static final String DEFAULT_RULE_SERVICE_PROVIDER = "edu.duke.cabig.c3pr.rules.runtime.RuleServiceProviderImpl";

    public RuleServiceProvider ruleServiceProvider;

    public LocalRuleExecutionSetProvider ruleSetProvider;

    public RuleAdministrator ruleAdministrator;

    public RepositoryService repositoryService;

    public ApplicationContext applicationContext;

    private static RuleServiceContext instance;

    private RuleServiceContext() throws RuleException{
        initializeService();
    }

    private void initializeService() throws RuleException{
        try {
            /*
             * RuleServiceProviderManager.registerRuleServiceProvider(
             * RuleExecutionServiceImpl.RULE_SERVICE_PROVIDER, Class
             * .forName("org.drools.jsr94.rules.RuleServiceProviderImpl"));
             */

//        	if(! applicationContext.containsBean("jcrService")){
        		 this.applicationContext = new ClassPathXmlApplicationContext(
                         new String[] { "classpath*:edu/duke/cabig/c3pr/rules/config/spring/applicationContext-rules-jcr.xml" });
//        	}
           

            this.repositoryService = (RepositoryServiceImpl) applicationContext
                            .getBean("jcrService");

            RuleServiceProviderManager.registerRuleServiceProvider(
                            RuleExecutionServiceImpl.RULE_SERVICE_PROVIDER, Class
                                            .forName(DEFAULT_RULE_SERVICE_PROVIDER));

            this.ruleServiceProvider = RuleServiceProviderManager
                            .getRuleServiceProvider(RuleExecutionServiceImpl.RULE_SERVICE_PROVIDER);

            this.ruleAdministrator = this.ruleServiceProvider.getRuleAdministrator();

            this.ruleSetProvider = this.ruleAdministrator.getLocalRuleExecutionSetProvider(null);

        } catch (RemoteException e) {
            throw new RuleException(e.getMessage(), e);
        } catch (ConfigurationException e) {
            throw new RuleException(e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            throw new RuleException(e.getMessage(), e);
        }
    }

    /*
     * private static class RuleServiceContextHolder { static RuleServiceContext instance = new
     * RuleServiceContext(); }
     */

    public static RuleServiceContext getInstance() throws RuleException{
        if (instance == null) instance = new RuleServiceContext();
        return instance;
        // return RuleServiceContextHolder.instance;
    }

}
