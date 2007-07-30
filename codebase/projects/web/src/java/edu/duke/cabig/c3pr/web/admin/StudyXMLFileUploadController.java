package edu.duke.cabig.c3pr.web.admin;


import edu.duke.cabig.c3pr.web.ajax.StudyXMLFileImportAjaxFacade;
import edu.duke.cabig.c3pr.web.ajax.BaseStudyAjaxFacade;
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

/**
 * User: kherm
 * @author kherm
 * @author kherm manav.kher@semanticbits.com
 */
public class StudyXMLFileUploadController  extends SimpleFormController {

    private static Log log = LogFactory.getLog(StudyXMLFileUploadController.class);
    private StudyXMLFileImportAjaxFacade studyXMLFileAjaxFacade;

    public StudyXMLFileUploadController() {
         setBindOnNewForm(true);
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, BindException errors) throws Exception {
        //save it to session
        httpServletRequest.getSession().setAttribute(getFormSessionAttributeName(),o);

        try {
            Object viewData = studyXMLFileAjaxFacade.getTable(null,httpServletRequest);
            httpServletRequest.setAttribute("studies", viewData);
        } catch (XMLUtilityException e1) {
            log.debug("Uploaded file contains invalid study");
            errors.reject("Could not import Studies",e1.getMessage());
        }

        return new ModelAndView(this.getSuccessView(), errors.getModel());
    }

     @Override
protected ModelAndView showForm(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BindException e) throws Exception {
     return new ModelAndView(this.getFormView(),e.getModel());
}

    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
            throws ServletException {
        // to actually be able to convert Multipart instance to byte[]
        // we have to register a custom editor
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        // now Spring knows how to handle multipart object and convert them
    }

    public StudyXMLFileImportAjaxFacade getStudyXMLFileAjaxFacade() {
        return studyXMLFileAjaxFacade;
    }

    public void setStudyXMLFileAjaxFacade(StudyXMLFileImportAjaxFacade studyXMLFileAjaxFacade) {
        this.studyXMLFileAjaxFacade = studyXMLFileAjaxFacade;
    }
}
