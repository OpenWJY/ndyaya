package com.ndyaya.module.report.framework.security.config;

import com.ndyaya.framework.security.config.AuthorizeRequestsCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

/**
 * Report 模块的 Security 配置
 */
@Configuration("reportSecurityConfiguration")
public class SecurityConfiguration {

    @Bean("reportAuthorizeRequestsCustomizer")
    public AuthorizeRequestsCustomizer authorizeRequestsCustomizer() {
        return new AuthorizeRequestsCustomizer() {

            @Override
            public void customize(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
                registry.requestMatchers("/jmreport/**").permitAll(); // 积木报表
            }

        };
    }

}
