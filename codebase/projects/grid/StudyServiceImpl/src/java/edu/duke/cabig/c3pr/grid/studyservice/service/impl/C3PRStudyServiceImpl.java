package edu.duke.cabig.c3pr.grid.studyservice.service.impl;

import java.io.StringWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;

import org.apache.axis.message.MessageElement;
import org.oasis.wsrf.properties.GetMultipleResourcePropertiesResponse;
import org.oasis.wsrf.properties.GetMultipleResourceProperties_Element;
import org.oasis.wsrf.properties.GetResourcePropertyResponse;
import org.oasis.wsrf.properties.QueryResourcePropertiesResponse;
import org.oasis.wsrf.properties.QueryResourceProperties_Element;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.orm.hibernate3.support.OpenSessionInViewInterceptor;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.SiteStudyStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyCoordinatingCenter;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.factory.StudyFactory;
import edu.duke.cabig.c3pr.domain.repository.StudyRepository;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.grid.common.StudyServiceI;
import edu.duke.cabig.c3pr.service.StudyService;
import edu.duke.cabig.c3pr.utils.SessionAndAuditHelper;
import edu.duke.cabig.c3pr.utils.XMLUtils;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import gov.nih.nci.cabig.ccts.domain.Message;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;
import gov.nih.nci.cagrid.common.Utils;

public class C3PRStudyServiceImpl implements StudyServiceI {

    private XmlMarshaller xmlMarshaller;
    
    private StudyFactory studyFactory;

    private OpenSessionInViewInterceptor interceptor;
    
    private StudyRepository studyRepository;
    
    private XMLUtils xmUtils;
    
    public void createStudy(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
        List<Study> objects = xmUtils.getDomainObjectsFromList(Study.class, xmUtils.getArguments(message));
        if (objects.size() != 1) {
            throw new RemoteException(
                            "Illegal Argument(s). Make sure there is exactly one study defination in the message.");
        }
        WebRequest webRequest=SessionAndAuditHelper.setupHibernateSessionAndAudit(interceptor, "C3PR Admin", "Coordinating Center", new Date(), "Coordinating Center");
        try {
            Study study=objects.get(0);
            studyFactory.buildStudy(study);
            for(StudySite studySite:study.getStudySites()){
                studySite.setHostedMode(false);
                studySite.setSiteStudyStatus(SiteStudyStatus.PENDING);
            }
            for(StudyCoordinatingCenter studyCoordinatingCenter:study.getStudyCoordinatingCenters()){
                studyCoordinatingCenter.setHostedMode(false);
            }
            study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
            studyRepository.createStudy(study);
        }catch (C3PRCodedException e) {
            throw new RemoteException("error building the study", e);
        }finally{
            SessionAndAuditHelper.tearDownHibernateSession(interceptor, webRequest);
        }
    }

