package by.bsuir.suite.page.person.panel;

import by.bsuir.suite.dto.person.ChangePasswordDto;
import by.bsuir.suite.dto.person.PersonDto;
import by.bsuir.suite.page.base.panel.HostelPanel;
import by.bsuir.suite.service.person.PersonService;
import by.bsuir.suite.session.HostelAuthenticatedWebSession;
import by.bsuir.suite.util.WicketUtils;
import by.bsuir.suite.validator.*;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author i.sukach
 */
public class ChangePasswordPanel extends HostelPanel {

    @SpringBean
    private PersonService personService;

    private final ChangePasswordDto changePasswordDto;

    public ChangePasswordPanel(String id, final ModalWindow window, final PersonDto personDto) {
        super(id);
        final FeedbackPanel feedbackPanel = new FeedbackPanel("changePasswordFeedback");
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);

        changePasswordDto = new ChangePasswordDto();

        IModel<ChangePasswordDto> model = new CompoundPropertyModel<ChangePasswordDto>(changePasswordDto);
        Form<ChangePasswordDto> changePasswordForm = new Form<ChangePasswordDto>("changePasswordForm", model);
        changePasswordForm.setOutputMarkupId(true);
        add(changePasswordForm);
        changePasswordForm.add(new AjaxButton("submitButton") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> components) {
                personService.changePassword(personDto.getId(), changePasswordDto.getNewPassword());
                ((HostelAuthenticatedWebSession) getSession()).setPassword(changePasswordDto.getNewPassword());
                personDto.setPasswordChanged(true);
                window.close(target);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> components) {
                target.add(feedbackPanel);
            }
        });
        changePasswordForm.add(new AjaxFallbackLink<Void>("cancelButton") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                window.close(target);
            }
        });

        FormComponent<String> oldPassword = WicketUtils.addPasswordFieldWithLabel("oldPassword", changePasswordForm, this);
        oldPassword.add(new OldPasswordValidator(personDto.getId()));
        FormComponent<String> newPassword = WicketUtils.addPasswordFieldWithLabel("newPassword", changePasswordForm, this);
        newPassword.add(new MinLengthValidator(ValidationConstants.PASSWORD_MIN_LENGTH,
                ValidationConstants.PASSWORD_MIN_LENGTH_ERROR_KEY));
        newPassword.add(new DigitsAndLettersOnlyValidator(ValidationConstants.PASSWORD_DIGITS_AND_LETTERS_ERROR_KEY));
        newPassword.add(new NewPasswordNotEqualsOldValidator(personDto.getId()));
        FormComponent<String> newPasswordConfirm
                = WicketUtils.addPasswordFieldWithLabel("newPasswordConfirm", changePasswordForm, this);

        changePasswordForm.add(new EqualPasswordInputValidator(newPassword, newPasswordConfirm));
    }
}
