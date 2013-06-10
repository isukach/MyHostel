package by.bsuir.suite.page.base.panel;

import by.bsuir.suite.dto.person.FloorDto;
import by.bsuir.suite.dto.person.HostelDto;
import by.bsuir.suite.dto.person.PersonInfoDto;
import by.bsuir.suite.dto.person.RoomDto;
import by.bsuir.suite.page.base.NavigationWindow;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;

/**
 * @author d.shemerey
 */
public abstract class NavigationPanel extends Panel {

    private ModalWindow dialog = new NavigationWindow("navigationDialog");

    private final WebMarkupContainer notSelectedLabel;
    private Label hostelLabel;
    private Label floorLabel;
    private Label personLabel;

    private NavigationDialogPanel panel;

    public NavigationPanel(String id, final boolean forRegistration) {
        super(id);
        Injector.get().inject(this);

        notSelectedLabel = new WebMarkupContainer("notSelectedLabel") {
            @Override
            public boolean isVisible() {
                return !panel.isHostelSelected();
            }
        };
        notSelectedLabel.setOutputMarkupPlaceholderTag(true);
        add(notSelectedLabel);

        IModel<String> hostelLabelModel = new StringResourceModel("hostel.label", this, null) {
            @Override
            protected String load() {
                return getLocalizer().getString("hostel.label", NavigationPanel.this) + " " + getHostelLabel();
            }
        };

        hostelLabel = new Label("hostelLabel", hostelLabelModel) {
            @Override
            public boolean isVisible() {
                return panel.isHostelSelected();
            }
        };
        hostelLabel.add(new AjaxEventBehavior("onclick") {
            @Override
            protected void onEvent(AjaxRequestTarget target) {
                addLabelsToTarget(target);
                dialog.show(target);
                panel.openHostelSlide(target);
            }
        });
        hostelLabel.setOutputMarkupPlaceholderTag(true);
        add(hostelLabel);

        IModel<String> floorLabelModel = new StringResourceModel("floor.label", this, null) {
            @Override
            protected String load() {
                return getLocalizer().getString("floor.label", NavigationPanel.this) + " " + getFloorLabel();
            }
        };

        floorLabel = new Label("floorLabel", floorLabelModel) {
            @Override
            public boolean isVisible() {
                return panel.isFloorSelected();
            }
        };
        floorLabel.add(new AjaxEventBehavior("onclick") {
            @Override
            protected void onEvent(AjaxRequestTarget target) {
                panel.openFloorSlide(panel.getSelectedHostel(), target);
                dialog.show(target);
            }
        });
        floorLabel.setOutputMarkupPlaceholderTag(true);
        add(floorLabel);

        IModel<String> personLabelModel = new StringResourceModel("person.label", this, null) {
            @Override
            protected String load() {
                String label = getLocalizer().getString("room.label", NavigationPanel.this)
                        + " " + getRoomNumberLabel();
                if (!forRegistration) {
                    label += ", " + getPersonNameLabel();
                }
                return label;
            }
        };

        personLabel = new Label("personLabel", personLabelModel) {
            @Override
            public boolean isVisible() {
                return panel.isPersonSelected() || panel.isRoomSelected();
            }
        };
        personLabel.add(new AjaxEventBehavior("onclick") {
            @Override
            protected void onEvent(AjaxRequestTarget target) {
                panel.openPersonSlide(panel.getSelectedHostel(), panel.getSelectedFloor(), target);
                dialog.show(target);
            }
        });
        personLabel.setOutputMarkupPlaceholderTag(true);
        add(personLabel);

        panel = new NavigationDialogPanel(dialog.getContentId(), dialog, forRegistration) {

            @Override
            public void onHostelSelected(AjaxRequestTarget target) {
                addLabelsToTarget(target);
            }

            @Override
            public void onFloorSelected(AjaxRequestTarget target) {
                addLabelsToTarget(target);
            }

            @Override
            public void onPersonSelected(AjaxRequestTarget target) {
                addLabelsToTarget(target);
                dialog.close(target);
                NavigationPanel.this.onPersonSelected(target);
            }

            @Override
            public void onPersonDeselected(AjaxRequestTarget target) {
                NavigationPanel.this.onPersonDeselected(target);
            }

            @Override
            public void onEmptySelected(AjaxRequestTarget target,
                                        RoomDto selectedRoom, FloorDto selectedFloor, HostelDto selectedHostel) {
                addLabelsToTarget(target);
                NavigationPanel.this.onEmptySelected(target, selectedRoom, selectedFloor, selectedHostel);
            }
        };

        dialog.setContent(panel);
        dialog.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
            @Override
            public void onClose(AjaxRequestTarget target) {
                NavigationPanel.this.onClose(target);
            }
        });
        add(dialog);

        AjaxFallbackLink<Void> openDialogButton = new AjaxFallbackLink<Void>("navigationButton") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                dialog.show(target);
                panel.openHostelSlide(target);
            }
        };
        add(openDialogButton);
    }

    public void addLabelsToTarget(AjaxRequestTarget target) {
        target.add(hostelLabel);
        target.add(floorLabel);
        target.add(personLabel);
        target.add(notSelectedLabel);
    }

    protected void onClose(AjaxRequestTarget target) {
    }

    protected void onPersonSelected(AjaxRequestTarget target) {
    }

    protected void onPersonDeselected(AjaxRequestTarget target) {
    }

    protected void onEmptySelected(AjaxRequestTarget target,
                                   RoomDto selectedRoom, FloorDto selectedFloor, HostelDto selectedHostel) {
    }

    public boolean isPersonSelected() {
        return panel.isPersonSelected();
    }

    public boolean isRoomSelected() {
        return panel.isRoomSelected();
    }

    public FloorDto getSelectedFloor() {
        return panel.getSelectedFloor();
    }

    public HostelDto getSelectedHostel() {
        return panel.getSelectedHostel();
    }

    public Long getSelectedPersonId() {
        if (panel.isPersonSelected()) {
            return panel.getSelectedPerson().getId();
        } else {
            return null;
        }
    }

    public void initializeSelectedFields(RoomDto selectedRoom, FloorDto selectedFloor, HostelDto selectedHostel) {
        panel.initializeSelectedFields(selectedRoom, selectedFloor, selectedHostel);
    }

    private String getHostelLabel() {
        HostelDto selectedHostel = panel.getSelectedHostel();
        if (selectedHostel != null) {
            return selectedHostel.getNumber().toString();
        } else {
            return "";
        }
    }

    private String getFloorLabel() {
        FloorDto selectedFloor = panel.getSelectedFloor();
        if (selectedFloor != null) {
            return selectedFloor.getNumber();
        } else {
            return "";
        }
    }

    private String getPersonNameLabel() {
        PersonInfoDto person = panel.getSelectedPerson();
        if (person != null) {
            return person.getFullName();
        } else {
            return "";
        }
    }

    private String getRoomNumberLabel() {
        PersonInfoDto person = panel.getSelectedPerson();
        FloorDto selectedFloor = panel.getSelectedFloor();
        RoomDto selectedRoom = panel.getSelectedRoom();
        if (person != null && selectedFloor != null) {
            return selectedFloor.getNumber() + person.getRoomNumber();
        } else if (selectedRoom != null && selectedFloor != null) {
            return selectedFloor.getNumber() + selectedRoom.getRoomNumber();
        } else {
            return "";
        }
    }
}
