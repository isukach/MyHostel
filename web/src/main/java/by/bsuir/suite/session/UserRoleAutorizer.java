package by.bsuir.suite.session;

import by.bsuir.suite.page.sign.in.SignIn;
import by.bsuir.suite.page.test.TestPage;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.authroles.authorization.strategies.role.IRoleCheckingStrategy;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.component.IRequestableComponent;

/**
 * Created by IntelliJ IDEA.
 * User: DenisKravchenko
 * Date: 20.07.12
 * Time: 1:59
 * To change this template use File | Settings | File Templates.
 */
public class UserRoleAutorizer implements IRoleCheckingStrategy, IAuthorizationStrategy {
    public UserRoleAutorizer() {
    }

    public boolean isActionAuthorized(Component component, Action action) {
        return true;
    }

    public <T extends IRequestableComponent> boolean isInstantiationAuthorized(Class<T> componentClass) {
        if (TestPage.class.isAssignableFrom(componentClass)) {
            if (((HostelAuthenticatedWebSession) Session.get()).isSignedIn()) {
                return true;
            }
            throw new RestartResponseAtInterceptPageException(SignIn.class);
        }
        return true;
    }

    public boolean hasAnyRole(Roles role) {
        HostelAuthenticatedWebSession session = (HostelAuthenticatedWebSession) Session.get();
        return session != null && session.getUser().hasAnyRole(role);
    }
}
