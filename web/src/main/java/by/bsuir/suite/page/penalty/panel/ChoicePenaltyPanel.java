package by.bsuir.suite.page.penalty.panel;

import by.bsuir.suite.page.penalty.PenaltyPage;
import by.bsuir.suite.validator.NumberValidator;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

/**
 * User: CHEB
 */
public abstract class ChoicePenaltyPanel extends Panel {

    private boolean dutyChoice = false;
    private boolean workChoice = false;

    private String numberOfDuty;
    private String numberOfWork;

    private final TextField<String> dutyNumber;
    private final TextField<String> workNumber;

    private FeedbackPanel errorMessages;

    public ChoicePenaltyPanel(String id, final ModalWindow window) {
        super(id);
        errorMessages = new FeedbackPanel("feedbackPanel");
        errorMessages.setOutputMarkupId(true);
        add(errorMessages);

        Form form = new Form("choicePenaltyForm");
        add(form);

        dutyNumber = new TextField<String>("dutiesNumber", new PropertyModel<String>(this, "numberOfDuty")){
            @Override
            public boolean isEnabled() {
                return dutyChoice;
            }
        };
        dutyNumber.add(new AjaxFormComponentUpdatingBehavior("onchange") {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                target.add(dutyNumber);
            }
        });
        dutyNumber.setRequired(true);
        dutyNumber.add(new NumberValidator(2, PenaltyPage.MAX_DUTY_HOURS, "penaltyPage.validator.hours.duty.number"));
        dutyNumber.setOutputMarkupId(true);

        workNumber = new TextField<String>("worksNumber", (new PropertyModel<String>(this, "numberOfWork"))) {
            @Override
            public boolean isEnabled() {
                return workChoice;
            }
        };
        workNumber.add(new AjaxFormComponentUpdatingBehavior("onchange") {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                target.add(workNumber);
            }
        });
        workNumber.add(new NumberValidator(2, PenaltyPage.MAX_WORK_HOURS, "penaltyPage.validator.hours.work.number"));
        workNumber.setRequired(true);
        workNumber.setOutputMarkupId(true);

        AjaxCheckBox dutyCheckbox = new AjaxCheckBox("dutiesCheckbox", new PropertyModel<Boolean>(this, "dutyChoice")) {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                target.add(dutyNumber);
            }
        };

        AjaxCheckBox workCheckbox = new AjaxCheckBox("worksCheckbox", new PropertyModel<Boolean>(this, "workChoice")) {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                target.add(workNumber);
            }
        };

        AjaxSubmitLink okButton = new AjaxSubmitLink("okButton", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> components) {
                window.close(target);
                ChoicePenaltyPanel.this.onSubmit(target);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> components) {
                target.add(errorMessages);
            }
        };

        AjaxFallbackLink<Void> cancelButton = new AjaxFallbackLink<Void>("cancelButton") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                window.close(target);
            }
        };

        window.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
            @Override
            public void onClose(AjaxRequestTarget target) {
                ChoicePenaltyPanel.this.onClose(target);
            }
        });

        form.add(dutyCheckbox);
        form.add(dutyNumber);
        form.add(workCheckbox);
        form.add(workNumber);
        add(okButton);
        add(cancelButton);
    }

    protected void onClose(AjaxRequestTarget target) {
    }

    public abstract void onSubmit(AjaxRequestTarget target);

    public boolean isDutyChoice() {
        return dutyChoice;
    }

    public boolean isWorkChoice() {
        return workChoice;
    }

    public String getDutyNumber() {
        return numberOfDuty;
    }

    public String getWorkNumber() {
        return numberOfWork;
    }
}
