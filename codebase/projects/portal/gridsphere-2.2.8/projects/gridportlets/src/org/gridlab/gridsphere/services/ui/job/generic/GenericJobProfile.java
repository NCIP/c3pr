package org.gridlab.gridsphere.services.ui.job.generic;

import org.gridlab.gridsphere.services.ui.job.JobProfile;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GenericJobProfile.java,v 1.1.1.1 2007-02-01 20:42:07 kherm Exp $
 */

public class GenericJobProfile extends JobProfile {

    private static PortletLog log = SportletLog.getInstance(GenericJobProfile.class);

    public static final GenericJobProfile INSTANCE = new GenericJobProfile(true);

    public GenericJobProfile() {
        log.debug("GenericJobProfile() Creating new instance");
        key = getClass().getPackage().getName();
        description = "Generic Application";
        jobViewPageClass = GenericJobViewPage.class;
        submittedJobViewPageClass = GenericJobViewPage.class;
        canceledJobViewPageClass = JobTaskViewComp.class;
        deletedJobViewPageClass = JobTaskViewComp.class;
        jobSpecViewPageClass = GenericJobSpecViewPage.class;
        supportsMigration = true;
        migratedJobViewPageClass = GenericJobViewPage.class;
    }

    public GenericJobProfile(boolean dontcare) {
        log.debug("GenericJobProfile() Creating new instance");
        key = getClass().getPackage().getName();
        description = "Generic Application";
        jobViewPageClass = GenericJobViewPage.class;
        submittedJobViewPageClass = GenericJobViewPage.class;
        canceledJobViewPageClass = JobTaskViewComp.class;
        deletedJobViewPageClass = JobTaskViewComp.class;
        jobSpecViewPageClass = GenericJobSpecViewPage.class;
        supportsMigration = true;
        migratedJobViewPageClass = GenericJobViewPage.class;
        initJobSpecEditPageClassNameList();
    }

    public void initJobSpecEditPageClassNameList() {
        log.debug("initJobSpecEditPageClassNameList()");
        jobSpecEditPageClassNameList.add(GenericApplicationSpecEditPage.class.getName());
        jobSpecEditPageClassNameList.add(GenericResourceSpecEditPage.class.getName());
        jobSpecEditPageClassNameList.add(GenericJobSpecConfirmPage.class.getName());
    }
}
