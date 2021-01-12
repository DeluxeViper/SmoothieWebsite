package com.ceruleansource.SmoothieWebsite.backend.Authentication;

import com.ceruleansource.SmoothieWebsite.backend.Models.user.MyUserDetails;
import com.ceruleansource.SmoothieWebsite.backend.Models.user.User;
import com.ceruleansource.SmoothieWebsite.backend.Repositories.UserRepository;
import com.ceruleansource.SmoothieWebsite.backend.Services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

@Component
@SessionScope
public class UserSession {

    @Autowired
    MyUserDetailsService userDetailsService;

    /**
     * Returns the user object of the logged in user
     *
     * @return User object based on the type of user logging in
     */
    @Transactional
    public User getUser() {
        if (isLoggedIn()) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof OAuth2AuthenticationToken) {
                // Google user login
                OAuth2AuthenticatedPrincipal principal = (OAuth2AuthenticatedPrincipal) authentication.getPrincipal();
                return userDetailsService.getUserByEmailAndRole(principal.getAttribute("email"), "GOOGLE_USER");
            } else if (authentication instanceof UsernamePasswordAuthenticationToken) {
                // Normal use login
                MyUserDetails userDetails = (MyUserDetails) userDetailsService.loadUserByUsername(authentication.getName());
                return userDetails.getUser();
            } else if (authentication instanceof AnonymousAuthenticationToken) {
                // Anonymous user login
                return new User("guest", "guest",
                        "guest", "confidential", "0", true, "GOOGLE_USER");
            }
        }
        return null;
    }

    /**
     * Checks if the user is logged in.
     *
     * @return true if the user is logged in. False otherwise.
     */
    public static boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null
                && !(authentication instanceof AnonymousAuthenticationToken)
                && authentication.isAuthenticated();
    }

    /**
     * logs out user
     */
    public void logout() {
        SecurityContextHolder.clearContext();
    }
}
