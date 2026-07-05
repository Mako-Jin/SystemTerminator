package com.yaocode.sts.auth.domain.port;


import com.yaocode.sts.auth.domain.valueobjects.composites.JwtPayload;

import java.util.Map;

/**
 * JWT 端口（出站端口）
 * 领域层需要 JWT 功能，但不知道具体实现
 */
public interface JwtTokenPort {

    /**
     * 生成 JWT
     * @param payload 载荷数据
     * @param ttl 有效期
     * @return JWT 字符串
     */
    String generate(Map<String, Object> payload);

    /**
     * 验证 JWT
     * @param jwt JWT 字符串
     * @return 是否有效（签名正确且未过期）
     */
    boolean verify(String jwt);

    /**
     * 解析 JWT（不验证签名）
     * @param jwt JWT 字符串
     * @return 载荷，解析失败返回 null
     */
    JwtPayload parse(String jwt);

    /**
     * 获取算法类型
     */
    String getAlgorithm();

}
