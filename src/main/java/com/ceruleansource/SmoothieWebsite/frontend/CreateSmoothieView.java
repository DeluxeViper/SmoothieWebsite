package com.ceruleansource.SmoothieWebsite.frontend;

import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;

@Route(value = "create-smoothie", layout = MainView.class)
@PageTitle("Create Smoothie")
public class CreateSmoothieView extends PolymerTemplate<TemplateModel> {
}
