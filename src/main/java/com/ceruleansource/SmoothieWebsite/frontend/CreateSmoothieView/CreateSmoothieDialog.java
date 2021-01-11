package com.ceruleansource.SmoothieWebsite.frontend.CreateSmoothieView;

import com.ceruleansource.SmoothieWebsite.backend.Authentication.UserSession;
import com.ceruleansource.SmoothieWebsite.backend.Models.Smoothie;
import com.ceruleansource.SmoothieWebsite.backend.Services.SmoothieService;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.shared.Registration;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@CssImport("./src/styles/views/createsmoothie/create-smoothie-view.css")
public class CreateSmoothieDialog extends Dialog {

    @Autowired
    UserSession userSession;

    @Autowired
    SmoothieService smoothieService;

    private final ComboBox<Smoothie> userSmoothies;

    private Smoothie selectedSmoothie;

    public CreateSmoothieDialog(ComboBox<Smoothie> userSmoothies) {
        this.userSmoothies = userSmoothies;
    }

    @PostConstruct
    public void dialogInitialization() {
//        createSmoothieDialog = initCreateSmoothieDialog();
        TextField smoothieNameField = new TextField("Smoothie Name");
        Button addSmoothieDialogBtn = new Button("Add Smoothie");
        Button cancelDialogBtn = new Button("Cancel");

        addSmoothieDialogBtn.addClickListener(e -> addSmoothieDialogBtnMethod(smoothieNameField));
        cancelDialogBtn.addClickListener(e -> close());
    }

    public void initCreateSmoothieDialog() {
        Dialog createSmoothieDialog = new Dialog();

        TextField smoothieNameField = new TextField("Smoothie Name");
        Button addSmoothieDialogBtn = new Button("Add Smoothie");
        Button cancelDialogBtn = new Button("Cancel");

        addSmoothieDialogBtn.addClickListener(e -> addSmoothieDialogBtnMethod(smoothieNameField));
        cancelDialogBtn.addClickListener(e -> close());

    }

    public void addSmoothieDialogBtnMethod(TextField smoothieNameField) {
        Smoothie newSmoothie = new Smoothie(smoothieNameField.getValue(), userSession.getUser());

        if (smoothieService.saveSmoothie(newSmoothie)) {
            Notification.show("Successfully added " + newSmoothie.getName()).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            userSmoothies.setItems(smoothieService.getSmoothiesForCurrentUser(userSession.getUser()));
            selectedSmoothie = newSmoothie;
            userSmoothies.setValue(selectedSmoothie);

//            refreshGrid();
//            populateForm(null);
            close();
        } else {
            Notification.show("Error! Failed to add " + newSmoothie.getName()).addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    public Smoothie getSelectedSmoothie() {
        return selectedSmoothie;
    }
}
