package com.yaocode.sts.auth.infrastructure.port;

import com.yaocode.sts.auth.domain.enums.TokenTypeEnums;
import com.yaocode.sts.auth.domain.port.JwtTokenPort;
import com.yaocode.sts.auth.domain.valueobjects.composites.JwtPayload;
import com.yaocode.sts.common.crypto.enums.AlgorithmTypeEnums;
import lombok.Getter;

import java.time.Duration;
import java.util.Map;

@Getter
public class Rsa384JwtTokenAdapter implements JwtTokenPort {

    private final AlgorithmTypeEnums algorithm;

    private final Long ttlSeconds;

    private final String issuer;

    private final String audience;

    private final String privateKey;

    private final String publicKey;

    private final TokenTypeEnums tokenType;

    public Rsa384JwtTokenAdapter(
            AlgorithmTypeEnums algorithm,
            Long ttlSeconds,
            String issuer,
            String audience,
            String privateKey,
            String publicKey,
            TokenTypeEnums tokenType
    ) {
        this.algorithm = algorithm;
        this.ttlSeconds = ttlSeconds;
        this.issuer = issuer;
        this.audience = audience;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.tokenType = tokenType;
    }

    @Override
    public String generate(Map<String, Object> payload, Duration ttl) {
        return "";
    }

    @Override
    public boolean verify(String jwt) {
        return false;
    }

    @Override
    public JwtPayload parse(String jwt) {
        return null;
    }

    @Override
    public String getAlgorithm() {
        return this.algorithm.getDisplayName();
    }

}
