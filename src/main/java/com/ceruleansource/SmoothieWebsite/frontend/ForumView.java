package com.ceruleansource.SmoothieWebsite.frontend;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;

@Route(value = "form", layout = MainView.class)
@PageTitle("Forum")
public class ForumView extends PolymerTemplate<TemplateModel> {
}
