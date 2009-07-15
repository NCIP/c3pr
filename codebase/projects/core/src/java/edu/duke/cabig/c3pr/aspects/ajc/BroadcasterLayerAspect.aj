package edu.duke.cabig.c3pr.aspects.ajc;

import net.sf.infrared.aspects.aj.AbstractApiAspect;

public aspect BroadcasterLayerAspect extends AbstractApiAspect {
    // This pointcut matches execution of all public APIs in 
    // com.my.app.layer1 and subpackages 
    public pointcut apiExecution():
    	execution(public *  edu.duke.cabig.c3pr.utils.PersonResolverUtils.broadcast*(..) );

    // InfraRED will record executions of all public APIs in 
    // com.my.app.layer1 and subpackages as Layer One.
    public String getLayer() {
        return "Broadcaster Layer"; 
    }
}
