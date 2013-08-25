package by.bsuir.suite.page.work.panel;

import by.bsuir.suite.dto.work.JobOfferDto;
import by.bsuir.suite.page.duty.panel.ConfirmationAnswer;
import by.bsuir.suite.page.work.window.ValidationWindow;
import by.bsuir.suite.validator.*;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormValidatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.yui.calendar.DateField;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.util.time.Duration;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;

import java.util.Date;

/**
 * User: Matveyenka Denis
 * Date: 21.07.13
 */
public abstract class CreateEditJobOfferPanel extends Panel {

    private JobOfferDto jobOfferDto = new JobOfferDto();

    private Form<Void> jobOfferForm;

    private FeedbackPanel feedbackPanel;

    public CreateEditJobOfferPanel(String id, JobOfferDto jobOfferDto, final ConfirmationAnswer answer, final ModalWindow modalWindow) {
        super(id);
        this.jobOfferDto = jobOfferDto;
        feedbackPanel = new FeedbackPanel("jobOfferValidationPanel");
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);
        createHeader();
        addJobOfferForm();

        Label dateLabel = new Label("dateLabel", new StringResourceModel("label.date", this, null));
        DateField dateField = new DateField("dateField", new PropertyModel<Date>(
                jobOfferDto, "date"));
        dateField.add(new JobOfferDateFieldValidator());
        addOnTheForm(dateLabel, dateField);

        Label descriptionLabel = new Label("descriptionLabel", new StringResourceModel("label.description", this, null));
        RequiredTextField<String> descriptionField = new RequiredTextField<String>("descriptionField", new PropertyModel<String>(jobOfferDto, "description"));
        descriptionField.add(new MaxLengthValidator(ValidationConstants.JOB_OFFER_DESCRIPTION_FIELD_MAX_LENGTH,
                ValidationConstants.JOB_OFFER_DESCRIPTION_LENGTH_ERROR_KEY));
        addOnTheForm(descriptionLabel, descriptionField);

        Label hoursLabel = new Label("hoursLabel", new StringResourceModel("label.hours", this, null));
        RequiredTextField<Integer> hoursField = new RequiredTextField<Integer>("hoursField", new PropertyModel<Integer>(jobOfferDto, "hours"));
        hoursField.add(new JobOfferHoursValidator());
        addOnTheForm(hoursLabel, hoursField);

        Label numberOfPeopleLabel = new Label("numberOfPeopleLabel", new StringResourceModel("label.numberOfPeople", this, null));
        RequiredTextField<Integer> numberOfPeopleField = new RequiredTextField<Integer>("numberOfPeopleField", new PropertyModel<Integer>(jobOfferDto, "numberOfPeoples"));
        numberOfPeopleField.add(new JobOfferNumberOfPeopleValidator());
        addOnTheForm(numberOfPeopleLabel, numberOfPeopleField);

        createButtons(answer, modalWindow);
        AjaxFormValidatingBehavior.addToAllFormComponents(jobOfferForm, "onkeyup", Duration.ONE_SECOND);
    }

    private void addOnTheForm(Label label, FormComponent formComponent) {
        jobOfferForm.add(label);
        jobOfferForm.add(formComponent);
    }

    private void createHeader() {
        add(new Label("header", getHeaderModel()));
    }

    private void addJobOfferForm() {
        jobOfferForm = new Form<Void>("jobOfferForm");
        jobOfferForm.setOutputMarkupId(true);
        add(jobOfferForm);
    }

    private void createButtons(final ConfirmationAnswer answer, final ModalWindow modalWindow) {
        AjaxFallbackButton cancelButton = new AjaxFallbackButton("cancel", jobOfferForm) {
            @Override
            protected void onError(AjaxRequestTarget ajaxRequestTarget, Form<?> components) {
                answer.setAnswer(false);
                modalWindow.close(ajaxRequestTarget);
            }

            @Override
            protected void onSubmit(AjaxRequestTarget ajaxRequestTarget, Form<?> components) {
                answer.setAnswer(false);
                modalWindow.close(ajaxRequestTarget);
            }
        };
        jobOfferForm.add(cancelButton);

        AjaxFallbackButton okButton = new AjaxFallbackButton("ok", jobOfferForm) {
            @Override
            protected void onError(AjaxRequestTarget ajaxRequestTarget, Form<?> components) {
                ajaxRequestTarget.add(feedbackPanel);
            }

            @Override
            protected void onSubmit(AjaxRequestTarget ajaxRequestTarget, Form<?> components) {
                answer.setAnswer(true);
                modalWindow.close(ajaxRequestTarget);
            }
        };
        jobOfferForm.add(okButton);
    }

    public JobOfferDto getJobOfferDto() {
        return jobOfferDto;
    }

    public abstract StringResourceModel getHeaderModel();
}
