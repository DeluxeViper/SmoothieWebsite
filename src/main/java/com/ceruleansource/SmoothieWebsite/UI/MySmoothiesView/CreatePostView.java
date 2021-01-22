package com.ceruleansource.SmoothieWebsite.UI.MySmoothiesView;

import com.ceruleansource.SmoothieWebsite.UI.MainView.MainView;
import com.ceruleansource.SmoothieWebsite.UI.NutritionalInfoView;
import com.ceruleansource.SmoothieWebsite.backend.Models.Post;
import com.ceruleansource.SmoothieWebsite.backend.Models.Smoothie;
import com.ceruleansource.SmoothieWebsite.backend.Services.SmoothieService;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.dom.DomEventListener;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@CssImport("./src/styles/views/createpost/create-post-view.css")
@Route(value = "create-post", layout = MainView.class)
public class CreatePostView extends Div implements HasUrlParameter<Long>, AfterNavigationObserver {

    @Autowired
    private SmoothieService smoothieService;

    private H1 title;

    private Long smoothieId;
    private Smoothie smoothie;
    private Post postToCreate;

    private VerticalLayout imageContainer;
    private Image postImage;

    private NutritionalInfoView nutritionalInfoView;
    private VerticalLayout overallVLayout;


    public CreatePostView(){
        setId("create-post-view");
        setHeight("100vh");
        setWidth("100vw");
        HorizontalLayout overallHLayout = new HorizontalLayout();
        overallHLayout.setId("overall-h-layout");
        overallHLayout.setSizeFull();
        overallVLayout = new VerticalLayout();
        overallVLayout.setId("overall-v-layout");
        overallVLayout.setHeightFull();

        postToCreate = new Post();
        initImageContainer();
        TextField postTitle = new TextField("Title");
        postTitle.setClassName("text-field");
        TextArea description = new TextArea("Description");
        description.setClassName("text-field");
        description.setId("post-description");

        MemoryBuffer buffer = new MemoryBuffer();
        Upload uploadPostImage = new Upload(buffer);
        uploadPostImage.setId("uploader");
        uploadPostImage.setAcceptedFileTypes("image/jpeg", "image/jpg", "image/png");
        uploadPostImage.addSucceededListener(succeededEvent -> {
            try {
                BufferedImage inputImage = ImageIO.read(buffer.getInputStream());
                ByteArrayOutputStream pngContent = new ByteArrayOutputStream();
                ImageIO.write(inputImage, "png", pngContent);
                saveProfilePicture(pngContent.toByteArray());
                showImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        uploadPostImage.getElement().addEventListener("file-remove", (DomEventListener) domEvent -> {
            postToCreate.setPostImage(null);
            imageContainer.removeAll();
        });
        overallVLayout.add(postTitle, description);

        VerticalLayout imageVLayout = new VerticalLayout(imageContainer, uploadPostImage);
        imageVLayout.setId("image-v-layout");
        imageVLayout.setHeightFull();
        overallHLayout.add(overallVLayout, imageVLayout);
        title = new H1("Create Your Smoothie Post");
        title.setId("post-title");
        add(title, overallHLayout);
    }

    private void showImage() {
        StreamResource sr = new StreamResource("post", () -> new ByteArrayInputStream(postToCreate.getPostImage()));
        sr.setContentType("image/png");
        postImage = new Image(sr, "post-picture");
        postImage.setId("post-image");
        imageContainer.removeAll();
        imageContainer.add(postImage);
    }


    private void saveProfilePicture(byte[] imageBytes) {
        postToCreate.setPostImage(imageBytes);
    }

    private void initImageContainer() {
        imageContainer = new VerticalLayout();
        imageContainer.setId("image-container");
        add(imageContainer);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter Long smoothieId) {
        this.smoothieId = smoothieId;
    }


    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        smoothie = smoothieService.getSmoothie(smoothieId);
        System.out.println("CreatePostView: Smoothie: " + smoothie);

        nutritionalInfoView = new NutritionalInfoView(smoothie.getTotalNutritionalInfoGrams(), smoothie.getTotalNutritionalInfoPercentage());
        overallVLayout.add(nutritionalInfoView);
    }
}
