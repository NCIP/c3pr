package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import edu.duke.cabig.c3pr.constants.APIName;

/**
 * 
 * @author Ram Chilukuri
 * 
 */
@Entity
@DiscriminatorValue(value = "SCC")
public class StudyCoordinatingCenter extends StudyOrganization {

    public StudyCoordinatingCenter() {
        super();
        this.setHostedMode(true);
    }
    
    @Transient
    public List<APIName> getPossibleEndpoints(){
        List<APIName> apiList=new ArrayList<APIName>();
        if(!isSuccessfullSend(APIName.ACTIVATE_STUDY_SITE)){
            apiList.add(APIName.ACTIVATE_STUDY_SITE);
        }
        return apiList;
    }
}
