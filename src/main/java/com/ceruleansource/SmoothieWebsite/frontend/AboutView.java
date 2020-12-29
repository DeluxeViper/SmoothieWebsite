package com.ceruleansource.SmoothieWebsite.frontend;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;


@Route(value = "about", layout = MainView.class)
@PageTitle("About")
public class AboutView extends PolymerTemplate<TemplateModel> {
    public AboutView(){
        H2 aboutTitle = new H2("About View");

    }
}
