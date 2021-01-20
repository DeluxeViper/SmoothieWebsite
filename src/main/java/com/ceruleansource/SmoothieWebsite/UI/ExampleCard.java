package com.ceruleansource.SmoothieWebsite.UI;

import com.github.appreciated.card.ClickableCard;
import com.github.appreciated.card.RippleClickableCard;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;

public class ExampleCard extends RippleClickableCard {

    public ExampleCard(){
        Div div = new Div();
        div.getStyle().set("background-color", "coral");
        div.setWidth("200px");
        div.setHeight("200px");
        H2 h2 = new H2("Hola");

        this.setWidth("300px");
        this.setHeight("300px");
        this.addClickListener(componentEvent -> {
            Notification.show("Ripple card clicked");
        });
        add(h2, div);
    }
}
