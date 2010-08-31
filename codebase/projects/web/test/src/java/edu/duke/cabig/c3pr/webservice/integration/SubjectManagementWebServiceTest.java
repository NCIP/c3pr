/**
 * 
 */
package edu.duke.cabig.c3pr.webservice.integration;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.dbunit.DefaultDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.IDatabaseConnection;

import edu.nwu.bioinformatics.commons.testing.DbTestCase;

/**
 * This test will run C3PR in embedded Tomcat and test Subject Management web
 * service against it.
 * 
 * @author dkrylov
 * 
 */
public class SubjectManagementWebServiceTest extends DbTestCase {

	private static final String CATALINA_HOME = "CATALINA_HOME";

	private Logger logger = Logger
			.getLogger(SubjectManagementWebServiceTest.class);

	protected File catalinaHome;
	protected File datasourceFile;
	protected File warFile;

	/**
	 * @param name
	 */
	public SubjectManagementWebServiceTest() {
		logger.setLevel(Level.INFO);
		setName("SubjectManagementWebServiceTest");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		logger.info("SubjectManagementWebServiceTest starting up...");
		initializeProperties();
		logger.info(toString());
		super.setUp();
	}

	private void initializeProperties() {
		String catalinaHomeEnv = System.getenv(CATALINA_HOME);
		if (StringUtils.isBlank(catalinaHomeEnv)) {
			throw new RuntimeException(
					"CATALINA_HOME is not set by the Ant script.");
		}
		catalinaHome = new File(catalinaHomeEnv);
		if (!catalinaHome.exists() || !catalinaHome.isDirectory()
				|| catalinaHome.list().length > 0) {
			throw new RuntimeException(
					"CATALINA_HOME must point to an existent and empty directory.");
		}

		datasourceFile = getFileFromProperty("test.datasource.file");
		warFile = getFileFromProperty("test.war.file");

	}

	private File getFileFromProperty(String propertyName) {
		String filePath = System.getProperty(propertyName);
		if (StringUtils.isBlank(filePath)) {
			throw new RuntimeException(propertyName
					+ " is not set by the Ant script.");
		}
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new RuntimeException(propertyName
					+ " must specify a valid path to a file.");
		}
		return file;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testSubjectManagement() {

	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	protected DataSource getDataSource() {
		try {
			Properties props = new Properties();
			props.load(new FileInputStream(datasourceFile));

			Properties dsProps = new Properties();
			dsProps.setProperty("url", props.getProperty("datasource.url"));
			dsProps.setProperty("username", props
					.getProperty("datasource.username"));
			dsProps.setProperty("password", props
					.getProperty("datasource.password"));
			dsProps.setProperty("driverClassName", props
					.getProperty("datasource.driver"));
			dsProps.setProperty("initialSize", "1");
			dsProps.setProperty("minIdle", "1");

			logger.info("Creating data source using properties:\r\n" + dsProps);

			return BasicDataSourceFactory.createDataSource(dsProps);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new RuntimeException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dbunit.DatabaseTestCase#newDatabaseTester()
	 */
	@Override
	protected IDatabaseTester newDatabaseTester() throws Exception {
		logger.debug("newDatabaseTester() - start");

		final IDatabaseConnection connection = getConnection();
		final IDatabaseTester tester = new DefaultDatabaseTester(connection) {
			public void closeConnection(IDatabaseConnection connection)
					throws Exception {
				// to fix a problem where Db Unit 2.2.1 attempts to close the
				// same connection multiple times. That produces SQLException.
				// To fix that, we simply disable connection closing.
				// We don't really need to close it.
				/**
				 * if (connection.getConnection()!=null &&
				 * !connection.getConnection().isClosed()) { connection.close();
				 * }
				 **/
			}
		};
		return tester;
	}

}
