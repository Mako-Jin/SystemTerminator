package com.yaocode.sts.auth.starter;

import com.yaocode.sts.common.resources.annotation.ServerResources;
import com.yaocode.sts.common.resources.annotation.ServiceResources;
import com.yaocode.sts.common.resources.annotation.SystemResources;
import com.yaocode.sts.common.web.annotation.EnableRestApiElapsed;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动类
 * @author: Jin-LiangBo
 * @date: 2025年10月07日 16:08
 */
@EnableRestApiElapsed
@SpringBootApplication
@ComponentScan(basePackages = "com.yaocode.sts.auth")
@ServiceResources(
    code = "000000000",
    name = "用户中心服务",
    desc = "租户、组织、用户、资源、权限管理服务",
    path = "/auth",
    belongTo = {
        @ServerResources(
            code = "000000",
                name = "权限控制服务",
                desc = "主要用户认证和权限控制服务",
                belongTo = {
                    @SystemResources(
                        code = "000",
                        name = "权限管理系统",
                        desc = "后台租户组织用户管理系统"
                )
            }
        )
    }
)
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

}
