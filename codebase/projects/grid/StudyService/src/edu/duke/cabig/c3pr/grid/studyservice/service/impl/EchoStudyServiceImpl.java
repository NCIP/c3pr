package edu.duke.cabig.c3pr.grid.studyservice.service.impl;

import edu.duke.cabig.c3pr.grid.studyservice.common.StudyServiceI;
import gov.nih.nci.cabig.ccts.domain.Message;
import gov.nih.nci.cabig.ccts.domain.Study;

import java.rmi.RemoteException;

import javax.xml.namespace.QName;

import org.oasis.wsrf.properties.GetMultipleResourcePropertiesResponse;
import org.oasis.wsrf.properties.GetMultipleResourceProperties_Element;
import org.oasis.wsrf.properties.GetResourcePropertyResponse;
import org.oasis.wsrf.properties.QueryResourcePropertiesResponse;
import org.oasis.wsrf.properties.QueryResourceProperties_Element;

public class EchoStudyServiceImpl implements StudyServiceI {

    public void activateStudySite(Message message) throws RemoteException {
        System.out.println("recieved message..");

    }

    public void amendStudy(Message message) throws RemoteException {
        System.out.println("recieved message..");
    }

    public GetMultipleResourcePropertiesResponse getMultipleResourceProperties(
                    GetMultipleResourceProperties_Element params) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    public GetResourcePropertyResponse getResourceProperty(QName params) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    public void openStudy(Message message) throws RemoteException {
    	System.out.println("recieved message..");
    }

    public QueryResourcePropertiesResponse queryResourceProperties(
                    QueryResourceProperties_Element params) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    public void updateStudySiteProtocolVersion(Message message) throws RemoteException {
    	System.out.println("recieved message..");
    }

	public void closeStudySiteToAccrual(Message message) throws RemoteException {
		System.out.println("recieved message..");		
	}

	public void closeStudySiteToAccrualAndTreatment(Message message)
			throws RemoteException {
		System.out.println("recieved message..");		
	}

	public void closeStudyToAccrual(Message message) throws RemoteException {
		System.out.println("recieved message..");		
	}

	public void closeStudyToAccrualAndTreatment(Message message)
			throws RemoteException {
		System.out.println("recieved message..");		
	}

	public void createStudyDefinition(Message message) throws RemoteException {
		System.out.println("recieved message..");		
	}

	public Message getStudy(Message message) throws RemoteException {
		System.out.println("recieved message..");
		return null;
	}

	public void temporarilyCloseStudySiteToAccrual(Message message)
			throws RemoteException {
		System.out.println("recieved message..");		
	}

	public void temporarilyCloseStudySiteToAccrualAndTreatment(Message message)
			throws RemoteException {
		System.out.println("recieved message..");		
	}

	public void temporarilyCloseStudyToAccrual(Message message)
			throws RemoteException {
		System.out.println("recieved message..");		
	}

	public void temporarilyCloseStudyToAccrualAndTreatment(Message message)
			throws RemoteException {
		System.out.println("recieved message..");		
	}

	public void updateStudy(Message message) throws RemoteException {
		System.out.println("recieved message..");		
	}

}
