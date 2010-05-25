package edu.duke.cabig.c3pr.aspects.loadtime.infrared;

import net.sf.infrared.aspects.aj.AbstractApiAspect;
import org.apache.log4j.Logger;

public aspect InfrastructureLayerAspect extends AbstractApiAspect {

	private Logger log = Logger.getLogger(InfrastructureLayerAspect.class);

    // This pointcut matches execution of all public APIs in 
    // com.my.app.layer1 and subpackages 
    public pointcut apiExecution():
//    execution(public * edu.duke.cabig.c3pr.*..*.save(..) ) ||
//    execution(public * edu.duke.cabig.c3pr.*..*.merge(..) ) ;

        execution(* *.RemoteInvestigatorResolver.*(..))  || 
        execution(* *.RemoteResearchStaffResolver.*(..)) ||
        execution(* *.RemoteHealthcareSiteResolver.*(..)) ;
    
    // InfraRED will record executions of all public APIs in 
    // com.my.app.layer1 and subpackages as Layer One.
    public String getLayer() {
    	log.error("$$$$$$$$$$  Inside the aspect InfrastructureLayerAspect ");
        return "Infrastructure Layer"; 
    }
}