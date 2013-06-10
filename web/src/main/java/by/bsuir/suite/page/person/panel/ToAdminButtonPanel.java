package by.bsuir.suite.page.person.panel;

import by.bsuir.suite.page.base.NonContentWindow;
import by.bsuir.suite.page.base.panel.ConfirmationPanel;
import by.bsuir.suite.page.base.panel.HostelPanel;
import by.bsuir.suite.page.duty.panel.ConfirmationAnswer;
import by.bsuir.suite.page.person.PersonPage;
import by.bsuir.suite.service.person.PersonService;
import by.bsuir.suite.util.Roles;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author i.sukach
 */
public class ToAdminButtonPanel extends HostelPanel {
    private String login;

    @SpringBean
    private PersonService personService;

    private ModalWindow resetPasswordConfirmationWindow;

    public ToAdminButtonPanel(String id, String login) {
        super(id);
        this.login = login;

        add(new AjaxButton("admin", new ResourceModel("edit")) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                PageParameters params = new PageParameters();
                params.add("isEdited", Boolean.TRUE);
                params.add(PersonPage.USERNAME_KEY, ToAdminButtonPanel.this.login);
                setResponsePage(PersonPage.class, params);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
            }
        });
        add(new ResetPasswordLink("resetPassword"));

        resetPasswordConfirmationWindow = new NonContentWindow("resetPasswordConfirm");
        add(resetPasswordConfirmationWindow);
    }

    @AuthorizeAction(action = Action.RENDER, roles = {Roles.SUPER_USER})
    private class ResetPasswordLink extends AjaxFallbackLink<String> {
        public ResetPasswordLink(String id) {
            super(id);
        }

        @Override
        public void onClick(AjaxRequestTarget target) {
            final ConfirmationAnswer answer = new ConfirmationAnswer();
            ConfirmationPanel panel = new ConfirmationPanel(
                    resetPasswordConfirmationWindow.getContentId(), answer, resetPasswordConfirmationWindow) {
                @Override
                public StringResourceModel getHeaderModel() {
                    return new StringResourceModel("resetPassword.header", this, null);
                }

                @Override
                public StringResourceModel getContentModel() {
                    return new StringResourceModel("resetPassword.content", this, null);
                }
            };
            resetPasswordConfirmationWindow.setContent(panel);
            resetPasswordConfirmationWindow.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
                @Override
                public void onClose(AjaxRequestTarget target) {
                    if (answer.isPositive()) {
                        personService.resetPassword(login);
                    }
                }
            });
            resetPasswordConfirmationWindow.show(target);
        }
    }
}
