/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.grid.registrationservice.service.impl;

import java.rmi.RemoteException;

import javax.xml.namespace.QName;

import org.oasis.wsrf.properties.GetMultipleResourcePropertiesResponse;
import org.oasis.wsrf.properties.GetMultipleResourceProperties_Element;
import org.oasis.wsrf.properties.GetResourcePropertyResponse;
import org.oasis.wsrf.properties.QueryResourcePropertiesResponse;
import org.oasis.wsrf.properties.QueryResourceProperties_Element;

import edu.duke.cabig.c3pr.grid.registrationservice.common.RegistrationServiceI;
import gov.nih.nci.cabig.ccts.domain.IdentifierType;
import gov.nih.nci.cabig.ccts.domain.Message;
import gov.nih.nci.cabig.ccts.domain.Registration;
import gov.nih.nci.cabig.ccts.domain.ScheduledEpochType;

public class EchoRegistrationServiceImpl implements RegistrationServiceI{

    public Message enroll(Message message) throws RemoteException {
        System.out.println("recieved message..");
        return null;
    }

    public GetMultipleResourcePropertiesResponse getMultipleResourceProperties(
                    GetMultipleResourceProperties_Element params) throws RemoteException {
        return null;
    }

    public GetResourcePropertyResponse getResourceProperty(QName params) throws RemoteException {
        return null;
    }

    public void offStudy(Message message) throws RemoteException {
        System.out.println("recieved message..");
    }

    public QueryResourcePropertiesResponse queryResourceProperties(
                    QueryResourceProperties_Element params) throws RemoteException {
        return null;
    }

    public Message transfer(Message message) throws RemoteException {
        System.out.println("recieved message..");
        return null;
    }

	public Message getRegistrations(Message message) throws RemoteException {
		System.out.println("recieved message..");
		return null;
	}

    
}
