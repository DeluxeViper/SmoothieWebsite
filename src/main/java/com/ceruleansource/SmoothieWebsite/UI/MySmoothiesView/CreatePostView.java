package com.ceruleansource.SmoothieWebsite.UI.MySmoothiesView;

import com.ceruleansource.SmoothieWebsite.UI.MainView.MainView;
import com.ceruleansource.SmoothieWebsite.UI.NutritionalInfoView;
import com.ceruleansource.SmoothieWebsite.backend.Models.Post;
import com.ceruleansource.SmoothieWebsite.backend.Models.Smoothie;
import com.ceruleansource.SmoothieWebsite.backend.Services.PostService;
import com.ceruleansource.SmoothieWebsite.backend.Services.SmoothieService;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.dom.DomEventListener;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

@PageTitle("Create Your Post")
@CssImport("./src/styles/views/createpost/create-post-view.css")
@Route(value = "create-post", layout = MainView.class)
public class CreatePostView extends Div implements HasUrlParameter<Long>, AfterNavigationObserver {

    @Autowired
    private SmoothieService smoothieService;

    // Overall info reference fields
    private Long smoothieId;
    private Smoothie smoothie;
    private Post postToCreate;

    // RHS - Uploading/Image fields
    private VerticalLayout imageContainer;
    private Image postImage;
    private Upload uploadPostImage;

    // LHS - vertical layout, nutritional view fields
    private TextField postTitle;
    private TextArea postDescription;
    private Details nutrViewDetails;
    private NutritionalInfoView nutritionalInfoView;
    private VerticalLayout LHSVLayout;
    private BeanValidationBinder<Post> postBinder;

    // Submit/Go back button fields
    private final Button submitPostBtn;
    private Button backBtn;


