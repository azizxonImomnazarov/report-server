package com.example.reportserver.security.handler;

import com.example.reportserver.bean.UserBean;
import com.example.reportserver.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.reportserver.constants.SessionConstants.USER_SESSION_ATTRIBUTE;

@Component
public class CustomSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserService userService;

    public CustomSuccessHandler(UserService userDetailsService) {
        this.userService = userDetailsService;
    }


    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request,
                                        final HttpServletResponse response,
                                        final Authentication authentication)
            throws IOException, ServletException {

        super.onAuthenticationSuccess(request, response, authentication);
        String username = authentication.getName();
        UserBean userbean = userService.getUserByUsername(username);
        request.getSession().setAttribute(USER_SESSION_ATTRIBUTE, userbean);
    }
}
