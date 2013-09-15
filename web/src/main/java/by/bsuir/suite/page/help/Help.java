package by.bsuir.suite.page.help;

import by.bsuir.suite.colors.ColorConstants;
import by.bsuir.suite.page.base.BasePage;
import by.bsuir.suite.page.help.panel.HelpPanel;
import by.bsuir.suite.session.HostelAuthenticatedWebSession;
import by.bsuir.suite.util.Roles;
import by.bsuir.suite.util.UploadUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.resource.ByteArrayResource;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * User: CHEB
 */
@AuthorizeInstantiation({Roles.USER, Roles.ADMIN, Roles.FLOOR_HEAD, Roles.MANAGERESS, Roles.COMMANDANT,
        Roles.EDUCATOR, Roles.SUPER_USER, Roles.REGISTRAR, Roles.YOUTH_CENTER})
public class Help extends BasePage {

    public Help() {
        super(ColorConstants.GREY);

        add(new HelpPanel("helpPanel"));
    }
}
