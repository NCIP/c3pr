package edu.duke.cabig.c3pr.setup;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;

import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.domain.repository.CSMUserRepository;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.StringUtils;

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
                return csmUserRepository.getCSMUsersByGroup(C3PRUserGroupType.C3PR_ADMIN).size() > 0;
            }
        });
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

    ////// INNER CLASSES

    private interface SetupChecker {
        boolean isPrepared();
    }

    public enum InitialSetupElement {
        SITE,
        ADMINISTRATOR
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
}
