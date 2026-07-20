package com.yaocode.sts.components.flow.core.engine.parser;

import com.yaocode.sts.components.flow.core.engine.parser.enums.ErrorSeverityEnums;
import com.yaocode.sts.components.flow.core.engine.parser.error.ParseError;
import com.yaocode.sts.components.flow.core.engine.parser.error.ParseWarning;
import lombok.Data;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * 验证结果
 *
 * <p>包含验证过程中产生的错误、警告和验证状态信息
 *
 * @author Process Engine Team
 */
@Data
public class ValidationResult {

    /**
     * 是否验证通过
     */
    private boolean valid;

    /**
     * 验证器名称
     */
    private String validatorName;

    /**
     * 验证错误列表
     */
    private List<ParseError> errors;

    /**
     * 验证警告列表
     */
    private List<ParseWarning> warnings;

    /**
     * 验证耗时（毫秒）
     * -- SETTER --
     *  设置验证耗时

     */
    private long validateTime;

    /**
     * 验证的消息
     */
    private String message;

    /**
     * 构造方法
     *
     * @param validatorName 验证器名称
     */
    public ValidationResult(String validatorName) {
        this.validatorName = validatorName;
        this.valid = true;
        this.errors = new ArrayList<>();
        this.warnings = new ArrayList<>();
        this.validateTime = 0;
    }

    /**
     * 构造方法（带初始状态）
     *
     * @param validatorName 验证器名称
     * @param valid         是否有效
     */
    public ValidationResult(String validatorName, boolean valid) {
        this.validatorName = validatorName;
        this.valid = valid;
        this.errors = new ArrayList<>();
        this.warnings = new ArrayList<>();
        this.validateTime = 0;
    }

    // ==================== 错误添加方法 ====================

    /**
     * 添加错误（仅消息）
     */
    public void addError(String message) {
        this.errors.add(ParseError.builder()
                .message(message)
                .severity(ErrorSeverityEnums.ERROR)
                .build());
        this.valid = false;
    }

    /**
     * 添加错误（消息 + 元素）
     */
    public void addError(String message, Element element) {
        this.errors.add(ParseError.builder()
                .message(message)
                .severity(ErrorSeverityEnums.ERROR)
                .element(element)
                .build());
        this.valid = false;
    }

    /**
     * 添加错误（消息 + 元素 + 错误码）
     */
    public void addError(String message, Element element, String errorCode) {
        this.errors.add(ParseError.builder()
                .message(message)
                .severity(ErrorSeverityEnums.ERROR)
                .element(element)
                .errorCode(errorCode)
                .build());
        this.valid = false;
    }

    /**
     * 添加错误（完整 ParseError）
     */
    public void addError(ParseError error) {
        if (error != null) {
            this.errors.add(error);
            if (error.getSeverity() == ErrorSeverityEnums.FATAL) {
                this.valid = false;
            }
        }
    }

    /**
     * 添加错误列表
     */
    public void addErrors(List<ParseError> errors) {
        if (errors != null) {
            this.errors.addAll(errors);
            if (!errors.isEmpty()) {
                this.valid = false;
            }
        }
    }

    // ==================== 警告添加方法 ====================

    /**
     * 添加警告
     */
    public void addWarning(String message) {
        this.warnings.add(ParseWarning.builder()
                .message(message)
                .build());
    }

    /**
     * 添加警告（消息 + 元素）
     */
    public void addWarning(String message, Element element) {
        this.warnings.add(ParseWarning.builder()
                .message(message)
                .element(element)
                .build());
    }

    /**
     * 添加警告（消息 + 元素 + 警告码）
     */
    public void addWarning(String message, Element element, String warningCode) {
        this.warnings.add(ParseWarning.builder()
                .message(message)
                .element(element)
                .warningCode(warningCode)
                .build());
    }

    /**
     * 添加警告（完整 ParseWarning）
     */
    public void addWarning(ParseWarning warning) {
        if (warning != null) {
            this.warnings.add(warning);
        }
    }

    /**
     * 添加警告列表
     */
    public void addWarnings(List<ParseWarning> warnings) {
        if (warnings != null) {
            this.warnings.addAll(warnings);
        }
    }

    // ==================== 合并方法 ====================

