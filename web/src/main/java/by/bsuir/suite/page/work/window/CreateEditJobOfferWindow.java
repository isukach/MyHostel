package by.bsuir.suite.page.work.window;

import by.bsuir.suite.page.base.NonContentWindow;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * User: Matveyenka Denis
 * Date: 21.07.13
 */
public class CreateEditJobOfferWindow extends NonContentWindow {

    public CreateEditJobOfferWindow(String id) {
        super(id);
        setTitle("");
        setCssClassName("modal.css");
    }

    @Override
    protected ResourceReference newCssResource() {
        return new CssResourceReference(CreateEditJobOfferWindow.class, "styles/modal.css");
    }
}
