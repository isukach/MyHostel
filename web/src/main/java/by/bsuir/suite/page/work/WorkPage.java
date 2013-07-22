package by.bsuir.suite.page.work;

import by.bsuir.suite.colors.ColorConstants;
import by.bsuir.suite.page.base.BasePage;
import by.bsuir.suite.page.work.panel.AdditionUserWorkPanel;
import by.bsuir.suite.page.work.panel.WorkTablePanel;
import by.bsuir.suite.util.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.StringResourceModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author d.matveenko
 */
@AuthorizeInstantiation({Roles.USER, Roles.ADMIN, Roles.FLOOR_HEAD,
        Roles.MANAGERESS, Roles.SUPER_USER})
public class WorkPage extends BasePage {
    
    public WorkPage() {
        super(ColorConstants.RED);
        List<ITab> tabs = new ArrayList<ITab>();
        tabs.add(new AbstractTab(new StringResourceModel("tabs.works", this, null)) {

            @Override
            public WebMarkupContainer getPanel(String panelId) {
                return new WorkTablePanel(panelId);
            }
        });
        tabs.add(new AbstractTab(new StringResourceModel("tabs.summary", this, null)) {

            @Override
            public WebMarkupContainer getPanel(String panelId) {
                return new AdditionUserWorkPanel(panelId);
            }
        });
        add(new AjaxTabbedPanel("tabs", tabs));
    }
}
