package com.yaocode.sts.auth.domain.valueobjects.primitives;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 时间范围（值对象）
 * 用于有效期、生效期等场景
 */
@Value
public class DateRange {

    LocalDateTime from;
    LocalDateTime to;

    private DateRange(LocalDateTime from, LocalDateTime to) {
        if (from != null && to != null && from.isAfter(to)) {
            throw new IllegalArgumentException("开始时间不能晚于结束时间");
        }
        this.from = from;
        this.to = to;
    }

    public static DateRange of(LocalDateTime from, LocalDateTime to) {
        return new DateRange(from, to);
    }

    public static DateRange forever() {
        return new DateRange(null, null);
    }

    public static DateRange fromNow(LocalDateTime to) {
        return new DateRange(LocalDateTime.now(), to);
    }

    public static DateRange until(LocalDateTime to) {
        return new DateRange(null, to);
    }

    public static DateRange from(LocalDateTime from) {
        return new DateRange(from, null);
    }

    /**
     * 判断当前时间是否在范围内
     */
    public boolean isActive() {
        LocalDateTime now = LocalDateTime.now();
        return isActiveAt(now);
    }

    /**
     * 判断指定时间是否在范围内
     */
    public boolean isActiveAt(LocalDateTime time) {
        if (from != null && time.isBefore(from)) return false;
        return to == null || !time.isAfter(to);
    }

    /**
     * 判断是否为永久有效
     */
    public boolean isForever() {
        return from == null && to == null;
    }

    /**
     * 判断是否有开始时间
     */
    public boolean hasFrom() {
        return from != null;
    }

    /**
     * 判断是否有结束时间
     */
    public boolean hasTo() {
        return to != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DateRange dateRange = (DateRange) o;
        return Objects.equals(from, dateRange.from) &&
                Objects.equals(to, dateRange.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    @Override
    public String toString() {
        if (isForever()) return "永久有效";
        if (from == null) return "至 " + to;
        if (to == null) return "从 " + from + " 起";
        return from + " 至 " + to;
    }
}
