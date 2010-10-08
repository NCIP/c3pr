/**
 * 
 */
package edu.duke.cabig.c3pr.webservice.integration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
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
import org.dbunit.DefaultDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.IDatabaseConnection;

/**
 * Base class for JUnit integration tests; C3PR in embedded Tomcat. This class
 * is responsible for setting up environment, starting and stopping Tomcat
 * instance. Subclasses can implement actual test methods.
 * 
 * @author dkrylov
 * 
 */
public abstract class C3PREmbeddedTomcatTestBase extends DbTestCase {

	private static final int TOMCAT_SHUTDOWN_TIMEOUT = 60;

	public static final String C3PR_CONTEXT = "/c3pr";

	public static final String ROOT = "ROOT";

	public static final String WEB_XML_FILENAME = "web.xml";

	public static final String SERVICE_KEYSTORE_BASENAME = "publicstore.jks";

	public static final String SERVICE_KEYSTORE_FILE = "/etc/c3pr/"
			+ SERVICE_KEYSTORE_BASENAME;

	public static final String CSM_JAAS_CONFIG_FILENAME = "csm_jaas.config";

	public static final String CATALINA_HOME = "CATALINA_HOME";

	public static final String TOMCAT_KEYSTORE_BASENAME = "tomcat.jks";

	protected Logger logger = Logger.getLogger(this.getClass().getName());

	protected File catalinaHome;
	protected File datasourceFile;
	protected File warFile;
	protected File webappsDir;
	protected File confDir;
	protected File tmpDir;
	protected File rulesDir;
	protected File tomcatKeystore;

	protected Embedded container;

	protected int port = 9090;

	protected int sslPort = 9443;

