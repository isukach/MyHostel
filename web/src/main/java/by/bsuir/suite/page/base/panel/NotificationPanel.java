package by.bsuir.suite.page.base.panel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;

/**
 * @author i.sukach
 */
public abstract class NotificationPanel extends Panel {

    public NotificationPanel(String id, String headerKey, String contentKey, final boolean showCancelButton) {
        super(id);

        add(new Label("header", new StringResourceModel(headerKey,  this, null)));
        add(new Label("content", new StringResourceModel(contentKey, this, null)));

        add(new AjaxFallbackLink<Void>("cancel") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                onCancel(target);
            }

            @Override
            public boolean isVisible() {
                return showCancelButton;
            }
        });

        add(new AjaxFallbackLink<Void>("ok") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                onConfirm(target);
            }
        });
    }

    public void updateContent(Model model){
        get("content").setDefaultModel(model);
    }

    public abstract void onCancel(AjaxRequestTarget target);

    public abstract void onConfirm(AjaxRequestTarget target);
}
