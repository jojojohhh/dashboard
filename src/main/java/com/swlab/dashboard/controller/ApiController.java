package com.swlab.dashboard.controller;

import com.google.api.services.calendar.Calendar;

import com.google.api.services.calendar.model.Events;
import com.swlab.dashboard.service.GitLabService;
import com.swlab.dashboard.service.GoogleCalendarService;
import com.swlab.dashboard.utils.ApiResult;

import lombok.RequiredArgsConstructor;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.*;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Principal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.swlab.dashboard.utils.ApiUtils.success;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ApiController {

    private final GitLabService gitLabService;
    private final GoogleCalendarService googleCalendarService;

    @GetMapping("/gitlab/version")
    public ApiResult<GitLabApi.ApiVersion> getGitLabApiVersion() {
        return success(gitLabService.getGitLabApi().getApiVersion());
    }

    @GetMapping("/gitlab/user/{email}")
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

    @GetMapping("/gitlab/projects/recent-created")
    public ApiResult<Map<Integer, Integer>> getRecentCreatedProjects() throws GitLabApiException {
        return success(gitLabService.getProjectsCreatedThisWeek());
    }

    @GetMapping("/gitlab/projects/{search}")
    public ApiResult<List<Project>> getGitLabProjectsBySearch(@PathVariable String search) throws GitLabApiException {
        return success(gitLabService.getGitLabApi().getProjectApi().getProjects(search));
    }

    @GetMapping("/gitlab/commit")
    public ApiResult<List<Commit>> getGitLabCommit() throws GitLabApiException {
        GitLabApi gitLabApi = gitLabService.getGitLabApi();
        List<Project> projects = gitLabApi.getProjectApi().getProjects();
        List<Commit> commits = new ArrayList<>();

        for (Project project: projects) {
            commits.addAll(gitLabApi.getCommitsApi().getCommits(project.getId()));
        }
        return success(commits);
    }

    @GetMapping("/gitlab/commit/monthly-count")
    public ApiResult<Map<Integer, Integer>> getGitLabMonthlyCommitCount() throws GitLabApiException {
        return success(gitLabService.getMonthlyCommitCount());
    }

    @GetMapping("/gitlab/project/{id}/issues-status")
    public ApiResult<Map<String, Integer>> getGitLabIssueStatusByProject(@PathVariable String id) throws GitLabApiException {
        return success(gitLabService.getIssueStatusCount(id));
    }

    @GetMapping("/gitlab/project/{id}/issues")
    public ApiResult<List<Issue>> getGitLabIssuesByProject(@PathVariable String id) throws GitLabApiException {
        return success(gitLabService.getGitLabApi().getIssuesApi().getIssues(id));
    }

    @GetMapping("/gitlab/group")
    public ApiResult<List<Group>> getGitLabGroup() throws GitLabApiException {
        List<List<Member>> allMemberInGroup = new ArrayList<>();
        for (Group group : gitLabService.getGitLabApi().getGroupApi().getGroups()) {
            gitLabService.getGitLabApi().getGroupApi().getAllMembers(group.getId());
        }
        return success(gitLabService.getGitLabApi().getGroupApi().getGroups());
    }

    @GetMapping("/gitlab/userinfo")
    public ApiResult<User> getGitLabUserInfo(Principal principal) throws GitLabApiException {
        return success(gitLabService.getGitLabApi().getUserApi().findUsers(principal.getName()).get(0));
    }

    @GetMapping("/google/calendar/events")
    public ApiResult<Events> getGoogleCalendarEvents() throws GeneralSecurityException, IOException {
        Calendar service = googleCalendarService.getCalendar();
        return success(service.events().list("niuspmd1mlngu374b6mv1g3ec0@group.calendar.google.com")
                .execute());
    }
}
