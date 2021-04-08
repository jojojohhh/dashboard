package com.swlab.dashboard.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SecurityConfigurationTest {

    TestRestTemplate restTemplate;
    URL base;
    @LocalServerPort int port;

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
