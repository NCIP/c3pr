/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.grid.studyservice.common;

import java.rmi.RemoteException;

/** 
 * This class is autogenerated, DO NOT EDIT.
 * 
 * This interface represents the API which is accessable on the grid service from the client. 
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public interface StudyServiceI {

  public org.oasis.wsrf.properties.GetMultipleResourcePropertiesResponse getMultipleResourceProperties(org.oasis.wsrf.properties.GetMultipleResourceProperties_Element params) throws RemoteException ;

  public org.oasis.wsrf.properties.GetResourcePropertyResponse getResourceProperty(javax.xml.namespace.QName params) throws RemoteException ;

  public org.oasis.wsrf.properties.QueryResourcePropertiesResponse queryResourceProperties(org.oasis.wsrf.properties.QueryResourceProperties_Element params) throws RemoteException ;

  public void activateStudySite(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException ;

  public void amendStudy(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException ;

  public void closeStudyToAccrual(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException ;

  public void closeStudySiteToAccrual(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException ;

  public void createStudyDefinition(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException ;

  public void openStudy(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException ;

  public void updateStudySiteProtocolVersion(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException ;

  public void updateStudy(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException ;

  public gov.nih.nci.cabig.ccts.domain.Message getStudy(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException ;

  public void closeStudyToAccrualAndTreatment(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException ;

  public void temporarilyCloseStudyToAccrual(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException ;

  public void temporarilyCloseStudyToAccrualAndTreatment(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException ;

  public void closeStudySiteToAccrualAndTreatment(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException ;

  public void temporarilyCloseStudySiteToAccrualAndTreatment(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException ;

  public void temporarilyCloseStudySiteToAccrual(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException ;

  public void createAndOpenStudy(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException ;

}

