package by.bsuir.suite.page.base;

import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * @author i.sukach
 */
public class NavigationWindow extends ModalWindow {

    public NavigationWindow(String id) {
        super(id);
        setTitle("");
        setCssClassName("navigation.css");
    }

    @Override
    protected ResourceReference newCssResource() {
        return new CssResourceReference(NonContentWindow.class, "styles/navigation.css");
    }
}
