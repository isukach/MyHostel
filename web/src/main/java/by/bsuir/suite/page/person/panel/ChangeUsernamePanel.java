package by.bsuir.suite.page.person.panel;

import by.bsuir.suite.dto.person.ChangeUsernameDto;
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
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author i.sukach
 */
public class ChangeUsernamePanel extends HostelPanel {

    @SpringBean
    private PersonService personService;

    private final ChangeUsernameDto changeUsernameDto;

    public ChangeUsernamePanel(String id, final ModalWindow window, final PersonDto personDto) {
        super(id);
        final FeedbackPanel feedbackPanel = new FeedbackPanel("changeUsernameFeedback");
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);

        changeUsernameDto = new ChangeUsernameDto();

        IModel<ChangeUsernameDto> model = new CompoundPropertyModel<ChangeUsernameDto>(changeUsernameDto);

        Form<ChangeUsernameDto> changeUsernameForm = new Form<ChangeUsernameDto>("changeUsernameForm", model);
        changeUsernameForm.setOutputMarkupId(true);
        add(changeUsernameForm);
        changeUsernameForm.add(new AjaxButton("submitButton") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                personService.changeUsername(personDto.getId(), changeUsernameDto.getNewUsername());
                ((HostelAuthenticatedWebSession) getSession()).setUsername(changeUsernameDto.getNewUsername());
                ((HostelAuthenticatedWebSession) getSession()).getUser().setUserName(changeUsernameDto.getNewUsername());
                personDto.setUsernameChanged(true);
                window.close(target);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(feedbackPanel);
            }
        });
        changeUsernameForm.add(new AjaxFallbackLink<Void>("cancelButton") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                window.close(target);
            }
        });

        WicketUtils.addTextFieldWithLabel(
                "oldUsername", changeUsernameForm, true, this, new OldUsernameValidator(personDto.getId()));
        FormComponent<String> newUsername = WicketUtils.addTextFieldWithLabel("newUsername", changeUsernameForm, true, this, null);
        newUsername.add(new MaxLengthValidator(
                ValidationConstants.USERNAME_MAX_LENGTH, ValidationConstants.USERNAME_MAX_LENGTH_ERROR_KEY));
        newUsername.add(new MinLengthValidator(
                ValidationConstants.USERNAME_MIN_LENGTH, ValidationConstants.USERNAME_MIN_LENGTH_ERROR_KEY));
        newUsername.add(new DigitsAndLettersOnlyValidator(ValidationConstants.USERNAME_DIGITS_AND_LETTERS_ERROR_KEY));
        newUsername.add(new UsernameExistsValidator());
    }
}
