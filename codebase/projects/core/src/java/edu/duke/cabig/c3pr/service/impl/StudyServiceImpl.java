package edu.duke.cabig.c3pr.service.impl;

import edu.duke.cabig.c3pr.domain.ServiceName;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.service.StudyService;
import edu.duke.cabig.c3pr.tools.Configuration;

public class StudyServiceImpl extends WorkflowServiceImpl implements StudyService{
    
    private Configuration configuration;
    
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public boolean canMultisiteBroadcast(StudyOrganization studyOrganization){
        return !studyOrganization.getHostedMode() && this.configuration.get(Configuration.MULTISITE_ENABLE).equalsIgnoreCase("true");
    }

    public String getLocalNCIInstituteCode(){
        return this.configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE);
    }

    @Override
    public ServiceName getMultisiteServiceName() {
        return ServiceName.STUDY;
    }
    
    public boolean isStudyOrganizationLocal(String nciInstituteCode){
        return getLocalNCIInstituteCode().equalsIgnoreCase(nciInstituteCode);
    }
}
