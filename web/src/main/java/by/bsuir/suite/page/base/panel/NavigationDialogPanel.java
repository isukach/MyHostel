package by.bsuir.suite.page.base.panel;

import by.bsuir.suite.comparator.FloorDtoByNumberComparator;
import by.bsuir.suite.comparator.PersonInfoDtoByNameComparator;
import by.bsuir.suite.comparator.RoomDtoByNumberComparator;
import by.bsuir.suite.dto.person.FloorDto;
import by.bsuir.suite.dto.person.HostelDto;
import by.bsuir.suite.dto.person.PersonInfoDto;
import by.bsuir.suite.dto.person.RoomDto;
import by.bsuir.suite.service.person.FloorService;
import by.bsuir.suite.service.person.HostelService;
import by.bsuir.suite.service.person.RoomService;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author i.sukach
 */
public abstract class NavigationDialogPanel extends Panel {

    private static final int MAX_ROOM_POPULATION = 4;

    private static final String HOSTEL_SLIDE_LEFT = "0%";
    private static final String FLOOR_SLIDE_LEFT = "-100%";
    private static final String PERSON_SLIDE_LEFT = "-200%";

    @SpringBean
    @SuppressWarnings("unused")
    private HostelService hostelService;

    @SpringBean
    @SuppressWarnings("unused")
    private FloorService floorService;

    @SpringBean
    @SuppressWarnings("unused")
    private RoomService roomService;

    private HostelDto selectedHostel;

    private FloorDto selectedFloor;

    private RoomDto selectedRoom;

    private PersonInfoDto selectedPerson;

    private final WebMarkupContainer floorSlide;

    private final WebMarkupContainer roomSlide;

    private final WebMarkupContainer navigationSlides;

