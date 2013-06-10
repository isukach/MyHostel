package by.bsuir.suite.page.work.panel;

import by.bsuir.suite.page.base.panel.NotificationPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.ResourceReference;


/**
 * @author a.garelik
 */
public abstract class  WorkDescriptionWindow extends ModalWindow {

    private NotificationPanel panel;

    public  WorkDescriptionWindow(String id, String headerKey) {

        super(id);
        setTitle("");
        setCssClassName("modal.css");
        panel = new NotificationPanel(this.getContentId(), headerKey, headerKey, false) {
            @Override
            public void onCancel(AjaxRequestTarget target) {
                WorkDescriptionWindow.this.onCancel(target);
            }

            @Override
            public void onConfirm(AjaxRequestTarget target) {
                WorkDescriptionWindow.this.onConfirm(target);
            }
        };
        setContent(panel);
    }

    public void updateDescriptionMsg(String msg){
        panel.updateContent(new Model(msg));
    }

    @Override
    protected ResourceReference newCssResource() {
        return new CssResourceReference(WorkDescriptionWindow.class, "styles/modal.css");
    }

    public abstract void onCancel(AjaxRequestTarget target);

    public abstract void onConfirm(AjaxRequestTarget target);
}
