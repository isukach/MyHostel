package by.bsuir.suite.page.work.panel;

import by.bsuir.suite.dto.person.PersonJobOfferDto;
import by.bsuir.suite.dto.work.CommitJobOfferDto;
import by.bsuir.suite.dto.work.JobOfferDto;
import by.bsuir.suite.page.base.NonContentWindow;
import by.bsuir.suite.page.base.NotificationWindow;
import by.bsuir.suite.page.base.panel.ConfirmationPanel;
import by.bsuir.suite.page.duty.panel.ConfirmationAnswer;
import by.bsuir.suite.page.work.model.DetachableJobOfferTableModel;
import by.bsuir.suite.page.work.window.CreateEditJobOfferWindow;
import by.bsuir.suite.service.notifications.NotificationService;
import by.bsuir.suite.service.notifications.common.NewJobIsAvailableTask;
import by.bsuir.suite.service.notifications.common.NotificationKeys;
import by.bsuir.suite.service.work.JobOfferService;
import by.bsuir.suite.session.HostelAuthenticatedWebSession;
import by.bsuir.suite.util.DateUtils;
import by.bsuir.suite.util.Roles;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * User: Matveyenka Denis
 * Date: 20.06.13
 */
public class WorkTablePanel extends Panel {

    private static final int NUMBER_ROWS = 15;

    private ModalWindow subscribeConfirmWindow;

    private ModalWindow createEditJobOfferWindow;

    private ModalWindow commitJobOfferWindow;

    private AjaxFallbackDefaultDataTable<JobOfferDto> table;

    @SpringBean
    private JobOfferService jobOfferService;

    @SpringBean
    private NotificationService notificationService;


    public WorkTablePanel(String id) {
        super(id);
        addConfirmWindow();
        addCreateEditJobOfferWindow();
        addCommitJobOfferWindow();
        addCreateNewJobOfferButton();
        addTable();
    }

    private void addCommitJobOfferWindow() {
        commitJobOfferWindow = new NonContentWindow("commitJobOfferDialog");
        commitJobOfferWindow.setOutputMarkupId(true);
        add(commitJobOfferWindow);
    }

    private void addCreateNewJobOfferButton() {
        AjaxFallbackLink createNewJobOfferButton = new CreateNewJobOfferButton("createNewJobOfferButton");
        add(createNewJobOfferButton);
    }

    private void addTable() {
        table = new AjaxFallbackDefaultDataTable<JobOfferDto>("worksSummary", getTableColumns(),
                new JobOfferDataProvider(), NUMBER_ROWS);
        table.setOutputMarkupId(true);
        add(table);
    }

    private List<IColumn<JobOfferDto>> getTableColumns() {
        List<IColumn<JobOfferDto>> columns = new ArrayList<IColumn<JobOfferDto>>();
        columns.add(getTableColumn("table.column.date", "work-date", "date"));
        columns.add(getTableColumn("table.column.description", "work-description", "description"));
        columns.add(getTableColumn("table.column.hours", "work-hours", "hours"));
        columns.add(getTableColumn("table.column.requiredNumber", "work-number", "numberOfPeoples"));
        columns.add(getTableColumn("table.column.subscribedNumber", "work-number", "personJobOfferSize"));
        columns.add(new AbstractColumn<JobOfferDto>(new StringResourceModel("table.column.actions", this, null)) {
            @Override
            public void populateItem(Item<ICellPopulator<JobOfferDto>> components, String componentId, IModel<JobOfferDto> jobOfferDtoIModel) {
                components.add(new AttributeModifier("class", "work-actions"));
                components.add(new ActionPanel(componentId, jobOfferDtoIModel.getObject()));
            }
        });

        return columns;
    }

    private AbstractColumn<JobOfferDto> getTableColumn(String resourceModelKey, final String columnStyle, String propertyExpression) {
        return new PropertyColumn<JobOfferDto>(new StringResourceModel(resourceModelKey, this, null),
                propertyExpression) {
            @Override
            public void populateItem(Item<ICellPopulator<JobOfferDto>> item, String componentId, IModel<JobOfferDto> rowModel) {
                item.add(new AttributeModifier("class", columnStyle));
                super.populateItem(item, componentId, rowModel);
            }
        };
    }

