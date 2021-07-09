package com.swlab.dashboard.controller;

import com.swlab.dashboard.config.properties.GitlabProperties;
import com.swlab.dashboard.utils.ApiResult;
import com.swlab.dashboard.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ApiController {

    private final GitlabProperties gitlabProperties;

    private GitLabApi gitLabApi;


    public GitLabApi getGitLabApi() {
        return new GitLabApi(gitlabProperties.getUrl(), gitlabProperties.getPersonalAccessToken());
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
