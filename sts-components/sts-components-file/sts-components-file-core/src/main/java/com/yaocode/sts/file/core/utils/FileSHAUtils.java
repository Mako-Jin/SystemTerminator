package com.yaocode.sts.file.core.utils;

import com.yaocode.sts.common.crypto.algorithm.hash.DigestAlgorithm;

public class FileSHAUtils {

    /**
     * 计算SHA-256
     */
    public static String calculateSha256(byte[] content) {
        if (content == null || content.length == 0) {
            return null;
        }
        return DigestAlgorithm.sha256Hex(content);  // sha256Hex 方法名
    }

}
