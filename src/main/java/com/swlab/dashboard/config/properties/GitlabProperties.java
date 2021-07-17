package com.swlab.dashboard.config.properties;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter @Setter
@Configuration
@ConfigurationProperties("gitlab")
public class GitlabProperties {

    private String url;
    private String personalAccessToken;
}
