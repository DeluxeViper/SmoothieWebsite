package com.ceruleansource.SmoothieWebsite.frontend;

import com.ceruleansource.SmoothieWebsite.backend.Models.NutritionalInformationGrams;
import com.ceruleansource.SmoothieWebsite.backend.Models.NutritionalInformationPercentage;
import com.vaadin.flow.templatemodel.Exclude;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;

/**
 * A Designer generated component for the nutritional-info-view template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("nutritional-info-view")
@JsModule("./src/views/nutritional-info-view.js")
public class NutritionalInfoView extends PolymerTemplate<NutritionalInfoView.NutritionalInfoViewModel> {

    /**
     * Creates a new NutritionalInfoView.
     */
    public NutritionalInfoView() {
        // You can initialise any data required for the connected UI components here.
    }

    public NutritionalInfoView(NutritionalInformationGrams nutritionalInformationGrams, NutritionalInformationPercentage nutritionalInformationPercentage){
        getModel().setNutritionalInformationGrams(nutritionalInformationGrams);
        getModel().setNutritionalInformationPercentage(nutritionalInformationPercentage);
    }

    public void setCalories(String calories){
        getModel().setCalories(calories);
    }

    public void setNutritionalInformation(NutritionalInformationGrams nutritionalInformationGrams, NutritionalInformationPercentage nutritionalInformationPercentage){
        getModel().setNutritionalInformationGrams(nutritionalInformationGrams);
        getModel().setNutritionalInformationPercentage(nutritionalInformationPercentage);
    }

    /**
     * This model binds properties between NutritionalInfoView and nutritional-info-view
     */
    public interface NutritionalInfoViewModel extends TemplateModel {
        void setCalories(String calories);

        @Exclude("id")
        void setNutritionalInformationGrams(NutritionalInformationGrams nutritionalInformationGrams);

        @Exclude("id")
        void setNutritionalInformationPercentage(NutritionalInformationPercentage nutritionalInformationPercentage);
        // Add setters and getters for template properties here.
    }
}
