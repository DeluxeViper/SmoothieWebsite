package com.ceruleansource.SmoothieWebsite.frontend;

import com.ceruleansource.SmoothieWebsite.backend.Authentication.UserSession;
import com.ceruleansource.SmoothieWebsite.backend.Models.Ingredient;
import com.ceruleansource.SmoothieWebsite.backend.Models.Smoothie;
import com.ceruleansource.SmoothieWebsite.backend.Models.user.MyUserDetails;
import com.ceruleansource.SmoothieWebsite.backend.Models.user.User;
import com.ceruleansource.SmoothieWebsite.backend.Repositories.IngredientRepository;
import com.ceruleansource.SmoothieWebsite.backend.Services.IngredientService;
import com.ceruleansource.SmoothieWebsite.backend.Services.MyUserDetailsService;
import com.ceruleansource.SmoothieWebsite.backend.Services.SmoothieService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private Button removeBtn = new Button("Remove");

    private BeanValidationBinder<Ingredient> ingredientBinder;

    private Ingredient ingredientToAdd;

    private Smoothie smoothie;
    private List<Ingredient> ingredientsOfSmoothie;

    // Smoothie Form
    private TextField smoothieName;
    private Button saveSmoothieBtn = new Button("Save Smoothie");

    @Autowired
    private SmoothieService smoothieService;
    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    UserSession userSession;

    @Autowired
    public CreateSmoothieView(IngredientService ingredientService) {
        setId("create-smoothie-view");
        saveSmoothieBtn.setId("save-smoothie-button");
        ingredientsOfSmoothie = new ArrayList<>();
        smoothie = new Smoothie("Smoothie");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
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
        List<Ingredient> list = ingredientService.fetchAll();
        ingredientGrid.setItems(smoothie.getIngredients());
        ingredientGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        ingredientGrid.setHeightFull();

        // TODO: Configure ingredient grid row selection
        ingredientGrid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Optional<Ingredient> ingredientOptional = ingredientService.getIngredient(event.getValue().getId());
                if (ingredientOptional.isPresent()) {
                    populateForm(ingredientOptional.get());
                } else {
                    refreshGrid();
                }
            } else {
                clearForm();
            }
        });

        // Binding Fields
        ingredientBinder = new BeanValidationBinder<>(Ingredient.class);
//        ingredientBinder.bindInstanceFields(this);
//        ingredientBinder.forField(ingredientName).asRequired().bind(ingred )
//        ingredientBinder.forField(ingredientName).bind(Ingredient::getName, Ingredient::setName);

        // Cancelling addition of ingredient
        cancelBtn.addClickListener(e -> {
            clearForm();
            // REFRESH GRID;
            refreshGrid();
        });

        // Adding ingredient save button
        saveBtn.addClickListener(e -> {
            try {
                if (this.ingredientToAdd == null) {
                    this.ingredientToAdd = new Ingredient();
                }
//                ingredientToAdd.setId();
//                ingredientToAdd.setName(ingredientAmount.getValue().getName());
//                ingredientToAdd.setQuantityTypeAndValue(ingredientAmount.getValue().getQuantityTypeAndValue());
//                ingredientToAdd.setNutritionalInformationGrams(ingredientAmount.getValue().getNutritionalInformationGrams());
//                ingredientToAdd.setNutritionalInformationPercentage(ingredientAmount.getValue().getNutritionalInformationPercentage());

                ingredientBinder.writeBean(this.ingredientAmount.getValue());
                System.out.println("Adding ingredient to smoothie: " + ingredientToAdd);
                smoothie.getIngredients().add(ingredientAmount.getValue());
//                smoothieService.addIngredient(userSession.getUser(), smoothie, ingredientAmount.getValue());
//                ingredientsOfSmoothie.add(ingredientToAdd);
                Notification.show("Added " + ingredientToAdd.getName());
                clearForm();
                refreshGrid();
            } catch (ValidationException validationException) {
                validationException.printStackTrace();
                Notification.show("An exception happened while trying to add the ingredient to the smoothie");
            } finally {
            }

        });
    }

    @PostConstruct
    public void init() {
        // Saving the smoothie into the user before any addition of ingredients occurs
        // Ensuring the smoothie object is created
//        userSession.getUser().addSmoothie(smoothie);
//        userDetailsService.updateUser(userSession.getUser());

        saveSmoothieBtn.addClickListener(e -> {
            System.out.println("Adding smoothie to user: " + smoothie);
//            smoothieService.addSmoothieToUser(smoothie);
            userSession.getUser().addSmoothie(smoothie);
//            userDetailsService.updateUser(user);
            userDetailsService.updateUser(userSession.getUser());
            Notification.show("Saved smoothie: " + smoothie.getName());
        });
    }

    private void createEditorLayout(SplitLayout splitLayout, IngredientService ingredientService) {
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

        List<Ingredient> ingredientList = ingredientService.fetchAll();

        // Populating the form combo boxes
        // Ingredient name
        ingredientName.setLabel("Name");
        ingredientName.setRequired(true);
        ingredientName.setClearButtonVisible(true);
        ingredientName.setItemLabelGenerator(Ingredient::getName);
        ingredientName.setItems(ingredientList);

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
                List<Ingredient> ingredientAmountList = ingredientService.findAllIngredientsWithName(event.getValue().getName());
//                List<Ingredient> ingredientAmountList = ingredientRepository.findAllByName(event.getValue().getName());
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
        buttonLayout.add(saveBtn, cancelBtn, removeBtn);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();

        // Header
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setHeightFull();
        H2 title = new H2("Craft Your Recipe");
        H3 subheader = new H3("Add your Ingredients");
        verticalLayout.add(title, subheader, saveSmoothieBtn, ingredientGrid);

        wrapper.add(verticalLayout);
        splitLayout.addToPrimary(wrapper);
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Ingredient ingredient) {
        this.ingredientToAdd = ingredient;
        ingredientBinder.readBean(ingredient);
//        ingredientBinder.readBean(this.ingredient);
    }

    private void refreshGrid() {
        ingredientGrid.select(null);
        ingredientGrid.setItems(smoothie.getIngredients());
        // TODO: Configure refresh grid logic
//        ingredientGrid.setItems(ingredientService.fetchAll());
    }
}
