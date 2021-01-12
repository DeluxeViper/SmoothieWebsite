package com.ceruleansource.SmoothieWebsite.frontend.CreateSmoothieView;

import com.ceruleansource.SmoothieWebsite.backend.Authentication.UserSession;
import com.ceruleansource.SmoothieWebsite.backend.Models.Ingredient;
import com.ceruleansource.SmoothieWebsite.backend.Models.Smoothie;
import com.ceruleansource.SmoothieWebsite.backend.Services.IngredientService;
import com.ceruleansource.SmoothieWebsite.backend.Services.SmoothieService;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@CssImport("./src/styles/views/createsmoothie/create-smoothie-view.css")
public class IngredientGridDiv extends Div {

    private final Grid<Ingredient> ingredientGrid = new Grid<>(Ingredient.class, false);

    // Lists all the smoothies in the current user
    private ComboBox<Smoothie> userSmoothies;

    // Current smoothie that is selected from userSmoothies
    private Smoothie selectedSmoothie;

    private CreateSmoothieDialog smoothieDialog;

    private Button deleteSmoothieBtn;

    @Autowired
    public IngredientGridDiv(IngredientService ingredientService, UserSession userSession, SmoothieService smoothieService) {
        setId("grid-wrapper");
        setWidthFull();

        VerticalLayout overallVLayout = new VerticalLayout();
        overallVLayout.setHeightFull();

        VerticalLayout headerLayout = initHeaderLayout(userSession, smoothieService);

        initializeIngredientGrid(ingredientService);

        overallVLayout.add(headerLayout, ingredientGrid);
        add(overallVLayout);
    }

    public VerticalLayout initHeaderLayout(UserSession userSession, SmoothieService smoothieService) {
        VerticalLayout vLayout = new VerticalLayout();

        H2 title = new H2("Craft Your Recipe");
        H3 subheader = new H3("Add your Ingredients");
        HorizontalLayout addSmoothieHLayout = initSmoothieFunctionsLayout(userSession, smoothieService);

        vLayout.add(title, subheader, addSmoothieHLayout);

        return vLayout;
    }

    public HorizontalLayout initSmoothieFunctionsLayout(UserSession userSession, SmoothieService smoothieService) {
        HorizontalLayout hLayout = new HorizontalLayout();

        deleteSmoothieBtn = new Button("Delete Smoothie");
        deleteSmoothieBtn.addThemeVariants(ButtonVariant.LUMO_ERROR);
        deleteSmoothieBtn.addClickListener(e -> deleteSmoothieButtonOnClick(userSession, smoothieService));

        userSmoothies = new ComboBox<>("Your Smoothies");
        userSmoothies.setItemLabelGenerator(Smoothie::getName);
        userSmoothies.setItems(smoothieService.getSmoothiesForCurrentUser(userSession.getUser()));
        userSmoothies.addValueChangeListener(this::userSmoothiesValueChangeMethod);

        // Initializing smoothieDialog
        initSmoothieCreateDialog(userSession, smoothieService);

        Button createSmoothieBtn = new Button("Create Smoothie");
        createSmoothieBtn.addClickListener(e-> smoothieDialog.open());

        hLayout.add(userSmoothies, createSmoothieBtn, deleteSmoothieBtn);

        return hLayout;
    }

    private void userSmoothiesValueChangeMethod(AbstractField.ComponentValueChangeEvent<ComboBox<Smoothie>, Smoothie> event) {
        if (event.getValue() != null){
            selectedSmoothie = event.getValue();
            deleteSmoothieBtn.setVisible(true);
            System.out.println("IngredientGridDiv: UserSmoothies currently selected the following smoothie:\n" + selectedSmoothie);
            refreshGrid();
        } else {
            selectedSmoothie = null;
            System.out.println("IngredientGridDiv: Currently user smoothies selected a null smoothie, will do nothing.");
            refreshGrid();
        }
    }

    private void initSmoothieCreateDialog(UserSession userSession, SmoothieService smoothieService) {
        smoothieDialog = new CreateSmoothieDialog(userSmoothies, userSession, smoothieService);
        smoothieDialog.addDetachListener(e -> {
            System.out.println("Dialog detach listener");
            selectedSmoothie = smoothieDialog.getSelectedSmoothie();
            System.out.println("Getting user smoothies value after detachment of dialog: " + userSmoothies.getValue());
        });
    }

    private void initializeIngredientGrid(IngredientService ingredientService) {
        // Configure Grid
        ingredientGrid.addColumn(Ingredient::getName).setHeader("Name").setAutoWidth(true);
        ingredientGrid.addColumn(Ingredient::getQuantityTypeAndValue)
                .setHeader("Amount").setAutoWidth(true);
        ingredientGrid.addColumn(Ingredient::getNutritionalInformationGrams)
                .setHeader("Nutritional Info Grams").setAutoWidth(true);
        ingredientGrid.addColumn(Ingredient::getNutritionalInformationPercentage)
                .setHeader("Nutritional Info %").setAutoWidth(true);

        // Initialize ingredient grid items
        ingredientGrid.setItems();
        ingredientGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        ingredientGrid.setHeightFull();
    }

    private void deleteSmoothieButtonOnClick(UserSession userSession, SmoothieService smoothieService) {
        if (smoothieService.deleteSmoothie(selectedSmoothie)){
            Set<Smoothie> newSmoothieSet = smoothieService.getSmoothiesForCurrentUser(userSession.getUser());

            if (newSmoothieSet.isEmpty()){
                selectedSmoothie = null;
                deleteSmoothieBtn.setVisible(false);
            } else {
                selectedSmoothie = newSmoothieSet.stream().findFirst().get();
            }

            userSmoothies.setItems(smoothieService.getSmoothiesForCurrentUser(userSession.getUser()));
            userSmoothies.setValue(selectedSmoothie);
            refreshGrid();
        }
    }

    public void refreshGrid(){
        ingredientGrid.select(null);
        if (selectedSmoothie != null){
            ingredientGrid.setItems(selectedSmoothie.getIngredients());
        } else {
            ingredientGrid.setItems();
        }
    }

    public Grid<Ingredient> getIngredientGrid(){
        return ingredientGrid;
    }

    public Smoothie getSelectedSmoothie(){
        return selectedSmoothie;
    }
}
