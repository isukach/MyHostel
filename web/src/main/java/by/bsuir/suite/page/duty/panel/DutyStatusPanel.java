package by.bsuir.suite.page.duty.panel;

import by.bsuir.suite.util.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;

/**
 * @author i.sukach
 */
public class DutyStatusPanel extends Panel {

    public DutyStatusPanel(String id, final ModalWindow window, String comment) {
        super(id);

        AjaxFallbackLink<Void> okButton = new AjaxFallbackLink<Void>("okButton") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                window.close(target);
            }
        };

        add(new Label("header", new StringResourceModel("dialog.header", this, null)));
        add(new Label("content", new Model<String>(StringUtils.replaceNewLineSymbols(comment))).setEscapeModelStrings(false));
        add(okButton);
    }
}
