package edu.duke.cabig.c3pr.aspects.loadtime.infrared;

import net.sf.infrared.aspects.aj.AbstractApiAspect;
import org.apache.log4j.Logger;

public aspect BroadcasterLayerAspect extends AbstractApiAspect {

	private Logger log = Logger.getLogger(BroadcasterLayerAspect.class);
	
    // This pointcut matches execution of all public APIs in 
    // com.my.app.layer1 and subpackages 
    public pointcut apiExecution():
    	execution(public *  *.PersonResolverUtils.broadcast*(..) );

    // InfraRED will record executions of all public APIs in 
    // com.my.app.layer1 and subpackages as Layer One.
    public String getLayer() {
    log.error("$$$$$$$$$$  Inside the aspect BroadcasterLayerAspect ");
        return "Broadcaster Layer"; 
    }
}
