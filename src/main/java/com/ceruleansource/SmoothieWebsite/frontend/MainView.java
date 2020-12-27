package com.ceruleansource.SmoothieWebsite.frontend;

import com.ceruleansource.SmoothieWebsite.backend.Models.Smoothie;
import com.ceruleansource.SmoothieWebsite.backend.Models.User;
import com.ceruleansource.SmoothieWebsite.backend.Repositories.IngredientRepository;
import com.ceruleansource.SmoothieWebsite.backend.Repositories.NutritionalInformationRepository;
import com.ceruleansource.SmoothieWebsite.backend.Repositories.SmoothieRepository;
import com.ceruleansource.SmoothieWebsite.backend.Repositories.UserRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;


import java.io.Serializable;

@Route
public class MainView extends VerticalLayout implements Serializable {

    private static final long serialVersionUID = 6529685098267757690L;

    UserRepository userRepository;
    SmoothieRepository smoothieRepository;
    IngredientRepository ingredientRepository;
    NutritionalInformationRepository nutritionalInformationRepository;

    public MainView(UserRepository userRepository, SmoothieRepository smoothieRepository) {
//        this.userRepository = userRepository;
//        this.smoothieRepository = smoothieRepository;

//        Label label = new Label("Smoothie Website");
        Button button = new Button("Click me", e -> Notification.show("Hello World"));
        TextField textField = new TextField("Your Name");
        textField.addValueChangeListener(event -> {
            Notification.show(event.getValue());
        });
        add(button, textField);
//        label.setText(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        System.out.println(userRepository.findAll().toString());
        System.out.println(smoothieRepository.findAll().toString());
    }
}
