package com.ceruleansource.SmoothieWebsite.frontend;

import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;

/**
 * A Designer generated component for the forum-view template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("forum-view")
@JsModule("./src/views/forum-view.js")
@Route(value = "forum", layout = MainView.class)
public class ForumView extends PolymerTemplate<ForumView.ForumViewModel> {

    /**
     * Creates a new ForumView.
     */
    public ForumView() {
        // You can initialise any data required for the connected UI components here.
    }

    /**
     * This model binds properties between ForumView and forum-view
     */
    public interface ForumViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
