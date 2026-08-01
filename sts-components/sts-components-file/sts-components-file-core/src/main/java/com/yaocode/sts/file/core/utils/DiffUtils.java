package com.yaocode.sts.file.core.utils;

import com.yaocode.sts.file.core.model.DiffResult;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * 文件差异计算工具类
 *
 * @author yaocode
 * @since 1.0.0
 */
@Slf4j
public class DiffUtils {

    /**
     * 计算两个文件的差异
     *
     * @param content1 文件1内容
     * @param content2 文件2内容
     * @return 差异结果
     */
    public static DiffResult calculateDiff(byte[] content1, byte[] content2) {
        if (content1 == null || content2 == null) {
            return DiffResult.builder()
                    .diffType("UNKNOWN")
                    .changePercentage(0.0)
                    .addedLines(0)
                    .deletedLines(0)
                    .modifiedLines(0)
                    .diffContent("")
                    .build();
        }

        // 如果内容完全相同
        if (Arrays.equals(content1, content2)) {
            return DiffResult.builder()
                    .diffType("IDENTICAL")
                    .changePercentage(0.0)
                    .addedLines(0)
                    .deletedLines(0)
                    .modifiedLines(0)
                    .diffContent("文件内容完全相同")
                    .build();
        }

        // 转为字符串（支持二进制文件检测）
        String text1 = new String(content1, StandardCharsets.UTF_8);
        String text2 = new String(content2, StandardCharsets.UTF_8);

        // 检测是否为二进制文件（包含不可打印字符）
        boolean isBinary1 = isBinary(content1);
        boolean isBinary2 = isBinary(content2);

        if (isBinary1 || isBinary2) {
            // 二进制文件差异
            return calculateBinaryDiff(content1, content2);
        }

        // 文本文件差异
        return calculateTextDiff(text1, text2);
    }

    /**
     * 计算文本文件差异
     */
    private static DiffResult calculateTextDiff(String text1, String text2) {
        String[] lines1 = text1.split("\n");
        String[] lines2 = text2.split("\n");

        // 使用最长公共子序列算法计算差异
        DiffMatchPatch dmp = new DiffMatchPatch();
        LinkedList<DiffMatchPatch.Diff> diffs = dmp.diffMain(text1, text2, false);
        dmp.diffCleanupSemantic(diffs);

        int added = 0, deleted = 0, modified = 0;
        StringBuilder diffContent = new StringBuilder();

        for (DiffMatchPatch.Diff diff : diffs) {
            String text = diff.text;
            // 移除换行符用于显示
            String displayText = text.replace("\n", "\\n\n");

            switch (diff.operation) {
                case INSERT:
                    added += countLines(text);
                    diffContent.append("+ ").append(displayText);
                    break;
                case DELETE:
                    deleted += countLines(text);
                    diffContent.append("- ").append(displayText);
                    break;
                case EQUAL:
                    modified += countLines(text);
                    // 只显示部分上下文（最多3行）
                    String[] equalLines = text.split("\n");
                    int showLines = Math.min(equalLines.length, 3);
                    for (int i = 0; i < showLines; i++) {
                        diffContent.append("  ").append(equalLines[i]).append("\n");
                    }
                    if (equalLines.length > 3) {
                        diffContent.append("  ... (").append(equalLines.length - 3).append(" lines skipped)\n");
                    }
                    break;
            }
        }

        double percentage = lines1.length > 0 ?
                (double) (added + deleted) / lines1.length * 100 : 0;

        return DiffResult.builder()
                .diffType("TEXT_DIFF")
                .changePercentage(Math.min(percentage, 100))
                .addedLines(added)
                .deletedLines(deleted)
                .modifiedLines(modified)
                .diffContent(diffContent.toString())
                .build();
    }

    /**
     * 计算二进制文件差异
     */
    private static DiffResult calculateBinaryDiff(byte[] content1, byte[] content2) {
        long sizeDiff = Math.abs(content1.length - content2.length);
        double percentage = content1.length > 0 ?
                (double) sizeDiff / content1.length * 100 : 0;

        // 计算字节级别的差异
        int minLen = Math.min(content1.length, content2.length);
        int diffBytes = 0;
        for (int i = 0; i < minLen; i++) {
            if (content1[i] != content2[i]) {
                diffBytes++;
            }
        }
        diffBytes += Math.abs(content1.length - content2.length);

        return DiffResult.builder()
                .diffType("BINARY_DIFF")
                .changePercentage(Math.min(percentage, 100))
                .addedLines(Math.max(content2.length - content1.length, 0))
                .deletedLines(Math.max(content1.length - content2.length, 0))
                .modifiedLines(diffBytes)
                .diffContent(String.format(
                        "二进制文件差异:\n" +
                                "  文件大小: %d -> %d bytes (变化: %d bytes)\n" +
                                "  差异字节数: %d\n" +
                                "  变更百分比: %.2f%%",
                        content1.length, content2.length,
                        content2.length - content1.length,
                        diffBytes,
                        Math.min(percentage, 100)
                ))
                .build();
    }

