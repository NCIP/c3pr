package edu.duke.cabig.c3pr.web.study.tabs;

import edu.duke.cabig.c3pr.domain.*;
import edu.duke.cabig.c3pr.domain.validator.EpochValidator;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;
import edu.duke.cabig.c3pr.web.beans.DefaultObjectPropertyReader;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To change this template
 * use File | Settings | File Templates.
 */
public class StudyDesignTab extends StudyTab {

    private EpochValidator epochValidator;

    private StudyValidator studyValidator;

    public StudyDesignTab() {
        super("Epochs and Arms", "Epochs & Arms", "study/study_design");
    }

    @Override
    public Map referenceData(HttpServletRequest request, StudyWrapper wrapper) {
        Map<String, Object> refdata = super.referenceData(wrapper);
        refdata.put("currentOperation", getConfigurationProperty().getMap().get("inclusion"));
        addConfigMapToRefdata(refdata, "yesNo");
        boolean isAdmin = isAdmin();

        if ((request.getAttribute("amendFlow") != null && request.getAttribute("amendFlow")
                .toString().equals("true"))
                || (request.getAttribute("editFlow") != null && request.getAttribute(
                "editFlow").toString().equals("true"))) {
            if (request.getSession().getAttribute(DISABLE_FORM_EPOCH_AND_ARMS) != null && !isAdmin) {
                refdata.put("disableForm", request.getSession().getAttribute(
                        DISABLE_FORM_EPOCH_AND_ARMS));
            } else {
                refdata.put("disableForm", new Boolean(false));
            }
        }
        return refdata;
    }

    /*
     * Clear the book entries (if any) corresponding to the treatment epoch. Called by the deleteRow
     * of the row manager.
     */
    @Override
    public ModelAndView deleteRow(HttpServletRequest request, Object command, Errors error)
            throws Exception {

        String listPath = request.getParameter(getCollectionParamName());
        // If treatementEpochs are deleted we directly call the super.deleteRow()
        StudyWrapper wrapper = (StudyWrapper) command ;
        if (listPath.indexOf(".") > 0) {
            listPath = listPath.substring(0, listPath.indexOf("."));
            Epoch te = (Epoch) new DefaultObjectPropertyReader(wrapper.getStudy(), listPath)
                    .getPropertyValueFromPath();

            Randomization randomization = te.getRandomization();
            if (randomization instanceof BookRandomization) {
                BookRandomization bRandomization = (BookRandomization) randomization;
                bRandomization.getBookRandomizationEntry().clear();

                List<StratumGroup> sgList = te.getStratumGroups();
                for (StratumGroup sg : sgList) {
                    sg.getBookRandomizationEntry().clear();
                }
            }
        }
        return super.deleteRow(request, wrapper.getStudy(), error);
    }

    @Override
    public void postProcessOnValidation(HttpServletRequest httpServletRequest, StudyWrapper wrapper,
                                        Errors errors) {
        updateRandomization(wrapper.getStudy());
    }

    @Override
    public void validate(StudyWrapper wrapper, Errors errors) {
       super.validate(wrapper, errors);
        this.studyValidator.validateStudyDesign(wrapper.getStudy(), errors);
        this.studyValidator.validateEpochs(wrapper.getStudy(), errors);
    }

    public EpochValidator getEpochValidator() {
        return epochValidator;
    }

    public void setEpochValidator(EpochValidator epochValidator) {
        this.epochValidator = epochValidator;
    }

    public StudyValidator getStudyValidator() {
        return studyValidator;
    }

    public void setStudyValidator(StudyValidator studyValidator) {
        this.studyValidator = studyValidator;
    }
}
