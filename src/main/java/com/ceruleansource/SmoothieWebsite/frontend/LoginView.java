package com.ceruleansource.SmoothieWebsite.frontend;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.ui.Transport;

import java.io.Serializable;

@PageTitle("Login")
@Route("login")
@Push(transport= Transport.LONG_POLLING)
public class LoginView extends VerticalLayout
//        implements AfterNavigationListener, BeforeEnterObserver
        implements Serializable
//        , LocaleChangeObserver
{
    private static final long serialVersionUID = 5225695098267758100L;

    private EmailField emailField;
    private PasswordField passwordField;
    private Button loginButton, registerButton;
    private LoginForm loginForm;

    public LoginView() {
        loginForm = new LoginForm();
        H2 title = new H2("Smoothie Website Login");
        emailField = new EmailField("Email");
        passwordField = new PasswordField("Password");
        loginButton = new Button("Sign in");
        registerButton = new Button("Register");
//        add(title, emailField, passwordField, loginButton, registerButton);
        loginForm.setAction("login");
        add(loginForm, registerButton);

        setAlignItems(Alignment.CENTER);
        setSizeFull();

        registerButton.addClickListener(e -> {
            registerButton.getUI().ifPresent(ui -> ui.navigate("register"));
        });
//        login.setI18n(createTranslatedI18N());
//        login.setAction("login");
//        login.setOpened(true);
//        login.setTitle("Login Overlay");
//        login.setDescription("");
    }

//    private LoginI18n createTranslatedI18N() {
//        LoginI18n i18n = LoginI18n.createDefault();
//        i18n.setHeader(new LoginI18n.Header());
//        i18n.setForm(new LoginI18n.Form());
//        i18n.getHeader().setTitle("Smoothie Website");
//        i18n.getHeader().setDescription("Login");
//        i18n.setAdditionalInformation(null);
//        i18n.setForm(new LoginI18n.Form());
//        i18n.getForm().setSubmit("Sign In");
//        i18n.getForm().setUsername("Email");
//        i18n.getForm().setPassword("Password");
//        return i18n;
//    }

//    @Override
//    public void localeChange(LocaleChangeEvent localeChangeEvent) {
//        if (localeChangeEvent.getLocale().getLanguage().equals("ar")) {
//            localeChangeEvent.getUI().setDirection(Direction.RIGHT_TO_LEFT);
//        } else {
//            localeChangeEvent.getUI().setDirection(Direction.LEFT_TO_RIGHT);
//        }
//    }

//    @Override
//    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
//        login.setError(afterNavigationEvent.getLocation().getQueryParameters()
//        .getParameters().containsKey("error"));
//    }
//
//    @Override
//    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
//        if (SecurityUtils.isUserLoggedIn()){
//            beforeEnterEvent.forwardTo(MainView.class);
//        } else {
//            login.setOpened(true);
//        }
//    }
}
