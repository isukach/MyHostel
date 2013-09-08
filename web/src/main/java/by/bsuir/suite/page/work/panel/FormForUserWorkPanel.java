package by.bsuir.suite.page.work.panel;

import by.bsuir.suite.dto.work.AddUserWorkDto;
import by.bsuir.suite.dto.work.JobDto;
import by.bsuir.suite.page.base.NotificationWindow;
import by.bsuir.suite.page.base.panel.ConfirmationPanel;
import by.bsuir.suite.page.duty.panel.ConfirmationAnswer;
import by.bsuir.suite.page.work.window.ValidationWindow;
import by.bsuir.suite.service.notifications.NotificationService;
import by.bsuir.suite.service.notifications.common.NotificationKeys;
import by.bsuir.suite.service.work.WorkService;
import by.bsuir.suite.util.DateUtils;
import by.bsuir.suite.util.Roles;
import by.bsuir.suite.validator.HoursValidator;
import by.bsuir.suite.validator.MaxLengthValidator;
import by.bsuir.suite.validator.ValidationConstants;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author d.matveenko
 */
@AuthorizeAction(action = Action.RENDER, roles = {Roles.MANAGERESS, Roles.SUPER_USER})
public abstract class FormForUserWorkPanel extends Panel {

    @SpringBean
    private WorkService workService;

    @SpringBean
    private NotificationService notificationService;

    private AddUserWorkDto addUserWorkDto;

    private ProgressBarPanel progressBarPanel;

    private Form additionUserWorkForm;

    private WebMarkupContainer container;

    private TextField<String> jobDescriptionField;

    private TextField<String> jobHoursField;
    
    private ModalWindow workConfirmDialog;
    
    private ModalWindow workValidationDialog;

    private FeedbackPanel feedbackPanel;
    
    private JobDto job = new JobDto();

    private JobDto jobDto = new JobDto();
    
    public FormForUserWorkPanel(String id, ProgressBarPanel panel) {
        super(id);
        this.progressBarPanel = panel;
        feedbackPanel = new FeedbackPanel("feedback");
        addWindows();
        addUserWorkForm();
        addContainer();
    }
    
    public abstract Long getPersonId();

    private void addUserWorkForm() {
        additionUserWorkForm = new Form<Void>("userWorkForm");
        add(additionUserWorkForm);
    }

    private void addContainer() {
        container = new WebMarkupContainer("addUserWork");

        addJobLabels();
        addJobModelsAndFields();
        addJobSubmitButton();

        container.setOutputMarkupPlaceholderTag(true);
        additionUserWorkForm.add(container);
    }

    private void addJobSubmitButton() {
        container.add(new AjaxFallbackButton("sendFormButton", additionUserWorkForm) {

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> components) {
                workValidationDialog.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
                    @Override
                    public void onClose(AjaxRequestTarget target) {
                        target.add(progressBarPanel);
                        target.add(container);
                    }
                });
                workValidationDialog.show(target);
            }

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> components) {
                submitForm(target);
            }
        });
    }
    
    private void submitForm(AjaxRequestTarget target) {
        final ConfirmationAnswer answer = new ConfirmationAnswer();
        workConfirmDialog.setContent(new ConfirmationPanel(workConfirmDialog.getContentId(), answer, workConfirmDialog) {
            @Override
            public StringResourceModel getHeaderModel() {
                return new StringResourceModel("workPage.confirmWindow.header", this, null);
            }

            @Override
            public StringResourceModel getContentModel() {
                return new StringResourceModel("workPage.confirmWindow.content", this, null);
            }
        });
        workConfirmDialog.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
            @Override
            public void onClose(AjaxRequestTarget target) {
                target.add(progressBarPanel);
                target.add(container);
                target.add(jobDescriptionField);
                target.add(jobHoursField);
                if(answer.isPositive()) {
                    updatePanels();
                    clearJob();
                }
            }
        });
        workConfirmDialog.show(target);
    }

    private void addWindows() {
        addConfirmWindow();
        addValidationWindow();
    }

    private void addConfirmWindow() {
        workConfirmDialog = new NotificationWindow("workConfirmDialog", "workPage.workConfirmDialog.header",
                "workPage.workConfirmDialog.content", false) {
            @Override
            public void onCancel(AjaxRequestTarget target) {
                close(target);
            }

            @Override
            public void onConfirm(AjaxRequestTarget target) {
                close(target);
            }
        };
        add(workConfirmDialog);
    }
    
    private void addValidationWindow() {
        workValidationDialog = new ValidationWindow("workValidationDialog", feedbackPanel) {

            @Override
            public void onCancel(AjaxRequestTarget target) {
                close(target);
            }
        };
        add(workValidationDialog);
    }

    private void addJobLabels() {
        container.add(new Label("descriptionMessage", new StringResourceModel("label.work.description", this, null)));
        container.add(new Label("hoursMessage", new StringResourceModel("label.work.hours", this, null)));
    }

    private void updatePanels() {
        updateForm();
        workService.update(addUserWorkDto);
        JobDto job = addUserWorkDto.getJobs().get(addUserWorkDto.getJobs().size()-1);
        notificationService.createNotification(progressBarPanel.getPersonId(), NotificationKeys.JOB_HOURS_ADDED,
                new String[]{String.valueOf(DateUtils.getFormattedDate(job.getDate().getTime())),
                        String.valueOf(job.getHours())}, null);

        progressBarPanel.updateProgressBarForSelectedUser();
    }

    private void addJobModelsAndFields() {
        addDescriptionField();
        addHoursField();
    }

    private void addDescriptionField() {
        jobDescriptionField = new TextField<String>("descriptionJob", new PropertyModel<String>(jobDto, "description"));
        jobDescriptionField.add(new MaxLengthValidator(
                        ValidationConstants.DESCRIPTION_FIELD_MAX_LENGTH,
                        ValidationConstants.JOB_DESCRIPTION_LENGTH_ERROR_KEY));
        jobDescriptionField.setRequired(true);
        jobDescriptionField.setOutputMarkupPlaceholderTag(true);
        container.add(jobDescriptionField);
    }

    private void addHoursField() {
        jobHoursField = new TextField<String>("hoursJob", new PropertyModel<String>(jobDto, "hours"));
        jobHoursField.add(new HoursValidator(this));
        jobHoursField.setRequired(true);
        jobHoursField.setOutputMarkupPlaceholderTag(true);
        container.add(jobHoursField);
    }
    
    private void updateForm() {
        addUserWorkDto = workService.getAddUserWorkDto(getPersonId());
        job = workService.duplicate(jobDto);
        addUserWorkDto.getJobs().add(job);
    }

    private void clearJob() {
        job.setDescription("");
        job.setHours("");
        jobDto.setDescription("");
        jobDto.setHours("");
    }

    public WorkService getWorkService() {
        return workService;
    }
}
