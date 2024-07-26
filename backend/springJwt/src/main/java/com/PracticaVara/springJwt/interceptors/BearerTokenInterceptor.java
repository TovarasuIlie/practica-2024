package com.PracticaVara.springJwt.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class BearerTokenInterceptor implements HandlerInterceptor {
    private BearerTokenWrapper tokenWrapper;

    public BearerTokenInterceptor(BearerTokenWrapper tokenWrapper) {
        this.tokenWrapper = tokenWrapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String authorizationHeaderValue = request.getHeader("Authorization");
        if (authorizationHeaderValue != null && authorizationHeaderValue.startsWith("Bearer")) {
            String token = authorizationHeaderValue.substring(7, authorizationHeaderValue.length());
            if (tokenWrapper.getToken() == null || !token.equals(tokenWrapper.getToken())) {
                tokenWrapper.setToken(token);
            }
        }
        return true;
    }
}
