package com.swlab.dashboard.controller;

import com.swlab.dashboard.service.GitLabService;
import com.swlab.dashboard.utils.ApiResult;

import lombok.RequiredArgsConstructor;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.User;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.swlab.dashboard.utils.ApiUtils.success;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ApiController {

    private final GitLabService gitLabService;

    @GetMapping("/gitlab/version")
    public ApiResult<GitLabApi.ApiVersion> getGitLabApiVersion() {
        return success(gitLabService.getGitLabApi().getApiVersion());
    }

    @GetMapping("/gitlab/users{email}")
    public ApiResult<List<User>> getGitLabUsersByEmail(@PathVariable String email) throws GitLabApiException {
        return success(gitLabService.getGitLabApi().getUserApi().findUsers(email));
    }

    @GetMapping("/gitlab/users")
    public ApiResult<List<User>> getGitLabUsers() throws GitLabApiException {
        return success(gitLabService.getGitLabApi().getUserApi().getActiveUsers());
    }

    @GetMapping("/gitlab/projects")
    public ApiResult<List<Project>> getGitLabProjects() throws GitLabApiException {
        return success(gitLabService.getGitLabApi().getProjectApi().getProjects());
    }

    @GetMapping("/gitlab/projects/{search}")
    public ApiResult<List<Project>> getGitLabProjectsBySearch(@PathVariable String search) throws GitLabApiException {
        return success(gitLabService.getGitLabApi().getProjectApi().getProjects(search));
    }
}
