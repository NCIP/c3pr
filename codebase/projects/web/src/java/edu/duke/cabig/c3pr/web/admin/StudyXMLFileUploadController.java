package edu.duke.cabig.c3pr.web.admin;


import edu.duke.cabig.c3pr.web.ajax.StudyXMLFileImportAjaxFacade;
import edu.duke.cabig.c3pr.web.beans.StudyXMLFileBean;
import edu.duke.cabig.c3pr.xml.StudyXMLImporter;
import gov.nih.nci.common.exception.XMLUtilityException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jul 12, 2007
 * Time: 11:27:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class StudyXMLFileUploadController  extends SimpleFormController {

    private static Log log = LogFactory.getLog(StudyXMLFileUploadController.class);
    private StudyXMLImporter studyXMLImporter;
    private StudyXMLFileImportAjaxFacade studyXMLFileAjaxFacade;



    @Override
    protected ModelAndView onSubmit(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, BindException e) throws Exception {
        StudyXMLFileBean studyXML =  (StudyXMLFileBean)o;
        //save it to session
        httpServletRequest.getSession().setAttribute(getFormSessionAttributeName(),o);
        
        try {
            //should be done in a validator
            studyXMLImporter.validate(studyXML.getReader());
            log.debug("Uploaded study has ID");

            Object viewData = studyXMLFileAjaxFacade.getTable(null,httpServletRequest);
            httpServletRequest.setAttribute("studies", viewData);

        } catch (XMLUtilityException e1) {
            log.debug("Uploaded file contains invalid study");
        }

        Map map = e.getModel();
        ModelAndView modelAndView= new ModelAndView(getSuccessView(), map);
        return modelAndView;

    }

    

    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
            throws ServletException {
        // to actually be able to convert Multipart instance to byte[]
        // we have to register a custom editor
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        // now Spring knows how to handle multipart object and convert them
    }

    public StudyXMLImporter getStudyXMLImporter() {
        return studyXMLImporter;
    }

    public void setStudyXMLImporter(StudyXMLImporter studyXMLImporter) {
        this.studyXMLImporter = studyXMLImporter;
    }

    public StudyXMLFileImportAjaxFacade getStudyXMLFileAjaxFacade() {
        return studyXMLFileAjaxFacade;
    }

    public void setStudyXMLFileAjaxFacade(StudyXMLFileImportAjaxFacade studyXMLFileAjaxFacade) {
        this.studyXMLFileAjaxFacade = studyXMLFileAjaxFacade;
    }
}
