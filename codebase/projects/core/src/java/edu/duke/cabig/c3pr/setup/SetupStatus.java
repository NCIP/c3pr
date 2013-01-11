/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.setup;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;

import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.domain.repository.CSMUserRepository;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.DatabaseMigrationHelper;
import edu.duke.cabig.c3pr.utils.StringUtils;
import gov.nih.nci.security.authorization.domainobjects.Group;
import gov.nih.nci.security.authorization.domainobjects.User;

/**
 * Examines the application (via the injected beans) and determines whether any initial setup steps are required.
 * Caches its results so that the setup-or-not filter is faster.
 *
 * @author Kruttik Aggarwal
 */
public class SetupStatus implements InitializingBean {
    private Map<InitialSetupElement, SetupChecker> checkers;
    private CSMUserRepository csmUserRepository;
    private Configuration configuration;
    private DatabaseMigrationHelper databaseMigrationHelper;

    private boolean[] prepared;

    public SetupStatus() {
        prepared = new boolean[InitialSetupElement.values().length];
        checkers = new LinkedHashMap<InitialSetupElement, SetupChecker>();
        checkers.put(InitialSetupElement.SITE, new SetupChecker() {
            public boolean isPrepared() {
                return !StringUtils.isEmpty(configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE))
                		 &&!StringUtils.isEmpty(configuration.get(Configuration.SITE_NAME))
                		 &&!StringUtils.isEmpty(configuration.get(Configuration.SMTP_PROTOCOL))
                		 &&!StringUtils.isEmpty(configuration.get(Configuration.SMTP_SSL_AUTH))
                		 &&!StringUtils.isEmpty(configuration.get(Configuration.OUTGOING_MAIL_AUTH))
                		 &&!StringUtils.isEmpty(configuration.get(Configuration.OUTGOING_MAIL_PASSWORD))
                		 &&!StringUtils.isEmpty(configuration.get(Configuration.OUTGOING_MAIL_SERVER))
                		 &&!StringUtils.isEmpty(configuration.get(Configuration.OUTGOING_MAIL_SERVER_PORT))
                		 &&!StringUtils.isEmpty(configuration.get(Configuration.OUTGOING_MAIL_USERNAME));
            }
        });
        checkers.put(InitialSetupElement.ADMINISTRATOR, new SetupChecker() {
            public boolean isPrepared() {
            	Set<User> adminUsers = csmUserRepository.getCSMUsersByGroup(C3PRUserGroupType.SYSTEM_ADMINISTRATOR);
            	Set<User> poManagerUsers = csmUserRepository.getCSMUsersByGroup(C3PRUserGroupType.PERSON_AND_ORGANIZATION_INFORMATION_MANAGER);
            	//Iterate over all the sys_admins to see if any of them is also a PERSON_AND_ORGANIZATION_INFORMATION_MANAGER
            	for(User adminUser: adminUsers){
            		for(User poManagerUser: poManagerUsers){
            			if(poManagerUser.getLoginName().equals(adminUser.getLoginName())){
            				return true;
            			}
            		}
            	}
            	return false;
            }
        });
        checkers.put(InitialSetupElement.DATABASE_MIGRATION, new SetupChecker() {
            public boolean isPrepared() {
                return !databaseMigrationHelper.isDatabaseMigrationNeeded();
            }
        });
    }

    
    /**
     * Gets the c3pr admin by groups.
     * Actually we simply check if there is a user who has the following two roles:
     * 		System Administrator
     * 		Person and Organization Information Manager
     * If such a user exist, we do not need to display the setup screen.
     *
     * @return the c3pr admin by groups
     */
    public boolean getC3PRAdminByGroups(){
    	boolean adminExists = false;
    	
    	Set<Group> groups = null;
    	Set<User> users = csmUserRepository.getCSMUsersByGroup(C3PRUserGroupType.SYSTEM_ADMINISTRATOR);
    	//Iterate over all the sys_admins to see if any of them is also a PERSON_AND_ORGANIZATION_INFORMATION_MANAGER
    	for(User user: users){
    		if(adminExists){
    			break;
    		}
    		groups = user.getGroups();
    		for(Group group: groups){
    			if(group.getGroupName().equals(C3PRUserGroupType.PERSON_AND_ORGANIZATION_INFORMATION_MANAGER.getCode())){
    				adminExists = true;
    				break;
    			}
    		}
    	}
    	return adminExists;
    }
    
    public void recheck() {
        for (Map.Entry<InitialSetupElement, SetupChecker> entry : checkers.entrySet()) {
            prepared[entry.getKey().ordinal()] = entry.getValue().isPrepared();
        }
    }

    public void afterPropertiesSet() throws Exception {
        recheck();
    }

    public boolean isPreAuthenticationSetupNeeded(){
        return !prepared[InitialSetupElement.ADMINISTRATOR.ordinal()];
    }

    public boolean isPostAuthenticationSetupNeeded(){
        return !prepared[InitialSetupElement.SITE.ordinal()];
    }
    
    public boolean isDatabaseMigrationNeeded(){
        return !prepared[InitialSetupElement.DATABASE_MIGRATION.ordinal()];
    }

    ////// INNER CLASSES

    private interface SetupChecker {
        boolean isPrepared();
    }

    public enum InitialSetupElement {
        SITE,
        ADMINISTRATOR,
        DATABASE_MIGRATION
    }

    //////CONFIGURATION
    
    @Required
	public void setCsmUserRepository(CSMUserRepository csmUserRepository) {
		this.csmUserRepository = csmUserRepository;
	}

    @Required
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

    @Required
	public void setDatabaseMigrationHelper(
			DatabaseMigrationHelper databaseMigrationHelper) {
		this.databaseMigrationHelper = databaseMigrationHelper;
	}
}
