package com.ceruleansource.SmoothieWebsite.frontend;

import com.ceruleansource.SmoothieWebsite.backend.Models.Ingredient;
import com.ceruleansource.SmoothieWebsite.backend.Models.Smoothie;
import com.ceruleansource.SmoothieWebsite.backend.Repositories.IngredientRepository;
import com.ceruleansource.SmoothieWebsite.backend.Services.IngredientService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.Tag;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

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

    private Grid<Ingredient> ingredientGrid = new Grid<>(Ingredient.class, false);

    // Editor layout fields
    private ComboBox<Ingredient> ingredientName;
    private ComboBox<Ingredient> ingredientAmount;
    private H3 nutritionalInfoGrams;
    private H3 nutritionalInfoPercentage;
    private Button saveBtn = new Button("Save");
    private Button cancelBtn = new Button("Cancel");

    private BeanValidationBinder<Ingredient> ingredientBinder;

    private Ingredient ingredient;
    private Smoothie smoothie;

    @Autowired
    IngredientService ingredientService;

    @Autowired
    public CreateSmoothieView(IngredientRepository ingredientRepository) {
        System.out.println("Ingredient service: " + ingredientService);
        setId("create-smoothie-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();
//        splitLayout.setOrientation(SplitLayout.Orientation.VERTICAL);
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout, ingredientRepository);

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
        ingredientGrid.setItems();
        ingredientGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        ingredientGrid.setHeightFull();

        // TODO: Configure ingredient grid row selection
        ingredientGrid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
            }
        });
        // You can initialise any data required for the connected UI components here.
//        Grid<Ingredient> createSmoothieGrid = new Grid<>(Ingredient.class);
////        Iterable<Ingredient> ingredientIterator = ingredientRepository.findAll();
////        List<Ingredient> ingredientList = new ArrayList<>();
////        ingredientIterator.forEach(ingredientList::add);
//        List<Ingredient> ingredientList = new ArrayList<>();
//        ingredientList.add(new Ingredient((long) 2, "Orange", "1 Cup", new NutritionalInformationGrams(), new NutritionalInformationPercentage()));
//        System.out.println(ingredientList);
//        createSmoothieGrid.addColumn(Ingredient::getName).setHeader("Ingredient");
//        createSmoothieGrid.addColumn(Ingredient::getQuantityTypeAndValue).setHeader("Amount");
////        createSmoothieGrid.addColumn(Ingredient::getNutritionalInformationGrams).setHeader("Nutritional Info: Grams");
////        createSmoothieGrid.addColumn(Ingredient::getNutritionalInformationPercentage).setHeader("Nutritional Info: Percentage");
//        createSmoothieGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
//        createSmoothieGrid.setItems(ingredientList);
//
//        final ListDataProvider<Ingredient> dataProvider = DataProvider.ofCollection(ingredientList);
//        createSmoothieGrid.setDataProvider(dataProvider);
//        System.out.println(createSmoothieGrid.getColumns());
        // Binding Fields
        ingredientBinder = new BeanValidationBinder<>(Ingredient.class);

//        ingredientBinder.bindInstanceFields(this);

        cancelBtn.addClickListener(e -> {
            clearForm();
            // REFRESH GRID;
            refreshGrid();
        });

        saveBtn.addClickListener(e ->{
            try {
                if (this.ingredient == null) {
                    this.ingredient = new Ingredient();
                }
//               ingredientBinder.writeBean(this.ingredient);

                // TODO: Add ingredient to smoothie logic

                clearForm();
                refreshGrid();
            } finally {

            }
//           catch (ValidationException validationException) {
//               validationException.printStackTrace();
//               Notification.show("An exception happened while trying to add the ingredient to the smoothie");
//           }
        });
    }

    private void createEditorLayout(SplitLayout splitLayout, IngredientRepository ingredientRepository) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setId("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setId("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();

        // TODO: Implement logic for Combo boxes
        ingredientName = new ComboBox<>("Ingredient Name");

        ingredientAmount = new ComboBox<>("Ingredient Amount");

        nutritionalInfoGrams = new H3();
        nutritionalInfoPercentage = new H3();

        List<Ingredient> ingredientList = new ArrayList<>();
        Iterable<Ingredient> iterable = ingredientRepository.findAll();
        iterable.forEach(ingredientList::add);
//
//        try {
//            ingredientList = ingredientService.fetchAll();
//            System.out.println("Ingredient list: " + ingredientList);
//        } catch (NullPointerException e){
//            System.out.println(ingredientService);
//        }
//        System.out.println("Ingredient Repo: " + ingredientRepository.findAll());

        // Populating the form combo boxes

        // Ingredient name
        ingredientName.setLabel("Name");
        ingredientName.setRequired(true);
        ingredientName.setItemLabelGenerator(Ingredient::getName);
        ingredientName.setItems(ingredientList);

        // Ingredient amount
        ingredientAmount.setLabel("Amount");
        ingredientAmount.setRequired(true);
        ingredientAmount.setItemLabelGenerator(Ingredient::getQuantityTypeAndValue);
        ingredientName.addValueChangeListener(event -> {
            if (event.getValue() == null) {
                // No ingredient selected
                ingredientAmount.setItems();
            } else {
                // Ingredient selected
                System.out.println("Selected: " + event.getValue());
//                List<Ingredient> ingredientAmountList = ingredientService.findAllIngredientsWithName(event.getValue().getName());
                List<Ingredient> ingredientAmountList = ingredientRepository.findAllByName(event.getValue().getName());
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
        saveBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancelBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        buttonLayout.add(saveBtn, cancelBtn);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(ingredientGrid);
    }

    private void clearForm(){
        populateForm(null);
    }

    private void populateForm(Ingredient ingredient) {
        this.ingredient = ingredient;
//        ingredientBinder.readBean(this.ingredient);
    }

    private void refreshGrid(){
        ingredientGrid.select(null);
        // TODO: Configure refresh grid logic
//        ingredientGrid.setItems(ingredientService.fetchAll());
    }
}
