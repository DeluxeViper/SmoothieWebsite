package com.ceruleansource.SmoothieWebsite.UI;

import com.ceruleansource.SmoothieWebsite.backend.Authentication.SecurityUtils;
import com.ceruleansource.SmoothieWebsite.backend.Authentication.UserSession;
import com.ceruleansource.SmoothieWebsite.UI.MainView.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.router.*;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A Designer generated component for the home-view template.
 * <p>
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("home-view")
@JsModule("./src/views/home-view.js")
@CssImport("./src/styles/views/homeview/home-view-styles.css")
@Route(value = "home", layout = MainView.class)
public class HomeView extends PolymerTemplate<HomeView.HomeViewModel> implements AfterNavigationObserver {
    @Id("createSmoothieButton")
    private Button createSmoothieButton;
    @Id("loginHomeButton")
    private Button loginHomeButton;

    @Autowired
    UserSession userSession;
    @Id("welcomeDiv")
    private Div welcomeDiv;

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
                loginHomeButton.getUI().ifPresent(ui -> ui.navigate("login"));
            }
        });
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        if (UserSession.isLoggedIn()){
            loginHomeButton.setVisible(false);
        }
    }

    /**
     * This model binds properties between HomeView and home-view
     */
    public interface HomeViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
