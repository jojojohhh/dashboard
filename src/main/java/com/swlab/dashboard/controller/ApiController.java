package com.swlab.dashboard.controller;

import com.swlab.dashboard.service.GitLabService;
import com.swlab.dashboard.utils.ApiResult;

import lombok.RequiredArgsConstructor;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.Issue;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.User;

import org.gitlab4j.api.utils.ISO8601;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    @GetMapping("/gitlab/issue")
    public ApiResult<List<Issue>> getGitLabIssue() throws GitLabApiException {
        return success(gitLabService.getGitLabApi().getIssuesApi().getIssues());
    }

    @GetMapping("/gitlab/userinfo")
    public ApiResult<User> getGitLabUserInfo(Principal principal) throws GitLabApiException {
        return success(gitLabService.getGitLabApi().getUserApi().findUsers(principal.getName()).get(0));
    }

    @GetMapping("/gitlab/commits")
    public ApiResult getGitLabCommits() throws GitLabApiException {
        List<Project> projects = gitLabService.getGitLabApi().getProjectApi().getProjects();
        for (Project project : projects) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            for (int i = 1; i <7; i++) {

            }
        }
        return success(projects);
    }
}
