package by.bsuir.suite.page.test;

import by.bsuir.suite.colors.ColorConstants;
import by.bsuir.suite.page.base.BasePage;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;

/**
 * Created by IntelliJ IDEA.
 * User: kda
 * Date: 17.07.12
 * Time: 14:25
 * To change this template use File | Settings | File Templates.
 */
@AuthorizeInstantiation({"user", "admin"})
public class TestPage extends BasePage {

    public TestPage() {
        super(ColorConstants.BLACK);
    }
}
