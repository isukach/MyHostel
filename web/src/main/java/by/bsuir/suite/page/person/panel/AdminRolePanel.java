package by.bsuir.suite.page.person.panel;

import by.bsuir.suite.util.Roles;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

/**
 * @author i.sukach
 */
public class AdminRolePanel extends Panel {

    public AdminRolePanel(String id) {
        super(id);

        RadioGroup radio = new RadioGroup("role");
        radio.add(new Radio<String>("educatorLabel", new Model<String>(Roles.EDUCATOR)));
        radio.add(new Radio<String>("manageressLabel", new Model<String>(Roles.MANAGERESS)));
        radio.add(new Radio<String>("registrarLabel", new Model<String>(Roles.REGISTRAR)));
        radio.add(new Radio<String>("youthCenterLabel", new Model<String>(Roles.YOUTH_CENTER)));
        add(radio);
    }
}