    /**
     * 合并另一个验证结果
     *
     * @param other 另一个验证结果
     */
    public void merge(ValidationResult other) {
        if (other == null) {
            return;
        }

        if (!other.isValid()) {
            this.valid = false;
        }

        this.errors.addAll(other.getErrors());
        this.warnings.addAll(other.getWarnings());
        this.validateTime += other.getValidateTime();

        if (other.getMessage() != null && this.message == null) {
            this.message = other.getMessage();
        }
    }

    /**
     * 合并多个验证结果
     *
     * @param results 验证结果列表
     * @return 合并后的验证结果
     */
    public static ValidationResult mergeAll(List<ValidationResult> results) {
        if (results == null || results.isEmpty()) {
            return new ValidationResult("merged");
        }

        ValidationResult merged = new ValidationResult("merged", true);
        for (ValidationResult result : results) {
            merged.merge(result);
        }
        return merged;
    }

    // ==================== 判断方法 ====================

    /**
     * 是否有错误
     */
    public boolean hasErrors() {
        return errors != null && !errors.isEmpty();
    }

    /**
     * 是否有致命错误
     */
    public boolean hasFatalError() {
        if (errors == null) {
            return false;
        }
        return errors.stream().anyMatch(e -> e.getSeverity() == ErrorSeverityEnums.FATAL);
    }

    /**
     * 是否有警告
     */
    public boolean hasWarnings() {
        return warnings != null && !warnings.isEmpty();
    }

    /**
     * 获取错误消息列表
     */
    public List<String> getErrorMessages() {
        if (errors == null) {
            return List.of();
        }
        return errors.stream()
                .map(e -> String.format("[%s] %s", e.getSeverity(), e.getMessage()))
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * 获取警告消息列表
     */
    public List<String> getWarningMessages() {
        if (warnings == null) {
            return List.of();
        }
        return warnings.stream()
                .map(ParseWarning::getMessage)
                .collect(java.util.stream.Collectors.toList());
    }

    // ==================== 构建方法 ====================

    /**
     * 创建成功的验证结果
     */
    public static ValidationResult success(String validatorName) {
        return new ValidationResult(validatorName, true);
    }

    /**
     * 创建失败的验证结果
     */
    public static ValidationResult failure(String validatorName) {
        return new ValidationResult(validatorName, false);
    }

    /**
     * 创建失败的验证结果（带错误消息）
     */
    public static ValidationResult failure(String validatorName, String message) {
        ValidationResult result = new ValidationResult(validatorName, false);
        result.setMessage(message);
        return result;
    }

    /**
     * 创建失败的验证结果（带错误列表）
     */
    public static ValidationResult failure(String validatorName, List<ParseError> errors) {
        ValidationResult result = new ValidationResult(validatorName, false);
        if (errors != null) {
            result.errors.addAll(errors);
        }
        return result;
    }

    // ==================== 重置方法 ====================

    /**
     * 重置验证结果
     */
    public void reset() {
        this.valid = true;
        this.errors.clear();
        this.warnings.clear();
        this.message = null;
        this.validateTime = 0;
    }

    // ==================== 输出方法 ====================

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ValidationResult{")
                .append("validatorName='").append(validatorName).append('\'')
                .append(", valid=").append(valid)
                .append(", errors=").append(errors.size())
                .append(", warnings=").append(warnings.size())
                .append(", validateTime=").append(validateTime).append("ms");

        if (message != null) {
            sb.append(", message='").append(message).append('\'');
        }

        sb.append('}');
        return sb.toString();
    }

    /**
     * 打印验证报告
     */
    public void printReport() {
        System.out.println("=== Validation Report ===");
        System.out.println("Validator: " + validatorName);
        System.out.println("Valid: " + valid);
        System.out.println("Errors: " + (errors != null ? errors.size() : 0));
        System.out.println("Warnings: " + (warnings != null ? warnings.size() : 0));
        System.out.println("Validate Time: " + validateTime + "ms");

        if (message != null) {
            System.out.println("Message: " + message);
        }

        if (errors != null && !errors.isEmpty()) {
            System.out.println("\n--- Errors ---");
            errors.forEach(e -> System.out.printf("  [%s] %s%n", e.getSeverity(), e.getMessage()));
        }

        if (warnings != null && !warnings.isEmpty()) {
            System.out.println("\n--- Warnings ---");
            warnings.forEach(w -> System.out.println("  " + w.getMessage()));
        }
    }
}
