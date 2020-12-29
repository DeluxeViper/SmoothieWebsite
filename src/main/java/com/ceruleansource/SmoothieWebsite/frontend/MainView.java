package com.ceruleansource.SmoothieWebsite.frontend;

import com.ceruleansource.SmoothieWebsite.backend.Repositories.IngredientRepository;
import com.ceruleansource.SmoothieWebsite.backend.Repositories.NutritionalInformationRepository;
import com.ceruleansource.SmoothieWebsite.backend.Repositories.SmoothieRepository;
import com.ceruleansource.SmoothieWebsite.backend.Repositories.UserRepository;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// TODO: Create offline html page
@PWA(name = "Smoothie Website", shortName =  "Smoothie Website",
        startPath = "login",
        offlinePath = "offline-page.html",
        enableInstallPrompt = false)
public class MainView extends AppLayout implements Serializable {

    private static final long serialVersionUID = 6529685098267757690L;

    UserRepository userRepository;
    SmoothieRepository smoothieRepository;
    IngredientRepository ingredientRepository;
    NutritionalInformationRepository nutritionalInformationRepository;

    private final Tabs mainMenu;

    public MainView(UserRepository userRepository, SmoothieRepository smoothieRepository) {
        mainMenu = createMenuTabs();
////        this.userRepository = userRepository;
////        this.smoothieRepository = smoothieRepository;
//
////        Label label = new Label("Smoothie Website");
//        Button button = new Button("Click me", e -> Notification.show("Hello World"));
//        TextField textField = new TextField("Your Name");
//        textField.addValueChangeListener(event -> {
//            Notification.show(event.getValue());
//        });
//        add(button, textField);
////        label.setText(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
//        System.out.println(userRepository.findAll().toString());
//        System.out.println(smoothieRepository.findAll().toString());
    }

    private static Tabs createMenuTabs() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.HORIZONTAL);
        tabs.add(getAvailableTabs());
        return tabs;
    }

    private static Tab[] getAvailableTabs() {
        final List<Tab> tabs = new ArrayList<>(5);

        // Home Tab
        tabs.add(createTab(VaadinIcon.HOME, "Home", HomeView.class));

        // Create Tab
        tabs.add(createTab(VaadinIcon.LIGHTBULB, "Create", CreateSmoothieView.class));

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

    private static Tab createTab(Component component){
        final Tab tab = new Tab();
        tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        tab.add(component);
        return tab;
    }
    private static <T extends HasComponents> T populateLink(T a, VaadinIcon icon, String title) {
        a.add(icon.create());
        a.add(title);
        return a;
    }
}
