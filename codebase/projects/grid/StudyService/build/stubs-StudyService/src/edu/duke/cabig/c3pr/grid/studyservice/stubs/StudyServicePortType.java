/**
 * StudyServicePortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package edu.duke.cabig.c3pr.grid.studyservice.stubs;

public interface StudyServicePortType extends java.rmi.Remote {
    public edu.duke.cabig.c3pr.grid.studyservice.stubs.CreateStudyResponse createStudy(edu.duke.cabig.c3pr.grid.studyservice.stubs.CreateStudyRequest parameters) throws java.rmi.RemoteException;
    public edu.duke.cabig.c3pr.grid.studyservice.stubs.OpenStudyResponse openStudy(edu.duke.cabig.c3pr.grid.studyservice.stubs.OpenStudyRequest parameters) throws java.rmi.RemoteException;
    public edu.duke.cabig.c3pr.grid.studyservice.stubs.ApproveStudySiteForActivationResponse approveStudySiteForActivation(edu.duke.cabig.c3pr.grid.studyservice.stubs.ApproveStudySiteForActivationRequest parameters) throws java.rmi.RemoteException;
    public edu.duke.cabig.c3pr.grid.studyservice.stubs.ActivateStudySiteResponse activateStudySite(edu.duke.cabig.c3pr.grid.studyservice.stubs.ActivateStudySiteRequest parameters) throws java.rmi.RemoteException;
    public edu.duke.cabig.c3pr.grid.studyservice.stubs.AmendStudyResponse amendStudy(edu.duke.cabig.c3pr.grid.studyservice.stubs.AmendStudyRequest parameters) throws java.rmi.RemoteException;
    public edu.duke.cabig.c3pr.grid.studyservice.stubs.UpdateStudySiteProtocolVersionResponse updateStudySiteProtocolVersion(edu.duke.cabig.c3pr.grid.studyservice.stubs.UpdateStudySiteProtocolVersionRequest parameters) throws java.rmi.RemoteException;
    public edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudyResponse closeStudy(edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudyRequest parameters) throws java.rmi.RemoteException;
    public edu.duke.cabig.c3pr.grid.studyservice.stubs.UpdateStudyStatusResponse updateStudyStatus(edu.duke.cabig.c3pr.grid.studyservice.stubs.UpdateStudyStatusRequest parameters) throws java.rmi.RemoteException;
    public edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudySiteResponse closeStudySite(edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudySiteRequest parameters) throws java.rmi.RemoteException;
    public org.oasis.wsrf.properties.GetMultipleResourcePropertiesResponse getMultipleResourceProperties(org.oasis.wsrf.properties.GetMultipleResourceProperties_Element getMultipleResourcePropertiesRequest) throws java.rmi.RemoteException, org.oasis.wsrf.properties.InvalidResourcePropertyQNameFaultType, org.oasis.wsrf.properties.ResourceUnknownFaultType;
    public org.oasis.wsrf.properties.GetResourcePropertyResponse getResourceProperty(javax.xml.namespace.QName getResourcePropertyRequest) throws java.rmi.RemoteException, org.oasis.wsrf.properties.InvalidResourcePropertyQNameFaultType, org.oasis.wsrf.properties.ResourceUnknownFaultType;
    public org.oasis.wsrf.properties.QueryResourcePropertiesResponse queryResourceProperties(org.oasis.wsrf.properties.QueryResourceProperties_Element queryResourcePropertiesRequest) throws java.rmi.RemoteException, org.oasis.wsrf.properties.InvalidResourcePropertyQNameFaultType, org.oasis.wsrf.properties.InvalidQueryExpressionFaultType, org.oasis.wsrf.properties.QueryEvaluationErrorFaultType, org.oasis.wsrf.properties.ResourceUnknownFaultType, org.oasis.wsrf.properties.UnknownQueryExpressionDialectFaultType;
    public gov.nih.nci.cagrid.introduce.security.stubs.GetServiceSecurityMetadataResponse getServiceSecurityMetadata(gov.nih.nci.cagrid.introduce.security.stubs.GetServiceSecurityMetadataRequest parameters) throws java.rmi.RemoteException;
}
