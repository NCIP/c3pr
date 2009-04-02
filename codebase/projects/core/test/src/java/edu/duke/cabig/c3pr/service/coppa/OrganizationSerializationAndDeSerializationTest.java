package edu.duke.cabig.c3pr.service.coppa;

import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.coppa.po.Organization;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;

import javax.xml.namespace.QName;

import junit.framework.TestCase;

import org.iso._21090.ENON;
import org.iso._21090.ENXP;
import org.iso._21090.EntityNamePartType;
import org.iso._21090.II;
import org.iso._21090.NullFlavor;

public class OrganizationSerializationAndDeSerializationTest extends TestCase {
	public void testDeSerialize() {
		try {

			InputStream inputStream =  getClass().getClassLoader().getResourceAsStream(
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

	public void testSerialize() {
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
			serialize(org);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void serialize(Organization org) {
		QName idQname = new QName("http://po.coppa.nci.nih.gov", "Organization");
		StringWriter writer = new StringWriter();
		InputStream wsddIs = getClass().getResourceAsStream(
				"/gov/nih/nci/coppa/services/client/client-config.wsdd");
		try {
			Utils.serializeObject(org, idQname, writer, wsddIs);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(writer.toString());
	}
}
