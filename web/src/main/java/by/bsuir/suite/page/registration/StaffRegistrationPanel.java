package by.bsuir.suite.page.registration;

import by.bsuir.suite.dto.registration.StaffRegistrationDto;
import by.bsuir.suite.page.base.NotificationWindow;
import by.bsuir.suite.page.base.panel.HostelPanel;
import by.bsuir.suite.page.person.panel.AdminRolePanel;
import by.bsuir.suite.service.registration.RegistrationService;
import by.bsuir.suite.util.Roles;
import by.bsuir.suite.validator.DigitsAndLettersOnlyValidator;
import by.bsuir.suite.validator.MaxLengthValidator;
import by.bsuir.suite.validator.MinLengthValidator;
import by.bsuir.suite.validator.UsernameExistsValidator;
import by.bsuir.suite.validator.ValidationConstants;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormValidatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.time.Duration;

import static by.bsuir.suite.util.WicketUtils.addPasswordFieldWithLabel;
import static by.bsuir.suite.util.WicketUtils.addTextFieldWithLabel;

/**
 * @author i.sukach
 */
@AuthorizeAction(action = Action.RENDER, roles = {Roles.REGISTRAR, Roles.SUPER_USER})
public class StaffRegistrationPanel extends HostelPanel {

    @SpringBean
    private RegistrationService registrationService;

    private ModalWindow registrationSuccessWindow;

    public StaffRegistrationPanel(String id) {
        super(id);

        final FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackPanel");
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);

        StaffRegistrationDto dto = new StaffRegistrationDto();
        dto.setRole(Roles.EDUCATOR); //default role within group of checkboxes

        registrationSuccessWindow = new NotificationWindow("registrationSuccessWindow",
                "dialog.register.success.header", "dialog.register.success.text", false) {
            @Override
            public void onConfirm(AjaxRequestTarget target) {
                close(target);
            }
        };
        registrationSuccessWindow.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
            @Override
            public void onClose(AjaxRequestTarget target) {
                setResponsePage(StaffRegistrationPage.class);
            }
        });
        add(registrationSuccessWindow);

        IModel<StaffRegistrationDto> compoundModel = new CompoundPropertyModel<StaffRegistrationDto>(dto);
        Form<StaffRegistrationDto> staffForm = new Form<StaffRegistrationDto>("adminForm", compoundModel);
        add(staffForm);
        staffForm.setOutputMarkupId(true);

        addTextFieldWithLabel("firstName", staffForm, true, this, new MaxLengthValidator(
                ValidationConstants.FIRST_NAME_MAX_LENGTH, ValidationConstants.FIRST_NAME_LENGTH_ERROR_KEY));
        addTextFieldWithLabel("lastName", staffForm, true, this, new MaxLengthValidator(
                ValidationConstants.LAST_NAME_MAX_LENGTH, ValidationConstants.LAST_NAME_LENGTH_ERROR_KEY));
        addTextFieldWithLabel("middleName", staffForm, false, this, new MaxLengthValidator(
                ValidationConstants.MIDDLE_NAME_MAX_LENGTH, ValidationConstants.MIDDLE_NAME_LENGTH_ERROR_KEY));
        FormComponent<String> usernameField = addTextFieldWithLabel("username", staffForm, true, this, null);
        usernameField.add(new MaxLengthValidator(
                ValidationConstants.USERNAME_MAX_LENGTH, ValidationConstants.USERNAME_MAX_LENGTH_ERROR_KEY));
        usernameField.add(new MinLengthValidator(
                ValidationConstants.USERNAME_MIN_LENGTH, ValidationConstants.USERNAME_MIN_LENGTH_ERROR_KEY));
        usernameField.add(new DigitsAndLettersOnlyValidator(ValidationConstants.USERNAME_DIGITS_AND_LETTERS_ERROR_KEY));
        usernameField.add(new UsernameExistsValidator());
        FormComponent<String> passwordField = addPasswordFieldWithLabel("password", staffForm, this);
        passwordField.add(new MinLengthValidator(
                ValidationConstants.PASSWORD_MIN_LENGTH, ValidationConstants.PASSWORD_MIN_LENGTH_ERROR_KEY));
        passwordField.add(new MaxLengthValidator(
                ValidationConstants.PASSWORD_MAX_LENGTH, ValidationConstants.PASSWORD_MAX_LENGTH_ERROR_KEY));
        passwordField.add(new DigitsAndLettersOnlyValidator(ValidationConstants.PASSWORD_DIGITS_AND_LETTERS_ERROR_KEY));
        FormComponent<String> passwordConfirmField = addPasswordFieldWithLabel("passwordConfirm", staffForm, this);
        staffForm.add(new EqualPasswordInputValidator(passwordField, passwordConfirmField));

        staffForm.add(new AdminRolePanel("rolePanel"));

        AjaxFormValidatingBehavior.addToAllFormComponents(staffForm, "onkeyup", Duration.ONE_SECOND);

        staffForm.add(new AjaxButton("submitAdminForm", staffForm) {

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                StaffRegistrationDto dto = (StaffRegistrationDto) form.getModelObject();
                registrationService.registerAdministrator(dto);
                registrationSuccessWindow.show(target);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(feedbackPanel);
            }
        });
    }
}
