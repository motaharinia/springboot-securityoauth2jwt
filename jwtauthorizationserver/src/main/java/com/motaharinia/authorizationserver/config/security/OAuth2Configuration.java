package com.motaharinia.authorizationserver.config.security;

import com.motaharinia.authorizationserver.security.business.service.SecurityUserServiceImpl;
import net.devh.boot.grpc.server.security.authentication.BasicGrpcAuthenticationReader;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpointAuthenticationFilter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class OAuth2Configuration extends AuthorizationServerConfigurerAdapter {

    @Value("${app.security.jwt.keystore-location}")
    private String KEY_STORE_FILE;
    @Value("${app.security.jwt.keystore-password}")
    private String KEY_STORE_PASSWORD;
    @Value("${app.security.jwt.key-alias}")
    private String KEY_ALIAS;
    @Value("${app.security.jwt.key-id}")
    private String JWK_KID;
    @Value("${app.security.check-user-scopes}")
    private Boolean SECURITY_CHECK_USER_SCOPES;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityUserServiceImpl userDetailsService;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;


    @Bean
    public GrpcAuthenticationReader grpcAuthenticationReader() {
        return new BasicGrpcAuthenticationReader();
    }


    @Bean
    public OAuth2RequestFactory requestFactory() {
        CustomOauth2RequestFactory requestFactory = new CustomOauth2RequestFactory(clientDetailsService);
        requestFactory.setCheckUserScopes(true);
        return requestFactory;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new CustomTokenEnhancer();
        converter.setKeyPair(new KeyStoreKeyFactory(new ClassPathResource(KEY_STORE_FILE), KEY_STORE_PASSWORD.toCharArray()).getKeyPair(KEY_ALIAS));
        return converter;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource).passwordEncoder(passwordEncoder);
    }


    @Bean
    public TokenEndpointAuthenticationFilter tokenEndpointAuthenticationFilter() {
        return new TokenEndpointAuthenticationFilter(authenticationManager, requestFactory());
    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore()).tokenEnhancer(jwtAccessTokenConverter()).authenticationManager(authenticationManager).userDetailsService(userDetailsService);
        if (SECURITY_CHECK_USER_SCOPES)
            endpoints.requestFactory(requestFactory());
    }
}
