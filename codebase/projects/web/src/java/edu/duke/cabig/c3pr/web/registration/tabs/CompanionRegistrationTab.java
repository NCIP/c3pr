package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.domain.CompanionStudyAssociation;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AjaxableUtils;
import edu.duke.cabig.c3pr.web.registration.RegistrationControllerUtils;
import edu.duke.cabig.c3pr.web.registration.StudySubjectWrapper;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;

public class CompanionRegistrationTab<C extends StudySubjectWrapper> extends RegistrationTab<C> {
	
	private RegistrationControllerUtils registrationControllerUtils;
	
	public CompanionRegistrationTab() {
		super("Companion Registrations", "Companion Registrations", "registration/reg_companion_reg");
	}
	
	@Override
	public Map<String,Object> referenceData(HttpServletRequest request, StudySubjectWrapper wrapper) {
		Map map = new HashMap();
		map.put("companions", registrationControllerUtils.getCompanionStudySubject(wrapper.getStudySubject().getSystemAssignedIdentifiers().get(0)));
		return map;
	}

	public void setRegistrationControllerUtils(
			RegistrationControllerUtils registrationControllerUtils) {
		this.registrationControllerUtils = registrationControllerUtils;
	}
	
	public ModelAndView refreshCompanionSection(HttpServletRequest request,Object obj, Errors errors) {
		return new ModelAndView(AjaxableUtils.getAjaxViewName(request));
	}
	
//	 public ModelAndView refreshCompanionSection(HttpServletRequest request, Object obj, Errors errors) {
//			StudySubjectWrapper wrapper = (StudySubjectWrapper) obj;
//			StudySubject studySubject = wrapper.getStudySubject();
//			
//			HashMap map = new HashMap();
//			
//        	Identifier identifier=studySubject.getSystemAssignedIdentifiers().get(0);
//        	List<Identifier> identifiers=new ArrayList<Identifier>();
//        	identifiers.add(identifier);
//        	studySubject=studySubjectRepository.getUniqueStudySubjects(identifiers);
//        	studySubjectDao.initialize(studySubject);
//            Study study = studyDao.getById(studySubject.getStudySite().getStudy().getId());
//    	    studyDao.initialize(study);
//    	    for(CompanionStudyAssociation companionStudyAssoc : study.getCompanionStudyAssociations()){
//    	    	Study companionStudy = companionStudyAssoc.getCompanionStudy();
//    	    	studyDao.initialize(companionStudy);
//    	    }
//			return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
//		}

		
}
