package by.bsuir.suite.page.penalty.panel;

import by.bsuir.suite.page.penalty.PenaltyPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.StringResourceModel;

/**
 * User: CHEB
 */
public abstract class ConfirmationPenaltyPanel extends Panel {

    public ConfirmationPenaltyPanel(String id, final ModalWindow window,
                                    String person, String room, int duty, int work) {
        super(id);

        Label personLabel = new Label("person", new StringResourceModel("dialog.content.person", this, null, new Object[]{person}));
        Label roomLabel = new Label("room", new StringResourceModel("dialog.content.room", this, null, new Object[]{room}));
        Label dutyLabel;
        if (duty > PenaltyPage.MIN_DUTY_HOURS && duty <= PenaltyPage.MAX_DUTY_HOURS) {
            dutyLabel = new Label("duty", new StringResourceModel("dialog.content.duty", this, null, new Object[]{duty}));
        } else {
            dutyLabel = new Label("duty");
        }
        Label workLabel;
        if (work > PenaltyPage.MIN_WORK_HOURS && work <= PenaltyPage.MAX_WORK_HOURS) {
            workLabel = new Label("work", new StringResourceModel("dialog.content.work", this, null, new Object[]{work}));
        } else {
            workLabel = new Label("work");
        }

        AjaxFallbackLink<Void> okButton = new AjaxFallbackLink<Void>("okButton") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                onSubmit(target);
                window.close(target);
            }
        };

        AjaxFallbackLink<Void> cancelButton = new AjaxFallbackLink<Void>("cancelButton") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                window.close(target);
            }
        };

        add(personLabel);
        add(roomLabel);
        add(dutyLabel);
        add(workLabel);
        add(okButton);
        add(cancelButton);
    }

    public abstract void onSubmit(AjaxRequestTarget target);
}
