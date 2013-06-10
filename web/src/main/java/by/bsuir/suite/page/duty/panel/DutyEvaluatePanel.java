package by.bsuir.suite.page.duty.panel;

import by.bsuir.suite.disassembler.duty.DutyStatusDto;
import by.bsuir.suite.page.base.panel.HostelPanel;
import by.bsuir.suite.validator.MaxLengthValidator;
import by.bsuir.suite.validator.MinLengthValidator;
import by.bsuir.suite.validator.ValidationConstants;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author i.sukach
 */
public abstract class DutyEvaluatePanel extends HostelPanel {

    private final List<String> OPTIONS = new ArrayList<String>();

    private final Map<String, DutyStatusDto> OPTIONS_MAP = new HashMap<String, DutyStatusDto>();

    private final Map<String, String> TRANSLATION_MAP = new HashMap<String, String>();

    private final String ADD_REMARK_KEY = "option.addRemark";
    private final String ABSENT_KEY = "option.absent";

    private final String ADD_REMARKS_TRANSLATION = new StringResourceModel(ADD_REMARK_KEY, this, null).getString();
    private final String ABSENT_TRANSLATION = new StringResourceModel(ABSENT_KEY, this, null).getString();

    private String selected = ABSENT_TRANSLATION;

    private String comment = "";

    private final TextArea<String> commentField;

    public DutyEvaluatePanel(String id, final ModalWindow window) {
        super(id);

        OPTIONS_MAP.put(ABSENT_KEY, DutyStatusDto.SKIPPED);
        OPTIONS_MAP.put(ADD_REMARK_KEY, DutyStatusDto.COMPLETED_BAD);

        TRANSLATION_MAP.put(ABSENT_TRANSLATION, ABSENT_KEY);
        OPTIONS.add(ABSENT_TRANSLATION);
        TRANSLATION_MAP.put(ADD_REMARKS_TRANSLATION, ADD_REMARK_KEY);
        OPTIONS.add(ADD_REMARKS_TRANSLATION);

        final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);

        Form<Void> form = new Form<Void>("evaluationForm");

        AjaxSubmitLink okButton = new AjaxSubmitLink("okButton", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> components) {
                window.close(target);
                DutyEvaluatePanel.this.onSubmit(target);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> components) {
                target.add(feedbackPanel);
            }
        };

        AjaxFallbackLink<Void> cancelButton = new AjaxFallbackLink<Void>("cancelButton") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                window.close(target);
            }
        };

        final RadioChoice<String> evaluationOptions = new RadioChoice<String>("dutyChoices",
                    new PropertyModel<String>(this, "selected"), OPTIONS);

        evaluationOptions.add(new AjaxFormChoiceComponentUpdatingBehavior() {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                target.add(commentField);
            }
        });

        commentField = new TextArea<String>("comment", new PropertyModel<String>(this, "comment")) {
            @Override
            public boolean isEnabled() {
                return DutyStatusDto.COMPLETED_BAD == OPTIONS_MAP.get(TRANSLATION_MAP.get(selected));
            }
        };
        commentField.setRequired(true);
        commentField.add(new AjaxFormComponentUpdatingBehavior("onchange") {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                target.add(feedbackPanel);
            }
        });
        commentField.add(new MinLengthValidator(ValidationConstants.DUTY_STATUS_COMMENT_MIN_LENGTH,
                ValidationConstants.DUTY_STATUS_COMMENT_MIN_LENGTH_ERROR_KEY));
        commentField.add(new MaxLengthValidator(ValidationConstants.DUTY_STATUS_COMMENT_MAX_LENGTH,
                ValidationConstants.DUTY_STATUS_COMMENT_MAX_LENGTH_ERROR_KEY));

        form.add(commentField);
        form.add(evaluationOptions);
        form.add(okButton);
        form.add(cancelButton);
        add(form);
    }

    public abstract void onSubmit(AjaxRequestTarget target);

    public DutyStatusDto getSelected() {
        return OPTIONS_MAP.get(TRANSLATION_MAP.get(selected));
    }

    public String getComment() {
        return comment;
    }
}
