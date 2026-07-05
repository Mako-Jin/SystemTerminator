package com.yaocode.sts.components.flow.core.job;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 定时器表达式解析器
 * 支持 ISO 8601 格式: PT30M, P1D, R/PT30M 等
 */
@Slf4
public class TimerExpressionParser {

    private static final Pattern DURATION_PATTERN = Pattern.compile(
            "P(?:(\\d+)D)?(?:T(?:(\\d+)H)?(?:(\\d+)M)?(?:(\\d+)S)?)?"
    );

    private static final Pattern REPEAT_PATTERN = Pattern.compile(
            "R(\\d*)/(.*)"
    );

    /**
     * 解析定时器表达式，计算下次执行时间
     */
    public static LocalDateTime parse(String expression, LocalDateTime baseTime) {
        if (expression == null || expression.trim().isEmpty()) {
            return baseTime;
        }

        expression = expression.trim();

        // 检查是否是重复表达式
        Matcher repeatMatcher = REPEAT_PATTERN.matcher(expression);
        if (repeatMatcher.matches()) {
            // R/PT30M 或 R5/PT30M
            String durationPart = repeatMatcher.group(2);
            Duration duration = parseDuration(durationPart);
            return baseTime.plus(duration);
        }

        // 普通持续时间
        Duration duration = parseDuration(expression);
        return baseTime.plus(duration);
    }

    /**
     * 解析持续时间
     */
    public static Duration parseDuration(String expression) {
        Matcher matcher = DURATION_PATTERN.matcher(expression);
        if (!matcher.matches()) {
            // 尝试简单格式: 30m, 1h, 1d
            return parseSimpleDuration(expression);
        }

        long days = matcher.group(1) != null ? Long.parseLong(matcher.group(1)) : 0;
        long hours = matcher.group(2) != null ? Long.parseLong(matcher.group(2)) : 0;
        long minutes = matcher.group(3) != null ? Long.parseLong(matcher.group(3)) : 0;
        long seconds = matcher.group(4) != null ? Long.parseLong(matcher.group(4)) : 0;

        return Duration.ofDays(days)
                .plus(Duration.ofHours(hours))
                .plus(Duration.ofMinutes(minutes))
                .plus(Duration.ofSeconds(seconds));
    }

    /**
     * 解析简单格式: 30m, 1h, 1d, 2w
     */
    private static Duration parseSimpleDuration(String expression) {
        String trimmed = expression.trim();
        char unit = trimmed.charAt(trimmed.length() - 1);
        long value = Long.parseLong(trimmed.substring(0, trimmed.length() - 1));

        switch (unit) {
            case 's':
                return Duration.ofSeconds(value);
            case 'm':
                return Duration.ofMinutes(value);
            case 'h':
                return Duration.ofHours(value);
            case 'd':
                return Duration.ofDays(value);
            case 'w':
                return Duration.ofDays(value * 7);
            default:
                // 纯数字，按秒处理
                try {
                    return Duration.ofSeconds(Long.parseLong(trimmed));
                } catch (NumberFormatException e) {
                    log.warn("无法解析定时器表达式: {}", expression);
                    return Duration.ZERO;
                }
        }
    }

    /**
     * 获取重复次数
     */
    public static int getRepeatCount(String expression) {
        if (expression == null || !expression.startsWith("R")) {
            return -1; // 无限重复
        }

        Matcher matcher = REPEAT_PATTERN.matcher(expression);
        if (matcher.matches()) {
            String countStr = matcher.group(1);
            if (countStr == null || countStr.isEmpty()) {
                return -1; // 无限重复
            }
            try {
                return Integer.parseInt(countStr);
            } catch (NumberFormatException e) {
                return -1;
            }
        }
        return -1;
    }
}
