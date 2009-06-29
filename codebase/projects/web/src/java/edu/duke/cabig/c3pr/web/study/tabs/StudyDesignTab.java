package edu.duke.cabig.c3pr.web.study.tabs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.domain.BookRandomization;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.Randomization;
import edu.duke.cabig.c3pr.domain.StratumGroup;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.validator.EpochValidator;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AjaxableUtils;
import edu.duke.cabig.c3pr.web.beans.DefaultObjectPropertyReader;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;

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
        addConfigMapToRefdata(refdata, "yesNo");
        boolean isAdmin = isAdmin();
        refdata.put("epochOrders", getEpochOrders(wrapper));

        if ((request.getAttribute("amendFlow") != null && request.getAttribute("amendFlow").toString().equals("true"))
                || (request.getAttribute("editFlow") != null && request.getAttribute("editFlow").toString().equals("true"))) {
            if (request.getSession().getAttribute(DISABLE_FORM_EPOCH_AND_ARMS) != null && !isAdmin) {
                refdata.put("disableForm", request.getSession().getAttribute(DISABLE_FORM_EPOCH_AND_ARMS));
            } else {
                refdata.put("disableForm", new Boolean(false));
            }
        }
        return refdata;
    }

    private List<Integer> getEpochOrders(StudyWrapper wrapper) {
		int epochs = wrapper.getStudy().getEpochs().size();
		List<Integer> epochOrders = new ArrayList<Integer>();
    	for(int i=1; i <= epochs; i++){
    		epochOrders.add(i);
    	}
    	return epochOrders ;
    }

	/*
     * Clear the book entries (if any) corresponding to the treatment epoch. Called by the deleteRow
     * of the row manager.
     */
    @Override
    public ModelAndView deleteRow(HttpServletRequest request, Object command, Errors error) throws Exception {
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
    
	public ModelAndView addEpoch(HttpServletRequest request, Object obj, Errors errors) {
		StudyWrapper wrapper = (StudyWrapper) obj;
		Study study = wrapper.getStudy();
		Epoch epoch = new Epoch();
//fix		epoch.setStudy(study);
		study.addEpoch(epoch);
		Map map = new HashMap();
//fix		map.put("index", study.getEpochsInternal().size() - 1);
		map.put("command", wrapper);
		return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
	}
}
