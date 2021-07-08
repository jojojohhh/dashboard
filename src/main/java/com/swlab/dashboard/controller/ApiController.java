package com.swlab.dashboard.controller;

import lombok.RequiredArgsConstructor;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ApiController {

    @Value("${spring.security.oauth2.client.registration.gitlab.url}")
    private final String gitlabUrl;

    @Value("${spring.security.oauth2.client.registration.gitlab.personalAccessToken}")
    private final String token;

    private GitLabApi gitLabApi;


    public GitLabApi getGitLabApi() {
        return new GitLabApi(gitlabUrl, token);
    }

    @RequestMapping("/users")
    public List<User> getUsers(@RequestParam String email) throws GitLabApiException {
        gitLabApi = getGitLabApi();
        List<User> users = gitLabApi.getUserApi().findUsers(email);
        return users;
    }

    @RequestMapping("/projects")
    public List<Project> getProjects() throws GitLabApiException {
        gitLabApi = getGitLabApi();
        List<Project> projects = gitLabApi.getProjectApi().getProjects();
        return projects;
    }

    @RequestMapping("/projects")
    public List<Project> getProjects(@RequestParam String search) throws GitLabApiException {
        gitLabApi = getGitLabApi();
        List<Project> projects = gitLabApi.getProjectApi().getProjects(search);
        return projects;
    }
}
