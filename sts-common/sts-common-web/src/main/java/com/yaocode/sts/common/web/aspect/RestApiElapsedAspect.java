package com.yaocode.sts.common.web.aspect;

import com.yaocode.sts.common.web.properties.RestApiElapsedProperties;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationAttributes;

import java.util.Objects;

/**
 * 日志切面
 * @author: Jin-LiangBo
 * @date: 2025年10月30日 20:34
 */
@Aspect
public class RestApiElapsedAspect {

    private static final Logger logger = LoggerFactory.getLogger(RestApiElapsedAspect.class);

    @Resource
    private HttpServletRequest request;

    private final RestApiElapsedProperties restApiElapsedProperties;
    private final AnnotationAttributes attributes;

    public RestApiElapsedAspect(RestApiElapsedProperties restApiElapsedProperties, AnnotationAttributes attributes) {
        this.restApiElapsedProperties = restApiElapsedProperties;
        this.attributes = attributes;
    }

    private static final String SPLIT = "#";

    @Pointcut(value = "execution(* com.yaocode.sts..*.controller..*(..))")
    public void allControllerMethod() {}

    @Around(value = "allControllerMethod()", argNames = "point")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        boolean defaultEnabled = Objects.nonNull(attributes) && attributes.getBoolean("value");
        if (!restApiElapsedProperties.isEnabled() || !defaultEnabled) {
            return point.proceed();
        }

        long startTime = System.currentTimeMillis();
        String invoke = "[" + point.getSignature().getDeclaringType().getSimpleName()
                + SPLIT + point.getSignature().getName() + "]" + "->"
                + this.request.getMethod().toUpperCase() + "::" + this.request.getServletPath();
        logger.info("{} invoke start...", invoke);
        try {
            Object result = point.proceed();
            long elapsedTime = System.currentTimeMillis() - startTime;
            logger.info("{} invoke finished, cost time = {}ms", invoke, elapsedTime);
            return result;
        } catch (Throwable throwable) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            logger.info("{} invoke error, cost time = {}ms, with error ==> {}", invoke, elapsedTime, throwable.getMessage());
            throw throwable;
        }
    }

}
