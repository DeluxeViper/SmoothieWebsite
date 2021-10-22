package com.ceruleansource.SmoothieWebsite.UI.ForumView;

import com.ceruleansource.SmoothieWebsite.UI.MainView.MainView;
import com.ceruleansource.SmoothieWebsite.UI.NutritionalInfoView;
import com.ceruleansource.SmoothieWebsite.backend.Models.Post;
import com.ceruleansource.SmoothieWebsite.backend.Services.PostService;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;

@PageTitle("Post View")
@CssImport("./src/styles/views/viewpost/view-post.css")
@Route(value = "post-view", layout = MainView.class)
public class PostView extends Div implements HasUrlParameter<Long>, AfterNavigationObserver {

    // Parameter information
    Long postId;

    // Post information
    Post post;
    H1 pageTitle;
    H3 postTitle;
    Span postDescription;

    Image postImage;
    NutritionalInfoView nutritionalInfoView;

    @Autowired
    PostService postService;

    public PostView(){
        setId("post-view");

        pageTitle = new H1("Forum Post");
        postTitle = new H3("Post Title");
        postDescription = new Span("Post description");
        postImage = new Image();
        postImage.setId("post-image");
        nutritionalInfoView = new NutritionalInfoView();
        nutritionalInfoView.setId("nutr-info-view");

        VerticalLayout verticalLayout = new VerticalLayout(pageTitle, postTitle, postDescription, nutritionalInfoView);
        verticalLayout.setId("v-layout");
        HorizontalLayout horizontalLayout = new HorizontalLayout(verticalLayout, postImage);
        horizontalLayout.setId("h-layout");
        System.out.println(post);
        add(horizontalLayout);
    }

    private void populatePost() {
        this.post = postService.getPost(this.postId);
//        System.out.println("PostView: post: " + post);
//        System.out.println("PostView: post: " + Arrays.toString(post.getPostImage()));
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter Long postId) {
        this.postId = postId;
        populatePost();
//        System.out.println("Postid: " + postId);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        if (post != null) {
            postTitle.setText(post.getTitle());

            StreamResource resource = new StreamResource(post.getTitle() +".jpg", () -> new ByteArrayInputStream(post.getPostImage()));
            postImage.setSrc(resource);

            postDescription.setText(post.getDescription());
            nutritionalInfoView.setNutritionalInformation(post.getSmoothie().getTotalNutritionalInfoGrams(), post.getSmoothie().getTotalNutritionalInfoPercentage());
        }
    }
}
