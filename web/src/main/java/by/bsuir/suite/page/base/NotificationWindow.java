package by.bsuir.suite.page.base;

import by.bsuir.suite.page.base.panel.NotificationPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * @author i.sukach
 */
public abstract class NotificationWindow extends ModalWindow {

    public NotificationWindow(String id, String headerKey, String contentKey, boolean showCancelButton) {
        super(id);
        setTitle("");
        setCssClassName("modal.css");

        setContent(new NotificationPanel(this.getContentId(), headerKey, contentKey, showCancelButton) {
            @Override
            public void onCancel(AjaxRequestTarget target) {
                NotificationWindow.this.onCancel(target);
            }

            @Override
            public void onConfirm(AjaxRequestTarget target) {
                NotificationWindow.this.onConfirm(target);
            }
        });
    }

    @Override
    protected ResourceReference newCssResource() {
        return new CssResourceReference(NotificationWindow.class, "styles/modal.css");
    }

    public void onCancel(AjaxRequestTarget target) {}

    public abstract void onConfirm(AjaxRequestTarget target);
}
