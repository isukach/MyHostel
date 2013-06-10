package by.bsuir.suite.page.duty.components;

import by.bsuir.suite.util.AjaxDownloadBehavior;
import by.bsuir.suite.util.Roles;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.markup.html.form.Form;
/**
 * @author a.garelik
 */
@AuthorizeAction(action = Action.RENDER, roles = {Roles.FLOOR_HEAD})
public class ExportButton extends AjaxButton {

    private AjaxDownloadBehavior ajaxDownloadBehavior;

    public ExportButton(String id, AjaxDownloadBehavior ajaxDownloadBehavior) {
        super(id);
        this.ajaxDownloadBehavior=ajaxDownloadBehavior;
    }

    @Override
    protected void onSubmit(AjaxRequestTarget ajaxRequestTarget, Form<?> components) {
        ajaxDownloadBehavior.initiate(ajaxRequestTarget);
    }

    @Override
    protected void onError(AjaxRequestTarget ajaxRequestTarget, Form<?> components) {
    }
}
