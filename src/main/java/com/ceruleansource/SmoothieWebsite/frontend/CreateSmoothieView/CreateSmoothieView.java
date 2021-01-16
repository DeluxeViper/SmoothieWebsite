package com.ceruleansource.SmoothieWebsite.frontend.CreateSmoothieView;

import com.ceruleansource.SmoothieWebsite.backend.Authentication.UserSession;
import com.ceruleansource.SmoothieWebsite.backend.Models.Ingredient;
import com.ceruleansource.SmoothieWebsite.backend.Models.NutritionalInformationGrams;
import com.ceruleansource.SmoothieWebsite.backend.Models.NutritionalInformationPercentage;
import com.ceruleansource.SmoothieWebsite.backend.Models.Smoothie;
import com.ceruleansource.SmoothieWebsite.backend.Services.IngredientService;
import com.ceruleansource.SmoothieWebsite.backend.Services.SmoothieService;
import com.ceruleansource.SmoothieWebsite.frontend.MainView.MainView;
import com.ceruleansource.SmoothieWebsite.frontend.NutritionalInfoView;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * A Designer generated component for the create-smoothie-view template.
 * <p>
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@PageTitle("Create Smoothie")
@CssImport("./src/styles/views/createsmoothie/create-smoothie-view.css")
@Route(value = "create-smoothie", layout = MainView.class)
public class CreateSmoothieView extends Div {

    private IngredientGridDiv ingredientGridDiv;

    // Editor layout fields
    private ComboBox<String> ingredientName;
    private ComboBox<Ingredient> ingredientAmount;
    private final Button ingredientSaveBtn = new Button("Save");
    private final Button cancelIngredientBtn = new Button("Cancel");
    private final Button removeIngredientBtn = new Button("Remove");
    private NutritionalInfoView nutritionalInfoView = new NutritionalInfoView();

    // Total info
    private NutritionalInformationGrams totalNutrGrams;
    private NutritionalInformationPercentage totalNutrPercentage;
    private NutritionalInfoView totalNutrInfoView = new NutritionalInfoView();

    // Smoothie delete button
    private Button deleteSmoothieBtn;


