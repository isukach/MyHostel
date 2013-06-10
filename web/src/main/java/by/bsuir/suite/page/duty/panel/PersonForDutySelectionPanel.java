package by.bsuir.suite.page.duty.panel;

import by.bsuir.suite.comparator.PersonInfoDtoByNameComparator;
import by.bsuir.suite.comparator.RoomDtoByNumberComparator;
import by.bsuir.suite.dto.person.FloorDto;
import by.bsuir.suite.dto.person.PersonInfoDto;
import by.bsuir.suite.dto.person.RoomDto;
import by.bsuir.suite.page.base.panel.HostelPanel;
import by.bsuir.suite.service.UserService;
import by.bsuir.suite.service.person.FloorService;
import by.bsuir.suite.service.person.RoomService;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Collections;
import java.util.List;

/**
 * @author i.sukach
 */
public abstract class PersonForDutySelectionPanel extends HostelPanel {

    public static final int MAX_ROOM_POPULATION = 4;

    @SpringBean
    @SuppressWarnings("unused")
    private RoomService roomService;

    @SpringBean
    @SuppressWarnings("unused")
    private UserService userService;

    @SpringBean
    @SuppressWarnings("unused")
    private FloorService floorService;

    private FloorDto floorDto;

    private PersonInfoDto selectedPerson;

    public PersonForDutySelectionPanel(String id, final ModalWindow window, final Long floorId) {
        super(id);

        floorDto = floorService.get(floorId);
        IModel<List<RoomDto>> roomModel = new LoadableDetachableModel<List<RoomDto>>() {
            @Override
            protected List<RoomDto> load() {
                List<RoomDto> rooms = roomService.getRoomsForFloorId(floorId);
                Collections.sort(rooms, new RoomDtoByNumberComparator());
                return rooms;
            }
        };

        WebMarkupContainer roomSlide = new WebMarkupContainer("roomContainer");
        roomSlide.add(new ListView<RoomDto>("roomRepeater", roomModel) {

            @Override
            protected void populateItem(ListItem<RoomDto> item) {
                RoomDto room = item.getModelObject();
                item.add(new Label("roomNumber", new Model<String>(floorDto.getNumber() + room.getRoomNumber())));
                Collections.sort(room.getPersons(), new PersonInfoDtoByNameComparator());
                item.add(new ListView<PersonInfoDto>("personButtonContainer", getFilledPersons(room)) {
                    @Override
                    protected void populateItem(ListItem<PersonInfoDto> item) {
                        final PersonInfoDto person = item.getModelObject();
                        AjaxFallbackLink<Void> personButton = new AjaxFallbackLink<Void>("personButton") {
                            @Override
                            public void onClick(AjaxRequestTarget target) {
                                if (person.getFullName() != null) {
                                    selectedPerson = person;
                                    onPersonSelected(target);
                                    window.close(target);
                                }
                            }
                        };
                        personButton.add(new Label("personName", person.getFullName() != null ? person.getFullName() : "Free"));
                        item.add(personButton);
                    }
                });
            }
        });
        roomSlide.setOutputMarkupPlaceholderTag(true);
        add(roomSlide);
        add(new AjaxFallbackLink<Void>("cancelButton") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                window.close(target);
            }
        });
    }

    public abstract void onPersonSelected(AjaxRequestTarget target);

    public PersonInfoDto getSelectedPerson() {
        return selectedPerson;
    }

    private List<PersonInfoDto> getFilledPersons(RoomDto room) {
        List<PersonInfoDto> persons = room.getPersons();
        int personsToAdd = MAX_ROOM_POPULATION - persons.size();
        for (int i = 0; i < personsToAdd; i++) {
            persons.add(new PersonInfoDto());
        }
        return persons;
    }
}
