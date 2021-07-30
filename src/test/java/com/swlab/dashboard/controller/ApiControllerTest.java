package com.swlab.dashboard.controller;

import com.swlab.dashboard.config.security.SecurityConfiguration;
import com.swlab.dashboard.config.security.handler.CustomWebAccessDeniedHandler;
import com.swlab.dashboard.model.user.UserRole;
import com.swlab.dashboard.repository.UserRepository;
import com.swlab.dashboard.service.SecurityUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ApiController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {SecurityConfiguration.class}))
@AutoConfigureMockMvc
@Slf4j
public class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final String BASEURL = "/api/v1";

    @Test
    @WithMockUser("User")
    public void getGitLabVersionTest() throws Exception {
        String url = BASEURL + "/gitlab/version";
        expectResponseIsOk(url);
    }

    @Test
    @WithMockUser("User")
    public void getGitLabProjectsTest() throws Exception {
        String url = BASEURL + "/gitlab/projects";
        expectResponseIsOk(url);
    }

    @Test
    @WithMockUser("User")
    public void getGitLabUsersTest() throws Exception {
        String url = BASEURL + "/gitlab/users";
        expectResponseIsOk(url);
    }

    public void expectResponseIsOk(String url) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> {
                    MockHttpServletResponse res = result.getResponse();
                    log.info("Status: " + res.getStatus());
                    log.info("response: " + res.getContentAsString());
                });
    }
}
