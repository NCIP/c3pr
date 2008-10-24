package edu.duke.cabig.c3pr.grid.service.impl;

import java.rmi.RemoteException;

import javax.xml.namespace.QName;

import org.oasis.wsrf.properties.GetMultipleResourcePropertiesResponse;
import org.oasis.wsrf.properties.GetMultipleResourceProperties_Element;
import org.oasis.wsrf.properties.GetResourcePropertyResponse;
import org.oasis.wsrf.properties.QueryResourcePropertiesResponse;
import org.oasis.wsrf.properties.QueryResourceProperties_Element;

import edu.duke.cabig.c3pr.grid.common.StudyServiceI;
import gov.nih.nci.cabig.ccts.domain.Message;

public class EchoStudyServiceImpl implements StudyServiceI {

    public void activateStudySite(Message message) throws RemoteException {
        System.out.println("recieved message..");

    }

    public void amendStudy(Message message) throws RemoteException {
        System.out.println("recieved message..");
    }

    public void approveStudySiteForActivation(Message message) throws RemoteException {
        System.out.println("recieved message..");
    }

    public void closeStudy(Message message) throws RemoteException {
        System.out.println("recieved message..");
    }

    public void closeStudySite(Message message) throws RemoteException {
        System.out.println("recieved message..");
    }

    public void closeStudySites(Message message) throws RemoteException {
        System.out.println("recieved message..");
    }

    public void createStudy(Message message) throws RemoteException {
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
        // TODO Auto-generated method stub

    }

    public QueryResourcePropertiesResponse queryResourceProperties(
                    QueryResourceProperties_Element params) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    public void updateStudySiteProtocolVersion(Message message) throws RemoteException {
        // TODO Auto-generated method stub

    }

    public void updateStudyStatus(Message message) throws RemoteException {
        // TODO Auto-generated method stub

    }

}
