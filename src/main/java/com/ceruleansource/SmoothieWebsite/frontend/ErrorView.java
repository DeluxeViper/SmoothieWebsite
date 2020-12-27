package com.ceruleansource.SmoothieWebsite.frontend;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.awt.*;

@Route("error")
public class ErrorView extends VerticalLayout {
    public ErrorView(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Label errLabel = new Label();

        if (auth != null){
            errLabel.setText("User: " + auth.getName() + " attempted to access the protected URL : "
                + "auth : " + auth.isAuthenticated() + ", Role: " + auth.getAuthorities());
        }

    }
}
