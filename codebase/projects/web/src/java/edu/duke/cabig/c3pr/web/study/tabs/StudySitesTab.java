package edu.duke.cabig.c3pr.web.study.tabs;

import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;
import org.springframework.validation.Errors;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 1:39:34 PM To change this template
 * use File | Settings | File Templates.
 */
public class StudySitesTab extends StudyTab {

    private StudyValidator studyValidator;
    
    protected Configuration configuration;

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public StudySitesTab() {
        super("Study Sites", "Study Sites", "study/study_studysites");
    }

    @Override
    public Map<String, Object> referenceData(HttpServletRequest request, StudyWrapper wrapper) {
        Map<String, Object> refdata = super.referenceData(wrapper);
        addConfigMapToRefdata(refdata, "studySiteStatusRefData");
        addConfigMapToRefdata(refdata, "studySiteRoleCodeRefData");
        refdata.put("multisiteEnv", new Boolean(this.configuration.get(Configuration.MULTISITE_ENABLE)));
        boolean isAdmin = isAdmin();
        return refdata;
    }

    @Override
    public void postProcessOnValidation(HttpServletRequest request, StudyWrapper wrapper,
                    Errors errors) {
        for(StudySite studySite: wrapper.getStudy().getStudySites()){
            if(studySite.getIsCoordinatingCenter() || studySite.getHostedMode())
                studySite.setCoordinatingCenterStudyStatus(wrapper.getStudy().getCoordinatingCenterStudyStatus());
            else if(WebUtils.hasSubmitParameter(request, "submitted") &&
                            (!WebUtils.hasSubmitParameter(request, studySite.getHealthcareSite().getNciInstituteCode()+"-wasHosted") || 
                             request.getParameter(studySite.getHealthcareSite().getNciInstituteCode()+"-wasHosted").equalsIgnoreCase("true")))
                studySite.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
        }
    }
    
    @Override
    public void validate(StudyWrapper wrapper, Errors errors) {
        super.validate(wrapper, errors);
        this.studyValidator.validateStudySites(wrapper.getStudy(), errors);
    }

    public StudyValidator getStudyValidator() {
        return studyValidator;
    }

    public void setStudyValidator(StudyValidator studyValidator) {
        this.studyValidator = studyValidator;
    }

}
