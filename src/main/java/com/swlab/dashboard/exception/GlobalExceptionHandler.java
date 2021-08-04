package com.swlab.dashboard.exception;

import com.swlab.dashboard.utils.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import static com.swlab.dashboard.utils.ApiUtils.error;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(GitLabApiException.class)
    public String gitLabApiException(GitLabApiException e, Model model) {
        ApiResult apiResult = error(e.getMessage(), e.getHttpStatus());
        model.addAttribute("error", apiResult);
        log.error(apiResult.toString());
        return "/err/500";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String noHandlerFoundException(Exception e) {
        log.error(e.getMessage());
        return "/err/404";
    }
}
