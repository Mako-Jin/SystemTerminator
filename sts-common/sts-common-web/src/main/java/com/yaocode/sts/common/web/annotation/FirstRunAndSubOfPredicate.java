package com.yaocode.sts.common.web.annotation;

import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotationPredicates;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月18日 11:50
 */
public class FirstRunAndSubOfPredicate <A extends Annotation> implements Predicate<MergedAnnotation<A>> {

    private final Function<? super MergedAnnotation<A>, ?> valueExtractor;

    public static <A extends Annotation> Predicate<MergedAnnotation<A>> firstRunAndSubOf(
            Function<? super MergedAnnotation<A>, ?> valueExtractor) {

        return new FirstRunAndSubOfPredicate<>(valueExtractor);
    }

    FirstRunAndSubOfPredicate(Function<? super MergedAnnotation<A>, ?> valueExtractor) {
        Assert.notNull(valueExtractor, "Value extractor must not be null");
        this.valueExtractor = valueExtractor;
    }

    @Override
    public boolean test(@Nullable MergedAnnotation<A> aMergedAnnotation) {
        if (aMergedAnnotation.getType() == SubRequestMapping.class) {
            return true;
        }
        return MergedAnnotationPredicates.firstRunOf(valueExtractor).test(aMergedAnnotation);
    }

}
