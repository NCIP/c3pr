package edu.duke.cabig.c3pr.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

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
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.RecipientScheduledNotification;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.repository.CSMUserRepository;
import edu.duke.cabig.c3pr.service.PersonnelService;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.Lov;
import gov.nih.nci.ccts.grid.smoketest.client.SmokeTestServiceClient;

/**
 * User: ion Date: Jun 11, 2008 Time: 1:56:21 PM
 */

public class DashboardController extends ParameterizableViewController {

    protected static final Log log = LogFactory.getLog(DashboardController.class);

    private StudyDao studyDao;
    
    private StudySubjectDao studySubjectDao;

    private ResearchStaffDao researchStaffDao;

    private PlannedNotificationDao plannedNotificationDao;

    private PersonnelService personnelService;

    private SecurityContextCredentialProvider delegatedCredentialProvider;

    public static final int MAX_RESULTS = 5;

    CSMUserRepository csmUserRepository;

    private Configuration configuration;
    
    private MailSender mailSender;

    public MailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public void setCsmUserRepository(CSMUserRepository csmUserRepository) {
        this.csmUserRepository = csmUserRepository;
    }

    protected ModelAndView handleRequestInternal(HttpServletRequest request,
                    HttpServletResponse response) throws Exception {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        GrantedAuthority[] groups = auth.getAuthorities();

        Set<Lov> links = new HashSet<Lov>();
        for (GrantedAuthority ga : groups) {
            if (DashboardController.class.getClassLoader().getResource(
                            ga.getAuthority() + ".links.properties") != null) {
                String filename = ga.getAuthority() + ".links.properties";
                log.debug("Found rolebased links file: " + filename);
                Properties p = new Properties();
                try {
                    p.load(DashboardController.class.getClassLoader().getResourceAsStream(filename));
                    log.debug("The links file has " + p.keySet().size() + " elements.");
                }
                catch (IOException e) {
                    log.error("Error while trying to read the property file: [" + filename + "]");
                }
                addLinks(links, p);
            }
        }
        request.setAttribute("links", links);
        getMostEnrolledStudies(request);
        getRecentPendingStudies(request);
        getRecentPendingRegistrations(request);

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
    
    
    
    private void getMostEnrolledStudies(HttpServletRequest request) {
    	GregorianCalendar cal = new GregorianCalendar();
        Date endDate = new Date(System.currentTimeMillis());
        cal.setTime(endDate);
        cal.roll(Calendar.DATE, -6);
        Date startDate = new Date(cal.getTime().getTime());
        
        List<Study> studiesFound = studySubjectDao.getMostEnrolledStudies(startDate, endDate);
        List<Study> studies = new ArrayList<Study>();
        for(int i = 0 ; i<studiesFound.size() && i<MAX_RESULTS ; i++){
        	studies.add(studiesFound.get(i));
        }
        log.debug("Most enrolled studies found: " + studies.size());
        request.setAttribute("aStudies", studies);
    }

    private void getRecentPendingStudies(HttpServletRequest request) {
        List<Study> studiesFound = studyDao.getStudiesByStatus(CoordinatingCenterStudyStatus.PENDING);
        List<Study> studies = new ArrayList<Study>();
        for(int i = 0 ; i<studiesFound.size() && i<MAX_RESULTS ; i++){
        	studies.add(studiesFound.get(i));
        }
        log.debug("Pending studies found: " + studies.size());
        request.setAttribute("pStudies", studies);
    }

    private void getRecentPendingRegistrations(HttpServletRequest request) {
        List<StudySubject> registrationsFound = studySubjectDao.getIncompleteRegistrations();
        List<StudySubject> registrations = new ArrayList<StudySubject>();
        for(int i = 0 ; i<registrationsFound.size() && i<MAX_RESULTS ; i++){
        	registrations.add(registrationsFound.get(i));
        }
        log.debug("Unregistred Registrations found: " + registrations.size());
        request.setAttribute("uRegistrations", registrations);
    }

    private void testSmokeTestGridService() throws Exception {
        SmokeTestServiceClient client = new SmokeTestServiceClient(getURL(),delegatedCredentialProvider.provideDelegatedCredentials().getCredential());
        client.ping();
    }
    
    private String getURL() {
        return this.configuration.get(Configuration.SMOKE_TEST_URL);
    }

    private void addLinks(Set<Lov> lovs , Properties properties){
    	for(Object key : properties.keySet()){
    		lovs.add(new Lov(key.toString(),properties.getProperty(key.toString())));
    	}
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

	public void setStudyDao(StudyDao studyDao) {
		this.studyDao = studyDao;
	}

	public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
		this.studySubjectDao = studySubjectDao;
	}
}
