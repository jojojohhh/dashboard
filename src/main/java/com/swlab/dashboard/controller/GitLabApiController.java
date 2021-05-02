package com.swlab.dashboard.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GitLabApiController {

    /*
        GitLab 인증 요청을 보내는 API
     */
    @PostMapping("/auth/gitlab/authorize")
    public void gitLabAuthorize() {
        //https://gitlab.example.com/oauth/authorize?client_id=APP_ID&redirect_uri=REDIRECT_URI&response_type=code&state=STATE&scope=REQUESTED_SCOPES&code_challenge=CODE_CHALLENGE&code_challenge_method=S256
    }

    /*
        GitLab 인증 결과 받는 API
     */
    @GetMapping("/auth/gitlab/callback")
    public void gitLabAuthCallback() {
        //https://example.com/oauth/redirect?code=1234567890&state=STATE
    }
}
