package com.fredsonchaves07.moviecatchapi.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;

import java.time.Duration;
import java.util.Arrays;

@Configuration
public class AuthorizationServerConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        return http.build();
    }

    @Bean
    public ProviderSettings providerSettings(MovieCatchSecurityProperties properties) {
        return ProviderSettings.builder()
                .issuer(properties.getProviderUrl())
                .build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder) {
        RegisteredClient moviecatchbackend = RegisteredClient
                .withId("1")
                .clientId("moviecatch-backend")
                .clientSecret(passwordEncoder.encode("movie@123"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .scope("READ")
                .tokenSettings(TokenSettings.builder()
                        .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                        .accessTokenTimeToLive(Duration.ofMinutes(120))
                        .reuseRefreshTokens(false)
                        .build())
                .build();
        return new InMemoryRegisteredClientRepository(Arrays.asList(moviecatchbackend));
    }

//    @Bean
//    public JWKSource<SecurityContext> jwkSource(JwtKeyStoreProperties properties) throws Exception {
//        char[] keyStorePass = properties.getPassword().toCharArray();
//        String keyPairAlias = properties.getKeypairAlias();
//        Resource jksLocation = properties.getJksLocation();
//        InputStream inputStream = jksLocation.getInputStream();
//        KeyStore keyStore = KeyStore.getInstance("JKS");
//        keyStore.load(inputStream, keyStorePass);
//        RSAKey rsaKey = RSAKey.load(keyStore, keyPairAlias, keyStorePass);
//        return new ImmutableJWKSet<>(new JWKSet(rsaKey));
//    }

//    @Bean
//    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer(UserRepository repository) {
//        return context -> {
//            Authentication authentication = context.getPrincipal();
//            User user = (User) authentication.getPrincipal();
//            context.getClaims().claim("user_id", user.getId().toString());
//            context.getClaims().claim("email", user.getEmail());
//        };
//    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
