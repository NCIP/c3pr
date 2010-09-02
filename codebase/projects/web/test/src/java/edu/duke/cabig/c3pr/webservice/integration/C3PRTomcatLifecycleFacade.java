package edu.duke.cabig.c3pr.webservice.integration;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

import org.apache.catalina.Engine;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Embedded;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

/**
 * This class starts and stops embedded Tomcat. Load this class in an isolated
 * {@link ClassLoader} containing a minimal set of jars (normally, catalina's
 * jars only), and then call its methods via reflection. See the test case for
 * Subject Management for details on how to use this class. <br>
 * <br>
 * <b>This class is not refactoring-safe because it is used via reflection.
 * Please exercise care when, for example, renaming methods or packages.</b>
 * 
 * @see http://frank.neatstep.com/node/28
 * @author dkrylov
 * 
 */
public final class C3PRTomcatLifecycleFacade {

	private String catalinaHome;

	private String webappsDir;

	private String warFile;

	private String defaultWebXml;

	private int port;

	private int sslPort;

	private Embedded container;

	private Log logger = LogFactory.getLog(getClass());

	public C3PRTomcatLifecycleFacade(String catalinaHome, String webappsDir,
			String warFile, String defaultWebXml, int port, int sslPort) {
		super();
		this.catalinaHome = catalinaHome;
		this.webappsDir = webappsDir;
		this.warFile = warFile;
		this.defaultWebXml = defaultWebXml;
		this.port = port;
		this.sslPort = sslPort;
	}

	/**
	 * @throws LifecycleException
	 * @throws IOException
	 */
	public void startTomcat() throws LifecycleException, IOException {

		container = new Embedded();
		container.setCatalinaHome(catalinaHome);

		Engine engine = container.createEngine();
		engine.setName("TestEngine");

		Host localHost = container.createHost("localhost", webappsDir);
		localHost.setDeployOnStartup(true);
		localHost.setAutoDeploy(true);
		engine.setDefaultHost(localHost.getName());
		engine.addChild(localHost);

		StandardContext rootContext = (StandardContext) container
				.createContext("", new File(webappsDir, "ROOT")
						.getAbsolutePath());
		rootContext.setReloadable(false);
		rootContext.setDefaultWebXml(defaultWebXml);

		StandardContext context = (StandardContext) container.createContext(
				"/c3pr", warFile);
		context.setReloadable(false);
		context.setDefaultWebXml(defaultWebXml);

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

	}

	public void stopTomcat() {
		try {
			if (container != null) {
				container.stop();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

}
