package edu.duke.cabig.c3pr.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import edu.duke.cabig.c3pr.dao.PlannedNotificationDao;
import edu.duke.cabig.c3pr.dao.ResearchStaffDao;
import edu.duke.cabig.c3pr.domain.C3PRUserGroupType;
import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.RecipientScheduledNotification;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.RoleBasedRecipient;
import edu.duke.cabig.c3pr.domain.ScheduledNotification;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.UserBasedRecipient;
import edu.duke.cabig.c3pr.domain.repository.CSMUserRepository;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.service.PersonnelService;
import edu.duke.cabig.c3pr.service.impl.StudyServiceImpl;
import edu.duke.cabig.c3pr.service.impl.StudySubjectServiceImpl;
import edu.duke.cabig.c3pr.utils.web.PropertyWrapper;

/**
 * User: ion
 * Date: Jun 11, 2008
 * Time: 1:56:21 PM
 */

public class DashboardController extends ParameterizableViewController {

	protected static final Log log = LogFactory.getLog(DashboardController.class);
	private String filename;
	private StudyServiceImpl studyService;
	private StudySubjectServiceImpl studySubjectService;
    private ResearchStaffDao researchStaffDao;
    private PlannedNotificationDao plannedNotificationDao;
    private PersonnelService personnelService;

	public static final int MAX_RESULTS = 5;

	CSMUserRepository csmUserRepository;

	public void setStudyService(StudyServiceImpl studyService) {
		this.studyService = studyService;
	}

	public void setStudySubjectService(StudySubjectServiceImpl studySubjectService) {
		this.studySubjectService = studySubjectService;
	}

	public void setCsmUserRepository(CSMUserRepository csmUserRepository) {
		this.csmUserRepository = csmUserRepository;
	}

	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {

		SecurityContext context = SecurityContextHolder.getContext();
		Authentication auth = context.getAuthentication();
		GrantedAuthority[] groups = auth.getAuthorities();

		filename = "default.links.properties";

		for (GrantedAuthority ga : groups) {
			if (DashboardController.class.getClassLoader().getResource(ga.getAuthority() + ".links.properties") != null) {
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
		} catch (IOException e) {
			log.error("Error while trying to read the property file: [" + filename + "]");
		}

		getMostActiveStudies(request);
		getRecentPendingStudies(request);
		getRecentPendingRegistrations(request);
		
		getRecentNotifications(request);

		return super.handleRequestInternal(request, response);
	}

	
	private void getRecentNotifications(HttpServletRequest request){
		gov.nih.nci.security.authorization.domainobjects.User user = (gov.nih.nci.security.authorization.domainobjects.User)request.getSession().getAttribute("userObject");
        List<ResearchStaff> rsList = researchStaffDao.getByEmailAddress(user.getEmailId());
        ResearchStaff rs  = null;
        List<RecipientScheduledNotification> recipientScheduledNotificationsList = new ArrayList<RecipientScheduledNotification>();
        List<ScheduledNotification> scheduledNotificationsList = new ArrayList<ScheduledNotification>();
        if(rsList.size() == 1){
        	rs = rsList.get(0);
        	//getting notifications set up as userBasedNotifications
        	for(UserBasedRecipient ubr: rs.getUserBasedRecipient()){
        		recipientScheduledNotificationsList.addAll(ubr.getRecipientScheduledNotification());
        	}
        	
        	//getting notifications set up as roleBasedNotifications
        	Iterator<C3PRUserGroupType> groupIterator = null;
        	List<String> groupRoles = new ArrayList<String>();
        	try{
        		groupIterator = personnelService.getGroups(user.getUserId().toString()).iterator();
        	}catch(C3PRBaseException cbe){
        		log.error(cbe.getMessage());
        	}
            while (groupIterator.hasNext()) {
            	groupRoles.add(((C3PRUserGroupType)groupIterator.next()).name());
            }
            //groupRoles now contains all the roles of the logged in user
        	for(PlannedNotification pn: plannedNotificationDao.getAll()){
        		for(RoleBasedRecipient rbr: pn.getRoleBasedRecipient()){
        			if(groupRoles.contains(rbr.getRole())){
        				recipientScheduledNotificationsList.addAll(rbr.getRecipientScheduledNotification());
        			}
        		}
        	}
        	
        }else{
        	//for the admin case
        	for(PlannedNotification pn: plannedNotificationDao.getAll()){
        		scheduledNotificationsList.addAll(pn.getScheduledNotification());
        	}
        }
        
        request.setAttribute("recipientScheduledNotification", recipientScheduledNotificationsList);
        request.setAttribute("scheduledNotifications", scheduledNotificationsList);		
	}

	private void getMostActiveStudies(HttpServletRequest request){
		Study study = new Study(true);
		study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.ACTIVE);
		List<Study> studies = studyService.searchByExample(study, false, MAX_RESULTS, "descending" , "id");
		log.debug("Active studies found: " + studies.size());

		Calendar cal = Calendar.getInstance();
		Date endDate = new Date(System.currentTimeMillis());
		cal.setTime(endDate);
		cal.roll(Calendar.DATE, -6);
		Date startDate = new Date(cal.getTime().getTime());

		for (Study st: studies) {
			st.setAcrrualsWithinLastWeek(studyService.countAcrrualsByDate(st, startDate, endDate));
		}
		request.setAttribute("aStudies", studies);
	}

	private void getRecentPendingStudies(HttpServletRequest request){
		Study study = new Study(true);
		study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
		List<Study> studies = studyService.searchByExample(study, false, MAX_RESULTS, "descending" , "id");
		log.debug("Pending studies found: " + studies.size());
		request.setAttribute("pStudies", studies);
	}

	private void getRecentPendingRegistrations(HttpServletRequest request){
		StudySubject registration = new StudySubject(true);
		List<StudySubject> registrations = studySubjectService.getIncompleteRegistrations(registration, MAX_RESULTS);
		log.debug("Unregistred Registrations found: " + registrations.size());
		request.setAttribute("uRegistrations", registrations);
	}
	
	public ResearchStaffDao getResearchStaffDao() {
		return researchStaffDao;
	}

	public void setResearchStaffDao(ResearchStaffDao researchStaffDao) {
		this.researchStaffDao = researchStaffDao;
	}

	public PlannedNotificationDao getPlannedNotificationDao() {
		return plannedNotificationDao;
	}

	public void setPlannedNotificationDao(
			PlannedNotificationDao plannedNotificationDao) {
		this.plannedNotificationDao = plannedNotificationDao;
	}

	public PersonnelService getPersonnelService() {
		return personnelService;
	}

	public void setPersonnelService(PersonnelService personnelService) {
		this.personnelService = personnelService;
	}
}
