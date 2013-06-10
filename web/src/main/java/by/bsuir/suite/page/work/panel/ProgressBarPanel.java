package by.bsuir.suite.page.work.panel;

import by.bsuir.suite.dto.work.JobDto;
import by.bsuir.suite.dto.work.WorkDescriptionCellDto;
import by.bsuir.suite.dto.work.WorkProgressBarDto;
import by.bsuir.suite.page.work.model.ProgressBarModel;
import by.bsuir.suite.service.work.JobService;
import by.bsuir.suite.service.work.WorkService;
import by.bsuir.suite.util.Roles;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.GridView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author d.matveenko
 * @author a.garelik
 */
@AuthorizeAction(action = Action.RENDER, roles = {Roles.USER, Roles.ADMIN, Roles.MANAGERESS, Roles.FLOOR_HEAD,
        Roles.COMMANDANT, Roles.SUPER_USER})
public abstract class ProgressBarPanel extends Panel {

    @SpringBean
    private WorkService workService;

    @SpringBean
    private JobService jobService;
    
    private WorkProgressBarDto progressBarDto;

    private WebMarkupContainer container;

    private List<WorkDescriptionCellDto> gridCells;

    private  WorkDescriptionWindow description;

    private GridView<WorkDescriptionCellDto> gridView;


    public ProgressBarPanel(String id) {
        super(id);
        container = new WebMarkupContainer("progressLine");
        updateProgressBarForSelectedUser();

        description = new WorkDescriptionWindow("description", "label.work.description") {
            @Override
            public void onCancel(AjaxRequestTarget target) {
                close(target);
            }

            @Override
            public void onConfirm(AjaxRequestTarget target) {
                close(target);
            }
        };
        add(description);

        gridCells=createJobCells(jobService.getJobsByPersonId(getPersonId()));

        addProgressBarLabel();
        add(container);
    }

    public final void updateProgressBarForSelectedUser() {
        Long personId = getPersonId();
        progressBarDto = workService.getProgressBarDtoById(personId);
        progressBarDto.setTotalHours(workService.calculateTotalHours(personId));
        container.add(new AttributeModifier("style", "width: " + calculateProgressBarWidth() + "%;"));

        gridCells=createJobCells(jobService.getJobsByPersonId(getPersonId()));

        if (gridView != null) {
            remove(gridView);
        }
        createDescripionMessages(gridCells.size());
    }

    public abstract Long getPersonId();

    private void addProgressBarLabel() {
        Model<ProgressBarModel> progressBarModel = new Model<ProgressBarModel>(
                new ProgressBarModel(progressBarDto.getTotalHours(), progressBarDto.getRequiredHours()));
        Label userHoursLabel = new Label("userHoursLabel", new StringResourceModel("workPage.progressBar.label", this, progressBarModel) {

            @Override
            protected String load() {
                return getLocalizer().getString("workPage.progressBar.label", ProgressBarPanel.this,
                        new Model<ProgressBarModel>(new ProgressBarModel(progressBarDto.getTotalHours(), progressBarDto.getRequiredHours())));
            }
        });
        userHoursLabel.setOutputMarkupPlaceholderTag(true);
        add(userHoursLabel);
    }

    private int calculateProgressBarWidth() {
        int totalHours = progressBarDto.getTotalHours();
        int requiredHours = progressBarDto.getRequiredHours();
        int result = (int)(((double)totalHours / requiredHours) * 100);
        if(result > 100) {
            result = 100;
        }
        return result;
    }

    private List<WorkDescriptionCellDto> createJobCells(List<JobDto> dtos) {
        Collections.sort(dtos, new Comparator<JobDto>() {
            @Override
            public int compare(JobDto o1, JobDto o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
        List<WorkDescriptionCellDto> listCells = new ArrayList<WorkDescriptionCellDto>();
        int offset = 0;
        for (JobDto dto: dtos){
             offset = (int) (offset + (Float.valueOf(dto.getHours()) / progressBarDto.getRequiredHours()) * 100);
             if(offset > 100) {
                 offset=100;
             }
             listCells.add(new WorkDescriptionCellDto(
                     dto.getDate().toString(),
                     dto.getHours(),
                     dto.getDescription(),
                     offset
             ));
        }
        return listCells;
    }

    private void createDescripionMessages(int messagesCount) {
        gridView = new GridView<WorkDescriptionCellDto>("rows",new WorkDescriptionDataProvider() {
            @Override
            public List<WorkDescriptionCellDto> getCells() {
                return gridCells;
            }
        }
        ){

            @Override
            protected void populateEmptyItem(Item<WorkDescriptionCellDto> item) {
                throw new IllegalStateException("No empty items should be on work page!");
            }

            @Override
            protected void populateItem(final Item<WorkDescriptionCellDto> item) {
                final WorkDescriptionCellDto dto = item.getModelObject();
                item.add(new AttributeAppender("style", new Model("left: "+dto.getRelativePosition()+"%"), ";"));
                appendWork(item, dto);
            }

        };

        gridView.setOutputMarkupPlaceholderTag(true);
        if (messagesCount > 0) {
            gridView.setColumns(messagesCount);
        }
        add(gridView);
    }
    private void appendWork(final Item<WorkDescriptionCellDto> item, final WorkDescriptionCellDto dto) {

        final WebMarkupContainer container = new WebMarkupContainer("container");
        container.add(new AjaxEventBehavior("onclick") {
            protected void onEvent(AjaxRequestTarget target) {
                description.updateDescriptionMsg(dto.getDescription()==null || dto.getDescription().equals("") ?
                        "No Description!" : dto.getDescription());
                description.show(target);
            }
        });
        container.setOutputMarkupPlaceholderTag(true);
        container.add(new Label("date", dto.getDate()));
        container.add(new Label("hours", createHoursSignature(dto.getHours())));
        item.add(container);
    }

    private String createHoursSignature(String hours){

        StringResourceModel model =null;
        if(hours.endsWith("1")){
            model = new StringResourceModel("label.work.hoursofwork.1",this,null);
        }else if(hours.endsWith("2") || hours.endsWith("3") || hours.endsWith("4")){
            model = new StringResourceModel("label.work.hoursofwork.2-4",this,null);
        }else{
            model = new StringResourceModel("label.work.hoursofwork.all",this,null);
        }
        return hours+" "+model.getString();
    }
}
