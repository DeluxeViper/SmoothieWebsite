package com.ceruleansource.SmoothieWebsite.UI.CreateSmoothieView;

import com.ceruleansource.SmoothieWebsite.backend.Authentication.UserSession;
import com.ceruleansource.SmoothieWebsite.backend.Models.Smoothie;
import com.ceruleansource.SmoothieWebsite.backend.Services.SmoothieService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Autowired;

@CssImport("./src/styles/views/createsmoothie/create-smoothie-view.css")
public class CreateSmoothieDialog extends Dialog {

    private final ComboBox<Smoothie> userSmoothies;

    private Smoothie selectedSmoothie;

    private final TextField smoothieNameField = new TextField("Smoothie Name");

    @Autowired
    public CreateSmoothieDialog(ComboBox<Smoothie> userSmoothies, UserSession userSession, SmoothieService smoothieService) {
        this.userSmoothies = userSmoothies;
        initCreateSmoothieDialog(userSession, smoothieService);
    }

    public void initCreateSmoothieDialog(UserSession userSession, SmoothieService smoothieService) {
        smoothieNameField.setMinLength(1);
        smoothieNameField.setMaxLength(30);
        Button addSmoothieDialogBtn = new Button("Add Smoothie");
        Button cancelDialogBtn = new Button("Cancel");

        addSmoothieDialogBtn.addClickListener(e -> addSmoothieDialogBtnMethod(smoothieNameField, userSession, smoothieService));
        cancelDialogBtn.addClickListener(e -> close());
        add(smoothieNameField, addSmoothieDialogBtn, cancelDialogBtn);
    }

    public void addSmoothieDialogBtnMethod(TextField smoothieNameField, UserSession userSession, SmoothieService smoothieService) {
        if (smoothieNameField.getValue().equals("")){
            Notification.show("Error. Smoothie name cannot be empty!").addThemeVariants(NotificationVariant.LUMO_ERROR);
        } else if (smoothieService.checkIfSmoothieNameisTaken(smoothieNameField.getValue(), userSession.getUser())) {
            Notification.show("Error. Smoothie name already exists!").addThemeVariants(NotificationVariant.LUMO_ERROR);
        } else {
            Smoothie newSmoothie = new Smoothie(smoothieNameField.getValue(), userSession.getUser());
            smoothieService.saveSmoothie(newSmoothie);
            Smoothie savedSmoothie = smoothieService.getSmoothie(smoothieNameField.getValue(), userSession.getUser());
            if (savedSmoothie != null) {
                Notification.show("Successfully added " + savedSmoothie.getName()).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                userSmoothies.setItems(smoothieService.getSmoothiesForCurrentUser(userSession.getUser()));
                selectedSmoothie = savedSmoothie;
                userSmoothies.setValue(selectedSmoothie);
                close();
            } else {
                Notification.show("Error! Failed to add " + newSmoothie.getName()).addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        }
    }

    public Smoothie getSelectedSmoothie() {
        return selectedSmoothie;
    }
}
