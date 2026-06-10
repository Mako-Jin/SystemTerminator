package com.yaocode.sts.auth.interfaces.assembler;

import com.yaocode.sts.auth.application.dto.request.AuthenticationRequestDto;
import com.yaocode.sts.auth.application.dto.request.PreLoginRequestDto;
import com.yaocode.sts.auth.interfaces.model.params.PreLoginParams;
import com.yaocode.sts.auth.interfaces.model.params.login.LoginRequestParams;
import com.yaocode.sts.common.web.utils.WebHttpRequestUtils;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 参数转化器
 * @author: Jin-LiangBo
 * @date: 2026年04月14日 10:49
 */
@Mapper(componentModel = "spring")
public interface AuthenticationAssembler {

    AuthenticationAssembler INSTANCE = Mappers.getMapper(AuthenticationAssembler.class);

    /**
     * 参数对象转dto
     * @param loginRequestParams 参数对象
     * @return AuthenticationDto
     */
    AuthenticationRequestDto toDto(LoginRequestParams loginRequestParams);

    /**
     * 转换为应用层命令
     * 自动补充服务端参数
     */
    default PreLoginRequestDto toPreLoginDto(PreLoginParams params) {
        PreLoginRequestDto preLoginDto = new PreLoginRequestDto();
        preLoginDto.setClientId(params.getClientId());
        preLoginDto.setDeviceId(params.getDeviceId());
        preLoginDto.setSessionId(params.getSessionId());
        preLoginDto.setRememberMe(params.getRememberMe());
        preLoginDto.setClientType(params.getClientType());
        preLoginDto.setClientVersion(params.getClientVersion());
        preLoginDto.setDeviceType(params.getDeviceType());
        preLoginDto.setOsVersion(params.getOsVersion());
        preLoginDto.setLanguage(params.getLanguage());
        preLoginDto.setRedirectUri(params.getRedirectUri());
        preLoginDto.setIpAddress(WebHttpRequestUtils.getClientIp());
        preLoginDto.setUserAgent(WebHttpRequestUtils.getUserAgent());
        return preLoginDto;
    }

}
