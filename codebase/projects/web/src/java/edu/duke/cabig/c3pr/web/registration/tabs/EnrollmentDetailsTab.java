package edu.duke.cabig.c3pr.web.registration.tabs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.constants.ICD9DiseaseSiteCodeDepth;
import edu.duke.cabig.c3pr.dao.ICD9DiseaseSiteDao;
import edu.duke.cabig.c3pr.domain.ICD9DiseaseSite;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySiteStudyVersion;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.StudySubjectStudyVersion;
import edu.duke.cabig.c3pr.domain.StudyVersion;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AjaxableUtils;
import edu.duke.cabig.c3pr.web.registration.StudySubjectWrapper;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To change this template
 * use File | Settings | File Templates.
 */
public class EnrollmentDetailsTab extends RegistrationTab<StudySubjectWrapper> {
	
	 private ICD9DiseaseSiteDao icd9DiseaseSiteDao;

    public void setIcd9DiseaseSiteDao(ICD9DiseaseSiteDao icd9DiseaseSiteDao) {
		this.icd9DiseaseSiteDao = icd9DiseaseSiteDao;
	}

	public EnrollmentDetailsTab() {
        super("Enrollment Details", "Enrollment Details", "registration/reg_registration_details");
    }
    
    @Override
    public Map referenceData(HttpServletRequest request,
    		StudySubjectWrapper command) {
    	Map refdata=super.referenceData(request, command);
    	Map<String, List<Lov>> configMap = configurationProperty.getMap();
    	refdata.put("paymentMethods", configMap.get("paymentMethods"));
    	 refdata.put("diseaseSiteCategories", getDiseaseSiteCategories());
    	return refdata;
    }
    
    public List<ICD9DiseaseSite> getDiseaseSiteCategories(){
    	List<ICD9DiseaseSite> icd9DiseaseSites = new ArrayList<ICD9DiseaseSite>();
    	icd9DiseaseSites.addAll(icd9DiseaseSiteDao.getByLevel(ICD9DiseaseSiteCodeDepth.LEVEL1));
    	
    	return icd9DiseaseSites;
    }
    
    @Override
    public void postProcess(HttpServletRequest request, StudySubjectWrapper command, Errors errors) {
    	StudySubjectWrapper wrapper = (StudySubjectWrapper) command ;
    	StudySubject studySubject = wrapper.getStudySubject();
    	if(WebUtils.hasSubmitParameter(request, "updateStudyVersion") && request.getParameter("updateStudyVersion").equals("false")){
    		Date registrationDate = null;
    		try {
				registrationDate = new SimpleDateFormat("MM/dd/yyyy").parse(request.getParameter("registrationDate"));
			} catch (ParseException e) {
				throw new RuntimeException("Invalid Submit. Registration Date is invalid");
			}
    		studySubject.changeStudyVersion(registrationDate);
    		return;
    	}
    		
        if(!StringUtils.isBlank(request.getParameter("treatingPhysicianInternal"))){
            for(StudyInvestigator studyInvestigator : studySubject.getStudySite().getStudyInvestigators()){
                if(studyInvestigator.getId()==Integer.parseInt(request.getParameter("treatingPhysicianInternal"))){
                	studySubject.setTreatingPhysician(studyInvestigator);
                    break;
                }
            }
        }

        if(command.getStudySubject().getDiseaseHistory() != null){
        	if(StringUtils.equals(command.getStudySubject().getDiseaseHistory().getOtherPrimaryDiseaseSiteCode(), "(Begin typing here)")){
        		command.getStudySubject().getDiseaseHistory().setOtherPrimaryDiseaseSiteCode("");
        	}
        }
    }
    
    @Override
    public void validate(StudySubjectWrapper command, Errors errors) {
    	Date date = command.getStudySubject().getStartDate();
	    if(date !=null){
			StudySiteStudyVersion studySiteStudyVersion = command.getStudySubject().getStudySubjectStudyVersion().getStudySiteStudyVersion();
			if (!studySiteStudyVersion.getStudySite().canEnroll(studySiteStudyVersion.getStudyVersion() , command.getStudySubject().getStartDate())){
				errors.reject("studySubject.startDate", "Study version invalid on this date");
			}
    	}
    }
    
    public ModelAndView validateRegistrationDate(HttpServletRequest request, Object command, Errors errors) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	Date consentSignedDate = null;
    	try {
    		consentSignedDate = new SimpleDateFormat("MM/dd/yyyy").parse(request.getParameter("consentSignedDate"));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		map.put("cannotEnroll", "false");
		StudySiteStudyVersion studySiteStudyVersion = ((StudySubjectWrapper)command).getStudySubject().getStudySubjectStudyVersion().getStudySiteStudyVersion();
		if (!studySiteStudyVersion.getStudySite().canEnroll(studySiteStudyVersion.getStudyVersion() , consentSignedDate)){
			map.put("cannotEnroll", "true");
			StudyVersion studyVersion = studySiteStudyVersion.getStudySite().getStudyVersion(consentSignedDate);
			map.put("studyVersion", studyVersion);
		}else{
			map.put(AjaxableUtils.getFreeTextModelName(), "");
			return new ModelAndView("",map);
		}
		return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
    }
    
}
