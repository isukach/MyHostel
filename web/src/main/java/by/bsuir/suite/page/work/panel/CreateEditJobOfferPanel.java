package by.bsuir.suite.page.work.panel;

import by.bsuir.suite.dto.work.JobOfferDto;
import by.bsuir.suite.page.duty.panel.ConfirmationAnswer;
import by.bsuir.suite.page.work.window.ValidationWindow;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormValidatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.yui.calendar.DateField;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
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
        dateField.setEnabled(true);
        jobOfferForm.add(dateLabel);
        jobOfferForm.add(dateField);

        Label descriptionLabel = new Label("descriptionLabel", new StringResourceModel("label.description", this, null));
        TextField<String> descriptionField = new TextField<String>("descriptionField", new PropertyModel<String>(jobOfferDto, "description"));
        descriptionField.setRequired(false);
        jobOfferForm.add(descriptionLabel);
        jobOfferForm.add(descriptionField);

        Label hoursLabel = new Label("hoursLabel", new StringResourceModel("label.hours", this, null));
        TextField<Integer> hoursField = new TextField<Integer>("hoursField", new PropertyModel<Integer>(jobOfferDto, "hours"));
        hoursField.setRequired(false);
        jobOfferForm.add(hoursLabel);
        jobOfferForm.add(hoursField);

        Label numberOfPeopleLabel = new Label("numberOfPeopleLabel", new StringResourceModel("label.numberOfPeople", this, null));
        TextField<Integer> numberOfPeopleField = new TextField<Integer>("numberOfPeopleField", new PropertyModel<Integer>(jobOfferDto, "numberOfPeoples"));
        numberOfPeopleField.setRequired(false);
        jobOfferForm.add(numberOfPeopleLabel);
        jobOfferForm.add(numberOfPeopleField);

        createButtons(answer, modalWindow);
        AjaxFormValidatingBehavior.addToAllFormComponents(jobOfferForm, "onkeyup", Duration.ONE_SECOND);
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
