/**
 * 
 */
package edu.duke.cabig.c3pr.webservice.integration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Date;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.catalina.Engine;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Embedded;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.time.DateFormatUtils;
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

	private static final String KEYSTORE_FILE = "/etc/c3pr/publicstore.jks";

	private static final String CSM_JAAS_CONFIG_FILENAME = "csm_jaas.config";

	private static final String CATALINA_HOME = "CATALINA_HOME";

	private Logger logger = Logger
			.getLogger(SubjectManagementWebServiceTest.class);

	protected File catalinaHome;
	protected File datasourceFile;
	protected File warFile;
	protected File webappsDir;
	protected File confDir;
	protected File tmpDir;

	private Embedded container;

	private int port = 9090;

	private int sslPort = 9443;

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
		try {
			logger.info("SubjectManagementWebServiceTest starting up...");
			initializeProperties();
			prepareCsmJaasConfig();
			prepareDatasourcePropertiesFile();
			prepareKeystore();

			// at this point, everything is ready for c3pr to start up.
			startTomcat();

			// this call will initialize database data.
			super.setUp();
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new RuntimeException(e);
		}

	}

	/**
	 * Starts c3pr in embedded Tomcat.
	 * 
	 * @throws LifecycleException
	 * @throws IOException
	 */
	private void startTomcat() throws LifecycleException, IOException {
		logger.info("Starting Tomcat...");

		File defaultWebXml = new File(tmpDir, "web.xml");
		FileUtils.copyURLToFile(SubjectManagementWebServiceTest.class
				.getResource("testdata/web.xml"), defaultWebXml);

		container = new Embedded();
		container.setCatalinaHome(catalinaHome.getCanonicalPath());

		Engine engine = container.createEngine();
		engine.setName("TestEngine");

		Host localHost = container.createHost("localhost", webappsDir
				.getCanonicalPath());
		localHost.setDeployOnStartup(true);
		localHost.setAutoDeploy(true);
		engine.setDefaultHost(localHost.getName());
		engine.addChild(localHost);

		StandardContext rootContext = (StandardContext) container
				.createContext("", new File(webappsDir, "ROOT")
						.getAbsolutePath());
		rootContext.setReloadable(false);
		rootContext.setDefaultWebXml(defaultWebXml.getCanonicalPath());

		StandardContext context = (StandardContext) container.createContext(
				"/c3pr", warFile.getAbsolutePath());
		context.setReloadable(false);
		context.setDefaultWebXml(defaultWebXml.getCanonicalPath());

		localHost.addChild(rootContext);
		localHost.addChild(context);

		container.addEngine(engine);

		Connector httpConnector = container.createConnector((InetAddress) null,
				port, false);
		httpConnector.setRedirectPort(sslPort);

		Connector httpsConnector = container.createConnector(
				(InetAddress) null, sslPort, true);
		httpsConnector.setScheme("https");
		container.addConnector(httpConnector);
		container.addConnector(httpsConnector);
		container.setAwait(true);

		// start server
		container.start();

		// add shutdown hook to stop server
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				stopContainer();
			}
		});

		logger.info("Tomcat has been started.");
	}

	/**
	 * 
	 */
	protected void stopContainer() {
		try {
			if (container != null) {
				logger.info("Stopping Tomcat...");
				container.stop();
				logger.info("Tomcat has been stopped.");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	/**
	 * Keystore file needs to be in place in order for c3pr to validate the
	 * certificate.
	 * 
	 * @throws IOException
	 */
	private void prepareKeystore() throws IOException {
		File keystoreFile = new File(KEYSTORE_FILE);
		logger.info("Creating " + keystoreFile.getCanonicalPath());
		FileUtils.copyURLToFile(SubjectManagementWebServiceTest.class
				.getResource("testdata/publicstore.jks"), keystoreFile);

	}

	/**
	 * The first place where c3pr will look for datasource.properties is
	 * CATALINA_HOME/conf/c3pr.
	 * 
	 * @throws IOException
	 */
	private void prepareDatasourcePropertiesFile() throws IOException {
		File dsFile = new File(confDir, "c3pr/datasource.properties");
		dsFile.getParentFile().mkdir();
		logger.info("Creating " + dsFile.getCanonicalPath());
		FileUtils.copyFile(datasourceFile, dsFile);

	}

	/**
	 * csm_jaas.config needs to be there, in any directory, in order for CSM to
	 * work properly.
	 * 
	 * @throws IOException
	 */
	private void prepareCsmJaasConfig() throws IOException {
		final File csmJaasConf = new File(tmpDir, CSM_JAAS_CONFIG_FILENAME);

		String template = IOUtils
				.toString(SubjectManagementWebServiceTest.class
						.getResourceAsStream("testdata/"
								+ CSM_JAAS_CONFIG_FILENAME));
		Properties dsProps = loadDataSourceProperties();
		template = injectPropertyValue(template, "driverClassName", dsProps);
		template = injectPropertyValue(template, "url", dsProps);
		template = injectPropertyValue(template, "username", dsProps);
		template = injectPropertyValue(template, "password", dsProps);
		logger.info("CSM JAAS Config located at "
				+ csmJaasConf.getCanonicalPath() + " will contain:\r\n"
				+ template);

		FileUtils.writeStringToFile(csmJaasConf, template, "UTF-8");

		System.setProperty("java.security.auth.login.config", csmJaasConf
				.getCanonicalPath());

	}

	private String injectPropertyValue(String template, String propName,
			Properties props) {
		return StringUtils.replace(template, "${" + propName + "}", props
				.getProperty(propName));
	}

	/**
	 * Will create a bunch of directories, test required files for existence.
	 * 
	 * @throws IOException
	 */
	private void initializeProperties() throws IOException {
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

		webappsDir = new File(catalinaHome, "webapps");
		FileUtils.forceMkdir(webappsDir);

		confDir = new File(catalinaHome, "conf");
		FileUtils.forceMkdir(confDir);

		// we derive temp dir's name from the timestamp, which has insignificant
		// risk of collision; so it's fine: such names are more readable.
		tmpDir = new File(SystemUtils.JAVA_IO_TMPDIR, DateFormatUtils.format(
				new Date(), "yyyy_MM_dd_HH_mm_ss_SSS"));
		FileUtils.forceMkdir(tmpDir);

		logger.info(toString());

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
		try {
			stopContainer();
			FileUtils.cleanDirectory(catalinaHome);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
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
			Properties dsProps = loadDataSourceProperties();
			logger.info("Creating data source using properties:\r\n" + dsProps);
			return BasicDataSourceFactory.createDataSource(dsProps);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private Properties loadDataSourceProperties() throws IOException,
			FileNotFoundException {

		InputStream is = null;
		try {
			is = new FileInputStream(datasourceFile);

			Properties props = new Properties();
			props.load(is);

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
			return dsProps;
		} finally {
			IOUtils.closeQuietly(is);
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
