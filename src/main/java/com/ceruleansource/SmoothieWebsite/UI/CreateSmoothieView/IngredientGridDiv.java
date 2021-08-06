package com.ceruleansource.SmoothieWebsite.UI.CreateSmoothieView;

import com.ceruleansource.SmoothieWebsite.backend.Authentication.UserSession;
import com.ceruleansource.SmoothieWebsite.backend.Models.Ingredient;
import com.ceruleansource.SmoothieWebsite.backend.Models.NutritionalInformationGrams;
import com.ceruleansource.SmoothieWebsite.backend.Models.NutritionalInformationPercentage;
import com.ceruleansource.SmoothieWebsite.backend.Models.Smoothie;
import com.ceruleansource.SmoothieWebsite.backend.Services.SmoothieService;
import com.ceruleansource.SmoothieWebsite.UI.NutritionalInfoView;
import com.vaadin.componentfactory.Tooltip;
import com.vaadin.componentfactory.TooltipAlignment;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
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

    // Create smoothie dialog
    private CreateSmoothieDialog smoothieDialog;

    @Autowired
    public IngredientGridDiv(UserSession userSession, SmoothieService smoothieService) {
        setId("grid-wrapper");

        VerticalLayout overallVLayout = new VerticalLayout();
        overallVLayout.setHeightFull();

        VerticalLayout headerLayout = initHeaderLayout(userSession, smoothieService);

        initializeIngredientGrid();

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

        userSmoothies = new ComboBox<>("Your Smoothies");
        userSmoothies.setItemLabelGenerator(Smoothie::getName);
        userSmoothies.setItems(smoothieService.getSmoothiesForCurrentUser(userSession.getUser()));

        // Initializing smoothieDialog
        initSmoothieCreateDialog(userSession, smoothieService);

        Button createSmoothieBtn = new Button("Create Smoothie");
        createSmoothieBtn.addClickListener(e -> smoothieDialog.open());

        hLayout.add(userSmoothies, createSmoothieBtn);

        return hLayout;
    }

    private void initSmoothieCreateDialog(UserSession userSession, SmoothieService smoothieService) {
        smoothieDialog = new CreateSmoothieDialog(userSession, smoothieService);
        smoothieDialog.addDetachListener(e -> {
            System.out.println("Dialog detach listener");
            selectedSmoothie = smoothieDialog.getSelectedSmoothie();
            userSmoothies.setItems(smoothieService.getSmoothiesForCurrentUser(userSession.getUser()));
            userSmoothies.setValue(smoothieDialog.getSelectedSmoothie());
            System.out.println("Getting user smoothies value after detachment of dialog: " + userSmoothies.getValue());
        });
    }

    private void initializeIngredientGrid() {
        // Configure Grid
        int multiplier = 1;
        ingredientGrid.addColumn(Ingredient::getName)
                .setHeader("Name").setAutoWidth(true)
                .setSortable(true);
        ingredientGrid.addColumn(Ingredient::getQuantityTypeAndValue)
                .setHeader("Amount").setAutoWidth(true);
        ingredientGrid.addColumn(Ingredient::getMultiplier).setHeader("Multiplier").setAutoWidth(true);
        ingredientGrid.addComponentColumn(ingredient -> {
            try {
                return getViewNutrFactsButton(ingredient);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Notification.show("Error. Could not retrieve Nutrition Facts View.").addThemeVariants(NotificationVariant.LUMO_ERROR);
            return null;
        }).setHeader("Nutritional Info");
        ingredientGrid.setHeightByRows(true);

        // Initialize ingredient grid items
        ingredientGrid.setItems();
        ingredientGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        ingredientGrid.setHeightFull();
    }

    /**
     * This method introduces a tooltip which contains the nutritional information table for the specific ingredient row
     * within the smoothie ingredients grid
     *
     * @param ingredient - ingredient to retrieve nutritional facts for
     * @return
     * @throws Exception
     */
    private Button getViewNutrFactsButton(Ingredient ingredient) throws Exception {
        Button viewFactsBtn = new Button(VaadinIcon.EYE.create());
        viewFactsBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        Tooltip tooltip = new Tooltip();
        tooltip.setThemeName("light");
        tooltip.setAlignment(TooltipAlignment.CENTER);
        NutritionalInfoView nutritionalInfoView = new NutritionalInfoView();

        // Multiply nutriInfo
        NutritionalInformationGrams nutritionalInformationGrams = ingredient.getNutritionalInformationGrams().multiplyGrams(ingredient.getMultiplier());
        NutritionalInformationPercentage nutritionalInformationPercentage = ingredient.getNutritionalInformationPercentage().multiplyPercentage(ingredient.getMultiplier());

        nutritionalInfoView.setNutritionalInformation(nutritionalInformationGrams, nutritionalInformationPercentage);

        tooltip.add(nutritionalInfoView);
        tooltip.setCloseButtonVisible(true);
        add(tooltip);
        viewFactsBtn.addClickListener(e -> tooltip.open());
        return viewFactsBtn;
    }

    public void refreshGrid(SmoothieService smoothieService) {
        ingredientGrid.select(null);
        if (selectedSmoothie != null) {
            // Refreshing smoothie (for possible multiplier change in ingredients which need an ingredients refresh)
            selectedSmoothie = smoothieService.getSmoothie(selectedSmoothie.getId());
            ingredientGrid.setItems(selectedSmoothie.getIngredients());
        } else {
            ingredientGrid.setItems();
        }
    }

    public Grid<Ingredient> getIngredientGrid() {
        return ingredientGrid;
    }

    public Smoothie getSelectedSmoothie() {
        return selectedSmoothie;
    }

    public void setSelectedSmoothie(Smoothie smoothie){
        this.selectedSmoothie = smoothie;
        userSmoothies.setValue(selectedSmoothie);
    }

    public ComboBox<Smoothie> getUserSmoothies() {
        return userSmoothies;
    }

    public void setUserSmoothies(Set<Smoothie> smoothieSet){
        userSmoothies.setItems(smoothieSet);
    }
}
