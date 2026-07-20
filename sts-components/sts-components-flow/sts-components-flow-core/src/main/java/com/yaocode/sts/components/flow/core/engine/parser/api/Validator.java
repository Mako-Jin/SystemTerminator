package com.yaocode.sts.components.flow.core.engine.parser.api;

import com.yaocode.sts.components.flow.core.engine.parser.ParseContext;
import com.yaocode.sts.components.flow.core.engine.parser.ValidationResult;

/**
 * 验证器接口
 *
 * <p>对解析结果进行校验，确保流程定义的完整性和正确性。
 *
 * <p>验证器按优先级顺序执行，数值越小优先级越高。
 *
 * @author Process Engine Team
 */
public interface Validator {

    /**
     * 执行验证
     *
     * @param context 解析上下文（包含已解析的元素和中间结果）
     * @return 验证结果（包含错误和警告）
     */
    ValidationResult validate(ParseContext context);

    /**
     * 获取验证器名称
     *
     * @return 验证器名称
     */
    String getName();

    /**
     * 获取验证优先级
     *
     * <p>数值越小优先级越高，建议值：
     * <ul>
     *   <li>10-30: 基础验证（如 ID 唯一性）</li>
     *   <li>40-60: 结构验证（如必须有开始事件）</li>
     *   <li>70-90: 业务验证（如节点配置完整性）</li>
     *   <li>100+: 扩展验证</li>
     * </ul>
     *
     * @return 优先级（默认 100）
     */
    default int getPriority() {
        return 100;
    }

    /**
     * 是否在遇到致命错误时继续执行
     *
     * @return true 继续执行，false 停止执行
     */
    default boolean continueOnFatalError() {
        return false;
    }
}
