package edu.duke.cabig.c3pr.web;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.mail.Transport;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.MailSender;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import edu.duke.cabig.c3pr.accesscontrol.SecurityContextCredentialProvider;
import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.dao.PlannedNotificationDao;
import edu.duke.cabig.c3pr.dao.ResearchStaffDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.LocalStudy;
import edu.duke.cabig.c3pr.domain.RecipientScheduledNotification;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.repository.CSMUserRepository;
import edu.duke.cabig.c3pr.domain.repository.StudyRepository;
import edu.duke.cabig.c3pr.infrastructure.C3PRMailSenderImpl;
import edu.duke.cabig.c3pr.service.PersonnelService;
import edu.duke.cabig.c3pr.service.impl.StudySubjectServiceImpl;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.web.PropertyWrapper;
import gov.nih.nci.ccts.grid.smoketest.client.SmokeTestServiceClient;

/**
 * User: ion Date: Jun 11, 2008 Time: 1:56:21 PM
 */

public class DashboardController extends ParameterizableViewController {

    protected static final Log log = LogFactory.getLog(DashboardController.class);

    private String filename;

    //private StudyServiceImpl studyService;
    
    private StudyRepository studyRepository;

    private StudySubjectServiceImpl studySubjectService;

    private ResearchStaffDao researchStaffDao;

    private PlannedNotificationDao plannedNotificationDao;

    private PersonnelService personnelService;

    private SecurityContextCredentialProvider delegatedCredentialProvider;

    public static final int MAX_RESULTS = 5;

    CSMUserRepository csmUserRepository;

    private Configuration configuration;
    
    private MailSender mailSender;
    
    private StudyDao studyDao;

    public StudyDao getStudyDao() {
		return studyDao;
	}

	public void setStudyDao(StudyDao studyDao) {
		this.studyDao = studyDao;
	}

	public MailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public void setStudySubjectService(StudySubjectServiceImpl studySubjectService) {
        this.studySubjectService = studySubjectService;
    }

    public void setCsmUserRepository(CSMUserRepository csmUserRepository) {
        this.csmUserRepository = csmUserRepository;
    }

    protected ModelAndView handleRequestInternal(HttpServletRequest request,
                    HttpServletResponse response) throws Exception {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        GrantedAuthority[] groups = auth.getAuthorities();

        filename = "default.links.properties";

        for (GrantedAuthority ga : groups) {
            if (DashboardController.class.getClassLoader().getResource(
                            ga.getAuthority() + ".links.properties") != null) {
                filename = ga.getAuthority() + ".links.properties";
                log.debug("Found rolebased links file: " + filename);
                break;
            }
        }

        Properties p = new Properties();
        try {
            p.load(DashboardController.class.getClassLoader().getResourceAsStream(filename));
            log.debug("The links file has " + p.keySet().size() + " elements.");
            request.setAttribute("links", new PropertyWrapper(p));
        }
        catch (IOException e) {
            log.error("Error while trying to read the property file: [" + filename + "]");
        }

        getMostActiveStudies(request);
        getRecentPendingStudies(request);
        getRecentPendingRegistrations(request);
        testSmtpConnection(request);

        getNotifications(request);
        request.setAttribute("cctsEnv", isCCTSEnv());
        if(isCCTSEnv()){
            try {
                testSmokeTestGridService();
                request.setAttribute("authentication", "Passed");
            }
            catch (Exception e) {
                request.setAttribute("authentication", "Failed");
                request.setAttribute("smokeTestError", e.getMessage());
                log.error(e);
            }
        }
        return super.handleRequestInternal(request, response);
    }

    private void getNotifications(HttpServletRequest request){
    	
    	List<RecipientScheduledNotification> recipientScheduledNotificationsList = personnelService.getRecentNotifications(request);
    	request.getSession().setAttribute("recipientScheduledNotification", recipientScheduledNotificationsList);
        //request.getSession().setAttribute("scheduledNotifications", scheduledNotificationsList);
    }
    
    
    
