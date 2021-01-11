package com.ceruleansource.SmoothieWebsite.frontend.CreateSmoothieView;

import com.ceruleansource.SmoothieWebsite.backend.Authentication.UserSession;
import com.ceruleansource.SmoothieWebsite.backend.Models.Ingredient;
import com.ceruleansource.SmoothieWebsite.backend.Models.Smoothie;
import com.ceruleansource.SmoothieWebsite.backend.Repositories.UserRepository;
import com.ceruleansource.SmoothieWebsite.backend.Services.IngredientService;
import com.ceruleansource.SmoothieWebsite.backend.Services.SmoothieService;
import com.ceruleansource.SmoothieWebsite.frontend.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * A Designer generated component for the create-smoothie-view template.
 * <p>
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
//@Tag("create-smoothie-view")
@PageTitle("Create Smoothie")
//@JsModule("./src/views/create-smoothie-view.js")
@CssImport("./src/styles/views/createsmoothie/create-smoothie-view.css")
@Route(value = "create-smoothie", layout = MainView.class)
public class CreateSmoothieView extends Div {

    private final Grid<Ingredient> ingredientGrid = new Grid<>(Ingredient.class, false);

    // Editor layout fields
    private ComboBox<String> ingredientName;
    private ComboBox<Ingredient> ingredientAmount;
    private H3 nutritionalInfoGrams;
    private H3 nutritionalInfoPercentage;
    private Button ingredientSaveBtn = new Button("Save");
    private Button cancelIngredientBtn = new Button("Cancel");
    private Button removeIngredientBtn = new Button("Remove");

    private BeanValidationBinder<Ingredient> ingredientBinder;

    private Ingredient ingredientToAdd;

    // Current Smoothie in selected
    private Smoothie smoothie;

    // Smoothie Form
    private ComboBox<Smoothie> userSmoothies;
    private Button saveSmoothieBtn = new Button("Save Smoothie");
    private Button createSmoothieBtn = new Button("Create Smoothie");
    private Dialog createSmoothieDialog = new Dialog();
    private Button deleteSmoothieBtn;

    @Autowired
    private SmoothieService smoothieService;

    @Autowired
    UserSession userSession;

    @Autowired
    UserRepository userRepository;

    @Autowired
    public CreateSmoothieView(IngredientService ingredientService) {
        setId("create-smoothie-view");
        saveSmoothieBtn.setId("save-smoothie-button");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout, ingredientService);
        createEditorLayout(splitLayout, ingredientService);

        add(splitLayout);

        // Configure Grid
        ingredientGrid.addColumn(Ingredient::getName).setHeader("Name").setAutoWidth(true);
        ingredientGrid.addColumn(Ingredient::getQuantityTypeAndValue)
                .setHeader("Amount").setAutoWidth(true);
        ingredientGrid.addColumn(Ingredient::getNutritionalInformationGrams)
                .setHeader("Nutritional Info Grams").setAutoWidth(true);
        ingredientGrid.addColumn(Ingredient::getNutritionalInformationPercentage)
                .setHeader("Nutritional Info %").setAutoWidth(true);
        // TODO: Currently fetching all items, should only display the ones added
//        List<Ingredient> list = ingredientService.fetchAll();
        ingredientGrid.setItems();
        ingredientGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        ingredientGrid.setHeightFull();

