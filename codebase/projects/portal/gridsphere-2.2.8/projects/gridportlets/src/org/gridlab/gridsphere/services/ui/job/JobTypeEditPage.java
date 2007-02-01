package org.gridlab.gridsphere.services.ui.job;

import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.job.generic.GenericJobProfile;
import org.gridlab.gridsphere.services.ui.wizard.ActionPage;
import org.gridlab.gridsphere.services.job.JobType;
import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;
import org.gridlab.gridsphere.provider.portletui.beans.ListBoxBean;
import org.gridlab.gridsphere.provider.portletui.beans.ListBoxItemBean;

import java.util.*;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobTypeEditPage.java,v 1.1.1.1 2007-02-01 20:42:06 kherm Exp $
 */

public class JobTypeEditPage extends JobComponent implements ActionPage {

    private transient static PortletLog log = SportletLog.getInstance(JobTypeEditPage.class);

    // Job component type variables
    protected JobProfileRegistryService jobProfileRegistryService = null;
    protected ListBoxBean jobProfileListBox = null;
    protected static Map jobProfileMap = new HashMap();
    protected JobProfile selectedJobProfile = GenericJobProfile.INSTANCE;
    // Job service type variables
    protected ListBoxBean jobServiceTypeListBox = null;
    protected static Map jobServiceTypeMap = new HashMap();
    protected JobType selectedJobServiceType = JobType.INSTANCE;
    protected int jobProfileListSize = 1;
    protected int jobServiceListSize = 1;

    public JobTypeEditPage(ActionComponentFrame container, String compName) {
        super(container, compName);
        // Page beans
        jobProfileListBox = createListBoxBean("jobProfileListBox");
        jobServiceTypeListBox = createListBoxBean("jobServiceTypeListBox");
        // Default action and page
        setDefaultAction("doJobTypeEdit");
        setDefaultJspPage("/jsp/job/generic/JobTypeEditComp.jsp");
    }

    public void onInit() throws PortletException {
        super.onInit();
        jobProfileRegistryService = (JobProfileRegistryService)
                getPortletService(JobProfileRegistryService.class);
    }

    public void onStore() throws PortletException {
        super.onStore();
        log.debug("jobProfileListSize = " + jobProfileListSize);
        log.debug("jobServiceListSize = " + jobServiceListSize);
        setPageAttribute("jobProfileListSize", new Integer(jobProfileListSize));
        setPageAttribute("jobServiceListSize", new Integer(jobServiceListSize));
    }

    public JobType getSelectedJobServiceType() {
        return selectedJobServiceType;
    }

    public JobProfile getSelectedJobProfile() {
        return selectedJobProfile;
    }

    public void doJobTypeEdit(Map parameters) throws PortletException {
        log.error("doJobTypeEdit");
        // Build job component type list
        selectedJobProfile = getJobProfile(parameters);
        if (selectedJobProfile == null) {
            selectedJobProfile = GenericJobProfile.INSTANCE;
        }
        buildJobProfileList();
        // Build job service type list
        selectedJobServiceType = getJobType(parameters);
        if (selectedJobServiceType == null) {
            selectedJobServiceType = JobType.INSTANCE;
        }
        buildJobServiceTypeList();
        // Set next state
        setNextState(defaultJspPage);
    }

    public void buildJobProfileList() {
        jobProfileListBox.clear();
        ListBoxItemBean firstItem = null;
        String selectedJobTypeKey = selectedJobProfile.getKey();
        List jobProfileList = sortJobProfileList(jobProfileRegistryService.getJobProfiles());
        jobProfileListSize = jobProfileList.size();
        for (int ii = 0; ii < jobProfileList.size(); ++ii) {
            JobProfile jobProfile = (JobProfile)jobProfileList.get(ii);
            ListBoxItemBean jobProfileItem = new ListBoxItemBean();
            String jobProfileItemName = jobProfile.getKey();
            jobProfileMap.put(jobProfileItemName, jobProfile);
            jobProfileItem.setName(jobProfileItemName);
            String jobProfileItemValue = jobProfile.getDescription();
            jobProfileItem.setValue(jobProfileItemValue);
            // Save reference to first item
            if (ii == 0) {
                firstItem = jobProfileItem;
            }
            // Set selected if equals our selection
            if (jobProfileItemName.equals(selectedJobTypeKey)) {
                jobProfileItem.setSelected(true);
            }
            jobProfileListBox.addBean(jobProfileItem);
        }
        // Set first item selected if none is already selected
        if (jobServiceTypeListBox.getSelectedName() == null && firstItem != null) {
            firstItem.setSelected(true);
        }
    }

