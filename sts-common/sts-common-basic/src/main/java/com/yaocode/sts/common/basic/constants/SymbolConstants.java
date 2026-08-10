package com.yaocode.sts.common.basic.constants;

/**
 * 符号常量接口
 * @author: Jin-LiangBo
 * @date: 2026年04月21日 16:10
 */
public interface SymbolConstants {

    String EMPTY_STR = "";
    String SPACE_STR = " ";
    String NUMBER_SIGN = "#";

    String FORWARD_SLASH = "/";

    String COMMA = ",";

    String BACKSLASH = "\\";

    String LEFT_BRACKETS = "[";
    String RIGHT_BRACKETS = "]";
    String RIGHT_ARROW = "->";
    String DOUBLE_COLON = "::";
    /**
     * 反引号
     */
    String BACKTICK = "`";
    String DOUBLE_QUOTES = "\"";
    char SEMICOLON = ';';
    char LEFT_PARENTHESIS = '(';
    String QUESTION_MARKS = "?";
    /** 换行字符 */
    char NEWLINE_CHAR = '\n';
    String LINE_SEPARATOR = String.valueOf(NEWLINE_CHAR);
    String LINE_SEPARATOR_CRLF = "\r\n";

    /** 可视化换行符（用于把真实换行替换为文本 \n） */
    String DISPLAY_NEWLINE = "\\n";

    char EQUAL_SIGN = '=';

    String DOT = ".";
    String AMPERSAND  = "&";

    /**
     * 小于号（HTML标签开始）
     */
    String LESS_THAN = "<";

    /**
     * 大于号（HTML标签结束）
     */
    String GREATER_THAN = ">";

    String SYMBOL_HYPHEN = "-";

    String SYMBOL_ASTERISK = "*";

    String UNDERSCORE = "_";

}