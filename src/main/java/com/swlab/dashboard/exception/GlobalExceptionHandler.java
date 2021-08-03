package com.swlab.dashboard.exception;


import com.swlab.dashboard.utils.ApiResult;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

import static com.swlab.dashboard.utils.ApiUtils.error;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GitLabApiException.class)
    public ApiResult gitLabApiException(GitLabApiException e, Model model) {
        model.addAttribute("error", error(e.getMessage(), e.getHttpStatus()));
        return error(e.getMessage(), e.getHttpStatus());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String noHandlerFoundException(HttpServletRequest req, Exception e, Model model) {
        model.addAttribute("message", e.getMessage());
        model.addAttribute("url", req.getRequestURL());
        return "/err/404";
    }
}
