package com.ceruleansource.SmoothieWebsite.frontend;

import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.templatemodel.TemplateModel;

@Route(value = "home", layout = MainView.class)
@PageTitle("Home")
public class HomeView extends PolymerTemplate<TemplateModel> {
}
