package com.ceruleansource.SmoothieWebsite.UI.CreateSmoothieView;

import com.ceruleansource.SmoothieWebsite.backend.Authentication.UserSession;
import com.ceruleansource.SmoothieWebsite.backend.Models.Ingredient;
import com.ceruleansource.SmoothieWebsite.backend.Models.NutritionalInformationGrams;
import com.ceruleansource.SmoothieWebsite.backend.Models.NutritionalInformationPercentage;
import com.ceruleansource.SmoothieWebsite.backend.Models.Smoothie;
import com.ceruleansource.SmoothieWebsite.backend.Services.IngredientService;
import com.ceruleansource.SmoothieWebsite.backend.Services.SmoothieService;
import com.ceruleansource.SmoothieWebsite.UI.MainView.MainView;
import com.ceruleansource.SmoothieWebsite.UI.NutritionalInfoView;
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
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@PageTitle("Create Smoothie")
@CssImport("./src/styles/views/createsmoothie/create-smoothie-view.css")
@Route(value = "create-smoothie", layout = MainView.class)
public class CreateSmoothieView extends Div {

    private IngredientGridDiv ingredientGridDiv;

    // Editor layout fields
    private ComboBox<String> ingredientName;
    private ComboBox<Ingredient> ingredientAmount;
    private NumberField ingredientAmountNumberField;
    private final Button ingredientSaveBtn = new Button("Save");
    private final Button cancelIngredientBtn = new Button("Cancel");
    private final Button removeIngredientBtn = new Button("Remove");
    private NutritionalInfoView ingrNutritionalInfoView = new NutritionalInfoView();

    private Ingredient selectedIngredient; // Selected ingredient in editor
    private NutritionalInformationGrams defaultNutrGramsInfo;
    private NutritionalInformationPercentage defaultNutrPercInfo;

    // Total info
    private NutritionalInformationGrams totalNutrGrams;
    private NutritionalInformationPercentage totalNutrPercentage;
    private NutritionalInfoView totalNutrInfoView = new NutritionalInfoView();

    // Smoothie delete button: Here because UserSmoothies.asSelect() is here
    private Button deleteSmoothieBtn;

    // Nutritional info that includes the multiplied value within the multiplier
    private NutritionalInformationGrams nutrInfoGrams;
    private NutritionalInformationPercentage nutrInfoPerc;

