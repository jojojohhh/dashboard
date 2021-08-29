package com.swlab.dashboard.controller;

import com.swlab.dashboard.service.GitLabService;
import lombok.RequiredArgsConstructor;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    private final GitLabService gitLabService;

    @GetMapping(value = {"", "/dashboard"})
    public String getDashboard(Model model) throws GitLabApiException {
        model.addAttribute("activeProjects", gitLabService.getGitLabApi().getProjectApi().getProjects(1, 5));
        model.addAttribute("monthlyCommitCnt", gitLabService.getMonthlyCommitCount());
        return "index";
    }

    @GetMapping("/calendar")
    public String getCalendar() {
        return "pages/calendar";
    }

    @GetMapping("/kanban")
    public String getKanban() {
        return "pages/kanban";
    }

    @GetMapping("/projects")
    public String getProjects(Model model) throws GitLabApiException {
        model.addAttribute("projects", gitLabService.getGitLabApi().getProjectApi().getProjects());
        model.addAttribute("dateFormat", new SimpleDateFormat("yyyy-MM-dd"));
        return "pages/projects/projects";
    }

    @GetMapping("/project-add")
    public String getProjectAdd() {
        return "pages/projects/project-add";
    }

    @GetMapping("/project-detail")
    public String getProjectDetail() {
        return "pages/projects/project-detail";
    }

    @GetMapping("/contacts")
    public String getContacts() {
        return "pages/projects/contacts";
    }
}
