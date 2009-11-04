package edu.duke.cabig.c3pr.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.util.StopWatch;

//@Aspect
public class SimpleProfiler {

    private Logger log = Logger.getLogger(SimpleProfiler.class.getName());
    
   @Pointcut("execution(* edu.duke.cabig.c3pr.infrastructure.RemoteInvestigatorResolver.*(..)) ||" +
		   	 "execution(* edu.duke.cabig.c3pr.infrastructure.RemoteResearchStaffResolver.*(..)) ||" +
		   	 "execution(* edu.duke.cabig.c3pr.infrastructure.RemoteHealthcareSiteResolver.*(..)) ||" +
		   	 "execution(* edu.duke.cabig.c3pr.dao.InvestigatorDao.updateDatabaseWithRemoteContent*(..)) ||" +
		   	 "execution(* edu.duke.cabig.c3pr.dao.ResearchStaffDao.updateDatabaseWithRemoteContent*(..)) ||" +
		   	 "execution(* edu.duke.cabig.c3pr.dao.HealthcareSiteDao.updateDatabaseWithRemoteContent*(..)) ||" +
		     "execution(* edu.duke.cabig.c3pr.utils.PersonResolverUtils.*(..))")
   private void investigatorResolverExecution(){
	   
   }
   
   //@Around("investigatorResolverExecution()")
   public Object profile(ProceedingJoinPoint call) throws Throwable {
      StopWatch clock = new StopWatch(
            "Profiling for " + call.getSignature().getDeclaringTypeName() + "." + call.getSignature().getName());
      try {
         clock.start(call.toShortString());
         return call.proceed();
      } finally {
         clock.stop();
         log.debug(clock.shortSummary() + " (seconds) = " + clock.getTotalTimeSeconds());
      }
   }



}