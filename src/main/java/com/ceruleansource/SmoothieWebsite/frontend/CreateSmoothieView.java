package com.ceruleansource.SmoothieWebsite.frontend;

import com.ceruleansource.SmoothieWebsite.backend.Models.Ingredient;
import com.ceruleansource.SmoothieWebsite.backend.Models.NutritionalInformationGrams;
import com.ceruleansource.SmoothieWebsite.backend.Models.NutritionalInformationPercentage;
import com.ceruleansource.SmoothieWebsite.backend.Repositories.IngredientRepository;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * A Designer generated component for the create-smoothie-view template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("create-smoothie-view")
@JsModule("./src/views/create-smoothie-view.js")
@Route(value = "create-smoothie", layout = MainView.class)
public class CreateSmoothieView extends PolymerTemplate<CreateSmoothieView.CreateSmoothieViewModel> {

    /**
     * Creates a new CreateSmoothieView.
     */
    public CreateSmoothieView(IngredientRepository ingredientRepository) {
        // You can initialise any data required for the connected UI components here.
        Grid<Ingredient> createSmoothieGrid = new Grid<>(Ingredient.class);
//        Iterable<Ingredient> ingredientIterator = ingredientRepository.findAll();
//        List<Ingredient> ingredientList = new ArrayList<>();
//        ingredientIterator.forEach(ingredientList::add);
        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(new Ingredient((long) 2, "Orange", "1 Cup", new NutritionalInformationGrams(), new NutritionalInformationPercentage()));
        System.out.println(ingredientList);
        createSmoothieGrid.addColumn(Ingredient::getName).setHeader("Ingredient");
        createSmoothieGrid.addColumn(Ingredient::getQuantityTypeAndValue).setHeader("Amount");
//        createSmoothieGrid.addColumn(Ingredient::getNutritionalInformationGrams).setHeader("Nutritional Info: Grams");
//        createSmoothieGrid.addColumn(Ingredient::getNutritionalInformationPercentage).setHeader("Nutritional Info: Percentage");
        createSmoothieGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        createSmoothieGrid.setItems(ingredientList);

        final ListDataProvider<Ingredient> dataProvider = DataProvider.ofCollection(ingredientList);
        createSmoothieGrid.setDataProvider(dataProvider);
        System.out.println(createSmoothieGrid.getColumns());
    }

    /**
     * This model binds properties between CreateSmoothieView and create-smoothie-view
     */
    public interface CreateSmoothieViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
