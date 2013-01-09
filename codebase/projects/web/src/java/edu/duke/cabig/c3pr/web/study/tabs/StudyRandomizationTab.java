/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.study.tabs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.constants.RandomizationType;
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

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> referenceDataForTab(HttpServletRequest request, StudyWrapper wrapper) {
    	 Map<String, Object> refdata = super.referenceDataForTab(request,wrapper);
        Study study = wrapper.getStudy();
        String flowType = "";
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
