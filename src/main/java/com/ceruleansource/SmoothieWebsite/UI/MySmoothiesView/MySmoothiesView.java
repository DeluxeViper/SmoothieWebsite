package com.ceruleansource.SmoothieWebsite.UI.MySmoothiesView;

import com.ceruleansource.SmoothieWebsite.UI.CreateSmoothieView.CreateSmoothieDialog;
import com.ceruleansource.SmoothieWebsite.UI.MainView.MainView;
import com.ceruleansource.SmoothieWebsite.UI.NutritionalInfoView;
import com.ceruleansource.SmoothieWebsite.backend.Authentication.UserSession;
import com.ceruleansource.SmoothieWebsite.backend.Models.Smoothie;
import com.ceruleansource.SmoothieWebsite.backend.Services.SmoothieService;
import com.vaadin.componentfactory.Tooltip;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.gridpro.GridPro;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;


@Route(value = "my-smoothies", layout = MainView.class)
public class MySmoothiesView extends Div {

    GridPro<Smoothie> smoothieGrid = new GridPro<>();

    Button addSmoothieBtn = new Button("Add Smoothie");
    Button deleteSmoothiesBtn = new Button("Delete Smoothies");

    // Selected smoothies grid on the multiselect
    Set<Smoothie> selectedSmoothies;

    @Autowired
    public MySmoothiesView(SmoothieService smoothieService, UserSession userSession) {
        deleteSmoothiesBtn.addThemeVariants(ButtonVariant.LUMO_ERROR);
        deleteSmoothiesBtn.setVisible(false);
        deleteSmoothiesBtn.addClickListener(buttonClickEvent -> deleteSmoothieClick(smoothieService, userSession));
        addSmoothieBtn.addClickListener(buttonClickEvent -> openCreateSmoothieDialog(userSession, smoothieService));
        H1 pageTitle = new H1("Your Saved Recipes");
        add(new VerticalLayout(pageTitle, initSmoothieGrid(smoothieService, userSession),
                addSmoothieBtn, deleteSmoothiesBtn));
    }

    private void deleteSmoothieClick(SmoothieService smoothieService, UserSession userSession) {
        smoothieService.deleteSmoothies(selectedSmoothies);
        refreshSmoothieGrid(smoothieService, userSession);
    }

    private Grid<Smoothie> initSmoothieGrid(SmoothieService smoothieService, UserSession userSession) {
        smoothieGrid.setWidthFull();
        smoothieGrid.setHeightByRows(true);
        smoothieGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        smoothieGrid.addEditColumn(Smoothie::getName).text((smoothie, newName) -> {
            smoothie.setName(newName);
            if (smoothieService.updateSmoothie(smoothie)) {
                Notification.show("Successfully updated smoothie")
                        .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            }
        }).setHeader("Smoothie Name").setAutoWidth(true).setSortable(true);
        smoothieGrid.addColumn(Smoothie::getIngredientNames)
                .setHeader("Ingredients").setAutoWidth(true);
        smoothieGrid.addComponentColumn(this::showTotalNutrInfo).setHeader("Total Nutritional Information").setAutoWidth(true);
        smoothieGrid.addComponentColumn(smoothie -> {
            Button postButton = new Button("Post");
            postButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            postButton.addClickListener(buttonClickEvent -> {
                getUI().ifPresent(ui -> ui.navigate(CreatePostView.class, smoothie.getId()));
            });
            if (smoothie.getPost() != null){
                postButton.setEnabled(false);
            }
            return postButton;
        }).setAutoWidth(true).setKey("post_column");
        smoothieGrid.setItems(smoothieService.getSmoothiesForCurrentUser(userSession.getUser()));
        smoothieGrid.asMultiSelect().addValueChangeListener(this::gridOnMultiSelect);
//        smoothieGrid.addSelectionListener(selectionEvent -> System.out.println("SelectionListener: " + selectionEvent));
//        smoothieGrid.addItemClickListener(smoothieItemClickEvent -> {
//            System.out.println("ItemClickListener: " + smoothieItemClickEvent);
//            System.out.println(smoothieItemClickEvent.getItem());
//        });
        return smoothieGrid;
    }

    private void gridOnMultiSelect(AbstractField.ComponentValueChangeEvent<Grid<Smoothie>, Set<Smoothie>> gridSetComponentValueChangeEvent) {
        if (gridSetComponentValueChangeEvent.getValue().size() > 1) {
            deleteSmoothiesBtn.setText("Delete Smoothies");
        } else {
            deleteSmoothiesBtn.setText("Delete Smoothie");
        }
        if (!gridSetComponentValueChangeEvent.getValue().isEmpty()) {
            deleteSmoothiesBtn.setText("Delete Smoothies");
            deleteSmoothiesBtn.setVisible(true);
            selectedSmoothies = gridSetComponentValueChangeEvent.getValue();
        } else {
            deleteSmoothiesBtn.setVisible(false);
            selectedSmoothies = null;
        }
    }

    private Button showTotalNutrInfo(Smoothie smoothie) {
        Button showTotalNutrBtn = new Button(VaadinIcon.EYE.create());
        showTotalNutrBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        Tooltip totalNutrTooltip = new Tooltip();
        totalNutrTooltip.setThemeName("light");
        NutritionalInfoView totalNutrView = new NutritionalInfoView(smoothie.getTotalNutritionalInfoGrams(), smoothie.getTotalNutritionalInfoPercentage());
        totalNutrTooltip.add(totalNutrView);
        totalNutrTooltip.setCloseButtonVisible(true);
        showTotalNutrBtn.addClickListener(e -> totalNutrTooltip.open());
        add(totalNutrTooltip);
        return showTotalNutrBtn;
    }

    public void openCreateSmoothieDialog(UserSession userSession, SmoothieService smoothieService) {
        CreateSmoothieDialog createSmoothieDialog = new CreateSmoothieDialog(userSession, smoothieService);
        createSmoothieDialog.open();
        createSmoothieDialog.addDetachListener(detachEvent -> refreshSmoothieGrid(smoothieService, userSession));
    }

    private void refreshSmoothieGrid(SmoothieService smoothieService, UserSession userSession) {
        smoothieGrid.setItems(smoothieService.getSmoothiesForCurrentUser(userSession.getUser()));
    }
}
