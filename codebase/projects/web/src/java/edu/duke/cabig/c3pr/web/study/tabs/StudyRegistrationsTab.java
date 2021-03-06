/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.study.tabs;

import edu.duke.cabig.c3pr.web.study.StudyWrapper;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:32:32 PM To change this template
 * use File | Settings | File Templates.
 */
public class StudyRegistrationsTab extends StudyTab {

    public StudyRegistrationsTab() {
        super("Study Registrations", "Registrations", "study/study_registrations");
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> referenceDataForTab(HttpServletRequest request,StudyWrapper wrapper) {
    	 Map<String, Object> refdata = super.referenceDataForTab(request,wrapper);
        refdata.put("participantAssignments", this.studyDao.getStudySubjectsForStudy(wrapper.getStudy().getId()));

        return refdata;
    }
}
