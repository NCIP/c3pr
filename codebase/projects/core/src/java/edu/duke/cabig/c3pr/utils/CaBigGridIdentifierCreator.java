package edu.duke.cabig.c3pr.utils;

import java.net.URI;
import java.net.URISyntaxException;

import net.handle.server.BadConfigurationFileException;
import net.handle.server.IDSvcInterface;
import net.handle.server.IDSvcInterfaceFactory;
import net.handle.server.InvalidSubclassException;
import net.handle.server.ResourceIdInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.duke.cabig.c3pr.utils.GridIdentifierCreator;

/**
 * CaBig version of Grid Identifiers
 * 
 * @author Priyatam
 */
public class CaBigGridIdentifierCreator implements GridIdentifierCreator{
	
	private static final Log log = LogFactory.getLog(CaBigGridIdentifierCreator.class);
	
	// Path for setting Configuration file (config.dct)
	private String configDctFolderPath;	
	
	// namespace 
	private String namespace="urn://cabig";
	
	/**
	 * Get the grid identifier of the domain Object (given the unique id)
	 * @param domainObjectId Unique Id of the object
	 * Typical implementation is (Class+ primary key)
	 * @return the Grid Identifier
	 */
	public String getGridIdentifier(String uniqueObjectId) {
		IDSvcInterface idInterface = null;
		URI bigId = null;
		
		try {
			idInterface = IDSvcInterfaceFactory.getInterface(configDctFolderPath);
			} catch (BadConfigurationFileException e) {
				log.debug("Unable to find config.dct file. Check your property file" +
					"in classpath (classes): grid-security.properties");			
				e.printStackTrace();
			} catch (InvalidSubclassException e) {
			e.printStackTrace();
		} 		
		try {
			ResourceIdInfo rid = new ResourceIdInfo(new URI(namespace), uniqueObjectId);		
			bigId = idInterface.createOrGetGlobalID(rid);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		return bigId.toString();
	}
	
	public String getConfigDctFolderPath() {
		return configDctFolderPath;
	}

	public void setConfigDctFolderPath(String configDctFolderPath) {
		this.configDctFolderPath = configDctFolderPath;
	}


	public String getNamespace() {
		return namespace;
	}


	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	
}