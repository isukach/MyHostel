package by.bsuir.suite.page.base.panel;

import by.bsuir.suite.page.duty.panel.ConfirmationAnswer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.StringResourceModel;

/**
 * @author i.sukach
 */
public abstract class ConfirmationPanel extends Panel {

    public ConfirmationPanel(String id, final ConfirmationAnswer answer, final ModalWindow modalWindow) {
        super(id);

        add(new Label("header", getHeaderModel()));
        add(new Label("content", getContentModel()).setEscapeModelStrings(false));

        add(new AjaxFallbackLink<Void>("cancel") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                answer.setAnswer(false);
                modalWindow.close(target);
            }
        });

        add(new AjaxFallbackLink<Void>("ok") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                answer.setAnswer(true);
                modalWindow.close(target);
            }
        });
    }

    public abstract StringResourceModel getHeaderModel();

    public abstract StringResourceModel getContentModel();
}