        // TODO: Configure ingredient grid row selection
        ingredientGrid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Optional<Ingredient> ingredientOptional = ingredientService.getIngredient(event.getValue().getId());
                if (ingredientOptional.isPresent()) {
                    populateForm(ingredientOptional.get());
                    System.out.println("Selected: " + ingredientOptional.get());
                } else {
                    refreshGrid();
                }
            } else {
                clearForm();
            }
        });


        // Binding Fields
        ingredientBinder = new BeanValidationBinder<>(Ingredient.class);

        // Cancelling addition of ingredient
        cancelIngredientBtn.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        // Remove ingredient from smoothie
        removeIngredientBtn.addClickListener(e -> {
            if (ingredientAmount.getValue() != null){
                smoothieService.removeIngredient(smoothie, ingredientAmount.getValue());
                refreshGrid();
                populateForm(null);
                Notification.show(ingredientAmount.getValue().getName() + " removed!");
            } else {
                Notification.show("Please select an ingredient!").addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        // Adding ingredient save button
        ingredientSaveBtn.addClickListener(e -> {
            try {
                if (this.ingredientToAdd == null) {
                    this.ingredientToAdd = new Ingredient();
                }
                ingredientBinder.writeBean(this.ingredientAmount.getValue());
                System.out.println("Adding ingredient to smoothie: " + ingredientAmount.getValue());
                if (smoothie == null){
                    smoothie = new Smoothie("randomSmoothieInit", userSession.getUser());
                    Notification.show("Smoothie not selected so automatically created!").addThemeVariants(NotificationVariant.LUMO_CONTRAST);
                }
                smoothieService.addIngredient(smoothie, ingredientAmount.getValue());
                Notification.show("Added " + ingredientAmount.getValue().getName());
                refreshGrid();
                populateForm(null);
            } catch (ValidationException validationException) {
                validationException.printStackTrace();
                Notification.show("An exception happened while trying to add the ingredient to the smoothie");
            }
        });
    }

    @PostConstruct
    public void init() {

        // Save smoothie button
        saveSmoothieBtn.addClickListener(e -> {
            smoothie = new Smoothie("Smoothie", userSession.getUser());
            smoothieService.saveSmoothie(smoothie);
        });

        // Initializing smoothie selection
        userSmoothies.setItemLabelGenerator(Smoothie::getName);
        Set<Smoothie> smoothieSet = smoothieService.getSmoothiesForCurrentUser(userSession.getUser());
        userSmoothies.setItems(smoothieSet);
        userSmoothies.addValueChangeListener(event -> {
            if (event.getValue() != null){
                smoothie = event.getValue();
                deleteSmoothieBtn.setVisible(true);
//                System.out.println("Event value not null: " + smoothie);
                clearForm();
                refreshGrid();
            } else {
                System.out.println("Event value null");
            }
        });

        // Create Smoothie Dialog init
        TextField smoothieNameField = new TextField("Smoothie Name");

        // Create smoothie button
        createSmoothieBtn.addClickListener(e ->{
            createSmoothieDialog.open();
            smoothieNameField.focus();
        });

        // Create smoothie dialog initialization
        Button addSmoothieBtn = new Button("Add Smoothie", buttonClickEvent -> {
            // TODO: Add smoothie logic
            Smoothie newSmoothie = new Smoothie(smoothieNameField.getValue(), userSession.getUser());
            smoothieService.saveSmoothie(newSmoothie);
            userSmoothies.setItems(smoothieService.getSmoothiesForCurrentUser(userSession.getUser()));
            this.smoothie = newSmoothie;
            userSmoothies.setValue(newSmoothie);
            refreshGrid();
            populateForm(null);
            createSmoothieDialog.close();
        });
        Button cancelAddingSmoothieBtn = new Button("Cancel", event -> {
            createSmoothieDialog.close();
        });

        createSmoothieDialog.add(smoothieNameField, addSmoothieBtn, cancelAddingSmoothieBtn);

        deleteSmoothieBtn.addClickListener(buttonClickEvent -> {
            if (smoothieService.deleteSmoothie(smoothie)){
                Notification.show("Successfully deleted smoothie!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                Set<Smoothie> newSmoothieSet = smoothieService.getSmoothiesForCurrentUser(userSession.getUser());
                if (newSmoothieSet.isEmpty()){
                    smoothie = null;
                    deleteSmoothieBtn.setVisible(false);
                } else {
                    smoothie = newSmoothieSet.iterator().next();
                }
                userSmoothies.setItems(smoothieService.getSmoothiesForCurrentUser(userSession.getUser()));
                userSmoothies.setValue(smoothie);
                populateForm(null);
                refreshGrid();
            }
        });
    }

    private void createEditorLayout(SplitLayout splitLayout, IngredientService ingredientService) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setId("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setId("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();

        ingredientName = new ComboBox<>("Ingredient Name");

        ingredientAmount = new ComboBox<>("Ingredient Amount");

        nutritionalInfoGrams = new H3();
        nutritionalInfoPercentage = new H3();

        List<String> ingredientDistinctNamesList = ingredientService.findAllDistinctNamedIngredients();
        // Populating the form combo boxes
        // Ingredient name
        ingredientName.setLabel("Name");
        ingredientName.setRequired(true);
        ingredientName.setClearButtonVisible(true);
        ingredientName.setItems(ingredientDistinctNamesList);

        // Ingredient amount
        ingredientAmount.setLabel("Amount");
        ingredientAmount.setRequired(true);
        ingredientAmount.setClearButtonVisible(true);
        ingredientAmount.setItemLabelGenerator(Ingredient::getQuantityTypeAndValue);
        ingredientName.addValueChangeListener(event -> {
            if (event.getValue() == null) {
                // No ingredient selected
                ingredientAmount.setItems();
            } else {
                // Ingredient selected
                System.out.println("Selected: " + event.getValue());
                List<Ingredient> ingredientAmountList = ingredientService.findAllIngredientsWithName(event.getValue());
                ingredientAmount.setItems(ingredientAmountList);
            }
        });

        // Nutrition Info grams & Percentage
        ingredientAmount.addValueChangeListener(event -> {
            if (event.getValue() == null) {
                // No ingredient amount selected
                nutritionalInfoGrams.setText("Nutritional Info Grams: Empty");
                nutritionalInfoPercentage.setText("Nutritional Info %: Empty");
            } else {
                // Ingredient amount selected
                nutritionalInfoGrams.setText(event.getValue().getNutritionalInformationGrams().toString());
                nutritionalInfoPercentage.setText(event.getValue().getNutritionalInformationPercentage().toString());
            }
        });

        // Setting form layout
        Component[] fields = new Component[]{ingredientName, ingredientAmount, nutritionalInfoGrams, nutritionalInfoPercentage};
//        for (Component field : fields) {
//            ((HasStyle) field).addClassName("");
//        }
        formLayout.add(fields);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setId("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(true);
        ingredientSaveBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancelIngredientBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        removeIngredientBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        buttonLayout.add(ingredientSaveBtn, cancelIngredientBtn, removeIngredientBtn);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout, IngredientService ingredientService) {
        IngredientGridDiv ingredientGridDiv = new IngredientGridDiv(ingredientService);


//        Div wrapper = new Div();
//        wrapper.setId("grid-wrapper");
//        wrapper.setWidthFull();
//        userSmoothies = new ComboBox<>("Your Smoothies");
//
//        // Delete smoothie button init
//        deleteSmoothieBtn = new Button("Delete Smoothie");
//        deleteSmoothieBtn.addThemeVariants(ButtonVariant.LUMO_ERROR);
//        if (smoothie == null){
//            deleteSmoothieBtn.setVisible(false);
//        }
//
//        // Header
//        VerticalLayout verticalLayout = new VerticalLayout();
//        verticalLayout.setHeightFull();
//        H2 title = new H2("Craft Your Recipe");
//        H3 subheader = new H3("Add your Ingredients");
//        HorizontalLayout horizontalLayout = new HorizontalLayout();
//        horizontalLayout.setAlignItems(FlexComponent.Alignment.CENTER);
//        horizontalLayout.add(userSmoothies, createSmoothieBtn, deleteSmoothieBtn);
//        verticalLayout.add(title, subheader, horizontalLayout, ingredientGrid);
//
//        wrapper.add(verticalLayout);
        splitLayout.addToPrimary(ingredientGridDiv);
    }

    /**
     * Clears the form for adding ingredient
     */
    private void clearForm() {
        populateForm(null);
    }

    /**
     * Fills in the ingredient form
     * @param ingredient - populates form depending on the ingredient selected
     *                   - or populates the form null if no ingredient is passed in
     */
    private void populateForm(Ingredient ingredient) {
        ingredientBinder.readBean(ingredient);

        if (ingredient != null){
            ingredientName.setValue(ingredient.getName());
            ingredientAmount.setValue(ingredient);
            nutritionalInfoGrams.setText(ingredient.getNutritionalInformationGrams().toString());
            nutritionalInfoPercentage.setText(ingredient.getNutritionalInformationPercentage().toString());
        }
    }

    /**
     * Refreshes ingredients in item grid
     */
    private void refreshGrid() {
        ingredientGrid.select(null);
        if (smoothie != null){
            ingredientGrid.setItems(smoothie.getIngredients());
        }
//        userSmoothies.setValue(smoothie);
//        userSmoothies.setItems(smoothieService.getSmoothiesForCurrentUser(userSession.getUser()));
    }
}