    protected static List sortJobProfileList(List inputList) {
        LinkedList outputList = new LinkedList();
        for (int ii = 0; ii < inputList.size(); ++ii) {
            JobProfile type = (JobProfile)inputList.get(ii);
            int numOutList = outputList.size();
            if (numOutList == 0) {
                outputList.add(type);
            } else {
                int order = type.getOrder();
                for (int jj = numOutList-1; jj >= 0; --jj) {
                    JobProfile nextType = (JobProfile)outputList.get(jj);
                    int nextOrder = nextType.getOrder();
                    if (order < nextOrder) {
                        outputList.add(jj, type);
                        break;
                    } else {
                        int kk = jj+1;
                        if (kk == numOutList) {
                            outputList.add(type);
                        } else {
                            outputList.add(kk,type);
                        }
                        break;
                    }
                }
            }
        }
        return outputList;
    }

    public void buildJobServiceTypeList() {
        jobServiceTypeListBox.clear();
        ListBoxItemBean firstItem = null;
        List jobServiceTypeList = jobSubmissionService.getJobTypes();
        log.debug("There are " + jobServiceTypeList.size() + " job types");
        jobServiceListSize = jobServiceTypeList.size();
        for (int ii = 0; ii < jobServiceTypeList.size(); ++ii) {
            JobType jobServiceType = (JobType)jobServiceTypeList.get(ii);
            log.debug("Checking if job type " + jobServiceType.getClass().getName() + " has job resources");
            List jobResourceList = jobSubmissionService.getJobResources(jobServiceType, getUser());
            log.debug("Checking if job type " + jobServiceType.getClass().getName() + " has job resources");
            if (jobResourceList.size() > 0) {
                ListBoxItemBean jobServiceTypeItem = new ListBoxItemBean();
                String jobServiceTypeItemName = jobServiceType.getClassName();
                log.debug("Adding job type " + jobServiceTypeItemName);
                jobServiceTypeMap.put(jobServiceTypeItemName, jobServiceType);
                jobServiceTypeItem.setName(jobServiceTypeItemName);
                //String jobServiceTypeItemValue = JobTypeUtil.getJobTypeLabel(jobServiceTypeItemName);
                //jobServiceTypeItem.setValue(jobServiceTypeItemValue);
                String label = jobServiceType.getLabel(locale);
                jobServiceTypeItem.setValue(label);
                // Save reference to first item
                if (ii == 0) {
                    firstItem = jobServiceTypeItem;
                }
                // Set selected if equals our selection
                if (selectedJobServiceType.equals(jobServiceType)) {
                    jobServiceTypeItem.setSelected(true);
                }
                jobServiceTypeListBox.addBean(jobServiceTypeItem);
            }
        }
        if (jobServiceTypeListBox.getSelectedName() == null && firstItem != null) {
            firstItem.setSelected(true);
        }
    }

    public void doDisplayPage(Map parameters) throws PortletException {
        log.error("doDisplayPage");
        setNextState(defaultJspPage);
    }

    public boolean validatePage(Map parameters) throws PortletException {
        log.error("validatePage");
        boolean isValid = true;
        // Selected job component type
        String jobProfileItemName = jobProfileListBox.getSelectedName();
        if (jobProfileItemName == null) {
            messageBox.appendText("Please select the type of job you would like to submit");
            messageBox.setMessageType(TextBean.MSG_ERROR);
            isValid = false;
        } else {
            selectedJobProfile = (JobProfile)jobProfileMap.get(jobProfileItemName);
        }
        // Selected job service type
        String jobServiceTypeItemName = jobServiceTypeListBox.getSelectedName();
        if (jobServiceTypeItemName == null) {
            messageBox.appendText("Please select the job submission service you would like to use");
            messageBox.setMessageType(TextBean.MSG_ERROR);
            isValid = false;
        } else {
            selectedJobServiceType = (JobType)jobServiceTypeMap.get(jobServiceTypeItemName);
        }
        return isValid;
    }
}
