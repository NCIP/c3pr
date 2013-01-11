/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.grid.registrationservice.common;

import java.rmi.RemoteException;

/** 
 * This class is autogenerated, DO NOT EDIT.
 * 
 * This interface represents the API which is accessable on the grid service from the client. 
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public interface RegistrationServiceI {

  public org.oasis.wsrf.properties.GetMultipleResourcePropertiesResponse getMultipleResourceProperties(org.oasis.wsrf.properties.GetMultipleResourceProperties_Element params) throws RemoteException ;

  public org.oasis.wsrf.properties.GetResourcePropertyResponse getResourceProperty(javax.xml.namespace.QName params) throws RemoteException ;

  public org.oasis.wsrf.properties.QueryResourcePropertiesResponse queryResourceProperties(org.oasis.wsrf.properties.QueryResourceProperties_Element params) throws RemoteException ;

  public gov.nih.nci.cabig.ccts.domain.Message enroll(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException ;

  public gov.nih.nci.cabig.ccts.domain.Message transfer(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException ;

  public void offStudy(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException ;

  public gov.nih.nci.cabig.ccts.domain.Message getRegistrations(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException ;

}

