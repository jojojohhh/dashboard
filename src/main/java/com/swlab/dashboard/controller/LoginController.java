package com.swlab.dashboard.controller;

import com.swlab.dashboard.dto.UserDto;
import com.swlab.dashboard.model.user.SecurityUser;
import com.swlab.dashboard.model.user.UserRole;
import com.swlab.dashboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class LoginController {

    private final UserService userService;

    @RequestMapping(value = {"", "/login"})
    public String getLogin(@AuthenticationPrincipal SecurityUser user, HttpServletRequest req, Model model) {
        if (user != null && user.getRoleType().contains(UserRole.RoleType.USER)) {
            return "redirect:/home";
        }
        if (req.getAttribute("errorMsg") != null) {
            model.addAttribute("err", req.getAttribute("errorMsg"));
        }
        return "login";
    }
    @GetMapping("/test")
    public String test() {
        return "test";
    }


    /*
        GitLab 인증 요청을 보내는 API
     */
    @PostMapping("/auth/gitlab/authorize")
    public String gitLabAuthorize(HttpServletRequest req, Model model) throws GitLabApiException {
        return "forward:/auth/gitlab/callback";
        //https://gitlab.example.com/oauth/authorize?client_id=APP_ID&redirect_uri=REDIRECT_URI&response_type=code&state=STATE&scope=REQUESTED_SCOPES&code_challenge=CODE_CHALLENGE&code_challenge_method=S256
    }

    /*
        GitLab 인증 결과 받는 API
     */
    @GetMapping("/auth/gitlab/callback")
    public void gitLabAuthCallback() {
        //https://example.com/oauth/redirect?code=1234567890&state=STATE
    }

    @GetMapping("/join")
    public String getJoin() {
        return "join";
    }

    @ResponseBody
    @PostMapping(path = "/join", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, Object> postJoin(@RequestBody UserDto userDto) {
        Map<String, Object> res = new HashMap<>();

        if (userService.findByEmail(userDto.getEmail()).isPresent()) {
            res.put("duplicate", true);
            return res;
        }

        res.put("success", userService.join(userDto) != null ? true : false);
        return res;
    }

    @GetMapping("/denied")
    public String accessDenied() {
        return "denied";
    }
}
