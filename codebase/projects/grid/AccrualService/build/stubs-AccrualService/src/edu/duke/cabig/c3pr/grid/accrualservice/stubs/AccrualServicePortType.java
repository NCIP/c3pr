/**
 * AccrualServicePortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package edu.duke.cabig.c3pr.grid.accrualservice.stubs;

public interface AccrualServicePortType extends java.rmi.Remote {
    public edu.duke.cabig.c3pr.grid.accrualservice.stubs.GetSiteAccrualReportResponse getSiteAccrualReport(edu.duke.cabig.c3pr.grid.accrualservice.stubs.GetSiteAccrualReportRequest parameters) throws java.rmi.RemoteException;
    public edu.duke.cabig.c3pr.grid.accrualservice.stubs.GetDiseaseSiteAccrualReportsResponse getDiseaseSiteAccrualReports(edu.duke.cabig.c3pr.grid.accrualservice.stubs.GetDiseaseSiteAccrualReportsRequest parameters) throws java.rmi.RemoteException;
    public edu.duke.cabig.c3pr.grid.accrualservice.stubs.GetSiteCtepIdResponse getSiteCtepId(edu.duke.cabig.c3pr.grid.accrualservice.stubs.GetSiteCtepIdRequest parameters) throws java.rmi.RemoteException;
    public edu.duke.cabig.c3pr.grid.accrualservice.stubs.GetStudyAccrualReportsResponse getStudyAccrualReports(edu.duke.cabig.c3pr.grid.accrualservice.stubs.GetStudyAccrualReportsRequest parameters) throws java.rmi.RemoteException;
    public org.oasis.wsrf.properties.GetMultipleResourcePropertiesResponse getMultipleResourceProperties(org.oasis.wsrf.properties.GetMultipleResourceProperties_Element getMultipleResourcePropertiesRequest) throws java.rmi.RemoteException, org.oasis.wsrf.properties.InvalidResourcePropertyQNameFaultType, org.oasis.wsrf.properties.ResourceUnknownFaultType;
    public org.oasis.wsrf.properties.GetResourcePropertyResponse getResourceProperty(javax.xml.namespace.QName getResourcePropertyRequest) throws java.rmi.RemoteException, org.oasis.wsrf.properties.InvalidResourcePropertyQNameFaultType, org.oasis.wsrf.properties.ResourceUnknownFaultType;
    public org.oasis.wsrf.properties.QueryResourcePropertiesResponse queryResourceProperties(org.oasis.wsrf.properties.QueryResourceProperties_Element queryResourcePropertiesRequest) throws java.rmi.RemoteException, org.oasis.wsrf.properties.InvalidResourcePropertyQNameFaultType, org.oasis.wsrf.properties.InvalidQueryExpressionFaultType, org.oasis.wsrf.properties.QueryEvaluationErrorFaultType, org.oasis.wsrf.properties.ResourceUnknownFaultType, org.oasis.wsrf.properties.UnknownQueryExpressionDialectFaultType;
    public gov.nih.nci.cagrid.introduce.security.stubs.GetServiceSecurityMetadataResponse getServiceSecurityMetadata(gov.nih.nci.cagrid.introduce.security.stubs.GetServiceSecurityMetadataRequest parameters) throws java.rmi.RemoteException;
}
