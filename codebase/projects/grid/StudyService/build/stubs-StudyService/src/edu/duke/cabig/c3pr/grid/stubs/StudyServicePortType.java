/**
 * StudyServicePortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package edu.duke.cabig.c3pr.grid.stubs;

public interface StudyServicePortType extends java.rmi.Remote {
    public edu.duke.cabig.c3pr.grid.stubs.CreateStudyResponse createStudy(edu.duke.cabig.c3pr.grid.stubs.CreateStudyRequest parameters) throws java.rmi.RemoteException;
    public edu.duke.cabig.c3pr.grid.stubs.OpenStudyResponse openStudy(edu.duke.cabig.c3pr.grid.stubs.OpenStudyRequest parameters) throws java.rmi.RemoteException;
    public edu.duke.cabig.c3pr.grid.stubs.ApproveStudySiteForActivationResponse approveStudySiteForActivation(edu.duke.cabig.c3pr.grid.stubs.ApproveStudySiteForActivationRequest parameters) throws java.rmi.RemoteException;
    public edu.duke.cabig.c3pr.grid.stubs.ActivateStudySiteResponse activateStudySite(edu.duke.cabig.c3pr.grid.stubs.ActivateStudySiteRequest parameters) throws java.rmi.RemoteException;
    public edu.duke.cabig.c3pr.grid.stubs.AmendStudyResponse amendStudy(edu.duke.cabig.c3pr.grid.stubs.AmendStudyRequest parameters) throws java.rmi.RemoteException;
    public edu.duke.cabig.c3pr.grid.stubs.UpdateStudySiteProtocolVersionResponse updateStudySiteProtocolVersion(edu.duke.cabig.c3pr.grid.stubs.UpdateStudySiteProtocolVersionRequest parameters) throws java.rmi.RemoteException;
    public edu.duke.cabig.c3pr.grid.stubs.CloseStudyResponse closeStudy(edu.duke.cabig.c3pr.grid.stubs.CloseStudyRequest parameters) throws java.rmi.RemoteException;
    public edu.duke.cabig.c3pr.grid.stubs.UpdateStudyStatusResponse updateStudyStatus(edu.duke.cabig.c3pr.grid.stubs.UpdateStudyStatusRequest parameters) throws java.rmi.RemoteException;
    public edu.duke.cabig.c3pr.grid.stubs.CloseStudySiteResponse closeStudySite(edu.duke.cabig.c3pr.grid.stubs.CloseStudySiteRequest parameters) throws java.rmi.RemoteException;
    public edu.duke.cabig.c3pr.grid.stubs.CloseStudySitesResponse closeStudySites(edu.duke.cabig.c3pr.grid.stubs.CloseStudySitesRequest parameters) throws java.rmi.RemoteException;
    public org.oasis.wsrf.properties.GetMultipleResourcePropertiesResponse getMultipleResourceProperties(org.oasis.wsrf.properties.GetMultipleResourceProperties_Element getMultipleResourcePropertiesRequest) throws java.rmi.RemoteException, org.oasis.wsrf.properties.InvalidResourcePropertyQNameFaultType, org.oasis.wsrf.properties.ResourceUnknownFaultType;
    public org.oasis.wsrf.properties.GetResourcePropertyResponse getResourceProperty(javax.xml.namespace.QName getResourcePropertyRequest) throws java.rmi.RemoteException, org.oasis.wsrf.properties.InvalidResourcePropertyQNameFaultType, org.oasis.wsrf.properties.ResourceUnknownFaultType;
    public org.oasis.wsrf.properties.QueryResourcePropertiesResponse queryResourceProperties(org.oasis.wsrf.properties.QueryResourceProperties_Element queryResourcePropertiesRequest) throws java.rmi.RemoteException, org.oasis.wsrf.properties.InvalidResourcePropertyQNameFaultType, org.oasis.wsrf.properties.InvalidQueryExpressionFaultType, org.oasis.wsrf.properties.QueryEvaluationErrorFaultType, org.oasis.wsrf.properties.ResourceUnknownFaultType, org.oasis.wsrf.properties.UnknownQueryExpressionDialectFaultType;
    public gov.nih.nci.cagrid.introduce.security.stubs.GetServiceSecurityMetadataResponse getServiceSecurityMetadata(gov.nih.nci.cagrid.introduce.security.stubs.GetServiceSecurityMetadataRequest parameters) throws java.rmi.RemoteException;
}
