package com.swlab.dashboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping(value = {"", "/dashboard"})
    public String getDashboard() {
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
    public String getProjects() {
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
