package by.bsuir.suite.page.person.panel;

import by.bsuir.suite.dto.person.PersonDto;
import by.bsuir.suite.page.base.panel.HostelPanel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;

/**
 * @author i.sukach
 */
public class InfoPanelView extends HostelPanel {
    public InfoPanelView(String id, PersonDto personDto) {
        super(id);
        
        add(new Label("name", personDto.getFirstName() + " " + personDto.getLastName()));
        add(new Label("faculty",
                new StringResourceModel("faculty." + personDto.getFaculty().name().toLowerCase(), this, null)));
        add(new Label("course", personDto.getCourse()));
        add(new Label("group", personDto.getGroup()));
        add(new Label("phoneNumber", personDto.getPhoneNumber()));
        add(new Label("role", new ResourceModel(personDto.getRole())));
        add(new Label("from", personDto.getFrom()));
        add(new Label("facilities", personDto.getFacilities()));
        add(new Label("about", personDto.getAbout()));

        if (personDto.getRoom()!= null) {
            add(new Label("room", personDto.getFloor().getNumber() + personDto.getRoom().getRoomNumber()));
            add(new Label("hostel", "#" + personDto.getHostel().getNumber()));
        } else {
            add(new Label("room", new StringResourceModel("user.status.evicted", this, null)));
            add(new Label("hostel", new StringResourceModel("user.status.evicted", this, null)));
        }

    }
}
