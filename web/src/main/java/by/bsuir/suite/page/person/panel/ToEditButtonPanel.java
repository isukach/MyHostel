package by.bsuir.suite.page.person.panel;


import by.bsuir.suite.dto.person.PersonDto;
import by.bsuir.suite.page.base.NonContentWindow;
import by.bsuir.suite.page.base.NotificationWindow;
import by.bsuir.suite.page.person.PersonPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author i.sukach
 */
public class ToEditButtonPanel extends Panel {

    private ModalWindow changePasswordWindow;
    private ModalWindow changeUsernameWindow;
    private PersonDto personDto;
    private ModalWindow passwordChangeSuccessWindow;
    private ModalWindow usernameChangeSuccessWindow;

    public ToEditButtonPanel(String id, final PersonDto personDto) {
        super(id);
        this.personDto = personDto;
        personDto.setPasswordChanged(false);
        add(new AjaxButton("edit", new ResourceModel("button.edit")) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                PageParameters params = new PageParameters();
                params.add("isEdited", Boolean.TRUE);
                setResponsePage(PersonPage.class, params);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
            }
        });
        add(new ChangePasswordButton("changePassword", new StringResourceModel("changePassword", this, null)));
        changePasswordWindow = new NonContentWindow("changePasswordDialog");
        changePasswordWindow.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
            @Override
            public void onClose(AjaxRequestTarget target) {
               if (personDto.isPasswordChanged()) {
                   passwordChangeSuccessWindow.show(target);
                   personDto.setPasswordChanged(false);
               }
            }
        });
        add(changePasswordWindow);

        add(new ChangeUsernameButton("changeUsername", new StringResourceModel("changeUsername", this, null)));
        changeUsernameWindow = new NonContentWindow("changeUsernameDialog");
        changeUsernameWindow.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
            @Override
            public void onClose(AjaxRequestTarget target) {
                if (personDto.isUsernameChanged()) {
                    usernameChangeSuccessWindow.show(target);
                    personDto.setUsernameChanged(false);
                }
            }
        });
        add(changeUsernameWindow);

        passwordChangeSuccessWindow = new NotificationWindow("passwordChangeSuccessWindow",
                "change.password.header", "change.password.text", false) {
            @Override
            public void onConfirm(AjaxRequestTarget target) {
                close(target);
            }
        };
        add(passwordChangeSuccessWindow);

        usernameChangeSuccessWindow = new NotificationWindow("usernameChangeSuccessWindow",
                "change.username.header", "change.username.text", false) {
            @Override
            public void onConfirm(AjaxRequestTarget target) {
                close(target);
            }
        };
        add(usernameChangeSuccessWindow);
    }

    private class ChangePasswordButton extends AjaxButton {

        public ChangePasswordButton(String id, IModel<String> model) {
            super(id, model);
        }

        @Override
        protected void onSubmit(AjaxRequestTarget target, Form<?> components) {
            ChangePasswordPanel panel
                    = new ChangePasswordPanel(changePasswordWindow.getContentId(), changePasswordWindow, personDto);
            changePasswordWindow.setContent(panel);
            changePasswordWindow.show(target);
        }

        @Override
        protected void onError(AjaxRequestTarget ajaxRequestTarget, Form<?> components) {
        }
    }

    private class ChangeUsernameButton extends AjaxButton {

        public ChangeUsernameButton(String id, IModel<String> model) {
            super(id, model);
        }

        @Override
        protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
            ChangeUsernamePanel changeUsernamePanel
                    = new ChangeUsernamePanel(changeUsernameWindow.getContentId(), changeUsernameWindow, personDto);
            changeUsernameWindow.setContent(changeUsernamePanel);
            changeUsernameWindow.show(target);
        }

        @Override
        protected void onError(AjaxRequestTarget target, Form<?> form) {
        }
    }
}
