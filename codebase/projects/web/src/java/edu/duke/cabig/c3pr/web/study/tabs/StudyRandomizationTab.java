package edu.duke.cabig.c3pr.web.study.tabs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.domain.RandomizationType;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AjaxableUtils;
import edu.duke.cabig.c3pr.web.ajax.BookRandomizationAjaxFacade;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;

/**
 * Author: gangoliV Date: July 30, 2007
 */

public class StudyRandomizationTab extends StudyTab {

    private BookRandomizationAjaxFacade bookRandomizationAjaxFacade;

    public StudyRandomizationTab() {
        super("Randomization", "Randomization", "study/study_randomizations");
    }

    @Override
    public Map<String, Object> referenceData(HttpServletRequest request, StudyWrapper wrapper) {
        Map<String, Object> refdata = super.referenceData(wrapper);
        Study study = wrapper.getStudy();
        String flowType = "";
        boolean isAdmin = isAdmin();

        if (getFlow().getName().equals("Create Study")) {
            flowType = "CREATE_STUDY";
        } else if (getFlow().getName().equals("Edit Study")) {
            flowType = "EDIT_STUDY";
        } else if (getFlow().getName().equals("Amend Study")) {
            flowType = "AMEND_STUDY";
        }
        if (study.getRandomizedIndicator()
                && study.getRandomizationType() == RandomizationType.BOOK 
                && request.getAttribute("bookRandomizationEntries") == null) {
            Map<String, List> dummyMap = new HashMap<String, List>();
            String[] bookRandomizationEntries = new String[study.getEpochs().size()];
            for (int i = 0; i < study.getEpochs().size(); i++) {
                bookRandomizationEntries[i] = bookRandomizationAjaxFacade.getTableForWrapper(dummyMap, "", i
                        + "", request, flowType, wrapper);
            }
            request.setAttribute("bookRandomizationEntries", bookRandomizationEntries);
        }
        if ((request.getAttribute("amendFlow") != null && request.getAttribute("amendFlow")
                .toString().equals("true"))
                || (request.getAttribute("editFlow") != null && request.getAttribute(
                "editFlow").toString().equals("true"))) {
            if (request.getSession().getAttribute(DISABLE_FORM_RANDOMIZATION) != null && !isAdmin) {
                refdata.put("disableForm", request.getSession().getAttribute(
                        DISABLE_FORM_RANDOMIZATION));
            } else {
                refdata.put("disableForm", new Boolean(false));
            }
        }
        return refdata;
    }

    @Override
    public void postProcessOnValidation(HttpServletRequest req, StudyWrapper wrapper, Errors errors) {
        if (wrapper.getStudy().getRandomizationType() != null
                && wrapper.getStudy().getRandomizationType().equals(RandomizationType.BOOK) && req.getParameter("index") != null) {
            parseFile(req, wrapper, errors);
        }
    }

    public ModelAndView parseFile(HttpServletRequest request, Object commandObj, Errors error) {

        String flowType = " ";
        if (getFlow().getName().equals("Create Study")) {
            flowType = "CREATE_STUDY";
        } else if (getFlow().getName().equals("Edit Study")) {
            flowType = "EDIT_STUDY";
        } else if (getFlow().getName().equals("Amend Study")) {
            flowType = "AMEND_STUDY";
        }

        Map <String, String>map = new HashMap<String, String>();
        try {
            String index = request.getParameter("index").toString();
            StudyWrapper wrapper = (StudyWrapper) commandObj ;
            
            Object viewData = bookRandomizationAjaxFacade.getTableForWrapper(new HashMap<String, List>(),
                    wrapper.getFile(), index, request, flowType, wrapper);
            if (StringUtils.isEmpty(viewData.toString())) {
                map.put(AjaxableUtils.getFreeTextModelName(), "<div><b>Incorrect format. Please try again.</b></div>");
            } else {
            	ArrayList<String> bookRandomizationEntries = new ArrayList<String>();
                bookRandomizationEntries.add(Integer.parseInt(index), viewData.toString());
                request.setAttribute("bookRandomizationEntries", bookRandomizationEntries.toArray());
                map.put(AjaxableUtils.getFreeTextModelName(), viewData.toString());
            }
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ModelAndView("", map);
    }

    public BookRandomizationAjaxFacade getBookRandomizationAjaxFacade() {
        return bookRandomizationAjaxFacade;
    }

    public void setBookRandomizationAjaxFacade(
            BookRandomizationAjaxFacade bookRandomizationAjaxFacade) {
        this.bookRandomizationAjaxFacade = bookRandomizationAjaxFacade;
    }

}