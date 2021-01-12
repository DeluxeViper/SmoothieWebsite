package com.ceruleansource.SmoothieWebsite.frontend.Error;

import com.ceruleansource.SmoothieWebsite.frontend.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.flow.router.*;

import javax.servlet.http.HttpServletResponse;

public class CustomRouteNotFoundError extends RouteNotFoundError {
    public CustomRouteNotFoundError() {
        RouterLink link = Component.from(ElementFactory.createRouterLink("home", "Go to the front page."),
                RouterLink.class);
        getElement().appendChild(new Text("Oops you hit a 404. ").getElement(), link.getElement());
    }

    @Override
    public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<NotFoundException> parameter) {
        return HttpServletResponse.SC_NOT_FOUND;
    }
}
