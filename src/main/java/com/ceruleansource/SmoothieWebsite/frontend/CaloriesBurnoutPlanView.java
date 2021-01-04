package com.ceruleansource.SmoothieWebsite.frontend;

import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;

/**
 * A Designer generated component for the calories-burnout-plan-view template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("calories-burnout-plan-view")
@JsModule("./src/views/calories-burnout-plan-view.js")
@Route(value = "calories-burnout-plan", layout = MainView.class)
public class CaloriesBurnoutPlanView extends PolymerTemplate<CaloriesBurnoutPlanView.CaloriesBurnoutPlanViewModel> {

    /**
     * Creates a new CaloriesBurnoutPlanView.
     */
    public CaloriesBurnoutPlanView() {
        // You can initialise any data required for the connected UI components here.
    }

    /**
     * This model binds properties between CaloriesBurnoutPlanView and calories-burnout-plan-view
     */
    public interface CaloriesBurnoutPlanViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
