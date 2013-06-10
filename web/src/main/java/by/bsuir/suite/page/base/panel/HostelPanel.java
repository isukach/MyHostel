package by.bsuir.suite.page.base.panel;

import by.bsuir.suite.session.HostelAuthenticatedWebSession;
import by.bsuir.suite.util.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.html.panel.Panel;

import java.util.Locale;

/**
 * @author i.sukach
 */
public abstract class HostelPanel extends Panel {

    public HostelPanel(String id) {
        super(id);
        Injector.get().inject(this);
    }

    public HostelAuthenticatedWebSession getCurrentSession() {
        return (HostelAuthenticatedWebSession) getSession();
    }

    protected Locale getCurrentLocale() {
        return getSession().getLocale();
    }

    protected boolean userHasFloorHeadRole() {
        return ((HostelAuthenticatedWebSession) getSession()).getRoles().contains(Roles.FLOOR_HEAD);
    }

    protected boolean userHasCommandantRole() {
        return ((HostelAuthenticatedWebSession) getSession()).getRoles().contains(Roles.COMMANDANT);
    }

    protected boolean userHasEducatorRole() {
        return ((HostelAuthenticatedWebSession) getSession()).getRoles().contains(Roles.EDUCATOR);
    }

    protected boolean isSuperUser() {
        return ((HostelAuthenticatedWebSession) getSession()).getRoles().contains(Roles.SUPER_USER);
    }
}
