package edu.duke.cabig.c3pr.web;

import org.springframework.web.servlet.mvc.ParameterizableViewController;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.web.PropertyWrapper;
import edu.duke.cabig.c3pr.domain.repository.CSMUserRepository;
import edu.duke.cabig.c3pr.domain.*;
import edu.duke.cabig.c3pr.service.impl.StudyServiceImpl;
import edu.duke.cabig.c3pr.service.impl.StudySubjectServiceImpl;

import java.util.*;
import java.io.IOException;

/**
 * User: ion
 * Date: Jun 11, 2008
 * Time: 1:56:21 PM
 */

public class DashboardController extends ParameterizableViewController {

	protected static final Log log = LogFactory.getLog(DashboardController.class);
	private Configuration configuration;
	private String filename;
	private StudyServiceImpl studyService;
	private StudySubjectServiceImpl studySubjectService;

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

		return super.handleRequestInternal(request, response);
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
		StudySubject registration = new StudySubject();
		List<StudySubject> registrations = studySubjectService.getIncompleteRegistrations(registration, MAX_RESULTS);
		log.debug("Unregistred Registrations found: " + registrations.size());
		Collections.reverse(registrations);
		request.setAttribute("uRegistrations", registrations);
	}
}
