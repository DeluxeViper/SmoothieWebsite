package com.ceruleansource.SmoothieWebsite.frontend.MainView;

import com.ceruleansource.SmoothieWebsite.backend.Authentication.UserSession;
import com.ceruleansource.SmoothieWebsite.frontend.HomeView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

//@CssImport("./styles/views/main/main-view.css")
//@CssImport(value = "./styles/views/main/main-view.css", themeFor = "vaadin-app-layout")
@CssImport("./src/styles/views/main/main-view.css")
public class AccountDropDown extends Div {

    public AccountDropDown() {
        setId("header");
        Image userAvatar = new Image("images/user.svg", "Avatar");

        ContextMenu contextMenu = new ContextMenu();
        contextMenu.setOpenOnClick(true);
        contextMenu.setEnabled(true);
        contextMenu.addItem("Account Settings", e -> accountSettingsOnClick());
        contextMenu.addItem("My Smoothies", e -> mySmoothiesOnClick());
        contextMenu.addItem("Logout", e -> logoutOnClick());

        contextMenu.setTarget(this);
        userAvatar.setId("avatar");
        add(userAvatar);
    }

    private void mySmoothiesOnClick() {
    }

    private void accountSettingsOnClick() {
    }

    private void logoutOnClick() {
        if (UserSession.isLoggedIn()) {
            UserSession.logout();
            if (!getUI().equals(HomeView.class)) {
                getUI().ifPresent(ui -> ui.navigate("login"));
            }
        } else {
            Notification.show("You're not logged in!").addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }
}
