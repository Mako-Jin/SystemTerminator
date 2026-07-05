package com.yaocode.sts.auth.interfaces.assembler;

import com.yaocode.sts.auth.application.dto.request.AuthenticationRequestDto;
import com.yaocode.sts.auth.application.dto.response.PreLoginResponseDto;
import com.yaocode.sts.auth.interfaces.model.params.login.LoginRequestParams;
import com.yaocode.sts.auth.interfaces.model.vo.PreLoginVo;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-04T23:16:44+0800",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (Oracle Corporation)"
)
@Component
public class AuthenticationAssemblerImpl implements AuthenticationAssembler {

    @Override
    public AuthenticationRequestDto toAuthenticationDto(LoginRequestParams loginRequestParams) {
        if ( loginRequestParams == null ) {
            return null;
        }

        AuthenticationRequestDto authenticationRequestDto = new AuthenticationRequestDto();

        return authenticationRequestDto;
    }

    @Override
    public PreLoginVo toPreLoginVo(PreLoginResponseDto preLoginResponseDto) {
        if ( preLoginResponseDto == null ) {
            return null;
        }

        PreLoginVo.PreLoginVoBuilder preLoginVo = PreLoginVo.builder();

        preLoginVo.userInfo( buildUserInfo( preLoginResponseDto ) );
        preLoginVo.loginSuccessVo( buildLoginSuccessVo( preLoginResponseDto.getLoginSuccessDto() ) );
        preLoginVo.tenantLoginConfigs( buildLoginConfigVoList( preLoginResponseDto.getTenantLoginConfigs() ) );
        preLoginVo.isAuthenticated( preLoginResponseDto.getIsAuthenticated() );
        preLoginVo.stateToken( preLoginResponseDto.getStateToken() );
        preLoginVo.sessionId( preLoginResponseDto.getSessionId() );
        preLoginVo.needSelectTenant( preLoginResponseDto.getNeedSelectTenant() );

        preLoginVo.serverTime( java.time.Instant.now().toEpochMilli() );

        return preLoginVo.build();
    }
}
