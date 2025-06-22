package com.baerchen.jutils.runtime.control;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
/**
 * Aspect for logging method execution details when annotated with {@link Loggable}.
 * <p>
 * This aspect supports conditional logging of method parameters and return values.
 * It also extracts a `step` argument from method parameters, if present, and
 * places it into the MDC (Mapped Diagnostic Context) to enrich log context.
 * </p>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * @Loggable(logParams = true, logResult = false, description = "Logs a specific process")
 * public ApiResponse executePostRequest(String step, String url, ...) {
 *     ...
 * }
 * }
 * </pre>
 *
 * <p>Log output will include:</p>
 * <ul>
 *     <li>📌 Class and method name</li>
 *     <li>🔍 Parameters (if enabled)</li>
 *     <li>✅ Result (if enabled)</li>
 *     <li>❌ Exceptions, if any</li>
 *     <li>⏱ Execution time</li>
 * </ul>
 *
 * @see Loggable
 */
@Aspect
@Component
@Slf4j
public class LoggingAspect {

    /**
     * Around advice that intercepts method execution and applies conditional logging
     * based on the {@link Loggable} annotation configuration.
     *
     * <p>
     * If a parameter named <code>step</code> of type {@link String} is found among the
     * method parameters, its value will be placed into the MDC for enhanced traceability.
     * </p>
     *
     * @param joinPoint the join point providing reflective access to the intercepted method
     * @param loggable  the {@link Loggable} annotation instance on the method
     * @return the result of the method execution
     * @throws Throwable if the underlying method throws an exception
     */
    @Around("@annotation(loggable)")
    public Object logExecution(ProceedingJoinPoint joinPoint, Loggable loggable) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();
        Object[] args = joinPoint.getArgs();
        String[] paramNames = signature.getParameterNames();

        // 🎯 Try to extract 'step' from arguments and put into MDC
        String stepValue = null;
        for (int i = 0; i < paramNames.length; i++) {
            if ("step".equals(paramNames[i]) && args[i] instanceof String) {
                stepValue = (String) args[i];
                MDC.put("step", stepValue);
                break;
            }
        }

        if (loggable.logParams()) {
            log.info("🔍 [{}#{}] called with params: {}", className, methodName, args);
        }

        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long timeTaken = System.currentTimeMillis() - start;

            if (loggable.logResult()) {
                log.info("✅ [{}#{}] returned: {} (in {}ms)", className, methodName, result, timeTaken);
            }

            return result;
        } catch (Throwable ex) {
            long timeTaken = System.currentTimeMillis() - start;
            log.error("❌ [{}#{}] threw exception after {}ms: {}", className, methodName, timeTaken, ex.getMessage(), ex);
            throw ex;
        } finally {
            // Clean up MDC context
            if (stepValue != null) {
                MDC.remove("step");
            }
        }
    }
}
