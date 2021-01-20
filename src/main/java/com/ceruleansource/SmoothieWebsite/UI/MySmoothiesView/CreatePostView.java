package com.ceruleansource.SmoothieWebsite.UI.MySmoothiesView;

import ch.qos.logback.core.BasicStatusManager;
import com.ceruleansource.SmoothieWebsite.UI.MainView.MainView;
import com.ceruleansource.SmoothieWebsite.UI.NutritionalInfoView;
import com.ceruleansource.SmoothieWebsite.backend.Models.Post;
import com.ceruleansource.SmoothieWebsite.backend.Models.Smoothie;
import com.ceruleansource.SmoothieWebsite.backend.Services.SmoothieService;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.dom.DomEvent;
import com.vaadin.flow.dom.DomEventListener;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Route(value = "create-post", layout = MainView.class)
public class CreatePostView extends Div implements HasUrlParameter<Long>, AfterNavigationObserver {

    @Autowired
    private SmoothieService smoothieService;

    private Long smoothieId;
    private Smoothie smoothie;
    private Post postToCreate;

    private VerticalLayout imageContainer;
    private Image postImage;

    private NutritionalInfoView nutritionalInfoView;
    private VerticalLayout overallVLayout;


    public CreatePostView(){
        setHeight("100vh");
        setWidth("100vw");
        HorizontalLayout overallHLayout = new HorizontalLayout();
        overallHLayout.setSizeFull();
        overallVLayout = new VerticalLayout();
        overallVLayout.setHeightFull();

        postToCreate = new Post();
        initImageContainer();
        TextField postTitle = new TextField("Title");
        TextArea description = new TextArea("Description");

        MemoryBuffer buffer = new MemoryBuffer();
        Upload uploadPostImage = new Upload(buffer);
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
        imageVLayout.setHeightFull();
        overallHLayout.add(overallVLayout, imageVLayout);
        add(new H1("Create Your Post"), overallHLayout);
    }

    private void showImage() {
        StreamResource sr = new StreamResource("post", () -> new ByteArrayInputStream(postToCreate.getPostImage()));
        sr.setContentType("image/png");
        postImage = new Image(sr, "post-picture");
        postImage.setHeight("100%");
        imageContainer.removeAll();
        imageContainer.add(postImage);
    }


    private void saveProfilePicture(byte[] imageBytes) {
        postToCreate.setPostImage(imageBytes);
    }

    private void initImageContainer() {
        imageContainer = new VerticalLayout();
        imageContainer.setWidth("200px");
        imageContainer.setHeight("200px");
        imageContainer.getStyle().set("overflow-x", "auto");
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
