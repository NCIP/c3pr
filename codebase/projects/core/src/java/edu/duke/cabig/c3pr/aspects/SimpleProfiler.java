package edu.duke.cabig.c3pr.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.util.StopWatch;

@Aspect
public class SimpleProfiler {

   @Pointcut("execution(* edu.duke.cabig.c3pr.infrastructure.RemoteInvestigatorResolver.*(..))")
   private void investigatorResolverExecution(){
	   
   }
   
   @Around("investigatorResolverExecution()")
   public Object profile(ProceedingJoinPoint call) throws Throwable {
      StopWatch clock = new StopWatch(
            "Profiling for ");
      try {
         clock.start(call.toShortString());
         return call.proceed();
      } finally {
         clock.stop();
         System.out.println(clock.prettyPrint());
      }
   }
}