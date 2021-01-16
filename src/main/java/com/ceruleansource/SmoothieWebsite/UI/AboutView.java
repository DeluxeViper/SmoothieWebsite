package com.ceruleansource.SmoothieWebsite.UI;

import com.ceruleansource.SmoothieWebsite.UI.MainView.MainView;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;

/**
 * A Designer generated component for the about-view template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("about-view")
@JsModule("./src/views/about-view.js")
@Route(value = "about", layout = MainView.class)
public class AboutView extends PolymerTemplate<AboutView.AboutViewModel> {

    /**
     * Creates a new AboutView.
     */
    public AboutView() {
        // You can initialise any data required for the connected UI components here.
    }

    /**
     * This model binds properties between AboutView and about-view
     */
    public interface AboutViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
