package com.ceruleansource.SmoothieWebsite.UI;

import com.ceruleansource.SmoothieWebsite.UI.MainView.MainView;
import com.ceruleansource.SmoothieWebsite.backend.Authentication.UserSession;
import com.ceruleansource.SmoothieWebsite.backend.Models.Smoothie;
import com.ceruleansource.SmoothieWebsite.backend.Services.SmoothieService;
import com.vaadin.componentfactory.Tooltip;
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


@Route(value = "my-smoothies", layout = MainView.class)
public class MySmoothiesView extends Div {

    GridPro<Smoothie> smoothieGrid = new GridPro<>();

    Button addSmoothieBtn = new Button("Add Smoothie");
    Button deleteSmoothiesBtn = new Button("Delete Smoothies");

    @Autowired
    public MySmoothiesView(SmoothieService smoothieService, UserSession userSession){
        H1 pageTitle = new H1("Your Saved Recipes");
        add(new VerticalLayout(pageTitle, initSmoothieGrid(smoothieService, userSession)));
    }

    private Grid<Smoothie> initSmoothieGrid(SmoothieService smoothieService, UserSession userSession) {
        smoothieGrid.setWidthFull();
        smoothieGrid.setHeightByRows(true);
        smoothieGrid.addEditColumn(Smoothie::getName).text((smoothie, newName) -> {
            smoothie.setName(newName);
            if (smoothieService.updateSmoothie(smoothie)){
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
           return postButton;
        }).setAutoWidth(true);
        smoothieGrid.setItems(smoothieService.getSmoothiesForCurrentUser(userSession.getUser()));
        return smoothieGrid;
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
}
