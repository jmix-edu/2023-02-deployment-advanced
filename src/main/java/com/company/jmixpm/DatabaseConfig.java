package com.company.jmixpm;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.vault.core.VaultTemplate;

@Configuration
public class DatabaseConfig {

    @Autowired
    @Lazy
    private VaultTemplate vaultTemplate;

    @Primary
    @Profile("default")
    @Bean("dataSourceProperties")
    @ConfigurationProperties("main.datasource")
    DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Profile({"default", "k8s"})
    @Bean("dataSourceProperties")
    @ConfigurationProperties("main.datasource")
    DataSourceProperties dataSourcePropertiesK8s() {
        return new DataSourceProperties();
    }

    @Profile("prod")
    @Bean("dataSourceProperties")
    DataSourceProperties dataSourcePropertiesVault() {
        DataSourceProperties properties = new DataSourceProperties();

        DbCredentials dbCredentials = new ObjectMapper().convertValue(
                vaultTemplate.read("secret/data/application/database/credentials").getData().get("data"),
                DbCredentials.class);

        properties.setUrl(dbCredentials.getUrl());
        properties.setUsername(dbCredentials.getUsername());
        properties.setPassword(dbCredentials.getPassword());

        return properties;
    }
}
