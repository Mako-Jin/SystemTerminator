package com.yaocode.sts.auth.domain.constants;

import java.util.regex.Pattern;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月25日 11:29
 */
public interface CommonConstants {

    String DEFAULT_EN_STR = "DEFAULT";

    String SYMBOL_HYPHEN = "-";

    Pattern IDENTIFIER_CODE_REGEX = Pattern.compile("^[a-zA-Z0-9-]+$");

}
