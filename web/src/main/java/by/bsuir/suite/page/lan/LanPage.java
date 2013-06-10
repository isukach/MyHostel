package by.bsuir.suite.page.lan;

import by.bsuir.suite.colors.ColorConstants;
import by.bsuir.suite.page.base.BasePage;
import by.bsuir.suite.page.base.panel.NavigationPanel;
import by.bsuir.suite.session.HostelAuthenticatedWebSession;
import by.bsuir.suite.util.Permissions;
import by.bsuir.suite.util.Roles;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;

/**
 * @author d.shemerey
 */
@AuthorizeInstantiation({Permissions.LAN_VIEWING_PERMISSION, Roles.SUPER_USER, Roles.ADMIN})
public class LanPage extends BasePage {

    private final NavigationPanel navigationPanel;
    private final LanPaymentPanel lanPaymentPanel;

    public LanPage() {
        super(ColorConstants.VIOLET);

        navigationPanel = new LanNavigationPanel("navigationPanel");

        add(navigationPanel);

        lanPaymentPanel = new LanPaymentPanel("lanPaymentPanel") {
            @Override
            public Long getPersonId() {
                if (navigationPanel.isPersonSelected()) {
                    return navigationPanel.getSelectedPersonId();
                } else {
                    return ((HostelAuthenticatedWebSession) getSession()).getUser().getPersonId();
                }
            }

            @Override
            public boolean isVisible() {
                return !navigationPanel.isRenderAllowed() || navigationPanel.isPersonSelected();
            }
        };
        lanPaymentPanel.setOutputMarkupPlaceholderTag(true);
        add(lanPaymentPanel);
    }

    @AuthorizeAction(action = Action.RENDER, roles = {Roles.SUPER_USER, Roles.ADMIN})
    class LanNavigationPanel extends NavigationPanel {

        public LanNavigationPanel(String id) {
            super(id, false);
        }

        @Override
        protected void onPersonSelected(AjaxRequestTarget target) {
            target.add(lanPaymentPanel);
            lanPaymentPanel.updateCells();
        }

        @Override
        protected void onPersonDeselected(AjaxRequestTarget target) {
            target.add(lanPaymentPanel);
        }
    }
}
