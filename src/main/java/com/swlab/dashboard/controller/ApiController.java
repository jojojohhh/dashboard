package com.swlab.dashboard.controller;

import com.swlab.dashboard.utils.ApiResult;
import com.swlab.dashboard.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


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
    public ApiResult<List<User>> getUsers(@RequestParam String email) throws GitLabApiException {
        gitLabApi = getGitLabApi();
        return ApiUtils.success(
                gitLabApi
                        .getUserApi()
                        .findUsers(email)
                            .stream()
                            .collect(Collectors.toList())
        );
    }

    @RequestMapping("/projects")
    public ApiResult<List<Project>> getProjects() throws GitLabApiException {
        gitLabApi = getGitLabApi();
        return ApiUtils.success(
                gitLabApi
                        .getProjectApi()
                        .getProjects()
                            .stream()
                            .collect(Collectors.toList())
        );
    }

    @RequestMapping("/projects")
    public ApiResult<List<Project>> getProjects(@RequestParam String search) throws GitLabApiException {
        gitLabApi = getGitLabApi();
        return ApiUtils.success(
                gitLabApi
                        .getProjectApi()
                        .getProjects(search)
                            .stream()
                            .collect(Collectors.toList())
        );
    }
}
