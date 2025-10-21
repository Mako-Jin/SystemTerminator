package com.yaocode.sts.auth.application.listener;

import com.yaocode.sts.auth.domain.events.UserCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * 创建用户事件监听器
 * @author: Jin-LiangBo
 * @date: 2025年10月15日 20:57
 */
@Slf4j
@Component
public class UserCreateEventListener {

    /**
     * 用户创建完成的后续操作
     * @param event 用户创建完成事件
     */
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleUserCreateEvent(UserCreatedEvent event) {
        try {
            log.info("开始处理用户创建事件, 用户ID: {}", event.getUserId().getValue());

            // 1. 记录审计日志
            // logAuditEvent(event);

            // 2. 发送欢迎邮件（可异步重试）
            sendWelcomeEmail(event);

            // 3. 同步到外部系统
            // syncToExternalSystems(event);

            // 4. 更新统计信息
            // updateUserStatistics(event);

            // 5. 初始化缓存
            // initializeUserCache(event);

            // 6. 通知相关方
            // notifyRelatedParties(event);

            log.info("用户创建事件处理完成, 用户ID: {}", event.getUserId().getValue());

        } catch (Exception e) {
            // 事件监听器中的异常不应该影响主流程
            log.error("处理用户创建事件失败, 用户ID: {}", event.getUserId().getValue(), e);

            // 可以发送告警、记录失败日志等
            handleEventListenerFailure(event, e);
        }
    }

    /**
     * 发送欢迎邮件
     */
    // @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    private void sendWelcomeEmail(UserCreatedEvent event) {

    }

    /**
     * 事件处理失败的处理
     */
    private void handleEventListenerFailure(UserCreatedEvent event, Exception error) {

    }

}
