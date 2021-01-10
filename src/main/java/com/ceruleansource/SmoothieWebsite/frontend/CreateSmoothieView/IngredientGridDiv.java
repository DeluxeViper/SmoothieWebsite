package com.ceruleansource.SmoothieWebsite.frontend.CreateSmoothieView;

import com.ceruleansource.SmoothieWebsite.backend.Authentication.UserSession;
import com.ceruleansource.SmoothieWebsite.backend.Models.Ingredient;
import com.ceruleansource.SmoothieWebsite.backend.Models.Smoothie;
import com.ceruleansource.SmoothieWebsite.backend.Services.SmoothieService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class IngredientGridDiv extends Div {

    @Autowired
    UserSession userSession;

    @Autowired
    SmoothieService smoothieService;

    private Grid<Ingredient> ingredientGrid = new Grid<Ingredient>(Ingredient.class, false);

    // Lists all the smoothies in the current user
    private ComboBox<Smoothie> userSmoothies;

    // Current smoothie that is selected from userSmoothies
    private Smoothie selectedSmoothie;

    // Opens a dialog to create smoothies
    private Dialog createSmoothieDialog;

    public IngredientGridDiv() {
        setId("grid-wrapper");
        setWidthFull();

        VerticalLayout overallVLayout = new VerticalLayout();
        overallVLayout.setHeightFull();

        VerticalLayout headerLayout = initHeaderLayout();

        overallVLayout.add(headerLayout, ingredientGrid);
    }

    @PostConstruct
    public void initialization(){
        createSmoothieDialog = initCreateSmoothieDialog();
    }

    public VerticalLayout initHeaderLayout() {
        VerticalLayout vLayout = new VerticalLayout();

        H2 title = new H2("Craft Your Recipe");
        H3 subheader = new H3("Add your Ingredients");
        HorizontalLayout addSmoothieHLayout = initSmoothieFunctionsLayout();

        vLayout.add(title, subheader, addSmoothieHLayout);

        return vLayout;
    }

    public HorizontalLayout initSmoothieFunctionsLayout() {
        HorizontalLayout hLayout = new HorizontalLayout();
        userSmoothies = new ComboBox("Your Smoothies");
        userSmoothies.setItemLabelGenerator(Smoothie::getName);

        Button createSmoothieBtn = new Button("Create Smoothie");
        Button deleteSmoothieBtn = new Button("Delete Smoothie");
        deleteSmoothieBtn.addThemeVariants(ButtonVariant.LUMO_ERROR);
        createSmoothieBtn.addClickListener(e-> createSmoothieDialog.open());

        hLayout.add(userSmoothies, createSmoothieBtn, deleteSmoothieBtn);

        return hLayout;
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

            refreshGrid();
            populateForm(null);
            createSmoothieDialog.close();
        } else {
            Notification.show("Error! Failed to add " + newSmoothie.getName()).addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    private void refreshGrid(){
        ingredientGrid.select(null);
        if (selectedSmoothie != null){
            ingredientGrid.setItems(selectedSmoothie.getIngredients());
        }
    }

    private void populateForm(Ingredient ingredient){
        // TODO: Implement logic
        if (ingredient != null){

        } else {

        }
    }
}
