package edu.duke.cabig.c3pr.infrastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.axis.message.MessageElement;
import org.iso._21090.AD;
import org.iso._21090.CD;
import org.iso._21090.DSETTEL;
import org.iso._21090.ENON;
import org.iso._21090.ENXP;
import org.iso._21090.EntityNamePartType;
import org.iso._21090.II;
import org.iso._21090.NullFlavor;

import edu.duke.cabig.c3pr.esb.impl.CaXchangeMessageBroadcasterImpl;
import edu.duke.cabig.c3pr.esb.test.TestMultisiteDelegatedCredentialProvider;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.utils.ApplicationContextTest;
import gov.nih.nci.caxchange.ResponseMessage;
import gov.nih.nci.coppa.po.Organization;

/**
 * The Class RemoteHealthcareSiteResolverTest.
 */
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
	
	
	/**
	 * Test broadcast organization search.
	 */
	public void testBroadcastOrganizationSearch(){
		String caXchangeResponseXml = null;
		
		//get sample serialized org
		String serializedOrg = getSampleSerializedOrg();   //getWorkingSerializedXml();
		
		try {
			//broadcast org xml
			caXchangeResponseXml = remoteHealthcareSiteResolver.broadcastOrganizationSearch(coppaMessageBroadcaster, serializedOrg);
		} catch (C3PRCodedException e) {
			e.printStackTrace();
			fail("Caxchange broadcast Failed");
		}
		assertTrue("Caxchange broadcast returned Failed message", caXchangeResponseXml.contains("SUCCESS"));
		assertNotNull("Null response from caxchange", caXchangeResponseXml);

		//deserialize caXchange response xml
		ResponseMessage rm = (ResponseMessage)remoteHealthcareSiteResolver.deSerializeFromCaXchange(caXchangeResponseXml);
		assertNotNull("Unable to deserialize caXchange reponse xml", rm);
		
		//get coppa payload from caXchange ResponseMessage
		List<MessageElement> meList = rm.getResponse().getTargetResponse()[0].getTargetBusinessMessage().get_any()[0].getChildren();
		List<Object> objList = new ArrayList<Object>();
		for(MessageElement me : meList){
			try {
				//deserialize coppa payload from caXchange response xml and get coppa object
				objList.add(remoteHealthcareSiteResolver.deSerializeFromCoppa(me.getAsString()));
			} catch (Exception e) {
				e.printStackTrace();
				fail("coppa deserialization Failed");
			}
		}
		
		assertTrue(objList.size() > 0);
		assertTrue(objList.get(0) instanceof gov.nih.nci.coppa.po.Organization);
	}
		
	
	public void testDeSerializeFromCoppa(){
		String responseXml =  "<ns1:Organization xmlns:ns1='http://po.coppa.nci.nih.gov'>" +
						      "<ns1:identifier displayable='true' extension='533' identifierName='NCI organization entity identifier' reliability='ISS' root='2.16.840.1.113883.3.26.4.2' scope='OBJ'/>" +
						      "<ns1:name>" +
						      "<ns2:part value='YYYYYYYYYY YYYYYYYYYY YYYYYYYYYY YYYYYYYYYY YYYYYYYYYY YYYYYYYYYY YYYYYYYYYY YYYYYYYYYY YYYYYYYYYY Y' xmlns:ns2='uri:iso.org:21090'/>" +
						      "</ns1:name>" +
						      "<ns1:postalAddress><ns3:part type='AL' value='123 abc ave.' xmlns:ns3='uri:iso.org:21090'/><ns4:part type='CTY' value='mycity' xmlns:ns4='uri:iso.org:21090'/>" +					
						      "<ns5:part type='STA' value='VA' xmlns:ns5='uri:iso.org:21090'/><ns6:part type='ZIP' value='12345' xmlns:ns6='uri:iso.org:21090'/><ns7:part code='USA' type='CNT' value='United States' xmlns:ns7='uri:iso.org:21090'/>" +
						      "</ns1:postalAddress><ns1:statusCode code='active'/><ns1:telecomAddress><ns8:item value='mailto:default@example.com' xsi:type='ns8:TEL.Email' xmlns:ns8='uri:iso.org:21090' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'/>" +
						      "<ns9:item value='mailto:abc@example.com' xsi:type='ns9:TEL.Email' xmlns:ns9='uri:iso.org:21090' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'/>" +
						      "<ns10:item value='x-text-fax:234-567-8901' xsi:type='ns10:TEL.Phone' xmlns:ns10='uri:iso.org:21090' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'/>" +
						      "<ns11:item value='tel:123-456-7890' xsi:type='ns11:TEL.Phone' xmlns:ns11='uri:iso.org:21090' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'/>" +
						      "<ns12:item value='http://default.example.com' xsi:type='ns12:TEL.Url' xmlns:ns12='uri:iso.org:21090' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'/>" +
						      "<ns13:item value='http://www.example.com' xsi:type='ns13:TEL.Url' xmlns:ns13='uri:iso.org:21090' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'/>" +
						      "<ns14:item value='x-text-tel:345-678-9012' xsi:type='ns14:TEL.Phone' xmlns:ns14='uri:iso.org:21090' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'/></ns1:telecomAddress></ns1:Organization>";
		deSerializeFromCoppa(responseXml);
	}
	
	
	public Object deSerializeFromCoppa(String responseXml) {
		try {
			return remoteHealthcareSiteResolver.deSerializeFromCoppa(responseXml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public String getSampleSerializedOrg() {
		try {
			//building a sample coppa object
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
//			enOn.setNullFlavor(NullFlavor.NI);
			org.setName(enOn);
			AD ad = new AD();
			ad.setNullFlavor(NullFlavor.NI);
			org.setPostalAddress(ad);
			CD cd = new CD();
			cd.setNullFlavor(NullFlavor.NI);
			org.setStatusCode(cd);
			DSETTEL dsettel = new DSETTEL();
			dsettel.setNullFlavor(NullFlavor.NI);
			org.setTelecomAddress(dsettel);
			//calling serialize
			return remoteHealthcareSiteResolver.serialize(org);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public String getWorkingSerializedXml(){
		String xml =   "<Organization xmlns='http://po.coppa.nci.nih.gov'><identifier nullFlavor='NI'/><name><part type='DEL' value='YYYY' xmlns='uri:iso.org:21090'/>" +
						"</name><postalAddress><part type='CTY' value='mycity' xmlns='uri:iso.org:21090'/></postalAddress><statusCode nullFlavor='NI'/><telecomAddress /></Organization>";

		return xml;
	}
}
