package edu.duke.cabig.c3pr.web.registration.tabs;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.utils.StringUtils;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To change this template
 * use File | Settings | File Templates.
 */
public class EnrollmentDetailsTab extends RegistrationTab<StudySubject> {

    public EnrollmentDetailsTab() {
        super("Enrollment Details", "Enrollment Details", "registration/reg_registration_details");
    }
    
    @Override
    public void postProcess(HttpServletRequest request, StudySubject command, Errors errors) {
        if(!StringUtils.isBlank(request.getParameter("treatingPhysicianInternal"))){
            for(StudyInvestigator studyInvestigator:command.getStudySite().getStudyInvestigators()){
                if(studyInvestigator.getId()==Integer.parseInt(request.getParameter("treatingPhysicianInternal"))){
                    command.setTreatingPhysician(studyInvestigator);
                    break;
                }
            }
        }
    }
}
