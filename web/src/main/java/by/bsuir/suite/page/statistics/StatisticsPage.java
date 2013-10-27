package by.bsuir.suite.page.statistics;

import by.bsuir.suite.colors.ColorConstants;
import by.bsuir.suite.comparator.FloorDtoByNumberComparator;
import by.bsuir.suite.dto.person.CalendarPersonDto;
import by.bsuir.suite.dto.person.FloorDto;
import by.bsuir.suite.dto.statistics.FloorStatisticsDto;
import by.bsuir.suite.page.base.BasePage;
import by.bsuir.suite.page.duty.panel.CalendarPanel;
import by.bsuir.suite.page.person.option.SelectOption;
import by.bsuir.suite.service.person.FloorService;
import by.bsuir.suite.service.person.PersonService;
import by.bsuir.suite.service.statistics.StatisticsService;
import by.bsuir.suite.session.HostelAuthenticatedWebSession;
import by.bsuir.suite.util.Roles;
import by.bsuir.suite.util.StatisticsUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.injection.Injector;
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
import java.util.Iterator;
import java.util.List;

/**
 * @author i.sukach
 */
@AuthorizeInstantiation({Roles.FLOOR_HEAD, Roles.MANAGERESS, Roles.COMMANDANT, Roles.SUPER_USER, Roles.EDUCATOR})
public class StatisticsPage extends BasePage {

    private AjaxFallbackDefaultDataTable statisticsTable;

    private DropDownChoice<SelectOption> floorChoice;

    private SelectOption currentSelectedFloor;

    private Long floorId;

    @SpringBean
    private StatisticsService statisticsService;

    @SpringBean
    private PersonService personService;

    @SpringBean
    private FloorService floorService;

    public StatisticsPage() {
        super(ColorConstants.GREY);

        addFloorSelector();
        addFloorStatisticsTable();
    }

    private void addFloorSelector() {
        CalendarPersonDto currentUser = personService.getCalendarPerson(
                ((HostelAuthenticatedWebSession) getSession()).getUser().getPersonId());
        floorId = currentUser.getFloorId();

        List<SelectOption> options = new ArrayList<SelectOption>();
        ChoiceRenderer<SelectOption> renderer = new ChoiceRenderer<SelectOption>("value", "key");
        floorChoice = new FloorDropDown("floorSelect",
                new PropertyModel<SelectOption>(this, "currentSelectedFloor"), options, renderer);

        if (floorChoice.isRenderAllowed()) {
            List<FloorDto> floors = floorService.getFloorsForHostelId(1L);
            Collections.sort(floors, new FloorDtoByNumberComparator());
            FloorDto defaultFloor;
            for (FloorDto floorDto : floors) {
                options.add(new SelectOption(floorDto.getId().toString(), floorDto.getNumber()));
            }
            if (floorId != null) {
                defaultFloor = floorService.get(floorId);
                floorChoice.setDefaultModelObject(new SelectOption(floorId.toString(), defaultFloor.getNumber()));
            } else {
                floorChoice.setDefaultModelObject(options.get(0));
                floorId = Long.valueOf(options.get(0).getKey());
            }
            floorChoice.add(new OnChangeAjaxBehavior() {
                @Override
                protected void onUpdate(AjaxRequestTarget target) {
                    floorId = Long.valueOf(currentSelectedFloor.getKey());
                    target.add(statisticsTable);
                }
            });
        }

        add(floorChoice);
        add(new FloorLabel("floorLabel", new StringResourceModel("floor.label", this, null)));
    }

    private void addFloorStatisticsTable() {
        List<IColumn<FloorStatisticsDto>> columns = new ArrayList<IColumn<FloorStatisticsDto>>();

        columns.add(new PropertyColumn<FloorStatisticsDto>(new StringResourceModel("tableTitle.first.name", this, null),
                StatisticsUtils.FLOOR_SORT_BY_FIRST_NAME, "firstName"));
        columns.add(new PropertyColumn<FloorStatisticsDto>(new StringResourceModel("tableTitle.last.name", this, null),
                StatisticsUtils.FLOOR_SORT_BY_LAST_NAME, "lastName"));
        columns.add(new PropertyColumn<FloorStatisticsDto>(new StringResourceModel("tableTitle.duty.percentage", this, null),
                StatisticsUtils.FLOOR_SORT_BY_COMPLETED_DUTIES_PERCENTAGE, "dutyCompletion"));
        columns.add(new PropertyColumn<FloorStatisticsDto>(new StringResourceModel("tableTitle.work.percentage", this, null),
                StatisticsUtils.FLOOR_SORT_BY_COMPLETED_HOURS_PERCENTAGE, "workCompletion"));

        statisticsTable = new AjaxFallbackDefaultDataTable<FloorStatisticsDto>("floorStatisticsTable", columns,
                new FloorStatisticsProvider(), 1000);
        statisticsTable.setOutputMarkupId(true);
        add(statisticsTable);
    }

    private class FloorStatisticsProvider extends SortableDataProvider<FloorStatisticsDto> {

        private FloorStatisticsProvider() {
            Injector.get().inject(this);
            setSort(StatisticsUtils.FLOOR_SORT_BY_LAST_NAME, SortOrder.ASCENDING);
        }

        @Override
        public Iterator<? extends FloorStatisticsDto> iterator(int i, int i2) {
            return statisticsService.getFloorStatistics(floorId, getSort().getProperty()).iterator();
        }

        @Override
        public int size() {
            return statisticsService.getPersonCountForFloor(floorId);
        }

        @Override
        public IModel<FloorStatisticsDto> model(FloorStatisticsDto floorStatisticsDto) {
            return new DetachableFloorStatisticsTableModel(floorStatisticsDto.getPersonId());
        }
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
