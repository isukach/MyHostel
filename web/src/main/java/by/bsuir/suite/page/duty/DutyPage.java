package by.bsuir.suite.page.duty;

import by.bsuir.suite.colors.ColorConstants;
import by.bsuir.suite.comparator.FloorDtoByNumberComparator;
import by.bsuir.suite.dto.person.CalendarPersonDto;
import by.bsuir.suite.dto.person.FloorDto;
import by.bsuir.suite.page.base.BasePage;
import by.bsuir.suite.page.duty.panel.CalendarPanel;
import by.bsuir.suite.page.person.option.SelectOption;
import by.bsuir.suite.service.notifications.NotificationService;
import by.bsuir.suite.service.notifications.common.NotificationKeys;
import by.bsuir.suite.service.person.FloorService;
import by.bsuir.suite.service.person.PersonService;
import by.bsuir.suite.session.HostelAuthenticatedWebSession;
import by.bsuir.suite.util.Roles;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author i.sukach
 */
@AuthorizeInstantiation({Roles.USER, Roles.ADMIN, Roles.FLOOR_HEAD, Roles.EDUCATOR, Roles.SUPER_USER, Roles.COMMANDANT})
public class DutyPage extends BasePage {

    private CalendarPanel calendarPanel;

    private DropDownChoice<SelectOption> floorChoice;

    private SelectOption currentSelectedFloor;

    @SpringBean
    private FloorService floorService;

    @SpringBean
    private PersonService personService;

    public DutyPage() {
        super(ColorConstants.ORANGE);

        CalendarPersonDto currentUser = personService.getCalendarPerson(
                ((HostelAuthenticatedWebSession) getSession()).getUser().getPersonId());
        Long floorId = currentUser.getFloorId();
        List<SelectOption> options = new ArrayList<SelectOption>();
        ChoiceRenderer<SelectOption> renderer = new ChoiceRenderer<SelectOption>("value", "key");
        floorChoice = new FloorDropDown("floorSelect",
                new PropertyModel<SelectOption>(this, "currentSelectedFloor"), options, renderer);

        if (floorId != null && !floorChoice.isRenderAllowed()) {
            calendarPanel = new CalendarPanel("calendarPanel", currentUser, floorId);
        } else if (floorChoice.isRenderAllowed()) {
            List<FloorDto> floors = floorService.getFloorsForHostelId(1L);
            Collections.sort(floors, new FloorDtoByNumberComparator());
            FloorDto defaultFloor;
            for (FloorDto floorDto : floors) {
                options.add(new SelectOption(floorDto.getId().toString(), floorDto.getNumber()));
            }
            if (floorId != null) {
                defaultFloor = floorService.get(floorId);
                floorChoice.setDefaultModelObject(new SelectOption(floorId.toString(), defaultFloor.getNumber()));
                calendarPanel = new CalendarPanel("calendarPanel", currentUser, floorId);
            } else {
                floorChoice.setDefaultModelObject(options.get(0));
                calendarPanel = new CalendarPanel("calendarPanel", currentUser, Long.valueOf(options.get(0).getKey()));
            }
            floorChoice.add(new OnChangeAjaxBehavior() {
                @Override
                protected void onUpdate(AjaxRequestTarget target) {
                    calendarPanel.updateCalendar(Long.valueOf(currentSelectedFloor.getKey()), target);
                }
            });
        }

        add(floorChoice);
        add(calendarPanel);
        add(new FloorLabel("floorLabel", new StringResourceModel("floor.label", this, null)));
    }

    @AuthorizeAction(action = Action.RENDER, roles = {Roles.COMMANDANT, Roles.EDUCATOR, Roles.SUPER_USER})
    private class FloorLabel extends Label {
        public FloorLabel(String floorLabel, StringResourceModel stringResourceModel) {
            super(floorLabel, stringResourceModel);
        }
    }

    @AuthorizeAction(action = Action.RENDER, roles = {Roles.COMMANDANT, Roles.EDUCATOR, Roles.SUPER_USER})
    private class FloorDropDown extends DropDownChoice<SelectOption> {
        public FloorDropDown(String id, IModel<SelectOption> model,
                             List<? extends SelectOption> choices, IChoiceRenderer<? super SelectOption> renderer) {
            super(id, model, choices, renderer);
        }
    }
}
