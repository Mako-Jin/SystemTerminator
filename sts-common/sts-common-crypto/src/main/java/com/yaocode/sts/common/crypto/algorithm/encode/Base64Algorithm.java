package com.yaocode.sts.common.crypto.algorithm.encode;

import com.yaocode.sts.common.crypto.exception.CryptoException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class Base64Algorithm {

    /**
     * BASE64解码
     * @param input input
     * @return byte[]
     * @author Mr.Jin-晋
     * @date 2021-01-09 15:26
     */
    public static byte[] decryptByBase64(String input) {
        return Base64.getDecoder().decode(input);
    }

    /**
     * BASE64编码
     * @param input byte[]
     * @return String
     */
    public static String encryptByBase64(byte[] input) {
        return Base64.getEncoder().encodeToString(input);
    }

    /**
     * BASE64编码
     * @param input String
     * @return byte[]
     */
    public static byte[] encryptByBase64(String input) {
        return Base64.getEncoder().encode(input.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * URL安全Base64编码
     */
    public static String encodeUrlSafe(byte[] data) {
        return Base64.getUrlEncoder().encodeToString(data);
    }

    /**
     * URL安全Base64编码（字符串）
     */
    public static String encodeUrlSafe(String data) {
        return encodeUrlSafe(data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * URL安全Base64解码
     */
    public static byte[] decodeUrlSafe(String encoded) {
        return Base64.getUrlDecoder().decode(encoded);
    }

    /**
     * URL安全Base64解码（返回字符串）
     */
    public static String decodeUrlSafeToStr(String encoded) {
        return new String(decodeUrlSafe(encoded), StandardCharsets.UTF_8);
    }

    /**
     * URL安全Base64编码（不带填充字符）
     * <p>用于JWT等需要无填充Base64编码的场景</p>
     *
     * @param data 输入字节数组
     * @return Base64 URL编码字符串（无填充）
     */
    public static String encodeUrlSafeNoPadding(byte[] data) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(data);
    }

    /**
     * URL安全Base64编码（不带填充字符，字符串输入）
     *
     * @param data 输入字符串
     * @return Base64 URL编码字符串（无填充）
     */
    public static String encodeUrlSafeNoPadding(String data) {
        return encodeUrlSafeNoPadding(data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * URL安全Base64解码（支持无填充）
     * <p>自动处理可能缺少的填充字符</p>
     *
     * @param encoded Base64 URL编码字符串
     * @return 解码后的字节数组
     */
    public static byte[] decodeUrlSafeNoPadding(String encoded) {
        if (encoded == null) {
            return null;
        }
        // 处理可能缺少的填充字符
        int padding = (4 - encoded.length() % 4) % 4;
        if (padding > 0) {
            encoded = encoded + "=".repeat(padding);
        }
        return Base64.getUrlDecoder().decode(encoded);
    }

    /**
     * URL安全Base64解码（支持无填充，返回字符串）
     *
     * @param encoded Base64 URL编码字符串
     * @return 解码后的字符串
     */
    public static String decodeUrlSafeNoPaddingToStr(String encoded) {
        return new String(decodeUrlSafeNoPadding(encoded), StandardCharsets.UTF_8);
    }

    public static String encodeImage(BufferedImage bufferedImage) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", stream);
            String b64Image = "data:image/png;base64," + Base64.getEncoder().encodeToString(stream.toByteArray());
            stream.close();
            return b64Image;
        }catch (Exception e) {
            throw new CryptoException("图片Base64编码失败", e);
        }
    }

    public static String encodeImage(byte[] byteImage) {
        return "data:image/png;base64," + Base64.getEncoder().encodeToString(byteImage);
    }

    /**
     * encode file to base64 Code String.
     *
     * @param fileName file path
     * @return *
     * @throws Exception e
     */

    public static String fileToBase64(String fileName) throws Exception {
        byte[] buffer = Files.readAllBytes(Paths.get(fileName));
        return encryptByBase64(buffer);
    }

    /**
     * base64 Code decode String save to targetPath.
     *
     * @param base64Code String
     * @param targetPath String
     * @throws Exception e
     */

    public static void decodeBase64ToFile(String base64Code, String targetPath) throws Exception {
        byte[] buffer = decryptByBase64(base64Code);
        try (FileOutputStream out = new FileOutputStream(targetPath)) {
            out.write(buffer);
        }
    }

    /**
     * base64 code save to file.
     *
     * @param base64Code String
     * @param targetPath String
     * @throws Exception e
     */
    public static void base64ToFile(String base64Code, String targetPath) throws Exception {
        byte[] buffer = base64Code.getBytes(StandardCharsets.UTF_8);
        try (FileOutputStream out = new FileOutputStream(targetPath)) {
            out.write(buffer);
        }
    }

}