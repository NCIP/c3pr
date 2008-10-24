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

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Study;
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
    
    public void createStudy(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
        List<Study> objects = getDomainObjectsFromList(Study.class, getArguments(message));
        if (objects.size() != 1) {
            throw new RemoteException(
                            "Illegal Argument(s). Make sure there is exactly one study defination in the message.");
        }
        WebRequest webRequest=SessionAndAuditHelper.setupHibernateSessionAndAudit(interceptor, "C3PR Admin", "Coordinating Center", new Date(), "Coordinating Center");
        try {
            Study study=objects.get(0);
            studyFactory.buildStudy(study);
            study.setHostedMode(false);
            studyRepository.createStudy(study);
        }catch (C3PRCodedException e) {
            throw new RemoteException("error building the study", e);
        }finally{
            SessionAndAuditHelper.tearDownHibernateSession(interceptor, webRequest);
        }
    }

    public void openStudy(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
        List<Identifier> objects = getDomainObjectsFromList(Identifier.class, getArguments(message));
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
        List arguments = getArguments(message);
        List<Identifier> identifiers = getDomainObjectsFromList(Identifier.class, arguments);
        if (identifiers.size() == 0) {
            throw new RemoteException(
                            "Illegal Argument(s). Make sure there is atleast one identifier in the message.");
        }
        List<HealthcareSite> heaList = getDomainObjectsFromList(HealthcareSite.class, arguments);
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
        List arguments = getArguments(message);
        List<Identifier> identifiers = getDomainObjectsFromList(Identifier.class, arguments);
        if (identifiers.size() == 0) {
            throw new RemoteException(
                            "Illegal Argument(s). Make sure there is atleast one identifier in the message.");
        }
        List<HealthcareSite> heaList = getDomainObjectsFromList(HealthcareSite.class, arguments);
        if (heaList.size() != 1) {
            throw new RemoteException(
                            "Illegal Argument(s). Make sure there is atleast one healtcare site defination in the message.");
        }
        WebRequest webRequest=SessionAndAuditHelper.setupHibernateSessionAndAudit(interceptor, "C3PR Admin", "Coordinating Center", new Date(), "Coordinating Center");
        try {
            studyRepository.activateStudySite(identifiers, heaList.get(0)
                            .getNciInstituteCode());
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
        List<Identifier> objects = getDomainObjectsFromList(Identifier.class, getArguments(message));
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
        // List<Identifier> objects=getDomainObjectsFromList(Identifier.class,
        // getArguments(message));
        // if(objects.size()==0){
        // throw new RemoteException("Illegal Argument(s). Make sure there is atleast one identifier
        // in the message.");
        // }
        // List<CoordinatingCenterStudyStatus>
        // status=getDomainObjectsFromList(CoordinatingCenterStudyStatus.class,
        // getArguments(message));
        // if(objects.size()==0){
        // throw new RemoteException("Illegal Argument(s). Make sure there is exactly once status in
        // the message.");
        // }
        // studyWorkflowService.updateStudyStatus(objects, status);
    }

    public void closeStudySite(gov.nih.nci.cabig.ccts.domain.Message message)
                    throws RemoteException {
        List arguments = getArguments(message);
        List<Identifier> identifiers = getDomainObjectsFromList(Identifier.class, arguments);
        if (identifiers.size() == 0) {
            throw new RemoteException(
                            "Illegal Argument(s). Make sure there is atleast one identifier in the message.");
        }
        List<HealthcareSite> heaList = getDomainObjectsFromList(HealthcareSite.class, arguments);
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
//        List arguments = getArguments(message);
//        List<Identifier> identifiers = getDomainObjectsFromList(Identifier.class, arguments);
//        if (identifiers.size() == 0) {
//            throw new RemoteException(
//                            "Illegal Argument(s). Make sure there is atleast one identifier in the message.");
//        }
    }

    private String getModifiedDomainXML(String messageXML){
        System.out.println("messageXML---------");
        System.out.println(messageXML);
        System.out.println("messageXML end---------");
        String domainXml=messageXML.substring(messageXML.indexOf('>')+1, messageXML.lastIndexOf('<'));
        System.out.println("domainXml---------");
        System.out.println(domainXml);
        System.out.println("domainXml end---------");
        String modifiedXML="<message>"+domainXml+"</message>";
        System.out.println("modifiedXML---------");
        System.out.println(modifiedXML);
        System.out.println("modifiedXML end---------");
        return modifiedXML;
    }
    private <T extends AbstractMutableDomainObject> List<T> getArguments(Message message)
                    throws RemoteException {
        StringWriter xmlWriter = new StringWriter();
        try {
            Utils.serializeObject(message, new QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain"), xmlWriter);
        }
        catch (Exception e) {
            throw new RemoteException("Cannot serialize..");
        }
        return new XMLUtils(xmlMarshaller).extractDomainObjectsFromXML(getModifiedDomainXML(xmlWriter.toString()));
    }

    private <T extends AbstractMutableDomainObject> List<T> getDomainObjectsFromList(
                    Class<T> domanObjectClass,
                    List<? extends AbstractMutableDomainObject> objectList) {
        List<T> list = new ArrayList<T>();
        for (AbstractMutableDomainObject o : objectList) {
            if (domanObjectClass.isInstance(o)) {
                list.add((T) o);
            }
        }
        return list;
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
