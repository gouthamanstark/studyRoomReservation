package com.computerpool.library.Security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                                            
        // Determine the default success URL based on the user's authorities (roles)
        String defaultTargetUrl = "/"; // Default URL for common users

        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("Admin")) {
                defaultTargetUrl = "/admin/home"; // URL for users with the "ROLE_ADMIN" authority
                break;
            }

            else if(authority.getAuthority().equals("Student")){
                defaultTargetUrl = "/student/home"; // URL for users with the "ROLE_ADMIN" authority
                break;
            }
            // Add more conditions for other role-based URLs if needed
        }

        redirectStrategy.sendRedirect(request, response, defaultTargetUrl);
    }

    
}
