package com.yaocode.sts.components.flow.core.executor;

import com.yaocode.sts.components.flow.core.exception.FlowException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Stack;

/**
 * 命令执行器
 */
@Slf4j
public class CommandExecutor {

    private final ThreadLocal<Stack<CommandContext>> contextStack = ThreadLocal.withInitial(Stack::new);

    /**
     * 执行命令
     */
    public <T> T execute(Command<T> command) {
        CommandContext context = new CommandContext();
        contextStack.get().push(context);

        try {
            log.debug("执行命令: {}", command.getClass().getSimpleName());
            T result = command.execute(context);

            // 提交事务
            context.commit();

            log.debug("命令执行成功: {}", command.getClass().getSimpleName());
            return result;

        } catch (Exception e) {
            // 回滚事务
            context.rollback();
            log.error("命令执行失败: {}", command.getClass().getSimpleName(), e);
            throw new FlowException("命令执行失败: " + e.getMessage(), e);

        } finally {
            contextStack.get().pop();
            if (contextStack.get().isEmpty()) {
                contextStack.remove();
            }
        }
    }

    /**
     * 获取当前命令上下文
     */
    public CommandContext getCurrentContext() {
        Stack<CommandContext> stack = contextStack.get();
        if (stack.isEmpty()) {
            return null;
        }
        return stack.peek();
    }

    // ==================== 内部类 ====================

    /**
     * 命令接口
     */
    public interface Command<T> {
        T execute(CommandContext context);
    }

    /**
     * 命令上下文
     */
    @Getter
    public static class CommandContext {

        /**
         * -- GETTER --
         *  是否已提交
         */
        private boolean committed = false;
        /**
         * -- GETTER --
         *  是否已回滚
         */
        private boolean rolledBack = false;
        /**
         * -- SETTER --
         *  设置异常
         * -- GETTER --
         *  获取异常

         */
        @Setter
        private Throwable exception;

        /**
         * 提交事务
         */
        public void commit() {
            if (rolledBack) {
                throw new FlowException("事务已回滚，无法提交");
            }
            this.committed = true;
            log.debug("事务提交");
        }

        /**
         * 回滚事务
         */
        public void rollback() {
            this.rolledBack = true;
            log.debug("事务回滚");
        }

    }
}
