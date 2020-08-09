package br.com.navita.api.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import lombok.Data;
import lombok.Getter;

@EnableConfigurationProperties
@ConfigurationProperties(prefix = "api", ignoreUnknownFields = true)
public class Property {

	@Getter
    private final Datasource datasource = new Datasource();

    @Data
    public static class Datasource {
         private boolean executeLiquibase;
    }
}