    private void getMostActiveStudies(HttpServletRequest request) {
        Study study = new LocalStudy(true);
        study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
        List<Study> studies = studyRepository.searchByExample(study, false, MAX_RESULTS, "descending",
                        "id");
        log.debug("Open studies found: " + studies.size());

    	GregorianCalendar cal = new GregorianCalendar();
        Date endDate = new Date(System.currentTimeMillis());
        cal.setTime(endDate);
        cal.roll(Calendar.DATE, -6);
        Date startDate = new Date(cal.getTime().getTime());

        for (Study st : studies) {
        	studyDao.initialize(st);
            st.setAcrrualsWithinLastWeek(studyRepository.countAcrrualsByDate(st, startDate, endDate));
        }
        //TODO Temp fix added for BUG# 	 CPR-955, need to fix it properly in next release.
        studyDao.clear();
        request.setAttribute("aStudies", studies);
    }

    private void getRecentPendingStudies(HttpServletRequest request) {
        Study study = new LocalStudy(true);
        study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
        List<Study> studies = studyRepository.searchByExample(study, false, MAX_RESULTS, "descending", "id");
        for (Study st : studies) {
        	studyDao.initialize(st);
        }
        //TODO Temp fix added for BUG# 	 CPR-955, need to fix it properly in next release.
        studyDao.clear();
        log.debug("Pending studies found: " + studies.size());
        request.setAttribute("pStudies", studies);
    }

    private void getRecentPendingRegistrations(HttpServletRequest request) {
        StudySubject registration = new StudySubject(true);
        List<StudySubject> registrations = studySubjectService.getIncompleteRegistrations(MAX_RESULTS);
        log.debug("Unregistred Registrations found: " + registrations.size());
        request.setAttribute("uRegistrations", registrations);
    }

    private void testSmokeTestGridService() throws Exception {
        SmokeTestServiceClient client = new SmokeTestServiceClient(getURL(),delegatedCredentialProvider.provideDelegatedCredentials().getCredential());
        client.ping();
    }
    
    private void testSmtpConnection(HttpServletRequest request) {
        boolean smtpConnectionSuccess = false ;
        String errorTrace = "" ;
    	try {
        	C3PRMailSenderImpl c3prMailSender = (C3PRMailSenderImpl) mailSender ;
            Transport transport = c3prMailSender.getSession().getTransport(c3prMailSender.getProtocol());
            transport.connect(c3prMailSender.getHost(), c3prMailSender.getPort(), c3prMailSender.getUsername(), c3prMailSender.getPassword());
            smtpConnectionSuccess = transport.isConnected();
        } catch (Exception e) {
        	errorTrace = e.toString() ; 
        	if(e.getMessage() != null){
        		errorTrace = errorTrace + " : " + e.getMessage();
        	}
            log.error(" Error in connection with smtp server, please check configuration " + e);
        }
        request.setAttribute("errorTrace" , errorTrace);
        request.setAttribute("smtpConnectionSuccess", smtpConnectionSuccess);
    }

    private String getURL() {
        return this.configuration.get(Configuration.SMOKE_TEST_URL);
    }

    public ResearchStaffDao getResearchStaffDao() {
        return researchStaffDao;
    }

    private boolean isCCTSEnv(){
        return this.configuration.get(Configuration.ESB_ENABLE).equalsIgnoreCase("true");
    }
    public void setResearchStaffDao(ResearchStaffDao researchStaffDao) {
        this.researchStaffDao = researchStaffDao;
    }

    public PlannedNotificationDao getPlannedNotificationDao() {
        return plannedNotificationDao;
    }

    public void setPlannedNotificationDao(PlannedNotificationDao plannedNotificationDao) {
        this.plannedNotificationDao = plannedNotificationDao;
    }

    public PersonnelService getPersonnelService() {
        return personnelService;
    }

    public void setPersonnelService(PersonnelService personnelService) {
        this.personnelService = personnelService;
    }

    public void setDelegatedCredentialProvider(
                    SecurityContextCredentialProvider delegatedCredentialProvider) {
        this.delegatedCredentialProvider = delegatedCredentialProvider;
    }

    public void setStudyRepository(StudyRepository studyRepository) {
        this.studyRepository = studyRepository;
    }
}
