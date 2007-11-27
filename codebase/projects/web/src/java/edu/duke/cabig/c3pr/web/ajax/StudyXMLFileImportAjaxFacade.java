package edu.duke.cabig.c3pr.web.ajax;

import edu.duke.cabig.c3pr.domain.Study;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.extremecomponents.table.context.Context;
import org.extremecomponents.table.context.HttpServletRequestContext;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.core.TableModelImpl;
import org.springframework.web.HttpSessionRequiredException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jul 13, 2007
 * Time: 4:55:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class StudyXMLFileImportAjaxFacade extends BaseStudyAjaxFacade {

    private edu.duke.cabig.c3pr.service.StudyXMLImporterService studyXMLImporterService;
    private static Log log = LogFactory.getLog(StudyXMLFileImportAjaxFacade.class);

    public String getTable(Map parameterMap, HttpServletRequest request)
            throws Exception {

        Context context = null;
        if (parameterMap == null) {
            context = new HttpServletRequestContext(request);
        } else {
            context = new HttpServletRequestContext(request, parameterMap);
        }

        TableModel model = new TableModelImpl(context);
        String action = "/pages/admin/importStudy";

        List<Study> studies = (List<Study>) getCommandOnly(request);
        return build(model, studies, "Imported Studies", action).toString();
    }


    private Object getCommandOnly(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new HttpSessionRequiredException("Must have session when trying to bind (in session-form mode)");
        }
        String formAttrName = "edu.duke.cabig.c3pr.web.admin.StudyXMLFileUploadController.FORM.command";
        Object sessionFormObject = session.getAttribute(formAttrName);

        return sessionFormObject;
    }


}