    public void openStudy(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
        List<Identifier> objects = xmUtils.getDomainObjectsFromList(Identifier.class, xmUtils.getArguments(message));
        if (objects.size() == 0) {
            throw new RemoteException(
                            "Illegal Argument(s). Make sure there is atleast one identifier in the message.");
        }
        WebRequest webRequest=SessionAndAuditHelper.setupHibernateSessionAndAudit(interceptor, "C3PR Admin", "Coordinating Center", new Date(), "Coordinating Center");
        try {
            studyRepository.openStudy(objects);
        }
        catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }finally{
            SessionAndAuditHelper.tearDownHibernateSession(interceptor, webRequest);
        }
    }

    public void approveStudySiteForActivation(gov.nih.nci.cabig.ccts.domain.Message message)
                    throws RemoteException {
        List arguments = xmUtils.getArguments(message);
        List<Identifier> identifiers = xmUtils.getDomainObjectsFromList(Identifier.class, arguments);
        if (identifiers.size() == 0) {
            throw new RemoteException(
                            "Illegal Argument(s). Make sure there is atleast one identifier in the message.");
        }
        List<HealthcareSite> heaList = xmUtils.getDomainObjectsFromList(HealthcareSite.class, arguments);
        if (heaList.size() != 1) {
            throw new RemoteException(
                            "Illegal Argument(s). Make sure there is atleast one healtcare site defination in the message.");
        }
        WebRequest webRequest=SessionAndAuditHelper.setupHibernateSessionAndAudit(interceptor, "C3PR Admin", "Coordinating Center", new Date(), "Coordinating Center");
        try {
            studyRepository.approveStudySiteForActivation(identifiers, heaList.get(0)
                            .getNciInstituteCode());
        }
        catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }finally{
            SessionAndAuditHelper.tearDownHibernateSession(interceptor, webRequest);
        }
    }

    public void activateStudySite(gov.nih.nci.cabig.ccts.domain.Message message)
                    throws RemoteException {
        List arguments = xmUtils.getArguments(message);
        List<Identifier> identifiers = xmUtils.getDomainObjectsFromList(Identifier.class, arguments);
        if (identifiers.size() == 0) {
            throw new RemoteException(
                            "Illegal Argument(s). Make sure there is atleast one identifier in the message.");
        }
        List<StudySite> studySiteList = xmUtils.getDomainObjectsFromList(StudySite.class, arguments);
        if (studySiteList.size() != 1) {
            throw new RemoteException(
                            "Illegal Argument(s). Make sure there is atleast one study site defination in the message.");
        }
        WebRequest webRequest=SessionAndAuditHelper.setupHibernateSessionAndAudit(interceptor, "C3PR Admin", "Coordinating Center", new Date(), "Coordinating Center");
        try {
            studyRepository.getUniqueStudy(identifiers).getStudySite(studySiteList.get(0).getHealthcareSite().getNciInstituteCode()).setIrbApprovalDate(studySiteList.get(0).getIrbApprovalDate());
            studyRepository.activateStudySite(identifiers, studySiteList.get(0).getHealthcareSite().getNciInstituteCode());
        }
        catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }finally{
            SessionAndAuditHelper.tearDownHibernateSession(interceptor, webRequest);
        }
    }

    public void amendStudy(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
        // TODO: Implement this autogenerated method
        throw new RemoteException("Not yet implemented");
    }

    public void updateStudySiteProtocolVersion(gov.nih.nci.cabig.ccts.domain.Message message)
                    throws RemoteException {
        // TODO: Implement this autogenerated method
        throw new RemoteException("Not yet implemented");
    }

    public void closeStudy(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
        List<Identifier> objects = xmUtils.getDomainObjectsFromList(Identifier.class, xmUtils.getArguments(message));
        if (objects.size() == 0) {
            throw new RemoteException(
                            "Illegal Argument(s). Make sure there is atleast one identifier in the message.");
        }
        WebRequest webRequest=SessionAndAuditHelper.setupHibernateSessionAndAudit(interceptor, "C3PR Admin", "Coordinating Center", new Date(), "Coordinating Center");
        try {
            studyRepository.closeStudy(objects);
        }
        catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }finally{
            SessionAndAuditHelper.tearDownHibernateSession(interceptor, webRequest);
        }
    }

    public void updateStudyStatus(gov.nih.nci.cabig.ccts.domain.Message message)
                    throws RemoteException {
     // TODO: Implement this autogenerated method
        throw new RemoteException("Not yet implemented");
        // List<Identifier> objects=xmUtils.getDomainObjectsFromList(Identifier.class,
        // xmUtils.getArguments(message));
        // if(objects.size()==0){
        // throw new RemoteException("Illegal Argument(s). Make sure there is atleast one identifier
        // in the message.");
        // }
        // List<CoordinatingCenterStudyStatus>
        // status=xmUtils.getDomainObjectsFromList(CoordinatingCenterStudyStatus.class,
        // xmUtils.getArguments(message));
        // if(objects.size()==0){
        // throw new RemoteException("Illegal Argument(s). Make sure there is exactly once status in
        // the message.");
        // }
        // studyWorkflowService.updateStudyStatus(objects, status);
    }

    public void closeStudySite(gov.nih.nci.cabig.ccts.domain.Message message)
                    throws RemoteException {
        List arguments = xmUtils.getArguments(message);
        List<Identifier> identifiers = xmUtils.getDomainObjectsFromList(Identifier.class, arguments);
        if (identifiers.size() == 0) {
            throw new RemoteException(
                            "Illegal Argument(s). Make sure there is atleast one identifier in the message.");
        }
        List<HealthcareSite> heaList = xmUtils.getDomainObjectsFromList(HealthcareSite.class, arguments);
        if (heaList.size() != 1) {
            throw new RemoteException(
                            "Illegal Argument(s). Make sure there is atleast one healtcare site defination in the message.");
        }
        WebRequest webRequest=SessionAndAuditHelper.setupHibernateSessionAndAudit(interceptor, "C3PR Admin", "Coordinating Center", new Date(), "Coordinating Center");
        try {
            studyRepository.closeStudySite(identifiers, heaList.get(0).getNciInstituteCode());
        }
        catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }finally{
            SessionAndAuditHelper.tearDownHibernateSession(interceptor, webRequest);
        }
    }

    public void closeStudySites(gov.nih.nci.cabig.ccts.domain.Message message)
                    throws RemoteException {
     // TODO: Implement this autogenerated method
        throw new RemoteException("Not yet implemented");
//        List arguments = xmUtils.getArguments(message);
//        List<Identifier> identifiers = xmUtils.getDomainObjectsFromList(Identifier.class, arguments);
//        if (identifiers.size() == 0) {
//            throw new RemoteException(
//                            "Illegal Argument(s). Make sure there is atleast one identifier in the message.");
//        }
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

    public QueryResourcePropertiesResponse queryResourceProperties(
                    QueryResourceProperties_Element params) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    public void setXmlMarshaller(XmlMarshaller xmlMarshaller) {
        this.xmlMarshaller = xmlMarshaller;
        this.xmUtils=new XMLUtils(xmlMarshaller);
    }

    public void setInterceptor(OpenSessionInViewInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    public void setStudyRepository(StudyRepository studyRepository) {
        this.studyRepository = studyRepository;
    }

    public void setStudyFactory(StudyFactory studyFactory) {
        this.studyFactory = studyFactory;
    }

}