    private void addCreateEditJobOfferWindow() {
        createEditJobOfferWindow = new CreateEditJobOfferWindow("createViewJobOfferDialog");
        add(createEditJobOfferWindow);
    }

    private void addConfirmWindow() {
        subscribeConfirmWindow = new NotificationWindow("subscribeConfirmDialog", "workTablePanel.subscribeConfirmDialog.header",
                "workTablePanel.subscribeConfirmDialog.content", false) {
            @Override
            public void onCancel(AjaxRequestTarget target) {
                close(target);
            }

            @Override
            public void onConfirm(AjaxRequestTarget target) {
                close(target);
            }
        };
        add(subscribeConfirmWindow);
    }

    private class JobOfferDataProvider extends SortableDataProvider<JobOfferDto> {

        public JobOfferDataProvider() {
            Injector.get().inject(this);
            setSort("date", SortOrder.ASCENDING);
        }


        @Override
        public Iterator<JobOfferDto> iterator(int first, int count) {
            return jobOfferService.findJobOffer(first, count).iterator();
        }

        @Override
        public int size() {
            return jobOfferService.getJobOfferCount().intValue();
        }

        @Override
        public IModel<JobOfferDto> model(JobOfferDto jobOfferDto) {
            return new DetachableJobOfferTableModel(jobOfferDto);
        }
    }

    private class ActionPanel extends  Panel {

        private JobOfferDto jobOfferDto;

        public ActionPanel(String id, JobOfferDto jobOfferDto) {
            super(id);
            this.jobOfferDto = jobOfferDto;
            Label subscribeLabel = new SubscribeJobOfferLabel("subscribeLabel", new StringResourceModel("subscribeLabel", this, null));
            AjaxFallbackLink subscribeLink = new SubscribeJobOfferLink("subscribeLink", jobOfferDto);
            add(new EditJobOfferLink("editLink", jobOfferDto));
            add(new CommitJobOfferLink("commitLink", jobOfferDto));
            add(subscribeLabel);
            add(subscribeLink);
            if (isSubscribed()) {
                subscribeLink.setVisible(false);
            } else {
                subscribeLabel.setVisible(false);
            }
        }

        private boolean isSubscribed() {
            boolean isSubscribed = false;
            Long personId = ((HostelAuthenticatedWebSession) getSession()).getUser().getPersonId();
            if (jobOfferDto != null && jobOfferDto.getPersonDtos() != null) {
                for (PersonJobOfferDto dto : jobOfferDto.getPersonDtos()) {
                    if (dto.getId().equals(personId)) {
                        isSubscribed = true;
                        break;
                    }
                }
            }

            return isSubscribed;
        }

    }

    @AuthorizeAction(action = Action.RENDER, roles = {Roles.MANAGERESS})
    private class EditJobOfferLink extends AjaxFallbackLink {

        private JobOfferDto jobOfferDto;

        public EditJobOfferLink(String id, JobOfferDto jobOfferDto) {
            super(id);
            this.jobOfferDto = jobOfferDto;
        }