    public CreatePostView(@Autowired PostService postService){
        setId("create-post-view");
        postToCreate = new Post();

        HorizontalLayout overallHLayout = new HorizontalLayout();
        overallHLayout.setId("overall-h-layout");

        initLHSVLayout();
        initImageContainer();
        initUploader();

        // Binder & Error message init
        postBinder = new BeanValidationBinder<>(Post.class);

        postBinder.forField(postTitle).asRequired(new StringLengthValidator("Please enter a title", 1, 32)).bind("title");
        postBinder.forField(postDescription).asRequired().bind("description");

        Span errorMessage = new Span();
        errorMessage.getStyle().set("color", "var(--lumo-error-text-color)");
        errorMessage.getStyle().set("padding", "0 0");

        postBinder.setStatusLabel(errorMessage);

        submitPostBtn = new Button("Submit");
        submitPostBtn.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        submitPostBtn.setId("submit-post-btn");
        submitPostBtn.addThemeName("primary");
        submitPostBtn.addClickListener(buttonClickEvent -> {
            try {
                postToCreate.setSmoothie(smoothie);
                postToCreate.setDateTime(LocalDateTime.now());
                postBinder.writeBean(postToCreate);
//                System.out.println("PostToCreate bean: " + postToCreate);

                // Save Post
                // Note: Post image is already set within uploader -> see initUploader()
                if (postService.savePost(postToCreate)){
                    getUI().ifPresent(ui -> {
                        ui.navigate("my-smoothies");
                        Notification.show("Successfully added post!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    });
                } else {
                    Notification.show("Error occurred while trying to save post").addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            } catch (ValidationException validationException) {
                validationException.printStackTrace();
            }

        });

        backBtn = new Button(new Icon(VaadinIcon.ARROW_LEFT));
        backBtn.setId("back-btn");
        backBtn.addThemeName("icon");
        backBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        backBtn.addClickListener(buttonClickEvent -> getUI().ifPresent(ui -> {
            ui.navigate("my-smoothies");
        }));

        VerticalLayout imageVLayout = new VerticalLayout(imageContainer, uploadPostImage);
        imageVLayout.setId("image-v-layout");
        overallHLayout.add(LHSVLayout, imageVLayout);
        H1 title = new H1("Create Your Smoothie Post");
        title.setId("post-title");
        add(backBtn, title, overallHLayout);
    }

    private void initLHSVLayout(){
        LHSVLayout = new VerticalLayout();
        postTitle = new TextField("Title");
        postTitle.setClassName("text-field");
        postDescription = new TextArea("Description");
        postDescription.setClassName("text-field");
        postDescription.setId("post-description");
        LHSVLayout.add(postTitle, postDescription);
        LHSVLayout.setId("LHS-v-layout");
    }

    private void initUploader() {
        MemoryBuffer buffer = new MemoryBuffer();
        uploadPostImage = new Upload(buffer);
        uploadPostImage.setId("uploader");
        uploadPostImage.setAcceptedFileTypes("image/jpeg", "image/jpg", "image/png");
        uploadPostImage.addSucceededListener(succeededEvent -> {
            try {
                BufferedImage inputImage = ImageIO.read(buffer.getInputStream());
                ByteArrayOutputStream pngContent = new ByteArrayOutputStream();
                ImageIO.write(inputImage, "png", pngContent);
                postToCreate.setPostImage(pngContent.toByteArray());
                postToCreate.setPostImageName(succeededEvent.getFileName());
                showImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        uploadPostImage.getElement().addEventListener("file-remove", (DomEventListener) domEvent -> {
            postToCreate.setPostImage(null);
            imageContainer.removeAll();
        });
    }

    private void showImage() {
        StreamResource sr = new StreamResource("post", () -> new ByteArrayInputStream(postToCreate.getPostImage()));
        sr.setContentType("image/png");
        postImage = new Image(sr, "post-picture");
        postImage.setId("post-image");
        imageContainer.removeAll();
        imageContainer.add(postImage);

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


    // Note: This gets called after @PostConstruct
    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        smoothie = smoothieService.getSmoothie(smoothieId);
//        System.out.println("CreatePostView: Smoothie: " + smoothie);

        postTitle.setPlaceholder(smoothie.getName());

        if (smoothie.getPost() != null){
            postToCreate = smoothie.getPost();
            submitPostBtn.setText("Update Post");
            setText("Edit Your Post");
            initPostView();
        }

        initNutrViewDetails();

        nutritionalInfoView = new NutritionalInfoView(smoothie.getTotalNutritionalInfoGrams(), smoothie.getTotalNutritionalInfoPercentage());
        nutritionalInfoView.setId("nutrition-facts-view");
        LHSVLayout.add(nutritionalInfoView);
        add(submitPostBtn);
    }

    private void initPostView() {
        postTitle.setValue(postToCreate.getTitle());
        postDescription.setValue(postToCreate.getDescription());

        // Updating uploader with image
        JsonArray jsonArray = Json.createArray();
        JsonObject jsonObject = Json.createObject();
        jsonObject.put("name", postToCreate.getPostImageName());
        jsonObject.put("progress", 100);
        jsonObject.put("complete", true);
        jsonArray.set(0, jsonObject);
        uploadPostImage.getElement().setPropertyJson("files", jsonArray);

        // Showing post image
        showImage();
    }

    private void initNutrViewDetails() {
        String smoothieIngredients = smoothie.getIngredientNames().toString();
        String smoothieName = smoothie.getName();
        Html smoothieNameText = new Html("<span><b>Name:</b> " + smoothieName + "</span>");
        smoothieNameText.setId("smoothie-name-text");
        Html smoothieIngredientsText = new Html("<span></br><b>Ingredients:</b> " + smoothieIngredients + "</span>");
        smoothieIngredientsText.setId("smoothie-ingredients-text");
        nutrViewDetails = new Details();
        nutrViewDetails.setSummaryText("Show Smoothie Details");
        nutrViewDetails.addContent(smoothieNameText, smoothieIngredientsText);
        LHSVLayout.addComponentAsFirst(nutrViewDetails);
        nutrViewDetails.addOpenedChangeListener(openedChangeEvent -> {
            if (openedChangeEvent.isOpened()){
                nutrViewDetails.setSummary(new Html("<b>Hide Smoothie Details</b>"));
            } else {
                nutrViewDetails.setSummaryText("Show Smoothie Details");
            }
        });
        nutrViewDetails.setId("nutr-view-details");
    }
}
