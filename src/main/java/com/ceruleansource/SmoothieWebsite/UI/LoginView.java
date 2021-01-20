package com.ceruleansource.SmoothieWebsite.UI;

import com.ceruleansource.SmoothieWebsite.backend.Authentication.UserSession;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.ui.Transport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.Collections;

@PageTitle("Login")
@Route("login")
@Push(transport = Transport.LONG_POLLING)
public class LoginView extends VerticalLayout
        implements BeforeEnterObserver {
    @Autowired
    UserSession userSession;

    private Button registerButton;
    private LoginForm loginForm;
    private static final String googleAuthUrl = "/oauth2/authorization/google";

    @Value("${spring.security.oauth2.client.registration.google.client-id")
    private String clientkey;

    public LoginView() {
        loginForm = new LoginForm();
        H2 title = new H2("Smoothie Website");
        registerButton = new Button("Register");
        loginForm.setAction("login");
        add(title, loginForm, registerButton);
        setAlignItems(Alignment.CENTER);

        registerButton.addClickListener(e -> {
            registerButton.getUI().ifPresent(ui -> ui.navigate("register"));
        });
    }

    @PostConstruct
    public void initView() {
        // Check that oauth keys are present
        if (clientkey == null || clientkey.isEmpty() || clientkey.length() < 16) {
            Paragraph text = new Paragraph("Could not find OAuth client key in application.properties. ");
            add(text);
        } else {
            Anchor googleLoginButton = new Anchor(googleAuthUrl, "Login with Google");
            add(googleLoginButton);
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        // inform the user about an authentication error
        // (yes, the API for resolving query parameters is annoying...)
//        System.out.println("Parameters: " + event.getLocation().getQueryParameters().getParameters());
        if (!event.getLocation().getQueryParameters().getParameters().getOrDefault("error", Collections.emptyList()).isEmpty()) {
            loginForm.setError(true);
        }
    }
}
