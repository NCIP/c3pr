package edu.duke.cabig.c3pr.web.study;

import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Study;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jun 15, 2007
 * Time: 12:51:05 PM
 * To change this template use File | Settings | File Templates.
 */
class StudyIdentifiersTab extends StudyTab {


    public StudyIdentifiersTab() {
        super("Study Identifier", "Identifiers", "study/study_identifiers");
    }

    @Override
    public Map<String, Object> referenceData() {
        Map<String, Object> refdata = super.referenceData();
        addConfigMapToRefdata(refdata, "identifiersTypeRefData");
        refdata.put("identifiersSourceRefData", getHealthcareSiteDao().getAll());

        return refdata;
    }

    @Override
    public void postProcess(HttpServletRequest httpServletRequest, Study study, Errors errors) {
        String action   = httpServletRequest.getParameter("_action");

        if ("addIdentifier".equals(action))
        {
            log.debug("Requested Add Identifier");
            Identifier id = new Identifier();
            id.setValue("<enter value>");
            id.setSource("<enter value>");
            study.addIdentifier(id);
        }
        else if ("removeIdentifier".equals(action))
        {
            int selected = Integer.parseInt(httpServletRequest.getParameter("_selected"));
            log.debug("Requested Remove Identifier");
            Identifier id = study.getIdentifiers().get(selected);
            study.removeIdentifier(id);
        }
    }


}
