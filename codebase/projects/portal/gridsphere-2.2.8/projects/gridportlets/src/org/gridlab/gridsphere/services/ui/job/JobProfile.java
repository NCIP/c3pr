package org.gridlab.gridsphere.services.ui.job;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.util.List;
import java.util.ArrayList;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobProfile.java,v 1.1.1.1 2007-02-01 20:42:05 kherm Exp $
 */

public class JobProfile {

    private static PortletLog log = SportletLog.getInstance(JobProfile.class);

    public static final JobProfile INSTANCE = new JobProfile();

    protected Class jobViewPageClass = JobViewPage.class;
    protected Class submittedJobViewPageClass = CanceledJobViewPage.class;
    protected Class canceledJobViewPageClass = CanceledJobViewPage.class;
    protected Class deletedJobViewPageClass = DeletedJobViewPage.class;
    protected Class jobSpecViewPageClass = JobSpecViewPage.class;
    protected ArrayList jobSpecEditPageList = new ArrayList();
    protected boolean supportsMigration = false;
    protected Class migratedJobViewPageClass = MigratedJobViewPage.class;

    protected int order = 0;
    protected String key = "org.gridlab.gridsphere.services.ui.job";
    protected String description = "Job";
    protected String jobViewPageClassName = JobViewPage.class.getName();
    protected String submittedJobViewPageClassName = SubmittedJobViewPage.class.getName();
    protected String canceledJobViewPageClassName = CanceledJobViewPage.class.getName();
    protected String deletedJobViewPageClassName = DeletedJobViewPage.class.getName();
    protected String jobSpecViewPageClassName = JobSpecViewPage.class.getName();
    protected ArrayList jobSpecEditPageClassNameList = new ArrayList();
    protected String migratedJobViewPageClassName = MigratedJobViewPage.class.getName();

    public JobProfile() {
        log.debug("JobProfile() Creating new instance");
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Class getJobListViewPageClass() {
        return JobListViewPage.class;
    }

    public Class getDeletedJobListViewPageClass() {
        return DeletedJobListViewPage.class;
    }

    public Class getJobViewPageClass() {
        return jobViewPageClass;
    }

    public Class getSubmittedJobViewPageClass() {
        return submittedJobViewPageClass;
    }

    public Class getCanceledJobViewPageClass() {
        return canceledJobViewPageClass;
    }

    public Class getDeletedJobViewPageClass() {
        return deletedJobViewPageClass;
    }

    public Class getJobSpecViewPageClass() {
        return jobSpecViewPageClass;
    }

    public List getJobSpecEditPageClassList() {
        log.debug("getJobSpecEditPageClassList()");
        if (jobSpecEditPageList.size() == 0) {
            log.debug("Creating classes from class name list " + jobSpecEditPageClassNameList.size());
            for (int ii = 0; ii < jobSpecEditPageClassNameList.size(); ++ii)  {
                String nextPageClassName = (String)jobSpecEditPageClassNameList.get(ii);
                Class nextPageClass = getClass(nextPageClassName);
                if (nextPageClass != null) {
                    jobSpecEditPageList.add(nextPageClass);
                }
            }
        }
        log.debug("Returning class list of size " + jobSpecEditPageList.size());
        return jobSpecEditPageList;
    }

    public boolean supportsJobMigration() {
        return supportsMigration;
    }

    public void setSupportsMigration(boolean supportsMigration) {
        this.supportsMigration = supportsMigration;
    }

    public Class getMigratedJobViewPageClass() {
        return migratedJobViewPageClass;
    }

    public String getJobViewPageClassName() {
        return jobViewPageClassName;
    }

    public void setJobViewPageClassName(String jobViewPageClassName) {
        this.jobViewPageClassName = jobViewPageClassName;
        this.jobViewPageClass = getClass(jobViewPageClassName);
    }

    public String getSubmittedJobViewPageClassName() {
        return submittedJobViewPageClassName;
    }

    public void setSubmittedJobViewPageClassName(String submittedJobViewPageClassName) {
        this.submittedJobViewPageClassName = submittedJobViewPageClassName;
        this.submittedJobViewPageClass = getClass(submittedJobViewPageClassName);
    }

    public String getCanceledJobViewPageClassName() {
        return canceledJobViewPageClassName;
    }

    public void setCanceledJobViewPageClassName(String canceledJobViewPageClassName) {
        this.canceledJobViewPageClassName = canceledJobViewPageClassName;
        this.canceledJobViewPageClass = getClass(canceledJobViewPageClassName);
    }

    public String getDeletedJobViewPageClassName() {
        return deletedJobViewPageClassName;
    }

    public void setDeletedJobViewPageClassName(String deletedJobViewPageClassName) {
        this.deletedJobViewPageClassName = deletedJobViewPageClassName;
        this.deletedJobViewPageClass = getClass(deletedJobViewPageClassName);
    }

    public String getJobSpecViewPageClassName() {
        return jobSpecViewPageClassName;
    }

    public void setJobSpecViewPageClassName(String jobSpecViewPageClassName) {
        this.jobSpecViewPageClassName = jobSpecViewPageClassName;
        this.jobSpecViewPageClass = getClass(jobSpecViewPageClassName);
    }

    public ArrayList getJobSpecEditPageClassNameList() {
        log.debug("getJobSpecEditPageClassNameList()");
        log.debug("Returning class name list of size " + jobSpecEditPageClassNameList.size());
        return jobSpecEditPageClassNameList;
    }

    public void setJobSpecEditPageClassNameList(ArrayList jobSpecEditPageClassNameList) {
        log.debug("setJobSpecEditPageClassNameList()");
        this.jobSpecEditPageClassNameList = jobSpecEditPageClassNameList;
        jobSpecEditPageList.clear();
        log.debug("Creating classes from class name list " + jobSpecEditPageClassNameList.size());
        for (int ii = 0; ii < jobSpecEditPageClassNameList.size(); ++ii)  {
            String nextPageClassName = (String)jobSpecEditPageClassNameList.get(ii);
            Class nextPageClass = getClass(nextPageClassName);
            if (nextPageClass != null) {
                jobSpecEditPageList.add(nextPageClass);
            }
        }
    }

    public String getMigratedJobViewPageClassName() {
        return migratedJobViewPageClassName;
    }

    public void setMigratedJobViewPageClassName(String migratedJobViewPageClassName) {
        this.migratedJobViewPageClassName = migratedJobViewPageClassName;
        this.migratedJobViewPageClass = getClass(migratedJobViewPageClassName);
    }

    public boolean equals(JobProfile type) {
        return key.equals(type.key);
    }

    protected static Class getClass(String className) {
        try {
            return Class.forName(className, true, Thread.currentThread().getContextClassLoader());
        } catch (ClassNotFoundException e) {
            log.error("Action component class not found: " + className, e);
            return null;
        }
    }
}
