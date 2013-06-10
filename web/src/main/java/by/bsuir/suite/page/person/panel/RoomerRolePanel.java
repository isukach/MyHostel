package by.bsuir.suite.page.person.panel;


import by.bsuir.suite.util.Roles;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

/**
 * @author  i.sukach
 */
public class RoomerRolePanel extends Panel {

    public RoomerRolePanel(String id) {
        super(id);

        RadioGroup radio = new RadioGroup("role");
        radio.add(new Radio<String>("userLabel", new Model<String>(Roles.USER)));
        radio.add(new Radio<String>("adminLabel", new Model<String>(Roles.ADMIN)));
        radio.add(new Radio<String>("floorHeadLabel", new Model<String>(Roles.FLOOR_HEAD)));
        radio.add(new Radio<String>("commandantLabel", new Model<String>(Roles.COMMANDANT)));
        add(radio);
    }
}
