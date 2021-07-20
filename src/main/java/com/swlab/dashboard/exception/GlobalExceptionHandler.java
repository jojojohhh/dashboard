package com.swlab.dashboard.exception;


import com.swlab.dashboard.utils.ApiResult;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.swlab.dashboard.utils.ApiUtils.error;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GitLabApiException.class)
    public ApiResult gitLabApiException(GitLabApiException e) {
        return error(e.getMessage(), e.getHttpStatus());
    }

    @ExceptionHandler(NotFoundException.class)
    public ApiResult notFoundException(NotFoundException e) {
        return error(e.getMessage(), e.getStatus());
    }
}
