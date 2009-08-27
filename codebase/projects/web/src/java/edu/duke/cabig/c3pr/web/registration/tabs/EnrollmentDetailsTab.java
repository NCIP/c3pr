package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.dao.ICD9DiseaseSiteDao;
import edu.duke.cabig.c3pr.domain.ICD9DiseaseSite;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.StringUtils;
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
    	icd9DiseaseSites.addAll(icd9DiseaseSiteDao.getAllOrderedByName());
    	
    	return icd9DiseaseSites;
    }
    
    @Override
    public void postProcess(HttpServletRequest request, StudySubjectWrapper command, Errors errors) {
    	StudySubjectWrapper wrapper = (StudySubjectWrapper) command ;
    	StudySubject studySubject = wrapper.getStudySubject();
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
}
