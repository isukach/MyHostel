package by.bsuir.suite.page.registration;

import by.bsuir.suite.colors.ColorConstants;
import by.bsuir.suite.dto.person.FloorDto;
import by.bsuir.suite.dto.person.HostelDto;
import by.bsuir.suite.dto.person.RoomDto;
import by.bsuir.suite.page.base.BasePage;
import by.bsuir.suite.page.base.panel.NavigationPanel;
import by.bsuir.suite.service.person.FloorService;
import by.bsuir.suite.service.person.HostelService;
import by.bsuir.suite.util.Roles;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author i.sukach
 */
@AuthorizeInstantiation(value = {Roles.REGISTRAR, Roles.SUPER_USER, Roles.FLOOR_HEAD})
public class RegistrationPage extends BasePage {

    private static final String HOSTEL_ID_KEY = "hostel_id";
    private static final String FLOOR_ID_KEY = "floor_id";

    private RoomerRegistrationPanel registrationPanel;

    private RegistrationNavigationPanel navigationPanel;

    @SpringBean
    private FloorService floorService;

    @SpringBean
    private HostelService hostelService;

    public RegistrationPage(PageParameters pageParameters) {
        super(ColorConstants.GREY);

        registrationPanel = new RoomerRegistrationPanel("registrationPanel") {
            @Override
            public boolean isVisible() {
                return navigationPanel.isRoomSelected();
            }

            @Override
            protected void onPageParametersAdded(PageParameters pageParameters) {
                FloorDto selectedFloor = navigationPanel.getSelectedFloor();
                if (selectedFloor != null) {
                    pageParameters.add(FLOOR_ID_KEY, selectedFloor.getId());
                }
                HostelDto selectedHostel = navigationPanel.getSelectedHostel();
                if (selectedHostel != null) {
                    pageParameters.add(HOSTEL_ID_KEY, selectedHostel.getId());
                }
            }
        };
        registrationPanel.setOutputMarkupPlaceholderTag(true);

        navigationPanel = new RegistrationNavigationPanel("navigationPanel", true);

        if (pageParameters != null) {
            retrieveHostelAndFloorFromParameters(pageParameters);
        }

        add(registrationPanel);
        add(navigationPanel);
    }

    private void retrieveHostelAndFloorFromParameters(PageParameters pageParameters) {
        long hostelId = pageParameters.get(HOSTEL_ID_KEY).toLong();
        HostelDto hostelDto = hostelService.get(hostelId);

        long floorId = pageParameters.get(FLOOR_ID_KEY).toLong();
        FloorDto floorDto = floorService.get(floorId);

        navigationPanel.initializeSelectedFields(null, floorDto, hostelDto);
    }

    private class RegistrationNavigationPanel extends NavigationPanel {

        public RegistrationNavigationPanel(String id, boolean forRegistration) {
            super(id, forRegistration);
        }

        @Override
        protected void onEmptySelected(AjaxRequestTarget target,
                                       RoomDto selectedRoom, FloorDto selectedFloor, HostelDto selectedHostel) {
            registrationPanel.setRoom(selectedRoom);
            target.add(registrationPanel);
        }

        @Override
        protected void onPersonDeselected(AjaxRequestTarget target) {
            target.add(registrationPanel);
        }
    }
}
