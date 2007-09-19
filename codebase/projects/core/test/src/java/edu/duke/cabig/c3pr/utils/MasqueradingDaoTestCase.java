package edu.duke.cabig.c3pr.utils;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.FileNotFoundException;
import java.io.InputStream;

import edu.nwu.bioinformatics.commons.ResourceRetriever;

/**
 * Allows test classes to Masquerade other
 * dao test cases. Helps reuse the same dbunit
 * test data for multiple tests
 *
 *  Created by IntelliJ IDEA.
 * User: kherm
 * Date: Sep 19, 2007
 * Time: 2:59:38 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class MasqueradingDaoTestCase <D extends HibernateDaoSupport> extends ContextDaoTestCase<D> {

    /**
     * What dao class is the test trying to Masquerade
     * @return
     */
    public abstract Class<D> getMasqueradingDaoClassName();


    /**
     * Defaults to the name of the class, less "Test", first letter in lowercase.
     */
    protected String getDaoBeanName() {
        StringBuilder name = new StringBuilder(getMasqueradingDaoClassName().getSimpleName());
        name.setCharAt(0, Character.toLowerCase(name.charAt(0)));
        return name.toString();

    }

    protected InputStream handleTestDataFileNotFound() throws FileNotFoundException {
        return ResourceRetriever.getResource(getMasqueradingDaoClassName().getPackage(), getTestDataFileName());
    }


    protected String getClassNameWithoutPackage() {
      return getMasqueradingDaoClassName().getName().substring(getMasqueradingDaoClassName().getPackage().getName().length() + 1) + "Test";
    }
}
