package com.swlab.dashboard.controller;

import com.swlab.dashboard.config.properties.GitlabProperties;
import org.gitlab4j.api.GitLabApi;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ApiController.class)
public class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GitlabProperties gitlabProperties;

    private ApiController apiController;

    @Test
    public void gitlabPropertiesTest() {
        Assertions.assertEquals("gitlab.ks.ac.kr:10800", gitlabProperties.getUrl());
        Assertions.assertEquals("TUbEkxRPaQnseeSMXxYa", gitlabProperties.getPersonalAccessToken());
    }
}
