package by.bsuir.suite.page.work.panel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.StringResourceModel;

/**
 * @author d.matveenko
 */
public abstract class ValidationPanel extends Panel {

    public ValidationPanel(String id, final FeedbackPanel feedbackPanel) {
        super(id);

        add(new Label("header", getHeaderModel()));
        add(feedbackPanel);

        Form form = new Form("validateForm");
        add(form);

        form.add(new AjaxFallbackButton("ok", form) {
            @Override
            protected void onError(AjaxRequestTarget target, Form<?> components) {

            }

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> components) {
                onCancel(target);
            }
        });
    }

    public abstract void onCancel(AjaxRequestTarget target);

    public abstract StringResourceModel getHeaderModel();
}
