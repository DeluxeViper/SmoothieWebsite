package com.ceruleansource.SmoothieWebsite.UI;

import com.ceruleansource.SmoothieWebsite.backend.Models.user.User;
import com.ceruleansource.SmoothieWebsite.backend.Services.MyUserDetailsService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;

@PageTitle("Register")
@Route("register")
public class RegisterView extends VerticalLayout {

    private PasswordField passwordField;
    private PasswordField confirmPasswordField;

    private MyUserDetailsService userDetailsService;
    private BeanValidationBinder<User> binder;
    private Button submitButton;

    /**
     * Disabling password validation before visit
     */
    private boolean enablePasswordValidation;

    public RegisterView(@Autowired MyUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;

        /**
         * Create the components we'll need
         */

        H2 title = new H2("Signup Form");

        TextField firstNameField = new TextField("First Name");
        TextField lastNameField = new TextField("Last Name");

        EmailField emailField = new EmailField("Email");

        passwordField = new PasswordField("Password");
        confirmPasswordField = new PasswordField("Confirm Password");

        Span errorMessage = new Span();

        submitButton = new Button("Join the community");
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        /**
         * Build the visible layout
         */

        FormLayout formLayout = new FormLayout(title, firstNameField, lastNameField,
                emailField, passwordField, confirmPasswordField, submitButton);

        formLayout.setMaxWidth("500px");
        formLayout.getStyle().set("margin", "0 auto");

        // Responsive form layout
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("490px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP));

        // The following components take full width regardless of column number
        formLayout.setColspan(title, 2);
        formLayout.setColspan(errorMessage, 2);
        formLayout.setColspan(submitButton, 2);

        // Styles to error message
        errorMessage.getStyle().set("color", "var(--lumo-error-text-color)");
        errorMessage.getStyle().set("padding", "15px 0");

        // Adding form to page
        add(formLayout);

        /**
         * Form Functionality
         */

        binder = new BeanValidationBinder<>(User.class);

        binder.forField(firstNameField).asRequired().bind("firstName");
        binder.forField(lastNameField).asRequired().bind("lastName");
        binder.forField(emailField).asRequired(new EmailValidator("Please select a valid email address"))
                .withValidator(this::emailValidator)
                .bind("email");
        binder.forField(passwordField).asRequired().withValidator(this::passwordValidator).bind("password");

        confirmPasswordField.addValueChangeListener(e -> {
            enablePasswordValidation = true;
            binder.validate();
        });

        binder.setStatusLabel(errorMessage);

        submitButton.addClickListener(e -> {
            try {
                User detailsBean = new User();

                binder.writeBean(detailsBean);

                // Default values
                detailsBean.setActive(true);
                detailsBean.setRoles("USER");
                detailsBean.setSmoothies(new HashSet<>());
                detailsBean.setIntake("0");

                System.out.println(detailsBean.toString());

                if (userDetailsService.addUser(detailsBean)){
                    showSuccess(detailsBean);
                } else {
                    Notification.show("Error! Failed to add user to database").addThemeVariants(NotificationVariant.LUMO_ERROR);
                }

            } catch (ValidationException validationException) {
                validationException.printStackTrace();
            }
        });
    }

    private void showSuccess(User detailsBean){
        Notification notification = Notification.show("Successfully created account, Welcome " + detailsBean.getFirstName() + "!");
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        submitButton.getUI().ifPresent(ui -> ui.navigate("login"));
    }

    /**
     *
     * Method to validate that:
     *  1) Password is at least 8 characters long
     *  2) Values in both fields match each other
     *
     * @param pass1
     * @param ctx
     * @return
     */
    private ValidationResult passwordValidator(String pass1, ValueContext ctx){
        if (pass1 == null || pass1.length() < 8){
            return ValidationResult.error("Password should be at least 8 characters long");
        }

        if (!enablePasswordValidation){
            // User has not visited the field yet, thus don't validate yet
            enablePasswordValidation = true;
            return ValidationResult.ok();
        }

        String confPass = confirmPasswordField.getValue();

        if (pass1 != null && pass1.equals(confPass)){
            return ValidationResult.ok();
        }

        return ValidationResult.error("Passwords do not match");
    }

    private ValidationResult emailValidator(String email, ValueContext ctx){
        boolean userExists = userDetailsService.userExists(email);
        if (userExists){
            return ValidationResult.error("Email is already registered");
        } else {
            return ValidationResult.ok();
        }
    }
}
