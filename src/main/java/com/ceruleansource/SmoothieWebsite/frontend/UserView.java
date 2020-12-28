package com.ceruleansource.SmoothieWebsite.frontend;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.security.core.context.SecurityContextHolder;

@Route("user")
public class UserView extends VerticalLayout {
    public UserView() {
        Label label = new Label("Welcome");
        Label name = new Label("");

        name.setText(SecurityContextHolder.getContext().getAuthentication().getName().toString());
        Button logoutBtn = new Button("Logout");

        add(label, name, logoutBtn);
        logoutBtn.addClickListener(e -> {
            SecurityContextHolder.clearContext();
            logoutBtn.getUI().ifPresent(ui -> ui.navigate("login"));
        });
    }

}