    @Autowired
    public CreateSmoothieView(IngredientService ingredientService, UserSession userSession, SmoothieService smoothieService) {
        setId("create-smoothie-view");

        // Initializing views & fields
        nutritionalInfoView.setVisible(false);
        totalNutrGrams = new NutritionalInformationGrams();
        totalNutrPercentage = new NutritionalInformationPercentage();

        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();
        splitLayout.setOrientation(SplitLayout.Orientation.VERTICAL);

        createGridLayout(splitLayout, ingredientService, userSession, smoothieService);
        createEditorLayout(splitLayout, ingredientService);

        add(splitLayout);

        // Editor: Cancelling addition of ingredient
        cancelIngredientBtn.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        // Editor: Remove ingredient from smoothie
        removeIngredientBtn.addClickListener(e -> {
            try {
                removeIngredientEditorMethod(smoothieService);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        // Editor: Adding ingredient save button
        ingredientSaveBtn.addClickListener(e -> {
            try {
                saveIngredientEditorMethod(smoothieService);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    private void saveIngredientEditorMethod(SmoothieService smoothieService) throws Exception {
        if (ingredientGridDiv.getSelectedSmoothie() != null) {
            if (ingredientAmount.getValue() != null) {
                smoothieService.addIngredient(ingredientGridDiv.getSelectedSmoothie(), ingredientAmount.getValue());
                totalNutrGrams.addGrams(ingredientAmount.getValue().getNutritionalInformationGrams());
                totalNutrPercentage.addPercentage(ingredientAmount.getValue().getNutritionalInformationPercentage());
                Notification.show("Added " + ingredientAmount.getValue().getName()).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                totalNutrInfoView.setNutritionalInformation(totalNutrGrams, totalNutrPercentage);
                ingredientGridDiv.refreshGrid();
                clearForm();
            } else {
                Notification.show("Error. Please select an ingredient first!").addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        } else {
            Notification.show("Please create/select a smoothie first!").addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    private void removeIngredientEditorMethod(SmoothieService smoothieService) throws Exception {
        if (ingredientAmount.getValue() != null) {
            if (ingredientGridDiv.getSelectedSmoothie() != null) {
                smoothieService.removeIngredient(ingredientGridDiv.getSelectedSmoothie(), ingredientAmount.getValue());
                totalNutrGrams.subtractGrams(ingredientAmount.getValue().getNutritionalInformationGrams());
                totalNutrPercentage.subtractPercentage(ingredientAmount.getValue().getNutritionalInformationPercentage());
                Notification.show(ingredientAmount.getValue().getName() + " removed!");
                totalNutrInfoView.setNutritionalInformation(totalNutrGrams, totalNutrPercentage);
                refreshGrid();
                clearForm();
            } else {
                Notification.show("Please create/select a smoothie first!").addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        } else {
            Notification.show("Please select an ingredient!").addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    private void createEditorLayout(SplitLayout splitLayout, IngredientService ingredientService) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setId("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setId("editor");

        FormLayout formLayout = new FormLayout();
        formLayout.setId("form-layout");
        ingredientName = new ComboBox<>("Ingredient Name");

        ingredientAmount = new ComboBox<>("Ingredient Amount");

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

        ingredientName.addValueChangeListener(event -> editorIngrNameChangeListener(ingredientService, event));

        // Nutrition Info grams & Percentage
        ingredientAmount.addValueChangeListener(this::editorIngrAmountChangeListener);

        // Setting form layout
        Component[] fields = new Component[]{ingredientName, ingredientAmount};
//        for (Component field : fields) {
//            ((HasStyle) field).addClassName("");
//        }
        formLayout.add(fields);
        editorDiv.add(formLayout, nutritionalInfoView);
        editorLayoutDiv.add(editorDiv, createButtonLayout());
        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void editorIngrAmountChangeListener(AbstractField.ComponentValueChangeEvent<ComboBox<Ingredient>, Ingredient> event) {
        if (event.getValue() == null) {
            // No ingredient amount selected
            nutritionalInfoView.setVisible(false);
        } else {
            // Ingredient amount selected
            nutritionalInfoView.setVisible(true);
            nutritionalInfoView.setNutritionalInformation(event.getValue().getNutritionalInformationGrams(), event.getValue().getNutritionalInformationPercentage());
            nutritionalInfoView.setCalories(String.valueOf(event.getValue().getNutritionalInformationGrams().getCalories()));
        }
    }

    private void editorIngrNameChangeListener(IngredientService ingredientService, AbstractField.ComponentValueChangeEvent<ComboBox<String>, String> event) {
        if (event.getValue() == null) {
            // No ingredient selected
            ingredientAmount.setItems();
            nutritionalInfoView.setVisible(false);
        } else {
            // Ingredient selected
            System.out.println("Selected: " + event.getValue());
            List<Ingredient> ingredientAmountList = ingredientService.findAllIngredientsWithName(event.getValue());
            ingredientAmount.setItems(ingredientAmountList);
        }
    }

    private HorizontalLayout createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setId("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(true);
        ingredientSaveBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancelIngredientBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        removeIngredientBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        buttonLayout.add(ingredientSaveBtn, cancelIngredientBtn, removeIngredientBtn);
        return buttonLayout;
    }

    private void createGridLayout(SplitLayout splitLayout, IngredientService ingredientService, UserSession userSession, SmoothieService smoothieService) {
        ingredientGridDiv = new IngredientGridDiv(userSession, smoothieService);
        ingredientGridDiv.getUserSmoothies().addValueChangeListener(this::userSmoothiesValueChangeMethod);
        ingredientGridDiv.setWidthFull();
        ingredientGridDiv.getIngredientGrid().asSingleSelect().addValueChangeListener(event -> onIngredientRowSelect(ingredientService, event));
        deleteSmoothieBtn = new Button("Delete Smoothie");
        deleteSmoothieBtn.addThemeVariants(ButtonVariant.LUMO_ERROR);
        deleteSmoothieBtn.addClickListener(event -> deleteSmoothieButtonOnClick(userSession, smoothieService));
        splitLayout.addToPrimary(deleteSmoothieBtn, ingredientGridDiv, totalNutrInfoView);
    }

    private void userSmoothiesValueChangeMethod(AbstractField.ComponentValueChangeEvent<ComboBox<Smoothie>, Smoothie> event) {
        if (event.getValue() != null) {
            ingredientGridDiv.setSelectedSmoothie(event.getValue());
            deleteSmoothieBtn.setVisible(true);
            populateTotalNutritionInfo();
            System.out.println("IngredientGridDiv: UserSmoothies currently selected the following smoothie:\n" + ingredientGridDiv.getSelectedSmoothie());
        } else {
            ingredientGridDiv.setSelectedSmoothie(null);
            System.out.println("IngredientGridDiv: Currently user smoothies selected a null smoothie, will do nothing.");
        }
        refreshGrid();
    }

    private void deleteSmoothieButtonOnClick(UserSession userSession, SmoothieService smoothieService) {
        if (smoothieService.deleteSmoothie(ingredientGridDiv.getSelectedSmoothie())) {
            Set<Smoothie> newSmoothieSet = smoothieService.getSmoothiesForCurrentUser(userSession.getUser());
            System.out.println("New Smoothie set after deletion: " + newSmoothieSet);
            if (newSmoothieSet.isEmpty()) {
                ingredientGridDiv.setSelectedSmoothie(null);
                deleteSmoothieBtn.setVisible(false);
            } else {
                ingredientGridDiv.setSelectedSmoothie(newSmoothieSet.stream().findFirst().get());
            }

            ingredientGridDiv.getUserSmoothies().setItems(newSmoothieSet);
            ingredientGridDiv.getUserSmoothies().setValue(ingredientGridDiv.getSelectedSmoothie());
            refreshGrid();
            populateTotalNutritionInfo();
        }
    }

    private void populateTotalNutritionInfo() {
        resetTotalNutrInfo();
        if (ingredientGridDiv.getSelectedSmoothie() != null){
            Set<Ingredient> selectedSmoothieIngr = ingredientGridDiv.getSelectedSmoothie().getIngredients();
            System.out.println("Smoothie selected: resetting info: " + selectedSmoothieIngr);

            selectedSmoothieIngr.forEach(ingr -> {
                try {
                    System.out.println("CreateSmoothieView: Ingredient nutr info: " + ingr.getNutritionalInformationGrams());
                    totalNutrGrams.addGrams(ingr.getNutritionalInformationGrams());
                    totalNutrPercentage.addPercentage(ingr.getNutritionalInformationPercentage());
                    totalNutrInfoView.setNutritionalInformation(totalNutrGrams, totalNutrPercentage);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
        }
    }

    public void resetTotalNutrInfo(){
        totalNutrGrams = new NutritionalInformationGrams();
        totalNutrPercentage = new NutritionalInformationPercentage();
        totalNutrInfoView.setNutritionalInformation(totalNutrGrams, totalNutrPercentage);
    }

    private void onIngredientRowSelect(IngredientService ingredientService, AbstractField.ComponentValueChangeEvent<Grid<Ingredient>, Ingredient> event) {
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
    }

    /**
     * Clears the form for adding ingredient
     */
    private void clearForm() {
        populateForm(null);
    }

    /**
     * Fills in the ingredient form
     *
     * @param ingredient - populates form depending on the ingredient selected
     *                   - or populates the form null if no ingredient is passed in
     */
    private void populateForm(Ingredient ingredient) {
        if (ingredient != null) {
            ingredientName.setValue(ingredient.getName());
            ingredientAmount.setValue(ingredient);
            nutritionalInfoView.setVisible(true);
            nutritionalInfoView.setNutritionalInformation(ingredient.getNutritionalInformationGrams(), ingredient.getNutritionalInformationPercentage());
        } else {
            ingredientName.setValue(null);
            ingredientAmount.setValue(null);
            nutritionalInfoView.setVisible(false);
        }
    }

    /**
     * Refreshes ingredients in item grid
     */
    private void refreshGrid() {
        ingredientGridDiv.getIngredientGrid().select(null);
        if (ingredientGridDiv.getSelectedSmoothie() != null) {
            ingredientGridDiv.getIngredientGrid().setItems(ingredientGridDiv.getSelectedSmoothie().getIngredients());
        } else {
            ingredientGridDiv.getIngredientGrid().setItems();
        }

    }
}