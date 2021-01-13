package com.ceruleansource.SmoothieWebsite.frontend;

import com.ceruleansource.SmoothieWebsite.backend.Authentication.SecurityUtils;
import com.ceruleansource.SmoothieWebsite.backend.Authentication.UserSession;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.PostConstruct;

/**
 * A Designer generated component for the home-view template.
 * <p>
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("home-view")
@JsModule("./src/views/home-view.js")
@Route(value = "home", layout = MainView.class)
public class HomeView extends PolymerTemplate<HomeView.HomeViewModel> {

    @Id("createSmoothieButton")
    private Button createSmoothieButton;
    @Id("loginHomeButton")
    private Button loginHomeButton;

    @Autowired
    UserSession userSession;

    /**
     * Creates a new HomeView.
     */
    public HomeView() {
        // You can initialise any data required for the connected UI components here.
        createSmoothieButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("create-smoothie")));

        loginHomeButton.addClickListener(e -> {
            if (SecurityUtils.isUserLoggedIn()) {
                Notification.show("You're already logged in!").addThemeVariants(NotificationVariant.LUMO_PRIMARY);
            } else {
                getUI().ifPresent(ui -> {
                    ui.navigate("login");
                });
            }
        });

    }

    /**
     * This model binds properties between HomeView and home-view
     */
    public interface HomeViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
