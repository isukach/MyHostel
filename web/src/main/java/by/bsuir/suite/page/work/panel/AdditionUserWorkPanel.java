package by.bsuir.suite.page.work.panel;

import by.bsuir.suite.page.base.panel.NavigationPanel;
import by.bsuir.suite.session.HostelAuthenticatedWebSession;
import by.bsuir.suite.util.Roles;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * @author d.matveenko
 */
public class AdditionUserWorkPanel extends Panel {

    private NavigationPanel navigationPanel;

    private ProgressBarPanel progressBarPanel;
    
    private FormForUserWorkPanel formForUserWorkPanel;

    public AdditionUserWorkPanel(String id) {
        super(id);
        
        addNavigationPanel();
        addProgressBarPanel();
        addFormForUserWorkPanel();
    }

    private void addNavigationPanel() {
        navigationPanel = new WorkNavigationPanel("navigationPanel");
        add(navigationPanel);
    }

    private void addProgressBarPanel() {
        progressBarPanel = new ProgressBarPanel("progressBarPanel") {

            @Override
            public Long getPersonId() {
                if(navigationPanel.isPersonSelected()) {
                    return navigationPanel.getSelectedPersonId();
                } else {
                    return ((HostelAuthenticatedWebSession)getSession()).getUser().getPersonId();
                }
            }

            @Override
            public boolean isVisible() {
                return !navigationPanel.isRenderAllowed() || navigationPanel.isPersonSelected();
            }
        };
        progressBarPanel.setOutputMarkupPlaceholderTag(true);
        add(progressBarPanel);
    }

    private void addFormForUserWorkPanel() {
        formForUserWorkPanel = new FormForUserWorkPanel("formForUserWorkPanel", progressBarPanel) {

            @Override
            public Long getPersonId() {
                return navigationPanel.getSelectedPersonId();
            }

            @Override
            public boolean isVisible() {
                return navigationPanel.isPersonSelected();
            }
        };
        formForUserWorkPanel.setOutputMarkupPlaceholderTag(true);
        add(formForUserWorkPanel);
    }

    @AuthorizeAction(action = Action.RENDER, roles = {Roles.MANAGERESS, Roles.SUPER_USER})
    class WorkNavigationPanel extends NavigationPanel {

        public WorkNavigationPanel(String id) {
            super(id, false);
        }

        @Override
        protected void onPersonSelected(AjaxRequestTarget target) {
            target.add(progressBarPanel);
            target.add(formForUserWorkPanel);
            progressBarPanel.updateProgressBarForSelectedUser();
        }

        @Override
        protected void onPersonDeselected(AjaxRequestTarget target) {
            target.add(progressBarPanel);
            target.add(formForUserWorkPanel);
        }
    }
}