    /**
     * 检测是否为二进制文件
     */
    private static boolean isBinary(byte[] content) {
        if (content == null || content.length == 0) {
            return false;
        }
        // 检查前 1024 字节
        int checkLen = Math.min(content.length, 1024);
        for (int i = 0; i < checkLen; i++) {
            byte b = content[i];
            // 如果包含不可打印字符（除了常见的空白字符）
            if (b < 0x09 || (b > 0x0D && b < 0x20) || b > 0x7E) {
                // 可能是二进制文件
                return true;
            }
        }
        return false;
    }

    /**
     * 统计行数
     */
    private static int countLines(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        return (int) text.chars().filter(ch -> ch == '\n').count() + 1;
    }

    /**
     * 从 InputStream 读取字节数组
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return new byte[0];
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        return baos.toByteArray();
    }

    /**
     * 从 InputStream 计算差异
     */
    public static DiffResult calculateDiff(InputStream input1, InputStream input2) throws IOException {
        byte[] content1 = readInputStream(input1);
        byte[] content2 = readInputStream(input2);
        return calculateDiff(content1, content2);
    }

    // ==================== DiffMatchPatch 内部类（简化版） ====================

    /**
     * 简化的差异匹配补丁类
     */
    public static class DiffMatchPatch {

        public enum Operation {
            DELETE, INSERT, EQUAL
        }

        public static class Diff {
            public Operation operation;
            public String text;

            public Diff(Operation operation, String text) {
                this.operation = operation;
                this.text = text;
            }
        }

        /**
         * 计算差异（简化实现）
         */
        public LinkedList<Diff> diffMain(String text1, String text2, boolean checklines) {
            LinkedList<Diff> diffs = new LinkedList<>();

            if (text1 == null || text1.isEmpty()) {
                if (text2 != null && !text2.isEmpty()) {
                    diffs.add(new Diff(Operation.INSERT, text2));
                }
                return diffs;
            }

            if (text2 == null || text2.isEmpty()) {
                diffs.add(new Diff(Operation.DELETE, text1));
                return diffs;
            }

            if (text1.equals(text2)) {
                diffs.add(new Diff(Operation.EQUAL, text1));
                return diffs;
            }

            // 使用 LCS 算法计算差异
            String[] lines1 = text1.split("\n", -1);
            String[] lines2 = text2.split("\n", -1);

            int[][] lcs = computeLCS(lines1, lines2);
            buildDiffsFromLCS(lines1, lines2, lcs, diffs);

            return diffs;
        }

        /**
         * 计算最长公共子序列
         */
        private int[][] computeLCS(String[] lines1, String[] lines2) {
            int m = lines1.length;
            int n = lines2.length;
            int[][] dp = new int[m + 1][n + 1];

            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    if (lines1[i - 1].equals(lines2[j - 1])) {
                        dp[i][j] = dp[i - 1][j - 1] + 1;
                    } else {
                        dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                    }
                }
            }
            return dp;
        }

        /**
         * 从LCS构建差异
         */
        private void buildDiffsFromLCS(String[] lines1, String[] lines2,
                                       int[][] lcs, LinkedList<Diff> diffs) {
            int i = lines1.length;
            int j = lines2.length;
            LinkedList<Diff> tempDiffs = new LinkedList<>();

            while (i > 0 || j > 0) {
                if (i > 0 && j > 0 && lines1[i - 1].equals(lines2[j - 1])) {
                    tempDiffs.addFirst(new Diff(Operation.EQUAL, lines1[i - 1] + "\n"));
                    i--;
                    j--;
                } else if (j > 0 && (i == 0 || lcs[i][j - 1] >= lcs[i - 1][j])) {
                    tempDiffs.addFirst(new Diff(Operation.INSERT, lines2[j - 1] + "\n"));
                    j--;
                } else if (i > 0 && (j == 0 || lcs[i - 1][j] > lcs[i][j - 1])) {
                    tempDiffs.addFirst(new Diff(Operation.DELETE, lines1[i - 1] + "\n"));
                    i--;
                }
            }

            // 合并相邻的相同操作
            mergeDiffs(tempDiffs, diffs);
        }

        /**
         * 合并相邻的相同操作
         */
        private void mergeDiffs(LinkedList<Diff> source, LinkedList<Diff> target) {
            for (Diff diff : source) {
                if (!target.isEmpty() && target.getLast().operation == diff.operation) {
                    target.getLast().text += diff.text;
                } else {
                    target.add(diff);
                }
            }
        }

        /**
         * 语义清理
         */
        public void diffCleanupSemantic(LinkedList<Diff> diffs) {
            // 简化实现，不做复杂语义清理
        }
    }
}
