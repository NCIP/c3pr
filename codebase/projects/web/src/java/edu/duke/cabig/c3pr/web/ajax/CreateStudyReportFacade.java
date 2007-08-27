package edu.duke.cabig.c3pr.web.ajax;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.extremecomponents.table.bean.Export;
import org.extremecomponents.table.context.Context;
import org.extremecomponents.table.context.HttpServletRequestContext;
import org.extremecomponents.table.core.TableConstants;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.core.TableModelImpl;

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.utils.StringUtils;

public class CreateStudyReportFacade extends BaseStudyAjaxFacade{
	
    private static Log log = LogFactory.getLog(CreateStudyReportFacade.class);
    private StudySubjectDao studySubjectDao;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");


    public Object build(TableModel model, Collection studies) throws Exception {

    	Export export = model.getExportInstance();
        export.setView(TableConstants.VIEW_XLS);
        export.setViewResolver(TableConstants.VIEW_XLS);
        export.setImageName(TableConstants.VIEW_XLS);
        export.setText(TableConstants.VIEW_XLS);
        export.setFileName("study_report.xls");
        model.addExport(export);

        return super.build(model, studies, "Search Results", "/pages/report/createStudyReport");
    }

    public String getTable(Map <String, List>parameterMap, String[] params, HttpServletRequest request) {
		
		Study study = new Study();   	
		study.setShortTitleText(params[0].toString());
		
		SystemAssignedIdentifier id;
		id = new SystemAssignedIdentifier();
		id.setValue(params[1].toString());
        study.addIdentifier(id);
		
		Participant participant = new Participant();

		id = new SystemAssignedIdentifier();
        id.setValue(params[2].toString());
        participant.addIdentifier(id);        

		participant.setFirstName(params[3].toString());
		participant.setLastName(params[4].toString()); 
        
        StudySite studySite = new StudySite();
        studySite.setStudy(study);
        
        StudySubject studySubject = new StudySubject();
        studySubject.setStudySite(studySite);
        studySubject.setParticipant(participant);        

        List<StudySubject> studySubjectResults = studySubjectDao.advancedStudySearch(studySubject);

        Context context = new HttpServletRequestContext(request, parameterMap);

        StudySubject ss;
        List <Study> studyList = new ArrayList<Study>();
        Iterator iter = studySubjectResults.iterator();
        while(iter.hasNext()){
        	ss = (StudySubject)iter.next();
        	studyList.add(ss.getStudySite().getStudy());
        }

        TableModel model = new TableModelImpl(context);
        try {
            return build(model, studyList).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

	public StudySubjectDao getStudySubjectDao() {
		return studySubjectDao;
	}

	public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
		this.studySubjectDao = studySubjectDao;
	}



}
