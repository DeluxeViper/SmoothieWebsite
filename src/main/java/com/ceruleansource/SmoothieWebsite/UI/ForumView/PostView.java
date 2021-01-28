package com.ceruleansource.SmoothieWebsite.UI.ForumView;

import com.ceruleansource.SmoothieWebsite.UI.MainView.MainView;
import com.ceruleansource.SmoothieWebsite.UI.NutritionalInfoView;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.router.*;

@PageTitle("Post View")
@Route(value = "post-view", layout = MainView.class)
public class PostView extends Div implements HasUrlParameter<Long>, AfterNavigationObserver {

    // Parameter information
    Long postId;

    // Post information
    H1 pageTitle;
    H3 postTitle;
    Span postDescription;
    Image postImage;
    NutritionalInfoView nutritionalInfoView;


    public PostView(){
        setId("post-view");
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter Long postId) {
        this.postId = postId;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {

    }
}