	/**
	 * @param name
	 */
	public C3PREmbeddedTomcatTestBase() {
		logger.setLevel(Level.INFO);
		setName(getClass().getName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		try {
			logger.info(getClass().getSimpleName()+" starting up...");
			initializeProperties();
			prepareCsmJaasConfig();
			prepareDatasourcePropertiesFile();
			prepareServiceKeystore();
			prepareTomcatKeystore();
			addShutdownHook();
			// at this point, everything is ready for c3pr to start up.
			startTomcat();

			// Subclasses will likely make HTTPS requests to this instance.
			// We need to disable SSL verification for testing purposes.
			disableSSLVerification();

			// this call will initialize database data.
			super.setUp();
		} catch (Exception e) {
			logger.severe(ExceptionUtils.getFullStackTrace(e));
			throw new RuntimeException(e);
		}

	}

	private void prepareTomcatKeystore() throws IOException {
		tomcatKeystore = new File(tmpDir, TOMCAT_KEYSTORE_BASENAME);
		logger.info("Creating " + tomcatKeystore.getCanonicalPath());
		FileUtils.copyURLToFile(C3PREmbeddedTomcatTestBase.class
				.getResource(TESTDATA + "/" + TOMCAT_KEYSTORE_BASENAME),
				tomcatKeystore);

	}

	/**
	 * Code of this method was simply Googled.
	 */
	void disableSSLVerification() {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(
					java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(
					java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };

		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection
					.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}

		com.sun.net.ssl.HostnameVerifier hv = new com.sun.net.ssl.HostnameVerifier() {

			public boolean verify(String urlHostname, String certHostname) {
				return true;
			}
		};
		com.sun.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(hv);

		HostnameVerifier hv2 = new HostnameVerifier() {

			public boolean verify(String urlHostName, SSLSession session) {
				return true;
			}
		};
		HttpsURLConnection.setDefaultHostnameVerifier(hv2);

	}

	private void addShutdownHook() {
		// add shutdown hook to stop server
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				cleanup();
			}
		});

	}

	/**
	 * Starts c3pr in embedded Tomcat.
	 * 
	 * @throws LifecycleException
	 * @throws IOException
	 */
	private void startTomcat() throws LifecycleException, IOException {
		logger.info("Starting Tomcat...");

		File defaultWebXml = new File(tmpDir, WEB_XML_FILENAME);
		FileUtils.copyURLToFile(C3PREmbeddedTomcatTestBase.class
				.getResource(TESTDATA + "/" + WEB_XML_FILENAME), defaultWebXml);

		final File rootContextDir = new File(webappsDir, ROOT);
		rootContextDir.mkdir();

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
				.createContext("", rootContextDir.getAbsolutePath());
		rootContext.setReloadable(false);
		rootContext.setDefaultWebXml(defaultWebXml.getCanonicalPath());

		StandardContext context = (StandardContext) container.createContext(
				C3PR_CONTEXT, warFile.getAbsolutePath());
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
		httpsConnector.setProperty("keystoreFile", tomcatKeystore
				.getCanonicalPath());

		container.addConnector(httpConnector);
		container.addConnector(httpsConnector);
		container.setAwait(true);

		// start server
		container.start();
		logger.info("Tomcat has been started.");
	}

	/**
	 * 
	 */
	private void stopContainer() {
		try {
			if (container != null) {
				logger.info("Stopping Tomcat...");
				// stopping Tomcat may block, so we need to do it in another
				// thread and join.
				final ExecutorService executor = Executors
						.newSingleThreadExecutor();
				try {
					Future future = executor.submit(new Runnable() {
						public void run() {
							try {
								container.stop();
								container = null;
								logger.info("Tomcat has been stopped.");
							} catch (LifecycleException e) {
								logger.severe(ExceptionUtils
										.getFullStackTrace(e));
							}
						}
					});
					future.get(TOMCAT_SHUTDOWN_TIMEOUT, TimeUnit.SECONDS);
				} finally {
					executor.shutdownNow();
				}
			}
		} catch (Exception e) {
			logger.severe(ExceptionUtils.getFullStackTrace(e));
		}

	}

	/**
	 * Keystore file needs to be in place in order for c3pr to validate the
	 * certificate.
	 * 
	 * @throws IOException
	 */
	private void prepareServiceKeystore() throws IOException {
		// In case this test is running on a developer's machine, keystore file
		// might already be present
		// and this test will overwrite it because the keystore file path at
		// this point is still hardcoded.
		// So if we find an existent file, we will try to save it and restore
		// later upon tearing down.
		// tearDown() might never get called, so there is still a risk of
		// loosing a developer's original keystore file.

		backupKeystoreFileIfNeeded();

		File keystoreFile = new File(SERVICE_KEYSTORE_FILE);
		logger.info("Creating " + keystoreFile.getCanonicalPath());
		FileUtils.copyURLToFile(C3PREmbeddedTomcatTestBase.class
				.getResource(TESTDATA + "/" + SERVICE_KEYSTORE_BASENAME),
				keystoreFile);

	}

	private void backupKeystoreFileIfNeeded() throws IOException {
		File keystoreFile = new File(SERVICE_KEYSTORE_FILE);
		if (keystoreFile.exists() && keystoreFile.isFile()) {
			logger.info("Backing up existent keystore file...");
			FileUtils.copyFile(keystoreFile,
					getTemporaryFileForKeystoreBackup());
		}
	}

	private void restoreKeystoreFileIfNeeded() throws IOException {
		File keystoreFile = new File(SERVICE_KEYSTORE_FILE);
		File backupFile = getTemporaryFileForKeystoreBackup();
		if (backupFile.exists() && backupFile.isFile()) {
			logger.info("Restoring keystore file to the original version...");
			FileUtils.copyFile(backupFile, keystoreFile);
		}
	}

	/**
	 * @return
	 */
	private File getTemporaryFileForKeystoreBackup() {
		return new File(tmpDir, SERVICE_KEYSTORE_BASENAME);
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

		// we need to change path for the rules repository.
		Properties props = new Properties();
		final FileInputStream is = new FileInputStream(datasourceFile);
		props.load(is);
		IOUtils.closeQuietly(is);

		final String rulesRepURL = encodeURL(rulesDir.toURL().toString());
		props.setProperty("rules.repository", rulesRepURL);
		final FileOutputStream os = new FileOutputStream(dsFile);
		props.store(os, "");
		os.flush();
		os.close();

		logger.info("Rules repository will be at " + rulesRepURL);

	}

	/**
	 * Quick fix for URLEncoding problem. Will revise to a fuller solution later.
	 * @param url
	 * @return
	 */
	private String encodeURL(String url) {
		StringBuilder sb = new StringBuilder();
		for (char c: url.toCharArray()) {
			if (c!=':' && c!='/') {
				sb.append(URLEncoder.encode(String.valueOf(c)));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
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
				.toString(C3PREmbeddedTomcatTestBase.class
						.getResourceAsStream(TESTDATA + "/"
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
			catalinaHome = null;
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

		rulesDir = new File(tmpDir, "rules");
		FileUtils.forceMkdir(rulesDir);

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
	@Override
	protected void tearDown() throws Exception {
		cleanup();
		super.tearDown();
	}

	/**
	 * @throws IOException
	 */
	private void cleanup() {
		try {
			restoreKeystoreFileIfNeeded();
			stopContainer();
			System.out.println("Cleaning up after ourselves: "
					+ catalinaHome.getAbsolutePath() + " and "
					+ tmpDir.getAbsolutePath());
			FileUtils.cleanDirectory(catalinaHome);
			FileUtils.cleanDirectory(tmpDir);
		} catch (IOException e) {
			logger.severe(ExceptionUtils.getFullStackTrace(e));
		}
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
			logger.severe(ExceptionUtils.getFullStackTrace(e));
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
		logger.fine("newDatabaseTester() - start");

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
