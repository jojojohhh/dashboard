package com.swlab.dashboard.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.Assertions;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SecurityConfigurationTest {

    TestRestTemplate restTemplate;
    URL base;
    @LocalServerPort int port;
    @Autowired
    MockMvc mockMvc;

    @Test
    public void index_user() throws Exception {
        mockMvc.perform(get("/home")
                .with(user("jo").roles("USER"))
        ).andDo(print())
                .andExpect(status().isOk());
    }

    @BeforeEach
    public void setUp() throws MalformedURLException {
        restTemplate = new TestRestTemplate("user", "password");
        base = new URL("http://localhost:" + port);
    }

    @Test
    public void whenLoggedUserRequestsHomePage_ThenSuccess() throws IllegalStateException, IOException {
        ResponseEntity<String> res = restTemplate.getForEntity(base.toString(), String.class);
        Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
        Assertions.assertTrue(res.getBody().contains("test"));
    }

    @Test
    public void whenUserWithWrongCredentials_thenUnauthorizedPage() throws Exception {
        restTemplate = new TestRestTemplate("user", "wrongpassword");
        ResponseEntity<String> res = restTemplate.getForEntity(base.toString(), String.class);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, res.getStatusCode());
        Assertions.assertTrue(res.getBody().contains("Unauthorized"));
    }
}
