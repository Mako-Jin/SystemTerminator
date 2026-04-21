package com.yaocode.sts.common.web.aspect;

import com.yaocode.sts.common.basic.constants.SymbolConstants;
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

    private static final String SPLIT = SymbolConstants.NUMBER_SIGN;

    @Pointcut(value = "execution(* com.yaocode.sts..*.controller..*(..))")
    public void allControllerMethod() {}

    @Around(value = "allControllerMethod()", argNames = "point")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        boolean defaultEnabled = Objects.nonNull(attributes) && attributes.getBoolean("value");
        if (!restApiElapsedProperties.isEnabled() || !defaultEnabled) {
            return point.proceed();
        }

        long startTime = System.currentTimeMillis();
        StringBuilder invoke = new StringBuilder();
        invoke.append(SymbolConstants.LEFT_BRACKETS);
        invoke.append(point.getSignature().getDeclaringType().getSimpleName());
        invoke.append(SPLIT);
        invoke.append(point.getSignature().getName());
        invoke.append(SymbolConstants.RIGHT_BRACKETS);
        invoke.append(SymbolConstants.RIGHT_ARROW);
        invoke.append(this.request.getMethod().toUpperCase());
        invoke.append(SymbolConstants.DOUBLE_COLON);
        invoke.append(this.request.getServletPath());
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
