package edu.duke.cabig.c3pr.infrastructure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.xml.namespace.QName;

import org.codehaus.groovy.control.io.InputStreamReaderSource;
import org.iso._21090.ENON;
import org.iso._21090.ENXP;
import org.iso._21090.EntityNamePartType;
import org.iso._21090.II;
import org.iso._21090.NullFlavor;

import com.mchange.v1.io.InputStreamUtils;

import edu.duke.cabig.c3pr.esb.BroadcastException;
import edu.duke.cabig.c3pr.esb.CCTSMessageBroadcaster;
import edu.duke.cabig.c3pr.esb.Metadata;
import edu.duke.cabig.c3pr.esb.OperationNameEnum;
import edu.duke.cabig.c3pr.esb.ServiceTypeEnum;
import edu.duke.cabig.c3pr.esb.impl.CaXchangeMessageBroadcasterImpl;
import edu.duke.cabig.c3pr.esb.test.TestMultisiteDelegatedCredentialProvider;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.utils.ApplicationContextTest;
import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.coppa.po.Organization;

public class RemoteHealthcareSiteResolverTest extends ApplicationContextTest{
	
	RemoteHealthcareSiteResolver remoteHealthcareSiteResolver = null;
	CaXchangeMessageBroadcasterImpl coppaMessageBroadcaster = null;
	
	public static final String idpUrl = "https://cbvapp-d1017.nci.nih.gov:38443/wsrf/services/cagrid/Dorian";
	public static final String ifsUrl = "https://cbvapp-d1017.nci.nih.gov:38443/wsrf/services/cagrid/Dorian";
	public static final String caXchangeUrl = "https://cbvapp-d1017.nci.nih.gov:28445/wsrf-caxchange/services/cagrid/CaXchangeRequestProcessor";
	
	public static final String username = "ccts@nih.gov";
	public static final String password = "!Ccts@nih.gov1";
	
	private HashMap<String, String> messageTypesMapping = new LinkedHashMap<String, String>();
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		remoteHealthcareSiteResolver = (RemoteHealthcareSiteResolver) getApplicationContext().getBean("remoteHealthcareSiteResolver");
		coppaMessageBroadcaster = (CaXchangeMessageBroadcasterImpl) getApplicationContext().getBean("coppaMessageBroadcaster");
		
		TestMultisiteDelegatedCredentialProvider testMultisiteDelegatedCredentialProvider = new TestMultisiteDelegatedCredentialProvider(username, password);
		testMultisiteDelegatedCredentialProvider.setIdpUrl(idpUrl);
		testMultisiteDelegatedCredentialProvider.setIfsUrl(ifsUrl);
		
		coppaMessageBroadcaster.setDelegatedCredentialProvider(testMultisiteDelegatedCredentialProvider);
		coppaMessageBroadcaster.setCaXchangeURL(caXchangeUrl);
		coppaMessageBroadcaster.setMessageTypesMapping(messageTypesMapping);
	}
	
	public void testBroadcastOrganizationSearch(){
		String iiResponseXml = null;
		
		//get sample serialized org
		String serializedOrg = getSampleSerializedOrg();
		
		//broadcast org xml
		try {
			iiResponseXml = remoteHealthcareSiteResolver.broadcastOrganizationSearch(coppaMessageBroadcaster, serializedOrg);
		} catch (C3PRCodedException e) {
			e.printStackTrace();
			fail();
		}
		assertNotNull(iiResponseXml);
		//broadcast response xml of ii to identifiedOrgs
		
		//deserialize final response xml
		
	}
	
	public void testEsbDuplicate(){
		String payloadXml = getPayloadForFile("ORGANIZATION_SEARCH.xml");
		String serviceResponsePayload = null;
        //build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.search.getName(), "extId", ServiceTypeEnum.ORGANIZATION.getName());
        try {
        	serviceResponsePayload = remoteHealthcareSiteResolver.broadcastOrganizationSearch(coppaMessageBroadcaster, payloadXml);
		}  catch(Exception e){
			e.getStackTrace();
			fail();
		}
		assertNotNull(serviceResponsePayload);
	}
	
	public String getPayloadForFile(String filename){
		String payloadXml = "";
        BufferedReader fr = null;

        try {
			String file = ClassLoader.getSystemResource(filename).getFile();
			File f = new File(file);
	        System.out.println(f.getAbsolutePath());
	        fr = new BufferedReader(new FileReader(f));
	        
	        String temp = "";
			while ((temp = fr.readLine()) != null) {
				payloadXml += temp;
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			fail();
		} catch (IOException e2) {
			e2.printStackTrace();
			fail();
		} catch (Exception e3) {
			e3.printStackTrace();
			fail();
		}
        return payloadXml;
	}
	
	
	
	public void deSerialize() {
		try {
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(
					"organization_search.xml");

			File f = new File("outFile.java");
			OutputStream out = new FileOutputStream(f);
			byte buf[] = new byte[1024];
			int len;
			while ((len = inputStream.read(buf)) > 0)
				out.write(buf, 0, len);
			out.close();
			inputStream.close();

			FileReader fr = new FileReader(f);
			InputStream wsddIs = getClass().getResourceAsStream(
					"/gov/nih/nci/coppa/services/client/client-config.wsdd");
			Organization org = (Organization) Utils.deserializeObject(fr,
					Organization.class, wsddIs);
			serialize(org);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getSampleSerializedOrg() {
		try {
			II id = new II();
			id.setIdentifierName("test");
			id.setRoot("root");
			id.setNullFlavor(NullFlavor.NI);
			Organization org = new Organization();
			org.setIdentifier(id);

			ENON enOn = new ENON();
			ENXP enXp = new ENXP();
			enXp.setValue("National Cancer");
			enXp.setType(EntityNamePartType.DEL);
			enOn.getPart().add(enXp);
			enOn.setNullFlavor(NullFlavor.NI);
			org.setName(enOn);
			return serialize(org);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	private String serialize(Organization org) {
		QName idQname = new QName("http://po.coppa.nci.nih.gov", "Organization");
		StringWriter writer = new StringWriter();
		InputStream wsddIs = getClass().getResourceAsStream(
				"/gov/nih/nci/coppa/services/client/client-config.wsdd");
		try {
			Utils.serializeObject(org, idQname, writer, wsddIs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return writer.toString();
	}
}
