package by.bsuir.suite.page.base;

import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * @author i.sukach
 */
public class NonContentWindow extends ModalWindow {

    public NonContentWindow(String id) {
        super(id);
        setTitle("");
        setCssClassName("modal.css");
    }

    @Override
    protected ResourceReference newCssResource() {
        return new CssResourceReference(NonContentWindow.class, "styles/modal.css");
    }
}
