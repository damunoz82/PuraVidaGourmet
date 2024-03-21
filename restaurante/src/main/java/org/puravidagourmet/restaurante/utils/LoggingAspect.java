package org.puravidagourmet.restaurante.utils;

import java.util.Arrays;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

  private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

  @Pointcut(
      "within(org.puravidagourmet.api.controllers.*)"
          + " || within(org.puravidagourmet.api.services.*)"
          + " || within(org.puravidagourmet.api.db.repository.*)")
  public void loggingPointcut() {}

  @Before(value = "loggingPointcut()")
  public void logBefore(JoinPoint joinPoint) {
    Object[] args = joinPoint.getArgs();
    String className = joinPoint.getTarget().getClass().getName();
    String methodName = joinPoint.getSignature().getName();
    LOGGER.info("START: {}() - {}", className + "." + methodName, Arrays.toString(args));
  }

  @AfterReturning(value = "loggingPointcut()", returning = "result")
  public void logAfter(JoinPoint joinPoint, Object result) {
    String className = joinPoint.getTarget().getClass().getName();
    String methodName = joinPoint.getSignature().getName();
    LOGGER.info("END: {}() - {}", className + "." + methodName, result);
  }

  //    @Around("loggingPointcut()")
  public Object profileServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();

    Object retVal = joinPoint.proceed();

    stopWatch.stop();

    StringBuffer logMessage = new StringBuffer();
    logMessage.append(joinPoint.getTarget().getClass().getName());
    logMessage.append(".");
    logMessage.append(joinPoint.getSignature().getName());
    logMessage.append("(");
    // append args
    Object[] args = joinPoint.getArgs();
    for (int i = 0; i < args.length; i++) {
      logMessage.append(args[i]).append(",");
    }
    if (args.length > 0) {
      logMessage.deleteCharAt(logMessage.length() - 1);
    }

    logMessage.append(")");
    logMessage.append(" execution time: ");
    logMessage.append(stopWatch.getTime());
    logMessage.append(" ms");
    LOGGER.info(logMessage.toString());
    return retVal;
  }
}
