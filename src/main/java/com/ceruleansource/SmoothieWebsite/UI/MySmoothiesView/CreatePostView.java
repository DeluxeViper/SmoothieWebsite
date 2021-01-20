package com.ceruleansource.SmoothieWebsite.UI.MySmoothiesView;

import com.ceruleansource.SmoothieWebsite.UI.NutritionalInfoView;
import com.ceruleansource.SmoothieWebsite.backend.Models.Post;
import com.ceruleansource.SmoothieWebsite.backend.Models.Smoothie;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.router.Route;

import java.awt.*;

public class CreatePostView extends Div {

    public CreatePostView(Smoothie smoothie){
        TextField postTitle = new TextField("Title");
        TextArea description = new TextArea("Description");
        NutritionalInfoView nutritionalInfoView = new NutritionalInfoView(smoothie.getTotalNutritionalInfoGrams(), smoothie.getTotalNutritionalInfoPercentage());
        Upload uploadPostImage = new Upload();

    }
}
