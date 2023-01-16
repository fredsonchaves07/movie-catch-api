package com.fredsonchaves07.moviecatchapi.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("jwt.keystore")
public class JwtKeyStoreProperties {

    private Resource jksLocation;

    private String password;

    private String keypairAlias;

    public Resource getJksLocation() {
        return jksLocation;
    }

    public String getPassword() {
        return password;
    }

    public String getKeypairAlias() {
        return keypairAlias;
    }
}
