package com.ceruleansource.SmoothieWebsite.frontend.CreateSmoothieView;

import com.ceruleansource.SmoothieWebsite.backend.Authentication.UserSession;
import com.ceruleansource.SmoothieWebsite.backend.Models.Ingredient;
import com.ceruleansource.SmoothieWebsite.backend.Models.Smoothie;
import com.ceruleansource.SmoothieWebsite.backend.Services.IngredientService;
import com.ceruleansource.SmoothieWebsite.backend.Services.SmoothieService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.GeneratedVaadinCheckbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.selection.SelectionEvent;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.servlet.Registration;
import java.util.Optional;
import java.util.Set;

@CssImport("./src/styles/views/createsmoothie/create-smoothie-view.css")
public class IngredientGridDiv extends Div {

    @Autowired
    UserSession userSession;

    @Autowired
    SmoothieService smoothieService;

    private Grid<Ingredient> ingredientGrid = new Grid<>(Ingredient.class, false);

    // Lists all the smoothies in the current user
    private ComboBox<Smoothie> userSmoothies;

    // Current smoothie that is selected from userSmoothies
    private Smoothie selectedSmoothie;

    private CreateSmoothieDialog smoothieDialog;

    private Button deleteSmoothieBtn;

    public IngredientGridDiv(@Autowired IngredientService ingredientService) {
        setId("grid-wrapper");
        setWidthFull();

        VerticalLayout overallVLayout = new VerticalLayout();
        overallVLayout.setHeightFull();

        VerticalLayout headerLayout = initHeaderLayout();

        initializeIngredientGrid(ingredientService);

        overallVLayout.add(headerLayout, ingredientGrid);

    }

    @PostConstruct
    public void initialization(){
        smoothieDialog = new CreateSmoothieDialog(userSmoothies);
        smoothieDialog.addDetachListener(e -> {
            System.out.println("Dialog detach listener");
            selectedSmoothie = smoothieDialog.getSelectedSmoothie();
        });

//        smoothieDialog.addDialogCloseActionListener(dialogCloseActionEvent -> {
//            System.out.println("Dialog close action listener");
//            selectedSmoothie = smoothieDialog.getSelectedSmoothie();
//            smoothieDialog.close();
//        });
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
        userSmoothies = new ComboBox<>("Your Smoothies");
        userSmoothies.setItemLabelGenerator(Smoothie::getName);

        Button createSmoothieBtn = new Button("Create Smoothie");
        createSmoothieBtn.addClickListener(e-> smoothieDialog.open());

        deleteSmoothieBtn = new Button("Delete Smoothie");
        deleteSmoothieBtn.addThemeVariants(ButtonVariant.LUMO_ERROR);
        deleteSmoothieBtn.addClickListener(e -> deleteSmoothieButtonOnClick());

        hLayout.add(userSmoothies, createSmoothieBtn, deleteSmoothieBtn);

        return hLayout;
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
                populateForm(null);
            }
        });
    }

    private void deleteSmoothieButtonOnClick() {
        if (smoothieService.deleteSmoothie(smoothieDialog.getSelectedSmoothie())){
            Set<Smoothie> newSmoothieSet = smoothieService.getSmoothiesForCurrentUser(userSession.getUser());

            if (newSmoothieSet.isEmpty()){
                selectedSmoothie = null;
                deleteSmoothieBtn.setVisible(false);
            } else {
                selectedSmoothie = newSmoothieSet.stream().findFirst().get();
            }

            userSmoothies.setItems(smoothieService.getSmoothiesForCurrentUser(userSession.getUser()));
            userSmoothies.setValue(selectedSmoothie);
            populateForm(null);
            refreshGrid();
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
