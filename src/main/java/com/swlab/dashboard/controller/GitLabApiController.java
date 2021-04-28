package com.swlab.dashboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GitLabApiController {

    /*
        GitLab 인증 요청을 보내는 API
     */
    @PostMapping("/auth/gitlab/authorize")
    public void gitLabAuthorize() {

    }

    /*
        GitLab 인증 결과 받는 API
     */
    @GetMapping("/auth/gitlab/callback")
    public void gitLabAuthCallback() {

    }
}
