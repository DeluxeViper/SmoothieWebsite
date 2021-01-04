package com.ceruleansource.SmoothieWebsite.frontend;

import com.ceruleansource.SmoothieWebsite.backend.Authentication.SecurityUtils;
import com.ceruleansource.SmoothieWebsite.backend.Authentication.UserSession;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A Designer generated component for the main-view template.
 * <p>
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
//@Tag("main-view")
//@JsModule("./src/views/main-view.js")
public class MainView extends AppLayout {

    private static Tabs mainMenu;
    private static Tabs tabs;
    private Button logoutButton, loginButton;

    @Autowired
    UserSession userSession;

    /**
     * Creates a new MainView.
     */
    public MainView() {
        mainMenu = createMenuTabs();
        mainMenu.setOrientation(Tabs.Orientation.HORIZONTAL);
        loginButton = new Button("Login");
        loginButton.setThemeName("tertiary");
        loginButton.addClickListener(e -> {
            if (userSession.isLoggedIn()){
                Notification.show("You're already logged in!").addThemeVariants(NotificationVariant.LUMO_PRIMARY);
            } else {
                getUI().ifPresent(ui -> { ui.navigate("login");});
            }
        });
        logoutButton = new Button("Logout", new Icon(VaadinIcon.ARROW_RIGHT));
        logoutButton.setVisible(false);

        logoutButton.addClickListener(e -> {
            if (userSession.isLoggedIn()){
                userSession.logout();
//                getUI().ifPresent(ui -> ui.navigate("home"));
                loginButton.setVisible(true);
                logoutButton.setVisible(false);
                if(!getUI().equals(HomeView.class)){
                    getUI().ifPresent(ui-> ui.navigate("login"));
                }
            } else {
                Notification.show("You're not logged in!").addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
        mainMenu.getStyle().set("padding", "5px");
        mainMenu.getStyle().set("padding-right", "10px");
        mainMenu.getStyle().set("align-self", "stretch");
        Div div = new Div(loginButton, logoutButton);
        div.getStyle().set("margin-left", "auto");
        div.getStyle().set("padding", "5px");

        loginButton.getStyle().set("margin", "5px");
        mainMenu.add(div);
        mainMenu.setSizeFull();
        addToNavbar(true, mainMenu);
    }

    private static Tabs createMenuTabs() {
        tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.HORIZONTAL);
        tabs.add(getAvailableTabs());
        return tabs;
    }

    private static Tab[] getAvailableTabs() {
        final List<Tab> tabs = new ArrayList<>(5);

        // Home Tab
        Tab homeTab = createTab(VaadinIcon.HOME, "Home", HomeView.class);
        tabs.add(homeTab);

        // Create Tab
        tabs.add(createTab(VaadinIcon.LIGHTBULB, "Create Smoothie", CreateSmoothieView.class));

        // Calories Burnout Plan Tab
        tabs.add(createTab(VaadinIcon.SPARK_LINE, "Calories Burnout Plan", CaloriesBurnoutPlanView.class));

        // Forum Tab
        tabs.add(createTab(VaadinIcon.USERS, "Forum", ForumView.class));

        // About Tab
        tabs.add(createTab(VaadinIcon.INFO, "About", AboutView.class));

        return tabs.toArray(new Tab[tabs.size()]);
    }

    private static Tab createTab(VaadinIcon icon, String title, Class<? extends Component> viewClass) {
        return createTab(populateLink(new RouterLink(null, viewClass), icon, title));
    }

    private static Tab createTab(Component component) {
        final Tab tab = new Tab();
        tab.add(component);
        return tab;
    }

    private static <T extends HasComponents> T populateLink(T a, VaadinIcon icon, String title) {
        a.add(icon.create());
        a.add(title);
        return a;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
//        getTabForComponent(getContent()).ifPresent(mainMenu::setSelectedTab);
        RouteConfiguration configuration = RouteConfiguration.forSessionScope();
        if (configuration.isRouteRegistered(this.getContent().getClass())) {
            String target = configuration.getUrl(this.getContent().getClass());
            Optional<Component> tabToSelect = mainMenu.getChildren().filter(tab -> {
                Component child = tab.getChildren().findFirst().get();
                return child instanceof RouterLink && ((RouterLink) child).getHref().equals(target);
            }).findFirst();
            tabToSelect.ifPresent(tab -> mainMenu.setSelectedTab((Tab) tab));
        } else {
            mainMenu.setSelectedTab(null);
        }
        if (userSession.isLoggedIn()){
            loginButton.setVisible(false);
            logoutButton.setVisible(true);
        } else {
            loginButton.setVisible(true);
            logoutButton.setVisible(false);
        }
    }

    private Optional<Tab> getTabForComponent(Component component) {
        return mainMenu.getChildren().filter(tab -> ComponentUtil.getData(tab, Class.class).equals(component.getClass()))
            .findFirst().map(Tab.class::cast);
    }
}
