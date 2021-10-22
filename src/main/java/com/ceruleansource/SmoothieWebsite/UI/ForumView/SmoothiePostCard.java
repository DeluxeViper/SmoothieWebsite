package com.ceruleansource.SmoothieWebsite.UI.ForumView;

import com.ceruleansource.SmoothieWebsite.backend.Models.Post;
import com.github.appreciated.card.RippleClickableCard;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamResource;

import java.io.ByteArrayInputStream;

@CssImport("./src/styles/views/forum/smoothie-post-card.css")
public class SmoothiePostCard extends Div {
    // TODO: Transport CSS into CSS file
    // TODO: Fix CSS - Prettify CSS
    public SmoothiePostCard(Post post) {
        StreamResource resource = new StreamResource(post.getTitle() + ".jpg", () -> new ByteArrayInputStream(post.getPostImage()));
        Image postImage = new Image();
        postImage.setSrc(resource);
        postImage.setWidth("100%");
        postImage.getStyle().set("border-top-left-radius", "50px");
        postImage.getStyle().set("border-top-right-radius", "50px");
        RippleClickableCard card = new RippleClickableCard(
                componentEvent -> {
                    Notification.show(post.getTitle() + " clicked");
                    getUI().ifPresent(ui -> ui.navigate(PostView.class, post.getId()));
                },
                new VerticalLayout(postImage, new H1(post.getTitle()), new H3(post.getDescription()))
        );
        card.setWidth("100%");
        card.setHeight("100%");
        card.setBorderRadius("50px");
        add(card);
    }
}
