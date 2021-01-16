package com.ceruleansource.SmoothieWebsite.frontend.MainView;

import com.ceruleansource.SmoothieWebsite.backend.Authentication.UserSession;
import com.ceruleansource.SmoothieWebsite.frontend.AboutView;
import com.ceruleansource.SmoothieWebsite.frontend.CaloriesBurnoutPlanView;
import com.ceruleansource.SmoothieWebsite.frontend.CreateSmoothieView.CreateSmoothieView;
import com.ceruleansource.SmoothieWebsite.frontend.ForumView;
import com.ceruleansource.SmoothieWebsite.frontend.HomeView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.RouterLink;

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
//@PWA(name = "Smoothie Website Application",
//        shortName = "Smoothie Web",
//        startPath = "home",
//        description = "This is a smoothie website application.")
@CssImport("./src/styles/views/main/main-view.css")
public class MainView extends AppLayout implements AfterNavigationObserver {

    private static Tabs mainMenu;
    private AccountDropDown accountDropDown;
    private final Button logoutButton;
    private final Button loginButton;

    /**
     * Creates a new MainView.
     */
    public MainView() {
        accountDropDown = new AccountDropDown();
        mainMenu = createMenuTabs();
        mainMenu.setOrientation(Tabs.Orientation.HORIZONTAL);
        mainMenu.setId("main-menu");
        loginButton = new Button("Login");
        loginButton.setThemeName("tertiary");

        logoutButton = new Button("Logout", new Icon(VaadinIcon.ARROW_RIGHT));
        logoutButton.setVisible(false);

        loginButton.addClickListener(e -> {
            if (UserSession.isLoggedIn()) {
                loginButton.setVisible(false);
                logoutButton.setVisible(true);
                Notification.show("You're already logged in!").addThemeVariants(NotificationVariant.LUMO_PRIMARY);
            } else {
                getUI().ifPresent(ui -> ui.navigate("login"));
            }
        });

        logoutButton.addClickListener(e -> {
            if (UserSession.isLoggedIn()) {
                UserSession.logout();
                loginButton.setVisible(true);
                logoutButton.setVisible(false);
                if (!getUI().equals(HomeView.class)) {
                    getUI().ifPresent(ui -> ui.navigate("login"));
                }
            } else {
                Notification.show("You're not logged in!").addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
        mainMenu.getStyle().set("padding", "5px");
        mainMenu.getStyle().set("padding-right", "10px");
        mainMenu.getStyle().set("align-self", "stretch");
        Div div = new Div(loginButton, accountDropDown);
        div.setId("navbar-right-div");

        loginButton.getStyle().set("margin", "5px");
        mainMenu.add(div);
        mainMenu.setSizeFull();
        addToNavbar(true, mainMenu);
    }

    private VerticalLayout createTopBar(AccountDropDown accountDropDown, Tabs mainMenu) {
        VerticalLayout layout = new VerticalLayout();
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setPadding(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.add(accountDropDown, mainMenu);
        return layout;
    }

    private static Tabs createMenuTabs() {
        Tabs tabs = new Tabs();
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

        tabs.forEach(tab -> {
            tab.setId("tab");
        });
        return tabs.toArray(new Tab[tabs.size()]);
    }

    private static Tab createTab(VaadinIcon icon, String title, Class<? extends Component> viewClass) {
        final Tab tab = new Tab();
        tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        RouterLink routerLink = new RouterLink(null, viewClass);
        routerLink.add(icon.create());
        routerLink.add(new Span(title));
        tab.add(routerLink);
        ComponentUtil.setData(tab, Class.class, viewClass);
        return tab;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        getTabForComponent(getContent()).ifPresent(mainMenu::setSelectedTab);
    }

    private Optional<Tab> getTabForComponent(Component component) {
        return mainMenu.getChildren().filter(tab -> ComponentUtil.getData(tab, Class.class).equals(component.getClass()))
                .findFirst().map(Tab.class::cast);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        if (UserSession.isLoggedIn()) {
            accountDropDown.setVisible(true);
//            logoutButton.setVisible(true);
            loginButton.setVisible(false);
            accountDropDown.setVisible(true);
        } else {
            accountDropDown.setVisible(false);
        }
//        System.out.println(accountDropDown.isVisible());
    }
}