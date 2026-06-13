package com.yaocode.sts.common.crypto.algorithm.asymmetric;

import com.yaocode.sts.common.crypto.algorithm.encode.Base64Algorithm;
import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.signers.SM2Signer;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.Security;

/**
 * SM2 国密非对称加密算法工具类
 * <p>SM2是国家密码管理局发布的椭圆曲线公钥密码算法标准</p>
 * @author: Jin-LiangBo
 * @date: 2026年06月02日
 */
public final class SM2Algorithm {

    /**
     * SM2 曲线名称
     */
    public static final String CURVE_NAME = "sm2p256v1";

    /**
     * 用户ID（默认值）
     */
    public static final String DEFAULT_USER_ID = "1234567812345678";

    /**
     * SM2 曲线参数
     */
    private static final X9ECParameters CURVE_PARAMS = GMNamedCurves.getByName(CURVE_NAME);
    private static final ECDomainParameters DOMAIN_PARAMS = new ECDomainParameters(
            CURVE_PARAMS.getCurve(), CURVE_PARAMS.getG(), CURVE_PARAMS.getN());

    /**
     * 安全随机数生成器
     */
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    static {
        // 注册 BouncyCastle 提供者
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    /**
     * 私有构造函数，防止实例化
     */
    private SM2Algorithm() {
    }

    // ==================== 密钥对生成 ====================

    /**
     * 生成 SM2 密钥对
     * @return 密钥对（包含公钥和私钥）
     */
    public static KeyPair generateKeyPair() {
        ECKeyPairGenerator generator = new ECKeyPairGenerator();
        ECKeyGenerationParameters keyGenParams = new ECKeyGenerationParameters(DOMAIN_PARAMS, SECURE_RANDOM);
        generator.init(keyGenParams);

        AsymmetricCipherKeyPair keyPair = generator.generateKeyPair();
        ECPublicKeyParameters publicKey = (ECPublicKeyParameters) keyPair.getPublic();
        ECPrivateKeyParameters privateKey = (ECPrivateKeyParameters) keyPair.getPrivate();

        return new KeyPair(publicKey, privateKey);
    }

    // ==================== SM2 签名/验签 ====================

    /**
     * SM2 签名
     * @param data 待签名数据
     * @param privateKey 私钥
     * @return 签名值（Base64编码）
     */
    public static String sign(byte[] data, ECPrivateKeyParameters privateKey) {
        return sign(data, privateKey, DEFAULT_USER_ID);
    }

    /**
     * SM2 签名
     * @param data 待签名数据
     * @param privateKey 私钥
     * @param userId 用户ID
     * @return 签名值（Base64编码）
     */
    public static String sign(byte[] data, ECPrivateKeyParameters privateKey, String userId) {
        try {
            SM2Signer signer = new SM2Signer();
            CipherParameters params = new org.bouncycastle.crypto.params.ParametersWithID(
                    privateKey, userId.getBytes(StandardCharsets.UTF_8));
            signer.init(true, params);
            signer.update(data, 0, data.length);

            byte[] signature = signer.generateSignature();
            return Base64Algorithm.encryptByBase64(signature);
        } catch (Exception e) {
            throw new IllegalArgumentException("SM2 签名失败", e);
        }
    }

    /**
     * SM2 签名（字符串输入）
     * @param data 待签名字符串
     * @param privateKey 私钥
     * @return 签名值（Base64编码）
     */
    public static String sign(String data, ECPrivateKeyParameters privateKey) {
        return sign(data.getBytes(StandardCharsets.UTF_8), privateKey);
    }

    /**
     * SM2 验证签名
     * @param data 原始数据
     * @param signatureStr 签名值（Base64编码）
     * @param publicKey 公钥
     * @return 验证结果
     */
    public static boolean verify(byte[] data, String signatureStr, ECPublicKeyParameters publicKey) {
        return verify(data, signatureStr, publicKey, DEFAULT_USER_ID);
    }

    /**
     * SM2 验证签名
     * @param data 原始数据
     * @param signatureStr 签名值（Base64编码）
     * @param publicKey 公钥
     * @param userId 用户ID
     * @return 验证结果
     */
    public static boolean verify(byte[] data, String signatureStr,
            ECPublicKeyParameters publicKey, String userId) {
        try {
            byte[] signature = Base64Algorithm.decryptByBase64(signatureStr);

            SM2Signer signer = new SM2Signer();
            CipherParameters params = new org.bouncycastle.crypto.params.ParametersWithID(
                    publicKey, userId.getBytes(StandardCharsets.UTF_8));
            signer.init(false, params);
            signer.update(data, 0, data.length);

            return signer.verifySignature(signature);
        } catch (Exception e) {
            throw new IllegalArgumentException("SM2 验签失败", e);
        }
    }

    /**
     * SM2 验证签名（字符串输入）
     * @param data 原始字符串
     * @param signatureStr 签名值（Base64编码）
     * @param publicKey 公钥
     * @return 验证结果
     */
    public static boolean verify(String data, String signatureStr, ECPublicKeyParameters publicKey) {
        return verify(data.getBytes(StandardCharsets.UTF_8), signatureStr, publicKey);
    }

    // ==================== 密钥转换 ====================

    /**
     * 获取公钥的字节数组表示
     * @param publicKey 公钥
     * @return 公钥字节数组
     */
    public static byte[] getPublicKeyBytes(ECPublicKeyParameters publicKey) {
        ECPoint point = publicKey.getQ();
        return point.getEncoded(false); // 压缩格式
    }

    /**
     * 获取私钥的字节数组表示
     * @param privateKey 私钥
     * @return 私钥字节数组
     */
    public static byte[] getPrivateKeyBytes(ECPrivateKeyParameters privateKey) {
        return privateKey.getD().toByteArray();
    }

    /**
     * 从字节数组构建公钥
     * @param keyBytes 公钥字节数组
     * @return 公钥对象
     */
    public static ECPublicKeyParameters getPublicKey(byte[] keyBytes) {
        ECPoint point = CURVE_PARAMS.getCurve().decodePoint(keyBytes);
        return new ECPublicKeyParameters(point, DOMAIN_PARAMS);
    }

    /**
     * 从字节数组构建私钥
     * @param keyBytes 私钥字节数组
     * @return 私钥对象
     */
    public static ECPrivateKeyParameters getPrivateKey(byte[] keyBytes) {
        BigInteger d = new BigInteger(1, keyBytes);
        return new ECPrivateKeyParameters(d, DOMAIN_PARAMS);
    }

    // ==================== 密钥对容器类 ====================

    /**
     * SM2 密钥对容器
     */
    public record KeyPair(ECPublicKeyParameters publicKey, ECPrivateKeyParameters privateKey) {}
}
