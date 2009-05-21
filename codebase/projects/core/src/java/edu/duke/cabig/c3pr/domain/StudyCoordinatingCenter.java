package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import edu.duke.cabig.c3pr.constants.APIName;

/**
 * The Class StudyCoordinatingCenter.
 * 
 * @author Ram Chilukuri
 */
@Entity
@DiscriminatorValue(value = "SCC")
public class StudyCoordinatingCenter extends StudyOrganization {

    /**
     * Instantiates a new study coordinating center.
     */
    public StudyCoordinatingCenter() {
        super();
        this.setHostedMode(true);
    }
    
    /**
     * Gets the possible endpoints.
     * 
     * @return the possible endpoints
     */
    @Transient
    public List<APIName> getPossibleEndpoints(){
        List<APIName> apiList=new ArrayList<APIName>();
        if(!isSuccessfullSend(APIName.ACTIVATE_STUDY_SITE)){
            apiList.add(APIName.ACTIVATE_STUDY_SITE);
        }
        return apiList;
    }
}
