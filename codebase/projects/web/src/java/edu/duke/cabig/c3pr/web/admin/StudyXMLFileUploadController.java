package edu.duke.cabig.c3pr.web.admin;

import java.io.File;
import java.util.Collection;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.service.StudyXMLImporterService;
import edu.duke.cabig.c3pr.web.ajax.StudyXMLFileImportAjaxFacade;
import edu.duke.cabig.c3pr.web.ajax.XMLFileUtils;
import edu.duke.cabig.c3pr.web.beans.FileBean;
import gov.nih.nci.common.exception.XMLUtilityException;

/**
 * User: kherm
 * 
 * @author kherm
 * @author kherm manav.kher@semanticbits.com
 */
public class StudyXMLFileUploadController extends SimpleFormController {

    private static Log log = LogFactory.getLog(StudyXMLFileUploadController.class);

    private StudyXMLFileImportAjaxFacade studyXMLFileAjaxFacade;

    private edu.duke.cabig.c3pr.service.StudyXMLImporterService studyXMLImporterService;

    public StudyXMLFileUploadController() {
        setBindOnNewForm(true);
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest httpServletRequest,
                    HttpServletResponse httpServletResponse, Object o, BindException errors)
                    throws Exception {
        // save it to session
    	String filePath = System.getenv("CATALINA_HOME") + System.getProperty("file.separator")
		        + "conf" + System.getProperty("file.separator") + "c3pr";
		File outputXMLDir = new File(filePath);
		outputXMLDir.mkdirs();
		String fileName = "importStudy-output-" + new Date().getTime() + ".xml";
    	File outputXMLFile = new File(filePath + System.getProperty("file.separator") + fileName);
        outputXMLFile.createNewFile();

        try {
            FileBean studyXMLFile = (FileBean) o;
            Collection<Study> studies = studyXMLImporterService.importStudies(studyXMLFile
                            .getInputStream(),outputXMLFile);

            log.debug("Storing imported studies into session for display in table");
            httpServletRequest.getSession().setAttribute(getFormSessionAttributeName(),
                            XMLFileUtils.getFilteredCopy(studies));

            Object validStudies = studyXMLFileAjaxFacade.getTable(null, httpServletRequest);

            Collection invalidStudies = XMLFileUtils.getInvalidImports(studies);
            for (Study invalidStudy : (Iterable<Study>) invalidStudies) {
                errors.reject("Could not import Study: " + invalidStudy.getTrimmedShortTitleText()
                                + " :" + invalidStudy.getImportErrorString());
            }

            httpServletRequest.setAttribute("studies", validStudies);
            httpServletRequest.setAttribute("filePath", fileName);
        }
        catch (XMLUtilityException e1) {
            log.debug("Uploaded file contains invalid study");
            errors.reject("Could not import Studies", e1.getMessage());
        }
        catch(Exception e1){
        	e1.printStackTrace();
        	log.debug("Uploaded file contains invalid studies");
            errors.reject("Could not import studies" + e1.getMessage());
        }

        return new ModelAndView(this.getSuccessView(), errors.getModel());
    }

    @Override
    protected ModelAndView showForm(HttpServletRequest httpServletRequest,
                    HttpServletResponse httpServletResponse, BindException e) throws Exception {
        return new ModelAndView(this.getFormView(), e.getModel());
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

    public StudyXMLImporterService getStudyXMLImporterService() {
        return studyXMLImporterService;
    }

    public void setStudyXMLImporterService(StudyXMLImporterService studyXMLImporterService) {
        this.studyXMLImporterService = studyXMLImporterService;
    }
}
