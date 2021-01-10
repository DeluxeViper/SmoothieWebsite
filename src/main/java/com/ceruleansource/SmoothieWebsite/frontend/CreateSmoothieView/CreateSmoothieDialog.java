package com.ceruleansource.SmoothieWebsite.frontend.CreateSmoothieView;

import com.ceruleansource.SmoothieWebsite.backend.Authentication.UserSession;
import com.ceruleansource.SmoothieWebsite.backend.Models.Smoothie;
import com.ceruleansource.SmoothieWebsite.backend.Services.SmoothieService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class CreateSmoothieDialog extends Dialog {

    @Autowired
    UserSession userSession;

    @Autowired
    SmoothieService smoothieService;

    // Opens a dialog to create smoothies
    private Dialog createSmoothieDialog;

    private ComboBox userSmoothies;

    private Smoothie selectedSmoothie;

    public CreateSmoothieDialog(ComboBox userSmoothies){
        this.userSmoothies = userSmoothies;
    }

    @PostConstruct
    public void dialogInitialization(){
        createSmoothieDialog = initCreateSmoothieDialog();
    }

    public Dialog initCreateSmoothieDialog() {
        Dialog createSmoothieDialog = new Dialog();

        TextField smoothieNameField = new TextField("Smoothie Name");
        Button addSmoothieDialogBtn = new Button("Add Smoothie");
        Button cancelDialogBtn = new Button("Cancel");

        addSmoothieDialogBtn.addClickListener(e -> addSmoothieDialogBtnMethod(createSmoothieDialog, smoothieNameField));
        cancelDialogBtn.addClickListener(e -> createSmoothieDialog.close());

        return createSmoothieDialog;
    }

    public void addSmoothieDialogBtnMethod(Dialog createSmoothieDialog, TextField smoothieNameField) {
        Smoothie newSmoothie = new Smoothie(smoothieNameField.getValue(), userSession.getUser());

        if (smoothieService.saveSmoothie(newSmoothie)) {
            Notification.show("Successfully added " + newSmoothie.getName()).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            userSmoothies.setItems(smoothieService.getSmoothiesForCurrentUser(userSession.getUser()));
            selectedSmoothie = newSmoothie;
            userSmoothies.setValue(selectedSmoothie);

//            refreshGrid();
//            populateForm(null);
            createSmoothieDialog.close();
        } else {
            Notification.show("Error! Failed to add " + newSmoothie.getName()).addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }
}
