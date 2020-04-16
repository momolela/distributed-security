package com.itheima.security.distributed.order.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 资源服务配置
 **/
@Configuration
@EnableResourceServer
public class ResouceServerConfig extends ResourceServerConfigurerAdapter {

    // 这个资源服务的ID，这个属性是可选的，但是推荐设置并在授权服务中进行验证。
    public static final String RESOURCE_ID = "res1";

    // TokenStore类的实例，指定令牌如何访问，与tokenServices配置可选
    @Autowired
    TokenStore tokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID) // 资源 id
                .tokenStore(tokenStore)
                // .tokenServices(tokenService()) // 验证令牌的服务
                .stateless(true);
    }

    /**
     * 请求匹配器，用来设置需要进行保护的资源路径，默认的情况下是保护资源服务的全部路径。
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() // 通过http.authorizeRequests()来设置受保护资源的访问规则
                .antMatchers("/**").access("#oauth2.hasScope('ROLE_ADMIN')")
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /**
     * 资源服务令牌解析服务，ResourceServerTokenServices 类的实例，用来实现令牌服务。
     *
     * @return
     */
//    @Bean
//    public ResourceServerTokenServices tokenService() {
//        // 使用远程服务请求授权服务器校验token,必须指定校验token 的url、client_id，client_secret
//        RemoteTokenServices service = new RemoteTokenServices();
//        service.setCheckTokenEndpointUrl("http://localhost:53020/uaa/oauth/check_token");
//        service.setClientId("c1");
//        service.setClientSecret("secret");
//        return service;
//    }

}
