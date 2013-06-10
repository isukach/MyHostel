package by.bsuir.suite.page.person.panel;

import by.bsuir.suite.page.person.PersonPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;

/**
 * @author i.sukach
 */
public class EditButtonPanel extends Panel {

    public EditButtonPanel(String id) {
        super(id);
        add(new SaveButton("save", new StringResourceModel("save", this, null)));
    }

    public void submit(AjaxRequestTarget target){
        ((PersonPage) getPage()).savePerson(target);
    }

    private class SaveButton extends AjaxButton{

        public SaveButton(String id, IModel<String> model) {
            super(id, model);
        }

        @Override
        protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
            EditButtonPanel.this.submit(target);
        }

        @Override
        protected void onError(AjaxRequestTarget target, Form<?> form) {

        }
    }
}
