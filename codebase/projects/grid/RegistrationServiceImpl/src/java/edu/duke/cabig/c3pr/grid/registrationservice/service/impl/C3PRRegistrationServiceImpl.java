package edu.duke.cabig.c3pr.grid.registrationservice.service.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.axis.message.MessageElement;
import org.oasis.wsrf.properties.GetMultipleResourcePropertiesResponse;
import org.oasis.wsrf.properties.GetMultipleResourceProperties_Element;
import org.oasis.wsrf.properties.GetResourcePropertyResponse;
import org.oasis.wsrf.properties.QueryResourcePropertiesResponse;
import org.oasis.wsrf.properties.QueryResourceProperties_Element;
import org.springframework.context.MessageSource;
import org.springframework.orm.hibernate3.support.OpenSessionInViewInterceptor;
import org.springframework.web.context.request.WebRequest;

import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.factory.StudySubjectFactory;
import edu.duke.cabig.c3pr.domain.repository.StudySubjectRepository;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.grid.registrationservice.common.RegistrationServiceI;
import edu.duke.cabig.c3pr.utils.SessionAndAuditHelper;
import edu.duke.cabig.c3pr.utils.XMLUtils;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import gov.nih.nci.cabig.ccts.domain.IdentifierType;
import gov.nih.nci.cabig.ccts.domain.Message;
import gov.nih.nci.cabig.ccts.domain.Registration;
import gov.nih.nci.cabig.ccts.domain.ScheduledEpochType;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

public class C3PRRegistrationServiceImpl implements RegistrationServiceI {

    private XMLUtils xmUtils;

    private StudySubjectRepository studySubjectRepository;
    
    private ParticipantDao participantDao;
    
    private MessageSource c3prErrorMessages;
    
    private OpenSessionInViewInterceptor interceptor;
    
    private C3PRExceptionHelper exceptionHelper;

    private StudySubjectFactory studySubjectFactory;

    public Message enroll(Message message) throws RemoteException {
        List<StudySubject> objects = xmUtils.getDomainObjectsFromList(StudySubject.class, xmUtils.getArguments(message));
        if (objects.size() != 1) {
            throw new RemoteException(
                            "Illegal Argument(s). Make sure there is exactly one studysubject in the message.");
        }
        WebRequest webRequest=SessionAndAuditHelper.setupHibernateSessionAndAudit(interceptor, "C3PR Admin", "Coordinating Center", new Date(), "Coordinating Center");
        try {
            StudySubject studySubject=studySubjectFactory.buildStudySubject(objects.get(0));
            if(studySubject.getParticipant().getId() == null){
                if (studySubject.getParticipant().validateParticipant())
                    participantDao.save(studySubject.getParticipant());
                else {
                    throw this.exceptionHelper
                    .getException(getCode("C3PR.EXCEPTION.REGISTRATION.SUBJECTS_INVALID_DETAILS.CODE"));
                }
            }
            studySubject=studySubjectRepository.enroll(studySubject);
            List<AbstractMutableDomainObject> domainObjects=new ArrayList<AbstractMutableDomainObject>();
            domainObjects.add(studySubject);
            Message returnMessage=xmUtils.getMessageForDomainObjects(domainObjects);
            return returnMessage;
        }
        catch (C3PRCodedException e) {
            throw new RemoteException("error building the studysubject", e);
        }finally{
            SessionAndAuditHelper.tearDownHibernateSession(interceptor, webRequest);
        }
    }

    public Message transfer(Message message)
                    throws RemoteException {
        List<Identifier> objects = xmUtils.getDomainObjectsFromList(Identifier.class, xmUtils.getArguments(message));
        if (objects.size() == 0) {
            throw new RemoteException(
                            "Illegal Argument(s). Make sure there is atleast one identifier in the message.");
        }
        WebRequest webRequest=SessionAndAuditHelper.setupHibernateSessionAndAudit(interceptor, "C3PR Admin", "Coordinating Center", new Date(), "Coordinating Center");
        try {
            StudySubject studySubject=studySubjectRepository.transferSubject(objects);
            List<AbstractMutableDomainObject> domainObjects=new ArrayList<AbstractMutableDomainObject>();
            domainObjects.add(studySubject);
            Message returnMessage=xmUtils.getMessageForDomainObjects(domainObjects);
            return returnMessage;
        }
        catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }finally{
            SessionAndAuditHelper.tearDownHibernateSession(interceptor, webRequest);
        }
    }

    public void offStudy(Message message)
                    throws RemoteException {
        List<Identifier> identifiers = xmUtils.getDomainObjectsFromList(Identifier.class, xmUtils.getArguments(message));
        if (identifiers.size() == 0) {
            throw new RemoteException(
                            "Illegal Argument(s). Make sure there is atleast one identifier in the message.");
        }
        List<StudySubject> studySubjects = xmUtils.getDomainObjectsFromList(StudySubject.class, xmUtils.getArguments(message));
        if (studySubjects.size() != 1) {
            throw new RemoteException(
                            "Illegal Argument(s). Make sure there is exactly one studysubject in the message.");
        }
        WebRequest webRequest=SessionAndAuditHelper.setupHibernateSessionAndAudit(interceptor, "C3PR Admin", "Coordinating Center", new Date(), "Coordinating Center");
        try {
            studySubjectRepository.takeSubjectOffStudy(identifiers, studySubjects.get(0).getOffStudyReasonText(), studySubjects.get(0).getOffStudyDate());
        }
        catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }finally{
            SessionAndAuditHelper.tearDownHibernateSession(interceptor, webRequest);
        }
    }
    
    public Message getRegistrations(Message message) throws RemoteException {
		throw new RemoteException("Not yet implemented");
	}

    public QueryResourcePropertiesResponse queryResourceProperties(
                    QueryResourceProperties_Element params) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
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

    public void setXmlMarshaller(XmlMarshaller xmlMarshaller) {
        this.xmUtils=new XMLUtils(xmlMarshaller);
    }

    public void setStudySubjectRepository(StudySubjectRepository studySubjectRepository) {
        this.studySubjectRepository = studySubjectRepository;
    }

    public void setStudySubjectFactory(StudySubjectFactory studySubjectFactory) {
        this.studySubjectFactory = studySubjectFactory;
    }

    public void setInterceptor(OpenSessionInViewInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    public void setParticipantDao(ParticipantDao participantDao) {
        this.participantDao = participantDao;
    }
    
    private int getCode(String errortypeString) {
        return Integer.parseInt(this.c3prErrorMessages.getMessage(errortypeString, null, null));
    }

    public void setC3prErrorMessages(MessageSource errorMessages) {
        c3prErrorMessages = errorMessages;
    }

    public void setExceptionHelper(C3PRExceptionHelper exceptionHelper) {
        this.exceptionHelper = exceptionHelper;
    }

}