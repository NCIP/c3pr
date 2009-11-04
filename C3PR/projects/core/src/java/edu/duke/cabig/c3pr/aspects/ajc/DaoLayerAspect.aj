package edu.duke.cabig.c3pr.aspects.ajc;

import net.sf.infrared.aspects.aj.AbstractApiAspect;

public aspect DaoLayerAspect extends AbstractApiAspect {
    // This pointcut matches execution of all public APIs in 
    // com.my.app.layer1 and subpackages 
    public pointcut apiExecution():
	    execution(public * edu.duke.cabig.c3pr.dao.ResearchStaffDao.*(..) ) ||
	    execution(public * edu.duke.cabig.c3pr.dao.InvestigatorDao.*(..) )  ||
	    execution(public * edu.duke.cabig.c3pr.dao.HealthcareSiteDao.*(..) ) ;
    
    // InfraRED will record executions of all public APIs in 
    // com.my.app.layer1 and subpackages as Layer One.
    public String getLayer() {
        return "DAO Layer"; 
    }
}
