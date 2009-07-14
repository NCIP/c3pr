package edu.duke.cabig.c3pr.aspects.ajc;

import net.sf.infrared.aspects.aj.AbstractApiAspect;

public aspect LayerOneAspect extends AbstractApiAspect {
    // This pointcut matches execution of all public APIs in 
    // com.my.app.layer1 and subpackages 
    public pointcut apiExecution():
        //execution(public * edu.duke.cabig.c3pr.domain.*..*(..) );
    execution(public * edu.duke.cabig.c3pr.*..*.save(..) ) ||
    execution(public * edu.duke.cabig.c3pr.*..*.merge(..) ) ;
//        execution(* edu.duke.cabig.c3pr.infrastructure.RemoteInvestigatorResolver.*(..)) || 
//            execution(* edu.duke.cabig.c3pr.infrastructure.RemoteResearchStaffResolver.*(..)) ||
//            execution(* edu.duke.cabig.c3pr.infrastructure.RemoteHealthcareSiteResolver.*(..)) ||
//            execution(* edu.duke.cabig.c3pr.dao.InvestigatorDao.updateDatabaseWithRemoteContent*(..)) ||
//            execution(* edu.duke.cabig.c3pr.dao.ResearchStaffDao.updateDatabaseWithRemoteContent*(..)) ||
//            execution(* edu.duke.cabig.c3pr.dao.HealthcareSiteDao.updateDatabaseWithRemoteContent*(..)) ||
//         execution(* edu.duke.cabig.c3pr.utils.PersonResolverUtils.*(..));
    
    // InfraRED will record executions of all public APIs in 
    // com.my.app.layer1 and subpackages as Layer One.
    public String getLayer() {
        return "Layer One"; 
    }
}
