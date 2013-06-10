package by.bsuir.suite.page.registration;

import by.bsuir.suite.colors.ColorConstants;
import by.bsuir.suite.page.base.BasePage;
import by.bsuir.suite.util.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;

/**
 * @author i.sukach
 */
@AuthorizeInstantiation(value = {Roles.REGISTRAR, Roles.SUPER_USER})
public class StaffRegistrationPage extends BasePage {

    public StaffRegistrationPage() {
        super(ColorConstants.GREY);

        add(new StaffRegistrationPanel("registrationPanel"));
    }
}
