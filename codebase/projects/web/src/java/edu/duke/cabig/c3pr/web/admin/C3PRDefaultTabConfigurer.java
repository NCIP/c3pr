/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.admin;

import gov.nih.nci.cabig.ctms.web.tabs.Flow;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;
import gov.nih.nci.cabig.ctms.web.tabs.TabConfigurer;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Implements {@link TabConfigurer} by delegating to the containing application context.
 * 
 * @author Ramakrishna Gundala
 */
public class C3PRDefaultTabConfigurer implements TabConfigurer, ApplicationContextAware {

    public C3PRDefaultTabConfigurer() {
        super();
        // TODO Auto-generated constructor stub
    }

    private ApplicationContext applicationContext;

    public void injectDependencies(Tab<?> tab) {
        AutowireCapableBeanFactory autowireer = getBeanFactory();
        autowireer
                        .autowireBeanProperties(tab, AutowireCapableBeanFactory.AUTOWIRE_BY_NAME,
                                        false /*
                                                 * Not all settable properties are expected to be in
                                                 * the context
                                                 */);
    }

    protected AutowireCapableBeanFactory getBeanFactory() {
        return applicationContext.getAutowireCapableBeanFactory();
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void injectDependencies(Flow<?> flow) {
        AutowireCapableBeanFactory autowireer = getBeanFactory();
        for (Tab<?> tab : flow.getTabs()) {
            autowireer
                            .autowireBeanProperties(tab,
                                            AutowireCapableBeanFactory.AUTOWIRE_BY_NAME, false /*
                                                                                                 * Not
                                                                                                 * all
                                                                                                 * settable
                                                                                                 * properties
                                                                                                 * are
                                                                                                 * expected
                                                                                                 * to
                                                                                                 * be
                                                                                                 * in
                                                                                                 * the
                                                                                                 * context
                                                                                                 */);
        }

    }
}
