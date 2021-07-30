package com.swlab.dashboard.controller;

import com.swlab.dashboard.config.properties.GitlabProperties;
import com.swlab.dashboard.utils.ApiResult;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.swlab.dashboard.utils.ApiUtils.success;

@RestController
@RequestMapping("/api/v1")
public class ApiController {

    private GitlabProperties gitlabProperties;

    private GitLabApi gitLabApi;

    public ApiController(GitlabProperties gitlabProperties) {   this.gitlabProperties = gitlabProperties;   }

    public void setGitLabApi() {
        this.gitLabApi = new GitLabApi(gitlabProperties.getUrl(), gitlabProperties.getPersonalAccessToken());
        gitLabApi.setRequestTimeout(1000, 5000);
    }

    @GetMapping("/gitlab/version")
    public ApiResult<GitLabApi.ApiVersion> getGitLabApiVersion() {
        setGitLabApi();
        return success(gitLabApi.getApiVersion());
    }

    @GetMapping("/gitlab/users{email}")
    public ApiResult<List<User>> getGitLabUsersByEmail(@PathVariable String email) throws GitLabApiException {
        setGitLabApi();
        return success(gitLabApi.getUserApi().findUsers(email));
    }

    @GetMapping("/gitlab/users")
    public ApiResult<List<User>> getGitLabUsers() throws GitLabApiException {
        setGitLabApi();
        return success(gitLabApi.getUserApi().getActiveUsers());
    }

    @GetMapping("/gitlab/projects")
    public ApiResult<List<Project>> getGitLabProjects() throws GitLabApiException {
        setGitLabApi();
        return success(gitLabApi.getProjectApi().getProjects());
    }

    @GetMapping("/gitlab/projects/{search}")
    public ApiResult<List<Project>> getGitLabProjectsBySearch(@PathVariable String search) throws GitLabApiException {
        setGitLabApi();
        return success(gitLabApi.getProjectApi().getProjects(search));
    }
}
