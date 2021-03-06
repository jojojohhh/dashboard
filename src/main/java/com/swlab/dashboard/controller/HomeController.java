package com.swlab.dashboard.controller;

import com.swlab.dashboard.config.properties.GitlabProperties;
import com.swlab.dashboard.config.properties.GoogleProperties;
import com.swlab.dashboard.service.GitLabService;

import lombok.RequiredArgsConstructor;

import org.gitlab4j.api.Constants;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.Issue;
import org.gitlab4j.api.models.Project;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    private final GitLabService gitLabService;

    private final GitlabProperties gitlabProperties;
    private final GoogleProperties googleProperties;

    private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @GetMapping(value = {"", "/dashboard"})
    public String getDashboard(Model model) throws GitLabApiException {
        model.addAttribute("activeProjects", gitLabService.getGitLabApi().getProjectApi().getProjects(1, 5));
        model.addAttribute("monthlyCommitCnt", gitLabService.getMonthlyCommitCount());
        model.addAttribute("projects", gitLabService.getGitLabApi().getProjectApi().getProjects());
        return "index";
    }

    @GetMapping("/calendar")
    public String getCalendar(Model model) {
        model.addAttribute("googleApiKey", googleProperties.getApiKey());
        model.addAttribute("googleCalendarId", googleProperties.getCalendarId());
        return "pages/calendar";
    }

    @GetMapping("/kanban")
    public String getKanban() {
        return "pages/kanban";
    }

    @GetMapping("/projects")
    public String getProjects(Model model) throws GitLabApiException {
        model.addAttribute("projects", gitLabService.getGitLabApi().getProjectApi().getProjects());
        model.addAttribute("dateFormat", df);
        return "pages/projects/projects";
    }

    @GetMapping("/project-add")
    public String getProjectAdd() {
        return "pages/projects/project-add";
    }

    @PostMapping("/project/add")
    public void postProjectAdd() throws GitLabApiException {
        gitLabService.getGitLabApi().getProjectApi().createProject(new Project());
    }

    @GetMapping("/{id}/project-detail")
    public String getProjectDetail(@PathVariable String id, Model model) throws GitLabApiException {
        Project project = gitLabService.getGitLabApi().getProjectApi().getProject(id);
        List<Commit> commits = gitLabService.getGitLabApi().getCommitsApi().getCommits(id);
        List<Issue> issues = gitLabService.getGitLabApi().getIssuesApi().getIssues(id);

        commits.sort((o1, o2) -> o2.getCommittedDate().compareTo(o1.getCommittedDate()));

        model.addAttribute("project", project);
        model.addAttribute("date", df);
        model.addAttribute("commits", commits.stream().limit(3).collect(Collectors.toList()));
        model.addAttribute("issuesCnt", issues.size());
        model.addAttribute("openedIssuesCnt", issues.stream().filter(issue -> issue.getState().equals(Constants.IssueState.OPENED)).collect(Collectors.toList()).size());
        model.addAttribute("baseUrl", gitlabProperties.getUrl());

        return "pages/projects/project-detail";
    }

    @GetMapping("/contacts")
    public String getContacts(Model model) throws GitLabApiException {
        model.addAttribute("baseUrl", gitlabProperties.getUrl());
        model.addAttribute("userList", gitLabService.getGitLabApi().getUserApi().getUsers());
        return "pages/projects/contacts";
    }
}
