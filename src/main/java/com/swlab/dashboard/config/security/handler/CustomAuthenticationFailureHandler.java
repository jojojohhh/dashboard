package com.swlab.dashboard.config.security.handler;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String err;

        if (exception instanceof BadCredentialsException) {
            err = "가입하지 않은 아이디이거나, 잘못된 비밀번호입니다.";
        } else if (exception instanceof DisabledException) {
            err = "계정이 비활성화 되었습니다. 관리자에게 문의 바랍니다.";
        } else {
            err = "로그인에 실패하였습니다. 관리자에게 문의 바랍니다.";
        }

        request.setAttribute("errorMsg", err);
        request.getRequestDispatcher("/login").forward(request, response);
    }
}
