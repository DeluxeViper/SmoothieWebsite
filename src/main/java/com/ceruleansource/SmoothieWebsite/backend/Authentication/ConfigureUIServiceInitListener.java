package com.ceruleansource.SmoothieWebsite.backend.Authentication;

import com.ceruleansource.SmoothieWebsite.UI.Components.OfflineBanner;
import com.ceruleansource.SmoothieWebsite.UI.LoginView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

/**
 * Adds before enter listener to check access to views.
 * Adds the Offline banner.
 *
 */
@Component
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {

    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addUIInitListener(uiEvent -> {
            final UI ui = uiEvent.getUI();
            ui.add(new OfflineBanner());
            ui.addBeforeEnterListener(this::beforeEnter);
        });
    }

    /**
     * Reroutes the user if she is not authorized to access the view.
     *
     * @param event
     *            before navigation event with event details
     */
    private void beforeEnter(BeforeEnterEvent event) {
        final boolean accessGranted = SecurityUtils.isAccessGranted(event.getNavigationTarget());
        if (!accessGranted) {
            if (SecurityUtils.isUserLoggedIn()) {
                event.rerouteToError(AccessDeniedException.class);
            } else {
                event.rerouteTo(LoginView.class);
            }
        }
    }
}
