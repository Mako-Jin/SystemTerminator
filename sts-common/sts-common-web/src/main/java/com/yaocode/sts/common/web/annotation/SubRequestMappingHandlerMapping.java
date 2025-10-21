package com.yaocode.sts.common.web.annotation;

import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotationPredicates;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.annotation.RepeatableContainers;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringValueResolver;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;


/**
 * 子路径映射处理器
 * @author: Jin-LiangBo
 * @date: 2025年10月18日 10:51
 */
public class SubRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    private Map<String, Predicate<Class<?>>> pathPrefixes = Collections.emptyMap();

    @Nullable
    private StringValueResolver embeddedValueResolver;

    private RequestMappingInfo.BuilderConfiguration config = new RequestMappingInfo.BuilderConfiguration();

    private static List<AnnotationDescriptor> getAnnotationDescriptors(AnnotatedElement element) {
        return MergedAnnotations.from(element, MergedAnnotations.SearchStrategy.TYPE_HIERARCHY, RepeatableContainers.none())
                .stream()
                .filter(MergedAnnotationPredicates.typeIn(RequestMapping.class, HttpExchange.class, SubRequestMapping.class))
                .filter(FirstRunAndSubOfPredicate.firstRunAndSubOf(MergedAnnotation::getAggregateIndex))
                .map(AnnotationDescriptor::new)
                .distinct()
                .toList();
    }

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo info = createRequestMappingInfo(method);
        if (info != null) {
            RequestMappingInfo typeInfo = createRequestMappingInfo(handlerType);
            if (typeInfo != null) {
                info = typeInfo.combine(info);
            }
            if (info.isEmptyMapping()) {
                info = info.mutate().paths("", "/").options(this.config).build();
            }
            String prefix = getPathPrefix(handlerType);
            if (prefix != null) {
                info = RequestMappingInfo.paths(prefix).options(this.config).build().combine(info);
            }
        }
        return info;
    }

    @Nullable
    String getPathPrefix(Class<?> handlerType) {
        for (Map.Entry<String, Predicate<Class<?>>> entry : this.pathPrefixes.entrySet()) {
            if (entry.getValue().test(handlerType)) {
                String prefix = entry.getKey();
                if (this.embeddedValueResolver != null) {
                    prefix = this.embeddedValueResolver.resolveStringValue(prefix);
                }
                return prefix;
            }
        }
        return null;
    }

    protected RequestMappingInfo createRequestMappingInfo(
            SubRequestMapping requestMapping, @Nullable RequestCondition<?> customCondition) {

        RequestMappingInfo.Builder builder = RequestMappingInfo
                .paths(resolveEmbeddedValuesInPatterns(requestMapping.path()))
                .methods(requestMapping.method())
                .params(requestMapping.params())
                .headers(requestMapping.headers())
                .consumes(requestMapping.consumes())
                .produces(requestMapping.produces())
                .mappingName(requestMapping.name());

        if (customCondition != null) {
            builder.customCondition(customCondition);
        }

        return builder.options(this.config).build();
    }

    @Nullable
    private RequestMappingInfo createRequestMappingInfo(AnnotatedElement element) {
        RequestMappingInfo requestMappingInfo = null;
        RequestCondition<?> customCondition = (element instanceof Class<?> clazz ?
                getCustomTypeCondition(clazz) : getCustomMethodCondition((Method) element));

        List<AnnotationDescriptor> descriptors = getAnnotationDescriptors(element);

        List<AnnotationDescriptor> requestMappings = descriptors.stream()
                .filter(desc -> desc.annotation instanceof RequestMapping)
                .toList();
        List<AnnotationDescriptor> subRequestMappings = descriptors.stream()
                .filter(desc -> desc.annotation instanceof SubRequestMapping)
                .toList();
        if (!requestMappings.isEmpty()) {
            if (requestMappings.size() > 1 && logger.isWarnEnabled()) {
                logger.warn("Multiple @RequestMapping annotations found on %s, but only the first will be used: %s"
                        .formatted(element, requestMappings));
            }
            requestMappingInfo = createRequestMappingInfo((RequestMapping) requestMappings.get(0).annotation, customCondition);
        }
        if (!subRequestMappings.isEmpty()) {
            SubRequestMapping subRequestMapping = (SubRequestMapping) subRequestMappings.get(0).annotation;
            RequestMappingInfo subRequestMappingInfo = createRequestMappingInfo(subRequestMapping, customCondition);
            requestMappingInfo = requestMappingInfo.combine(subRequestMappingInfo);
        }

        List<AnnotationDescriptor> httpExchanges = descriptors.stream()
                .filter(desc -> desc.annotation instanceof HttpExchange).toList();
        if (!httpExchanges.isEmpty()) {
            Assert.state(requestMappingInfo == null,
                    () -> "%s is annotated with @RequestMapping and @HttpExchange annotations, but only one is allowed: %s"
                            .formatted(element, Stream.of(requestMappings, httpExchanges).flatMap(List::stream).toList()));
            Assert.state(httpExchanges.size() == 1,
                    () -> "Multiple @HttpExchange annotations found on %s, but only one is allowed: %s"
                            .formatted(element, httpExchanges));
            requestMappingInfo = createRequestMappingInfo((HttpExchange) httpExchanges.get(0).annotation, customCondition);
        }

        return requestMappingInfo;
    }

    private static class AnnotationDescriptor {

        private final Annotation annotation;
        private final MergedAnnotation<?> root;

        AnnotationDescriptor(MergedAnnotation<Annotation> mergedAnnotation) {
            this.annotation = mergedAnnotation.synthesize();
            this.root = mergedAnnotation.getRoot();
        }

        @Override
        public boolean equals(Object obj) {
            return (obj instanceof AnnotationDescriptor that && this.annotation.equals(that.annotation));
        }

        @Override
        public int hashCode() {
            return this.annotation.hashCode();
        }

        @Override
        public String toString() {
            return this.root.synthesize().toString();
        }

    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
    }

}
