package com.ceruleansource.SmoothieWebsite.backend.Authentication;

import com.ceruleansource.SmoothieWebsite.UI.Error.AccessDeniedView;
import com.ceruleansource.SmoothieWebsite.UI.Error.CustomRouteNotFoundError;
import com.ceruleansource.SmoothieWebsite.UI.ForumView.ForumView;
import com.ceruleansource.SmoothieWebsite.UI.HomeView;
import com.ceruleansource.SmoothieWebsite.UI.LoginView;
import com.ceruleansource.SmoothieWebsite.UI.MainView.MainView;
import com.ceruleansource.SmoothieWebsite.UI.RegisterView;
import com.vaadin.flow.server.ServletHelper;
import com.vaadin.flow.shared.ApplicationConstants;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class SecurityUtils {

    private SecurityUtils(){
    }

    /**
     * Gets the email of the currently signed in user.
     *
     * @return the email of the current user or null if the user
     *         has not signed in
     */
    public static String getEmail(){
        SecurityContext context = SecurityContextHolder.getContext();
        Object principal = context.getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            UserDetails userDetails = (UserDetails) context.getAuthentication().getPrincipal();
            return userDetails.getUsername();
        }
        // Anonymous or no authentication.
        return null;
    }

    /**
     * Checks if access is granted for the current user for the given secured view,
     * defined by the view class.
     *
     * @param securedClass View class
     * @return true if access is granted, false otherwise.
     */
    public static boolean isAccessGranted(Class<?> securedClass) {
        final boolean publicView = LoginView.class.equals(securedClass)
                || AccessDeniedView.class.equals(securedClass)
                || CustomRouteNotFoundError.class.equals(securedClass)
                || RegisterView.class.equals(securedClass)
                || HomeView.class.equals(securedClass)
                || ForumView.class.equals(securedClass)
                || MainView.class.equals(securedClass);

        // Always allow access to public views
        if (publicView) {
            return true;
        }

        Authentication userAuthentication = SecurityContextHolder.getContext().getAuthentication();

        // All other views require authentication
        if (!isUserLoggedIn()) {
            return false;
        }

        // Allow if no roles are required.
        Secured secured = AnnotationUtils.findAnnotation(securedClass, Secured.class);
        if (secured == null) {
            return true;
        }

        List<String> allowedRoles = Arrays.asList(secured.value());
        return userAuthentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .anyMatch(allowedRoles::contains);
    }

    /**
     * Checks if the user is logged in.
     *
     * @return true if the user is logged in. False otherwise.
     */
    public static boolean isUserLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null
                && !(authentication instanceof AnonymousAuthenticationToken)
                && authentication.isAuthenticated();
    }

    /**
     * Tests if the request is an internal framework request. The test consists of
     * checking if the request parameter is present and if its value is consistent
     * with any of the request types know.
     *
     * @param request
     *            {@link HttpServletRequest}
     * @return true if is an internal framework request. False otherwise.
     */
    static boolean isFrameworkInternalRequest(HttpServletRequest request) {
        final String parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
        return parameterValue != null
                && Stream.of(ServletHelper.RequestType.values()).anyMatch(r -> r.getIdentifier().equals(parameterValue));
    }
}