        @Override
        public void onClick(AjaxRequestTarget ajaxRequestTarget) {
            final ConfirmationAnswer answer = new ConfirmationAnswer();
            final CreateEditJobOfferPanel panel = new CreateEditJobOfferPanel(createEditJobOfferWindow.getContentId(),
                    jobOfferDto, answer, createEditJobOfferWindow) {
                @Override
                public StringResourceModel getHeaderModel() {
                    return new StringResourceModel("panel.editJobOffer.header", this, null);
                }
            };
            createEditJobOfferWindow.setContent(panel);
            createEditJobOfferWindow.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
                @Override
                public void onClose(AjaxRequestTarget target) {
                    if(answer.isPositive()) {
                        JobOfferDto editedJobOfferDto = panel.getJobOfferDto();
                        jobOfferService.update(editedJobOfferDto);
                        target.add(table);
                    }
                }
            });
            createEditJobOfferWindow.show(ajaxRequestTarget);
        }
    }

    @AuthorizeAction(action = Action.RENDER, roles = {Roles.MANAGERESS})
    private class CommitJobOfferLink extends AjaxFallbackLink {

        private JobOfferDto jobOfferDto;

        public CommitJobOfferLink(String id, JobOfferDto jobOfferDto) {
            super(id);
            this.jobOfferDto = jobOfferDto;
        }

        @Override
        public void onClick(final AjaxRequestTarget ajaxRequestTarget) {
            final ConfirmationAnswer answer = new ConfirmationAnswer();
            final CommitJobOfferPanel panel = new CommitJobOfferPanel(commitJobOfferWindow.getContentId(),
                    getCommitJobOfferDto(), answer, commitJobOfferWindow);
            panel.setOutputMarkupId(true);
            commitJobOfferWindow.setContent(panel);
            ajaxRequestTarget.add(panel);
            ajaxRequestTarget.add(commitJobOfferWindow);
            commitJobOfferWindow.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
                @Override
                public void onClose(AjaxRequestTarget target) {
                    target.add(panel);
                    target.add(commitJobOfferWindow);
                    if (answer.isPositive()) {
                        List<CommitJobOfferDto> commitJobs = panel.getCommitJobOfferDtos();
                        jobOfferService.addJobsForAllPerson(commitJobs);
                        jobOfferDto.setActive(false);
                        jobOfferService.update(jobOfferDto);
                        target.add(table);
                        for (PersonJobOfferDto theDto: jobOfferDto.getPersonDtos()){
                            notificationService.createNotification(theDto.getId(), NotificationKeys.JOB_HOURS_ADDED,
                                    new String[]{String.valueOf(DateUtils.getFormattedDate(jobOfferDto.getDate().getTime())),
                                            String.valueOf(jobOfferDto.getHours())}, null);
                        }
                    } else if (panel.isDeleteButtonPressed()) {
                        reopenCommitJobOfferDialog(target, panel.getCommitJobOfferDtos());
                    }
                }
            });
            commitJobOfferWindow.show(ajaxRequestTarget);
        }

        private void reopenCommitJobOfferDialog(final AjaxRequestTarget ajaxRequestTarget, List<CommitJobOfferDto> newJobsDtos) {
            final ConfirmationAnswer answer = new ConfirmationAnswer();
            final CommitJobOfferPanel panel = new CommitJobOfferPanel(commitJobOfferWindow.getContentId(),
                    newJobsDtos, answer, commitJobOfferWindow);
            panel.setOutputMarkupId(true);
            commitJobOfferWindow.setContent(panel);
            ajaxRequestTarget.add(panel);
            ajaxRequestTarget.add(commitJobOfferWindow);
            commitJobOfferWindow.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
                @Override
                public void onClose(AjaxRequestTarget target) {
                    target.add(panel);
                    target.add(commitJobOfferWindow);
                    if(answer.isPositive()) {
                        List<CommitJobOfferDto> commitJobs = panel.getCommitJobOfferDtos();
                        jobOfferService.addJobsForAllPerson(commitJobs);
                        jobOfferDto.setActive(false);
                        jobOfferService.update(jobOfferDto);
                        target.add(table);
                    } else if (panel.isDeleteButtonPressed()) {
                        reopenCommitJobOfferDialog(target, panel.getCommitJobOfferDtos());
                    }
                }
            });
            commitJobOfferWindow.show(ajaxRequestTarget);
        }

        private List<CommitJobOfferDto> getCommitJobOfferDto() {
            List<CommitJobOfferDto> list = new LinkedList<CommitJobOfferDto>();
            if (jobOfferDto != null && jobOfferDto.getPersonDtos() != null) {
                for (PersonJobOfferDto personJobOfferDto : jobOfferDto.getPersonDtos()) {
                    CommitJobOfferDto dto = new CommitJobOfferDto();
                    dto.setHours(jobOfferDto.getHours());
                    dto.setDescription(jobOfferDto.getDescription());
                    dto.setPersonJobOfferDto(personJobOfferDto);
                    list.add(dto);
                }
            }

            return list;
        }
    }

    @AuthorizeAction(action = Action.RENDER, roles = {Roles.USER, Roles.COMMANDANT, Roles.FLOOR_HEAD, Roles.ADMIN})
    private class SubscribeJobOfferLink extends AjaxFallbackLink {

        private JobOfferDto jobOfferDto;

        public SubscribeJobOfferLink(String id, JobOfferDto jobOfferDto) {
            super(id);
            this.jobOfferDto = jobOfferDto;
        }

        @Override
        public void onClick(AjaxRequestTarget ajaxRequestTarget) {
            final ConfirmationAnswer answer = new ConfirmationAnswer();
            subscribeConfirmWindow.setContent(new ConfirmationPanel(subscribeConfirmWindow.getContentId(), answer, subscribeConfirmWindow) {
                @Override
                public StringResourceModel getHeaderModel() {
                    return new StringResourceModel("workTablePanel.subscribeConfirmDialog.header", this, null);
                }

                @Override
                public StringResourceModel getContentModel() {
                    return new StringResourceModel("workTablePanel.subscribeConfirmDialog.content", this, null);
                }
            });
            subscribeConfirmWindow.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
                @Override
                public void onClose(AjaxRequestTarget target) {
                    if(answer.isPositive()) {
                        getPersonJobOfferDto();
                        jobOfferDto.getPersonDtos().add(getPersonJobOfferDto());
                        jobOfferService.update(jobOfferDto);
                        target.add(table);
                    }
                }
            });
            subscribeConfirmWindow.show(ajaxRequestTarget);
        }

        private PersonJobOfferDto getPersonJobOfferDto() {
            Long personId = ((HostelAuthenticatedWebSession) getSession()).getUser().getPersonId();
            PersonJobOfferDto personJobOfferDto = new PersonJobOfferDto();
            personJobOfferDto.setId(personId);

            return personJobOfferDto;
        }
    }

    @AuthorizeAction(action = Action.RENDER, roles = {Roles.USER, Roles.COMMANDANT, Roles.FLOOR_HEAD, Roles.ADMIN})
    private class SubscribeJobOfferLabel extends Label {

        public SubscribeJobOfferLabel(String id, IModel<?> model) {
            super(id, model);
        }
    }

    @AuthorizeAction(action = Action.RENDER, roles = {Roles.MANAGERESS})
    private class CreateNewJobOfferButton extends AjaxFallbackLink {

//        @SpringBean
//        private NotificationService notificationService;
        @SpringBean
        private NewJobIsAvailableTask newJobIsAvailableTask;

        public CreateNewJobOfferButton(String id) {
            super(id);
        }

        @Override
        public void onClick(AjaxRequestTarget ajaxRequestTarget) {
            final ConfirmationAnswer answer = new ConfirmationAnswer();
            final CreateEditJobOfferPanel panel = new CreateEditJobOfferPanel(createEditJobOfferWindow.getContentId(),
                    new JobOfferDto(), answer, createEditJobOfferWindow) {
                @Override
                public StringResourceModel getHeaderModel() {
                    return new StringResourceModel("panel.createJobOffer.header", this, null);
                }
            };
            createEditJobOfferWindow.setContent(panel);
            createEditJobOfferWindow.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
                @Override
                public void onClose(AjaxRequestTarget target) {
                    if(answer.isPositive()) {
                        JobOfferDto createdJobOffer = panel.getJobOfferDto();
                        jobOfferService.create(createdJobOffer);
                        target.add(table);
                        newJobIsAvailableTask.setTime(createdJobOffer.getDate().getTime());
                        newJobIsAvailableTask.setPersonsCount(createdJobOffer.getNumberOfPeoples());
                        newJobIsAvailableTask.start();
//                        notificationService.createJobNotificationTask(createdJobOffer.getNumberOfPeoples(), createdJobOffer.getDate().getTime());
                    }
                }
            });
            createEditJobOfferWindow.show(ajaxRequestTarget);
        }
    }
}
