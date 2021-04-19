package com.swlab.dashboard.config.security.handler;

import com.swlab.dashboard.model.user.SecurityUser;
import com.swlab.dashboard.model.user.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class CustomWebAccessDeniedHandler implements AccessDeniedHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomWebAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value());

        if (accessDeniedException instanceof AccessDeniedException) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) {
                SecurityUser securityUser = (SecurityUser) auth.getPrincipal();
                Set<UserRole.RoleType> roleTypes = securityUser.getRoleType();
                if (!roleTypes.isEmpty()) {
                    request.setAttribute("msg", "접근권한이 없는 사용자입니다.");
                    if (roleTypes.contains(UserRole.RoleType.USER)) {
                        request.setAttribute("nextPage", "/home");
                    }
                }
            } else {
                request.setAttribute("msg", "로그인 권한이 없는 사용자입니다.");
                request.setAttribute("nextPage", "/login");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                SecurityContextHolder.clearContext();
            }
        } else {
            logger.info(accessDeniedException.getClass().getCanonicalName());
        }
        request.getRequestDispatcher("/denied").forward(request, response);
    }
}
