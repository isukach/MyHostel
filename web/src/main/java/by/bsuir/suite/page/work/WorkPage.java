package by.bsuir.suite.page.work;

import by.bsuir.suite.colors.ColorConstants;
import by.bsuir.suite.page.base.BasePage;
import by.bsuir.suite.page.work.panel.AdditionUserWorkPanel;
import by.bsuir.suite.util.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;

/**
 * @author d.matveenko
 */
@AuthorizeInstantiation({Roles.USER, Roles.ADMIN, Roles.FLOOR_HEAD,
        Roles.MANAGERESS, Roles.SUPER_USER})
public class WorkPage extends BasePage {
    
    public WorkPage() {
        super(ColorConstants.RED);
        add(new AdditionUserWorkPanel("additionUserWorkPanel"));
    }
}