    public NavigationDialogPanel(String id, final ModalWindow window, final boolean forRegistration) {
        super(id);
        Injector.get().inject(this);

        navigationSlides = new WebMarkupContainer("navigationSlides");
        navigationSlides.setOutputMarkupPlaceholderTag(true);
        add(navigationSlides);

        navigationSlides.add(new ListView<HostelDto>("hostelContainer", getAllHostels()) {

            @Override
            protected void populateItem(ListItem<HostelDto> item) {
                final HostelDto hostel = item.getModelObject();
                AjaxFallbackLink<Void> hostelButton = new AjaxFallbackLink<Void>("hostelButton") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        selectedHostel = hostel;
                        deselectFloor();
                        deselectRoom();
                        deselectPerson();
                        onHostelSelected(target);
                        target.add(floorSlide);
                        deselectPerson();
                    }
                };
                item.add(hostelButton);
                hostelButton.add(new Label("hostelNumber", hostel.getNumber().toString()));
            }
        });

        IModel<List<FloorDto>> floorModel = new LoadableDetachableModel<List<FloorDto>>() {
            @Override
            protected List<FloorDto> load() {
                if (selectedHostel != null) {
                    List<FloorDto> floors = floorService.getFloorsForHostelId(selectedHostel.getId());
                    Collections.sort(floors, new FloorDtoByNumberComparator());
                    return floors;
                } else {
                    return new ArrayList<FloorDto>();
                }
            }
        };

        floorSlide = new WebMarkupContainer("floorContainer");
        floorSlide.add(new ListView<FloorDto>("floorRepeater", floorModel) {
            @Override
            protected void populateItem(ListItem<FloorDto> item) {
                final FloorDto floor = item.getModelObject();
                AjaxFallbackLink<Void> floorButton = new AjaxFallbackLink<Void>("floorButton") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        selectedFloor = floor;
                        onFloorSelected(target);
                        target.add(roomSlide);
                        deselectPerson();
                        deselectRoom();
                    }
                };
                item.add(floorButton);
                floorButton.add(new Label("floorNumber", floor.getNumber()));

                WebMarkupContainer populationContainer = new WebMarkupContainer("populationBar");
                populationContainer.add(
                        new AttributeModifier("style", "width:" + getPopulationPercentageForFloor(floor) + "%"));
                floorButton.add(populationContainer);

                floorButton.add(new Label("populationLabel", getPopulationLabelForFloor(floor)));
            }
        });
        floorSlide.setOutputMarkupPlaceholderTag(true);
        navigationSlides.add(floorSlide);

        IModel<List<RoomDto>> roomModel = new LoadableDetachableModel<List<RoomDto>>() {
            @Override
            protected List<RoomDto> load() {
                if (selectedFloor != null) {
                    List<RoomDto> rooms = roomService.getRoomsForFloorId(selectedFloor.getId());
                    Collections.sort(rooms, new RoomDtoByNumberComparator());
                    return rooms;
                } else {
                    return new ArrayList<RoomDto>();
                }
            }
        };

        roomSlide = new WebMarkupContainer("roomContainer");
        roomSlide.add(new ListView<RoomDto>("roomRepeater", roomModel) {

            @Override
            protected void populateItem(ListItem<RoomDto> item) {
                final RoomDto room = item.getModelObject();
                item.add(new Label("roomNumber", new Model<String>(selectedFloor.getNumber() + room.getRoomNumber())));
                Collections.sort(room.getPersons(), new PersonInfoDtoByNameComparator());
                item.add(new ListView<PersonInfoDto>("personButtonContainer", getFilledPersons(room)) {
                    @Override
                    protected void populateItem(ListItem<PersonInfoDto> item) {
                        final PersonInfoDto person = item.getModelObject();
                        AjaxFallbackLink<Void> personButton = new AjaxFallbackLink<Void>("personButton") {
                            @Override
                            public void onClick(AjaxRequestTarget target) {
                                if (person.getFullName() != null && !forRegistration) {
                                    selectedRoom = null;
                                    selectedPerson = person;
                                    onPersonSelected(target);
                                    window.close(target);
                                } else if (person.getFullName() == null && forRegistration) {
                                    selectedPerson = null;
                                    selectedRoom = room;
                                    onEmptySelected(target, room, selectedFloor, selectedHostel);
                                    window.close(target);
                                }
                            }
                        };
                        if (person.getFullName() != null) {
                            personButton.add(new Label("personName", person.getFullName()));
                        } else {
                            personButton.add(new Label("personName",
                                    new StringResourceModel("label.free", NavigationDialogPanel.this, null)));
                        }
                        item.add(personButton);
                    }
                });
            }
        });
        roomSlide.setOutputMarkupPlaceholderTag(true);
        navigationSlides.add(roomSlide);

        add(new AjaxFallbackLink<Void>("cancelButton") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                if (!isPersonSelected()) {
                    onPersonDeselected(target);
                }
                window.close(target);
            }
        });
    }

    public abstract void onHostelSelected(AjaxRequestTarget target);

    public abstract void onFloorSelected(AjaxRequestTarget target);

    public abstract void onPersonSelected(AjaxRequestTarget target);

    public abstract void onPersonDeselected(AjaxRequestTarget target);

    public abstract void onEmptySelected(AjaxRequestTarget target,
                                         RoomDto selectedRoom, FloorDto selectedFloor, HostelDto selectedHostel);

    public boolean isHostelSelected() {
        return selectedHostel != null;
    }

    public boolean isFloorSelected() {
        return selectedFloor != null;
    }

    public boolean isPersonSelected() {
        return selectedPerson != null;
    }

    public boolean isRoomSelected() {
        return selectedRoom != null;
    }

    public FloorDto getSelectedFloor() {
        return selectedFloor;
    }

    public HostelDto getSelectedHostel() {
        return selectedHostel;
    }

    public PersonInfoDto getSelectedPerson() {
        return selectedPerson;
    }

    public RoomDto getSelectedRoom() {
        return selectedRoom;
    }

    public void openHostelSlide(AjaxRequestTarget target) {
        navigationSlides.add(new AttributeModifier("style", "left:" + HOSTEL_SLIDE_LEFT));
        target.add(navigationSlides);
    }

    public void openFloorSlide(HostelDto hostel, AjaxRequestTarget target) {
        selectedHostel = hostel;
        navigationSlides.add(new AttributeModifier("style", "left:" + FLOOR_SLIDE_LEFT));
        target.add(navigationSlides);
    }

    public void openPersonSlide(HostelDto hostel, FloorDto floor, AjaxRequestTarget target) {
        selectedHostel = hostel;
        selectedFloor = floor;
        navigationSlides.add(new AttributeModifier("style", "left:" + PERSON_SLIDE_LEFT));
        target.add(navigationSlides);
    }

    private List<PersonInfoDto> getFilledPersons(RoomDto room) {
        List<PersonInfoDto> persons = room.getPersons();
        int personsToAdd = MAX_ROOM_POPULATION - persons.size();
        for (int i = 0; i < personsToAdd; i++) {
            persons.add(new PersonInfoDto());
        }
        return persons;
    }

    private void deselectFloor() {
        selectedFloor = null;
    }

    private void deselectPerson() {
        selectedPerson = null;
    }

    private void deselectRoom() {
        selectedRoom = null;
    }

    private int getPopulationPercentageForFloor(FloorDto floor) {
        return ((int) (((double) floor.getCurrentPopulation() / (double) floor.getMaxPopulation()) * 100));
    }

    private String getPopulationLabelForFloor(FloorDto floor) {
        return floor.getCurrentPopulation() + "/" + floor.getMaxPopulation();
    }

    private List<HostelDto> getAllHostels() {
        return hostelService.getAll();
    }

    public void initializeSelectedFields(RoomDto selectedRoom, FloorDto selectedFloor, HostelDto selectedHostel) {
        this.selectedRoom = selectedRoom;
        this.selectedFloor = selectedFloor;
        this.selectedHostel = selectedHostel;
    }
}
