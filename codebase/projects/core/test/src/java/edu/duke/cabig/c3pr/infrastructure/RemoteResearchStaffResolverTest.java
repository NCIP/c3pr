package edu.duke.cabig.c3pr.infrastructure;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.xerces.xs.datatypes.ObjectList;
import org.globus.gsi.GlobusCredential;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.domain.APIName;
import edu.duke.cabig.c3pr.domain.EndPoint;
import edu.duke.cabig.c3pr.domain.EndPointConnectionProperty;
import edu.duke.cabig.c3pr.domain.EndPointType;
import edu.duke.cabig.c3pr.domain.GridEndPoint;
import edu.duke.cabig.c3pr.domain.RemoteResearchStaff;
import edu.duke.cabig.c3pr.domain.ServiceName;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.grid.studyservice.client.StudyServiceClient;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.XMLUtils;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import gov.nih.nci.cabig.ccts.domain.Message;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

public class RemoteResearchStaffResolverTest extends AbstractTestCase {
    
	RemoteResearchStaffResolver remoteResearchStaffResolver;
	
	public RemoteResearchStaffResolverTest() {
		remoteResearchStaffResolver = new RemoteResearchStaffResolver();
	}
	
    public void testFind(){
    	RemoteResearchStaff remoteResearchStaff = new RemoteResearchStaff();
    	List<Object> objectList = remoteResearchStaffResolver.find(remoteResearchStaff);
    	
    	assertEquals(9, objectList.size());
    	for(Object object : objectList){
    		
    	}
    }
    
}
