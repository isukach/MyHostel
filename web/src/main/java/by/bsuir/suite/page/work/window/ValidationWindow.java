package by.bsuir.suite.page.work.window;

import by.bsuir.suite.page.work.panel.ValidationPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * @author d.matveenko
 */
public abstract class ValidationWindow extends ModalWindow {

    public ValidationWindow(String id, FeedbackPanel feedbackPanel) {
        super(id);
        setTitle("");
        setCssClassName("modal.css");
        
        setContent(new ValidationPanel(this.getContentId(), feedbackPanel) {
            @Override
            public StringResourceModel getHeaderModel() {
                return new StringResourceModel("workPage.validationWindow.header", this, null);
            }

            @Override
            public void onCancel(AjaxRequestTarget target) {
                ValidationWindow.this.onCancel(target);
            }
        });
    }

    @Override
    protected ResourceReference newCssResource() {
        return new CssResourceReference(ValidationWindow.class, "styles/modal.css");
    }

    public abstract void onCancel(AjaxRequestTarget target);
}
