/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.aspects.compiletime;

import org.apache.log4j.Logger;


//@Aspect
public class RefreshInvestigatorAspect {

    private Logger log = Logger.getLogger(RefreshInvestigatorAspect.class);
    
	
//    @AfterReturning("execution(* edu.duke.cabig.c3pr.domain.RemoteInvestigator.getExternalId(..)) && cflow(call(* edu.duke.cabig.c3pr.web.*+.*(..)))")
    public void refreshRemoteInvestigator() {

          log.error("$$$$$$$$$$  Inside the aspect RefreshInvestigatorAspect for assignedId ");
          System.out.println("$$$$$$$$$$  Inside the aspect RefreshInvestigatorAspect for assignedId ");
    }


}
