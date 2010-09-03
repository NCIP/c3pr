package edu.duke.cabig.c3pr.webservice.integration;



import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.datatype.IDataTypeFactory;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;



import edu.nwu.bioinformatics.commons.testing.HsqlDataTypeFactory;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.Logger;

/**
 * This class has been modified from its original version in nwu's package to make it possible to run c3pr in embedded Tomcat from
 * a DBUnit test case. Original version had dependencies, such as dependency on Spring, which prevented c3pr from running properly.
 * Unnecessary dependencies have been removed.
 * @author dkrylov
 *  
 * Author: mmhohman
 * Date: Aug 26, 2004
 * <p/>
 * Last Checkin:    $Author: rsutphin $
 * Date:            $Date: 2005/01/13 01:53:46 $
 * Revision:        $Revision: 1.2 $
 */
public abstract class DbTestCase extends org.dbunit.DatabaseTestCase {
    public static final String TESTDATA = "testdata";
	private Logger log = Logger.getLogger(DbTestCase.class.getName());

    protected abstract DataSource getDataSource();

    protected void setUp() throws Exception {
        log.fine("---- Begin test " + getName() + " ----");
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        log.fine("----  End  test " + getName() + " ----");
    }

    protected IDataSet getDataSet() throws Exception {
        String testDataFileName = getTestDataFileName();
        InputStream testDataStream = getResource(getClass().getPackage(), testDataFileName);
        if (testDataStream == null) {
            testDataStream = handleTestDataFileNotFound();
            // if it is still null, fail gracefully
            if (testDataStream == null) {
                throw new NullPointerException(
                    "Test data resource " + getResourceName(getClass().getPackage(), testDataFileName)
                    + " not found and fallback call to handleTestDataFileNotFound() did not provide a substitute.");
            }
        }
        //NOTE: if you are using Apache Crimson, and you try to use local DTDs for your datasets (generated
        //      by DbUnit), you will get an error stating that you're using a relative URI without providing
        //      a baseURI. I upgraded to the latest Xerces, and the problem went away
        return new FlatXmlDataSet(testDataStream);
    }

    protected InputStream handleTestDataFileNotFound() throws FileNotFoundException {
        throw new FileNotFoundException("Couldn't load dataset with name " + getTestDataFileName());
    }

    protected DatabaseOperation getTearDownOperation() throws Exception {
        //Note: this only deletes data from tables mentioned in the dataset XML
        return DatabaseOperation.DELETE_ALL;
    }

    protected String getTestDataFileName() {
        return getClassSpecificTestDataFileName();
    }

    protected String getClassSpecificTestDataFileName() {
        return new StringBuffer(TESTDATA+"/").append(getClassNameWithoutPackage()).append(".xml").toString();
    }

    /** Use this method to override getTestDataFileName when you want test specific data */
    protected String getMethodSpecificTestDataFileName() {
        StringBuffer result = new StringBuffer()
            .append(TESTDATA+"/")
            .append(getClassNameWithoutPackage())
            .append("_")
            .append(getName())
            .append(".xml");
        return result.toString();
    }

    protected String getClassNameWithoutPackage() {
        return getClass().getName().substring(getClass().getPackage().getName().length()+1);
    }

   

    protected IDatabaseConnection getConnection() throws Exception {
        DatabaseConnection databaseConnection = new DatabaseConnection((getDataSource()).getConnection());
        databaseConnection.getConfig().setProperty("http://www.dbunit.org/properties/datatypeFactory", createDataTypeFactory());
        return databaseConnection;
    }

    protected IDataTypeFactory createDataTypeFactory() {
        return new HsqlDataTypeFactory();
    }
    
    public static InputStream getResource(Package pkg, String name) {
        return DbTestCase.class.getResourceAsStream(getResourceName(pkg, name));
    }

    public static String getResourceName(Package pkg, String name) {
        // absolute resources don't need package prepended
        if (name.charAt(0) == '/') {
            return name;
        } else {
            StringBuffer resourceName = new StringBuffer("/");
            resourceName.append(pkg.getName().replace('.', '/'));
            resourceName.append("/").append(name);
            return resourceName.toString();
        }
    }
}
