package edu.duke.cabig.c3pr.grid.registrationservice.service.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.oasis.wsrf.properties.GetMultipleResourcePropertiesResponse;
import org.oasis.wsrf.properties.GetMultipleResourceProperties_Element;
import org.oasis.wsrf.properties.GetResourcePropertyResponse;
import org.oasis.wsrf.properties.QueryResourcePropertiesResponse;
import org.oasis.wsrf.properties.QueryResourceProperties_Element;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.support.OpenSessionInViewInterceptor;
import org.springframework.web.context.request.WebRequest;

import edu.duke.cabig.c3pr.constants.NotificationEventTypeEnum;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.factory.StudySubjectFactory;
import edu.duke.cabig.c3pr.domain.repository.StudySubjectRepository;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.grid.registrationservice.common.RegistrationServiceI;
import edu.duke.cabig.c3pr.service.ScheduledNotificationService;
import edu.duke.cabig.c3pr.service.SchedulerService;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.SessionAndAuditHelper;
import edu.duke.cabig.c3pr.utils.XMLUtils;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import gov.nih.nci.cabig.ccts.domain.Message;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

public class C3PRRegistrationServiceImpl implements RegistrationServiceI, ApplicationContextAware{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(C3PRRegistrationServiceImpl.class);

    private XMLUtils xmUtils;

    private StudySubjectRepository studySubjectRepository;
    
    private ParticipantDao participantDao;
    
    private MessageSource c3prErrorMessages;
    
    private OpenSessionInViewInterceptor interceptor;
    
    private C3PRExceptionHelper exceptionHelper;

    private StudySubjectFactory studySubjectFactory;
    
    private Configuration configuration;
    
    private ApplicationContext applicationContext;
    
    private ScheduledNotificationService scheduledNotificationService;
    
    private SchedulerService schedulerService;
    
	public Message enroll(Message message) throws RemoteException {
        List<StudySubject> objects = xmUtils.getDomainObjectsFromList(StudySubject.class, xmUtils.getArguments(message));
        if (objects.size() != 1) {
            throw new RemoteException(
                            "Illegal Argument(s). Make sure there is exactly one studysubject in the message.");
        }
        StudySubject studySubject=null;
        WebRequest webRequest=SessionAndAuditHelper.setupHibernateSessionAndAudit(interceptor, "C3PR Admin", "Coordinating Center", new Date(), "Coordinating Center");
        try {
            studySubject=studySubjectFactory.buildStudySubject(objects.get(0));
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
        }catch (Exception e) {
        	e.printStackTrace();
        	String localNciCode = this.configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE);
        	//setting the healthcaresite as the local site for notification purpose
        	if(studySubject==null){
        		try {
					Study study= studySubjectFactory.buildStudy(objects.get(0).getStudySite().getStudy());
					for(StudySite studySite: study.getStudySites()){
						if(studySite.getHealthcareSite().getPrimaryIdentifier().equals(localNciCode)){
							objects.get(0).setStudySite(studySite);
							break;
						}
					}
				} catch (C3PRCodedException e1) {
					//catching exception. no need to do anything.
				}
        	}else{
        		for(StudySite studySite: studySubject.getStudySite().getStudy().getStudySites()){
					if(studySite.getHealthcareSite().getPrimaryIdentifier().equals(localNciCode)){
						objects.get(0).setStudySite(studySite);
						break;
					}
				}
        	}
			for(PlannedNotification pn: getPlannedNotifications(localNciCode)){
				if(pn.getEventName().equals(NotificationEventTypeEnum.MULTISITE_REGISTRATION_FAILURE)){
					Integer id= scheduledNotificationService.saveScheduledNotification(pn, objects.get(0));
					schedulerService.scheduleStudyNotification(pn, id);
				}
			}
            throw new RemoteException("error building the studysubject", e);
		}finally{
            SessionAndAuditHelper.tearDownHibernateSession(interceptor, webRequest);
        }
    }

    public Message transfer(Message message)
                    throws RemoteException {
    	List<StudySubject> objects = xmUtils.getDomainObjectsFromList(StudySubject.class, xmUtils.getArguments(message));
        if (objects.size() != 1) {
            throw new RemoteException(
                            "Illegal Argument(s). Make sure there is exactly one studysubject in the message.");
        }
        WebRequest webRequest=SessionAndAuditHelper.setupHibernateSessionAndAudit(interceptor, "C3PR Admin", "Coordinating Center", new Date(), "Coordinating Center");
        try {
        	StudySubject builtStudySubject=studySubjectFactory.buildReferencedStudySubject(objects.get(0));
        	builtStudySubject.setRegWorkflowStatus(objects.get(0).getRegWorkflowStatus());
        	List<StudySubject> stuList= studySubjectRepository.findRegistrations(builtStudySubject);
        	if(stuList.size()!=1){
        		throw new RemoteException(
                "There should be exactly one registration from the subject on a study at a site");
        	}
        	StudySubject studySubject= stuList.get(0);
        	studySubject.addScheduledEpoch(builtStudySubject.getScheduledEpoch());
        	studySubject=studySubjectRepository.transferSubject(studySubject);
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
    
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
    
    public List<PlannedNotification> getPlannedNotifications(String nciInstituteCode){
		List<PlannedNotification> result;
		List<String> nciCodeList = new ArrayList<String>();
		nciCodeList.add(nciInstituteCode);
		
		SessionFactory sessionFactory = (SessionFactory)applicationContext.getBean("sessionFactory");
		Session session = sessionFactory.openSession(sessionFactory.getCurrentSession().connection());
		session.setFlushMode(FlushMode.MANUAL);
		result = new ArrayList<PlannedNotification>();
        try {
          //Query query =  session.createQuery("select p from PlannedNotification p, HealthcareSite o where p.id = o.plannedNotificationsInternal.id and o.nciInstituteCode = ?");
          Query query =  session.createQuery("select p from PlannedNotification p, HealthcareSite o where p.id = o.plannedNotificationsInternal.id and o.nciInstituteCode in (:nciCodeList)").setParameterList("nciCodeList",nciCodeList);
          Query query1 =  session.createQuery("select p from PlannedNotification p, HealthcareSite o where p.id = o.plannedNotificationsInternal.id and o.nciInstituteCode="+"'"+nciInstituteCode+"'");
//          Query query = session.createQuery("Select p from PlannedNotification as p, o from HealthcareSite as o where p.id = o.plannedNotificationsInternal.id and" +
//          		"o.nci_institute_code in (:nciCodeList)").setParameterList("nciCodeList",nciCodeList);
//          query.setEntity(0, nciCodeList);
          result = query.list();
        }
        catch (DataAccessResourceFailureException e) {
        	logger.error(e.getMessage());
        }
        catch (IllegalStateException e) {
            e.printStackTrace();
        }
        catch (HibernateException e) {
        	logger.error(e.getMessage());
        }catch(Exception e){
        	logger.error(e.getMessage());
        }
        finally{
        	session.close();
        }
        return result;
        //result = organizationDao.getByNciIdentifier(configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE)).get(0).getPlannedNotifications();
        //return result;
	}
    
    public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
	public void setScheduledNotificationService(
			ScheduledNotificationService scheduledNotificationService) {
		this.scheduledNotificationService = scheduledNotificationService;
	}

	public void setSchedulerService(SchedulerService schedulerService) {
		this.schedulerService = schedulerService;
	}

}