package com.ceruleansource.SmoothieWebsite.UI.ForumView;

import com.ceruleansource.SmoothieWebsite.backend.Models.Post;
import com.github.appreciated.card.RippleClickableCard;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamResource;

import java.io.ByteArrayInputStream;

@CssImport("./src/styles/views/forum/smoothie-post-card.css")
public class SmoothiePostCard extends Div {
    // TODO: Transport CSS into CSS file
    // TODO: Fix CSS - Prettify CSS
    public SmoothiePostCard(Post post) {
        setId("smoothie-post-card");
        StreamResource resource = new StreamResource(post.getTitle() + ".jpg", () -> new ByteArrayInputStream(post.getPostImage()));
        Image postImage = new Image();
        postImage.setId("post-image");
        postImage.setSrc(resource);

        Paragraph datePosted = new Paragraph("Date posted: " + post.getDateTime().toLocalDate().toString());
        H1 postTitle = new H1(post.getTitle());
        Label postDescription = new Label(post.getDescription());
        postDescription.setId("post-description");
        postTitle.setId("post-title");
        datePosted.setId("post-date");

        VerticalLayout cardContent = new VerticalLayout(postTitle,
                postDescription,
                datePosted);
        cardContent.setId("card-content");
        RippleClickableCard card = new RippleClickableCard(
                componentEvent -> {
                    Notification.show(post.getTitle() + " clicked");
                    getUI().ifPresent(ui -> ui.navigate(PostView.class, post.getId()));
                },
                new VerticalLayout(postImage, cardContent)
        );
        card.setId("card");
        card.setWidth("100%");
        card.setHeight("100%");
        card.setBorderRadius("50px");
        add(card);
    }
}