    @Autowired
    public CreateSmoothieView(IngredientService ingredientService, UserSession userSession, SmoothieService smoothieService) {
        setId("create-smoothie-view");

        // Initializing views & fields
        ingrNutritionalInfoView.setVisible(false);
        totalNutrGrams = new NutritionalInformationGrams();
        totalNutrPercentage = new NutritionalInformationPercentage();

        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setId("split-layout");
        splitLayout.setSizeFull();
        splitLayout.setOrientation(SplitLayout.Orientation.HORIZONTAL);

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
                saveIngredientEditorMethod(smoothieService, ingredientService);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
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
        ingredientAmountNumberField = new NumberField();

        ingredientService.findAllDistinctNamedIngredientsWithSingleMultiplier();

        List<String> ingredientDistinctNamesList = ingredientService.findAllDistinctNamedIngredients();
        System.out.println("Distinct names list: " +ingredientDistinctNamesList);

        // Populating the form combo boxes
        // Ingredient name
        ingredientName.setLabel("Name");
        ingredientName.setRequired(true);
        ingredientName.setClearButtonVisible(true);
        ingredientName.setItems(ingredientDistinctNamesList);

        // Ingredient amount type
        ingredientAmount.setLabel("Amount Type");
        ingredientAmount.setRequired(true);
        ingredientAmount.setClearButtonVisible(true);
        ingredientAmount.setItemLabelGenerator(Ingredient::getQuantityTypeAndValue);
        ingredientName.addValueChangeListener(event -> editorIngrNameChangeListener(ingredientService, event));

        // Ingredient amount number field
        ingredientAmountNumberField.setLabel("Amount Multiplier");
        ingredientAmountNumberField.setHasControls(true);
        ingredientAmountNumberField.setMin(1);
        ingredientAmountNumberField.setValue(1.0);

        // Nutrition Info grams & Percentage
        ingredientAmount.addValueChangeListener(this::editorIngrAmountChangeListener);

        ingredientAmountNumberField.addValueChangeListener(numberFieldDoubleComponentValueChangeEvent -> {
            if (ingredientAmount.getValue() != null && selectedIngredient != null){
                double multiplier = numberFieldDoubleComponentValueChangeEvent.getValue();

                // Cloning to prevent changes to default nutritional information
                nutrInfoGrams = null;
                nutrInfoPerc = null;
                try {
                    nutrInfoGrams = (NutritionalInformationGrams) defaultNutrGramsInfo.clone();
                    nutrInfoPerc = (NutritionalInformationPercentage) defaultNutrPercInfo.clone();

                    nutrInfoGrams.multiplyGrams(multiplier);
                    nutrInfoPerc.multiplyPercentage(multiplier);
//                    selectedIngredient.setNutritionalInformationGrams(nutrInfoGrams);
//                    selectedIngredient.setNutritionalInformationPercentage(nutrInfoPerc);
//                    selectedIngredient.setMultiplier(multiplier);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (nutrInfoGrams != null && nutrInfoPerc != null){
                    ingrNutritionalInfoView.setNutritionalInformation(nutrInfoGrams, nutrInfoPerc);
                } else {
                    Notification.show("Error. Cannot find Nutritional Information.").addThemeVariants(NotificationVariant.LUMO_ERROR);
                }

                System.out.println("Selected ingredient: "+ selectedIngredient);
            } else {
                Notification.show("Please select an amount type.").addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
        // Setting form layout
        Component[] fields = new Component[]{ingredientName, ingredientAmount, ingredientAmountNumberField};
//        for (Component field : fields) {
//            ((HasStyle) field).addClassName("");
//        }
        formLayout.add(fields);
        editorDiv.add(formLayout, ingrNutritionalInfoView);
        editorLayoutDiv.add(editorDiv, createEditorButtonLayout());
        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private HorizontalLayout createEditorButtonLayout() {
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
        ingredientGridDiv.setId("ingredient-grid-div");
        ingredientGridDiv.getUserSmoothies().addValueChangeListener(this::userSmoothiesValueChangeMethod);
        ingredientGridDiv.setWidthFull();
        ingredientGridDiv.getIngredientGrid().asSingleSelect().addValueChangeListener(event -> onIngredientRowSelect(ingredientService, event));

        deleteSmoothieBtn = new Button("Delete Smoothie");
        deleteSmoothieBtn.addThemeVariants(ButtonVariant.LUMO_ERROR);
        deleteSmoothieBtn.setVisible(false);
        deleteSmoothieBtn.addClickListener(event -> deleteSmoothieButtonOnClick(userSession, smoothieService));

        // Automatically selecting first smoothie available on arriving to CreateSmoothieView page
        Set<Smoothie> smoothieSet = smoothieService.getSmoothiesForCurrentUser(userSession.getUser());
        if (!smoothieSet.isEmpty()) {
            ingredientGridDiv.getUserSmoothies().setValue(smoothieSet.iterator().next());
        }
        splitLayout.addToPrimary(ingredientGridDiv, deleteSmoothieBtn, totalNutrInfoView);
    }

    private void deleteSmoothieButtonOnClick(UserSession userSession, SmoothieService smoothieService) {
        if (ingredientGridDiv.getUserSmoothies().getValue() != null) {
            smoothieService.deleteSmoothie(ingredientGridDiv.getUserSmoothies().getValue());
            Set<Smoothie> smoothieSetInUser = new HashSet<>(userSession.getUser().getSmoothies());
            ingredientGridDiv.getUserSmoothies().setItems(smoothieSetInUser);
            if (!smoothieSetInUser.isEmpty()) {
                ingredientGridDiv.getUserSmoothies().setValue(smoothieSetInUser.iterator().next());
            }
            clearForm();
            refreshGrid();
        }
    }

    private void saveIngredientEditorMethod(SmoothieService smoothieService, IngredientService ingredientService) throws Exception {
        if (ingredientGridDiv.getSelectedSmoothie() != null) {
            if (ingredientAmount.getValue() != null) {
                // PROBLEM::
                //  SAVING the exact same ingredient to the smoothie doesn't change anything due to the set of ingredients within smoothie
                // POTENTIAL SOLUTION:
                //  Must detect whether or not the ingredient is already saved in the smoothie and add to it's multiplier

                // Try to save ingredient
                Ingredient ingredientToSave = new Ingredient(selectedIngredient.getName(), selectedIngredient.getQuantityTypeAndValue(), ingredientAmountNumberField.getValue(), nutrInfoGrams, nutrInfoPerc);
                Ingredient foundIngredient;
                if ((foundIngredient = checkIfIngredientExistsInSmoothie(ingredientToSave)) != null) {
                    // Ingredient does exist in smoothie, updating ingredient that exists in smoothie's multiplier
                    ingredientService.updateIngredientMultiplier(ingredientGridDiv.getSelectedSmoothie(), foundIngredient, ingredientToSave.getMultiplier());
                    Notification.show("Added " + ingredientToSave.getName()).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                } else {
                    // Ingredient doesn't exist in smoothie, simply save ingredient to smoothie
                    Ingredient ingredientInDb;
                    if ((ingredientInDb = ingredientService.findIngredient(ingredientToSave.getName(), ingredientToSave.getQuantityTypeAndValue(), ingredientToSave.getMultiplier())) != null){
                        // Ingredient already exists in database
                        smoothieService.addIngredient(ingredientGridDiv.getSelectedSmoothie(), ingredientInDb);
                    } else {
                        // Ingredient does not exist in database
                        Ingredient savedIngredient = ingredientService.saveIngredient(ingredientToSave);
                        smoothieService.addIngredient(ingredientGridDiv.getSelectedSmoothie(), savedIngredient);
                    }
                    Notification.show("Added " + ingredientToSave.getName()).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                }
                ingredientGridDiv.refreshGrid(smoothieService);
                System.out.println("Selected smoothie: " + ingredientGridDiv.getSelectedSmoothie());
                totalNutrInfoView.setNutritionalInformation(
                        ingredientGridDiv.getSelectedSmoothie().getTotalNutritionalInfoGrams(), ingredientGridDiv.getSelectedSmoothie().getTotalNutritionalInfoPercentage());
                clearForm();
            } else {
                Notification.show("Error. Please select an ingredient first!").addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        } else {
            Notification.show("Please create/select a smoothie first!").addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    private Ingredient checkIfIngredientExistsInSmoothie(Ingredient ingredientToSave) {
        for (Ingredient curIngr : ingredientGridDiv.getSelectedSmoothie().getIngredients()) {
            if (curIngr.getName().equals(ingredientToSave.getName()) && curIngr.getQuantityTypeAndValue().equals(ingredientToSave.getQuantityTypeAndValue()))
                return curIngr;
        }
        return null;
    }

    private void removeIngredientEditorMethod(SmoothieService smoothieService) throws Exception {
        if (ingredientAmount.getValue() != null) {
            if (ingredientGridDiv.getSelectedSmoothie() != null) {
                smoothieService.removeIngredient(ingredientGridDiv.getSelectedSmoothie(), ingredientAmount.getValue());
                Notification.show(ingredientAmount.getValue().getName() + " removed!");
                totalNutrInfoView.setNutritionalInformation(
                        ingredientGridDiv.getSelectedSmoothie().getTotalNutritionalInfoGrams(), ingredientGridDiv.getSelectedSmoothie().getTotalNutritionalInfoPercentage());
                ingredientGridDiv.refreshGrid(smoothieService);
                clearForm();
            } else {
                Notification.show("Please create/select a smoothie first!").addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        } else {
            Notification.show("Please select an ingredient!").addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    private void editorIngrAmountChangeListener(AbstractField.ComponentValueChangeEvent<ComboBox<Ingredient>, Ingredient> event) {
        if (event.getValue() == null) {
            // No ingredient amount selected
            ingrNutritionalInfoView.setVisible(false);
            selectedIngredient = null;
        } else {
            // Ingredient amount selected
            ingredientAmountNumberField.setValue(1.0);
            selectedIngredient = event.getValue();
            ingrNutritionalInfoView.setVisible(true);
            defaultNutrGramsInfo = event.getValue().getNutritionalInformationGrams();
            defaultNutrPercInfo = event.getValue().getNutritionalInformationPercentage();
            ingrNutritionalInfoView.setNutritionalInformation(defaultNutrGramsInfo, defaultNutrPercInfo);
            ingrNutritionalInfoView.setCalories(String.valueOf(defaultNutrGramsInfo.getCalories()));
        }
    }

    // On selection of editor ingredient name -> the ingredient amount types are selected according to ingredient
    private void editorIngrNameChangeListener(IngredientService ingredientService, AbstractField.ComponentValueChangeEvent<ComboBox<String>, String> event) {
        if (event.getValue() == null) {
            // No ingredient selected
            ingredientAmount.setItems();
            ingrNutritionalInfoView.setVisible(false);
        } else {
            // Ingredient selected
            System.out.println("Selected: " + event.getValue());
            List<Ingredient> ingredientAmountList = ingredientService.findAllIngredientsWithName(event.getValue());
            ingredientAmount.setItems(ingredientAmountList);
        }
    }

    private void userSmoothiesValueChangeMethod(AbstractField.ComponentValueChangeEvent<ComboBox<Smoothie>, Smoothie> event) {
        if (event.getValue() != null) {
            ingredientGridDiv.setSelectedSmoothie(event.getValue());
            deleteSmoothieBtn.setVisible(true);
            populateTotalNutritionInfo();
            System.out.println("IngredientGridDiv: UserSmoothies currently selected the following smoothie:\n" + ingredientGridDiv.getSelectedSmoothie());
        } else {
            deleteSmoothieBtn.setVisible(false);
            ingredientGridDiv.setSelectedSmoothie(null);
            System.out.println("IngredientGridDiv: Currently user smoothies selected a null smoothie, will do nothing.");
        }
        refreshGrid();
    }

    private void populateTotalNutritionInfo() {
        resetTotalNutrInfo();
        if (ingredientGridDiv.getSelectedSmoothie() != null) {
            totalNutrInfoView.setNutritionalInformation(
                    ingredientGridDiv.getSelectedSmoothie().getTotalNutritionalInfoGrams(), ingredientGridDiv.getSelectedSmoothie().getTotalNutritionalInfoPercentage());
            System.out.println("Smoothie selected: resetting info: " + ingredientGridDiv.getSelectedSmoothie());
        }
    }

    public void resetTotalNutrInfo() {
        totalNutrGrams = new NutritionalInformationGrams();
        totalNutrPercentage = new NutritionalInformationPercentage();
        totalNutrInfoView.setNutritionalInformation(totalNutrGrams, totalNutrPercentage);
    }

    private void onIngredientRowSelect(IngredientService ingredientService, AbstractField.ComponentValueChangeEvent<Grid<Ingredient>, Ingredient> event) {
        if (event.getValue() != null) {
            Optional<Ingredient> ingredientOptional = ingredientService.getIngredient(event.getValue().getId(), event.getValue().getMultiplier());
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
            ingrNutritionalInfoView.setVisible(true);
            ingrNutritionalInfoView.setNutritionalInformation(ingredient.getNutritionalInformationGrams(), ingredient.getNutritionalInformationPercentage());
        } else {
            ingredientName.setValue(null);
            ingredientAmount.setValue(null);
            ingrNutritionalInfoView.setVisible(false);
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