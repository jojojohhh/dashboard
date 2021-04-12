package com.swlab.dashboard.controller;

import com.swlab.dashboard.dto.UserDto;
import com.swlab.dashboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class LoginController {

    private UserService userService;

    @GetMapping(value = {"", "/login"})
    public String getLogin() {
        return "login";
    }


    @GetMapping("/join")
    public String getJoin() {
        return "join";
    }

    @ResponseBody
    @PostMapping("/join")
    public Map<String, Object> postJoin(@RequestBody UserDto userDto) {
        Map<String, Object> res = new HashMap<>();

        if (userService.findByEmail(userDto.getEmail()).isPresent()) {
            res.put("duplicate", true);
            return res;
        }
        res.put("success", userService.join(userDto) != null ? true : false);
        return res;
    }
}
