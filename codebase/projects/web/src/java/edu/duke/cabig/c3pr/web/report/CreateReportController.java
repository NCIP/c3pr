/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.report;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.extremecomponents.table.context.Context;
import org.extremecomponents.table.context.HttpServletRequestContext;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.core.TableModelImpl;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import edu.duke.cabig.c3pr.constants.RaceCodeEnum;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalStudy;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.RaceCodeAssociation;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.web.ajax.CreateReportFacade;

/*
 * @author Vinay Gangoli 
 * This controller produces a extreme components table based search results which can be 
 * downloaded as an excel report.
 */
public class CreateReportController extends SimpleFormController {

    private static Log log = LogFactory.getLog(CreateReportController.class);

    private StudySubjectDao studySubjectDao;

    private ConfigurationProperty configurationProperty;

    private ReportCommand reportCommand;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

    public ConfigurationProperty getConfigurationProperty() {
        return configurationProperty;
    }

    public void setConfigurationProperty(ConfigurationProperty configurationProperty) {
        this.configurationProperty = configurationProperty;
    }

    @Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
                    throws Exception {
        super.initBinder(request, binder);

        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(
                        "MM/dd/yyyy"), true));

        if (request.getMethod().equals(METHOD_GET)) {
            CreateReportFacade studyFacade = new CreateReportFacade();
            Context context = null;
            context = new HttpServletRequestContext(request);

            TableModel model = new TableModelImpl(context);
            Object viewData = null;
            try {
                viewData = studyFacade.build(model, null);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            request.setAttribute("assembler", viewData);
        }
    }

    protected Map<String, Object> referenceData(HttpServletRequest request) {

        Map<String, Object> configMap = configurationProperty.getMap();
        Map<String, Object> refdata = new HashMap<String, Object>();

        refdata.put("administrativeGenderCode", configMap.get("administrativeGenderCode"));
        refdata.put("ethnicGroupCode", configMap.get("ethnicGroupCode"));
        refdata.put("raceCode", configMap.get("raceCode"));

        return refdata;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
                    Object oCommand, BindException errors) throws Exception {

        CreateReportFacade reportFacade = new CreateReportFacade();
        Context context = null;
        context = new HttpServletRequestContext(request);

        Study study = new LocalStudy();
        TableModel model = new TableModelImpl(context);
        String[] params = reportCommand.getParams();
        String studyShortTitle = "";
        String studyCoordinatingSiteId = "";
        String siteName = "";
        String siteNciId = "";
        String rStartDate = "";
        String rEndDate = "";
        String birthDate = "";
        String raceCode = "";

        Map map = errors.getModel();
        map.put("raceCode", getConfigurationProperty().getMap().get("raceCode"));
        ModelAndView modelAndView = new ModelAndView(getSuccessView(), map);
        if (params == null) {
            return modelAndView;
        }

        if (params[0] != null && params[0].length() > 0) {
            studyShortTitle = params[0].toString();
        }

        if (params[1] != null && params[1].length() > 0) {
            studyCoordinatingSiteId = params[1].toString();
        }

        if (params[2] != null && params[2].length() > 0) {
            siteName = params[2].toString();
        }

        if (params[3] != null && params[3].length() > 0) {
            siteNciId = params[3].toString();
        }

        if (params[4] != null && params[4].length() > 0) {
            rStartDate = params[4].toString();
        }

        if (params[5] != null && params[5].length() > 0) {
            rEndDate = params[5].toString();
        }

        if (params[6] != null && params[6].length() > 0) {
            birthDate = params[6].toString();
        }

        if (params[7] != null && params[7].length() > 0) {
            raceCode = params[7].toString();
        }

        if (studyShortTitle != null && studyShortTitle != "") {
            study.setShortTitleText(studyShortTitle);
        }

        HealthcareSite hcs = new LocalHealthcareSite();
        if (siteName != null && siteName != "") {
            hcs.setName(siteName);
        }

        if (siteNciId != null && siteNciId != "") {
            hcs.setCtepCode(siteNciId);
        }

        StudySite studySite = new StudySite();
        studySite.setStudy(study);
        studySite.setHealthcareSite(hcs);

        Participant participant = new Participant();
		 RaceCodeAssociation  raceCodeAssociation = new RaceCodeAssociation();
         raceCodeAssociation.setRaceCode(RaceCodeEnum.valueOf(raceCode));
         participant.addRaceCodeAssociation(raceCodeAssociation);
		

        Date regStartDate = null;
        Date regEndDate = null;
        try {
            if (birthDate != null && !birthDate.equals("")) {
                participant.setBirthDate(simpleDateFormat.parse(birthDate));
            }
            if (rStartDate != null && !rStartDate.equals("")) {
                regStartDate = simpleDateFormat.parse(rStartDate);
            }
            if (rEndDate != null && !rEndDate.equals("")) {
                regEndDate = simpleDateFormat.parse(rEndDate);
            }
        }
        catch (ParseException pr) {
            log.error("DateFormat Exception in CreateReportController");
        }

        StudySubject studySubject = new StudySubject();
        studySubject.setStudySite(studySite);
        studySubject.setParticipant(participant);
        List<StudySubject> registrationResults = studySubjectDao.advancedSearch(studySubject,
                        regStartDate, regEndDate, studyCoordinatingSiteId);
        Object viewData = null;
        try {
            viewData = reportFacade.build(model, registrationResults);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("assembler", viewData);

        return modelAndView;
    }

    public StudySubjectDao getRegistrationDao() {
        return studySubjectDao;
    }

    public void setRegistrationDao(StudySubjectDao studySubjectDao) {
        this.studySubjectDao = studySubjectDao;
    }

    public ReportCommand getReportCommand() {
        return reportCommand;
    }

    public void setReportCommand(ReportCommand reportCommand) {
        this.reportCommand = reportCommand;
    }

    public StudySubjectDao getStudySubjectDao() {
        return studySubjectDao;
    }

    public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
        this.studySubjectDao = studySubjectDao;
    }

}
