package by.bsuir.suite.page.duty.panel;

import by.bsuir.suite.disassembler.duty.DutyStatusDto;
import by.bsuir.suite.dto.duty.CalendarCellDto;
import by.bsuir.suite.dto.duty.CalendarDutyDto;
import by.bsuir.suite.dto.duty.CalendarMonthDto;
import by.bsuir.suite.dto.duty.DutyShiftDto;
import by.bsuir.suite.dto.person.CalendarPersonDto;
import by.bsuir.suite.exception.OptimisticLockException;
import by.bsuir.suite.page.base.NavigationWindow;
import by.bsuir.suite.page.base.NonContentWindow;
import by.bsuir.suite.page.base.NotificationWindow;
import by.bsuir.suite.page.base.panel.ConfirmationPanel;
import by.bsuir.suite.page.base.panel.HostelPanel;
import by.bsuir.suite.page.duty.CalendarDataProvider;
import by.bsuir.suite.page.duty.CalendarDayModel;
import by.bsuir.suite.page.duty.PersonDutyDataProvider;
import by.bsuir.suite.page.duty.components.ExportButton;
import by.bsuir.suite.service.duty.DutyService;
import by.bsuir.suite.service.duty.ExportService;
import by.bsuir.suite.service.duty.MonthService;
import by.bsuir.suite.service.notifications.NotificationService;
import by.bsuir.suite.service.notifications.common.NotificationKeys;
import by.bsuir.suite.service.person.PersonService;
import by.bsuir.suite.util.*;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.GridView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.resource.FileResourceStream;
import org.apache.wicket.util.resource.IResourceStream;

import java.io.File;
import java.util.*;

/**
 * @author i.sukach
 */
public class CalendarPanel extends HostelPanel {

    private static final int MAX_DUTIES_FOR_PERSON = 2;

    private static final int CALENDAR_ROWS = 6;
    private static final int CALENDAR_COLUMNS = 7;

    private static final String FIRST_SHIFT_LINK = "firstShiftLink";
    private static final String SECOND_SHIFT_LINK = "secondShiftLink";
    private static final String FIRST_SHIFT_LABEL = "firstShiftLabel";
    private static final String SECOND_SHIFT_LABEL = "secondShiftLabel";

    @SpringBean
    @SuppressWarnings({"UnusedDeclaration"})
    private MonthService monthService;

    @SpringBean
    @SuppressWarnings({"UnusedDeclaration"})
    private DutyService dutyService;

    @SpringBean
    @SuppressWarnings({"UnusedDeclaration"})
    private PersonService personService;

    @SpringBean
    @SuppressWarnings({"UnusedDeclaration"})
    private ExportService exporter;

    @SpringBean
    @SuppressWarnings({"UnusedDeclaration"})
    private NotificationService notificationService;

    private CalendarMonthDto currentMonth;

    private List<CalendarCellDto> dataList;

    private AjaxFallbackLink buttonNext;

    private AjaxFallbackLink buttonPrevious;

    private Label currentMonthLabel;

    private final ModalWindow dutyNumberForCurrentMonthExceededDialog;

    private final ModalWindow dutyNumberForPersonExceededDialog;

    private final ModalWindow dutyLockedDialog;

    private final ModalWindow dutyEvaluateDialog;

    private final ModalWindow dutyStatusDialog;

    private final ModalWindow personForDutySelectionWindow;

    private final ModalWindow earlyEvaluationDialog;

    private final CalendarPersonDto currentUser;

    private final WebMarkupContainer calendarContainer = createCalendarContainer("calendarContainer");

    private MyDutiesPanel myDutiesPanel;

    private final ModalWindow dutyConfirmDialog;

    private AjaxButton enableMonthButton;

    private AjaxButton disableMonthButton;

    private Long floorId;
    private static final String SKIPPED_DUTY_LABEL = "Не вышел на дежурство";

    public CalendarPanel(String id, CalendarPersonDto currentUser, Long floorId) {
        super(id);

        final Form form = new Form("form");
        final AjaxDownloadBehavior ajaxDownloadBehavior = new AjaxDownloadBehavior() {
            private String filename;
            @Override
            protected String getFileName() {
                return filename;
            }

            @Override
            protected IResourceStream getResourceStream() {
                filename = exporter.export(getCurrentMonth().getId());
                File file = new File(filename);
                return new FileResourceStream(file);
            }
        };
        ExportButton exportButton = new ExportButton("exportButton", ajaxDownloadBehavior);
        form.add(exportButton);
        form.add(ajaxDownloadBehavior);
        add(form);

        enableMonthButton = new SwitchMonthAccessButton("enableMonthButton") {
            @Override
            public boolean isVisible() {
                return !getCurrentMonth().getEnabled();
            }
        };
        enableMonthButton.setOutputMarkupPlaceholderTag(true);

        disableMonthButton = new SwitchMonthAccessButton("disableMonthButton") {
            @Override
            public boolean isVisible() {
                return getCurrentMonth().getEnabled();
            }
        };
        disableMonthButton.setOutputMarkupPlaceholderTag(true);

        form.add(enableMonthButton);
        form.add(disableMonthButton);

        dutyNumberForCurrentMonthExceededDialog = createNotificationWindow("modalMonthExceeded",
                "dutyPage.exceeded.header", "dutyPage.exceeded.month.duties", false);
        dutyNumberForPersonExceededDialog = createNotificationWindow("modalPersonExceeded", "dutyPage.exceeded.header",
                "dutyPage.exceeded.person.duties", false);
        dutyLockedDialog = createNotificationWindow("dutyLocked", "dutyPage.exceeded.header",
                "duty.error.locked", false);
        earlyEvaluationDialog = createNotificationWindow("dutyEvaluationEarly", "dutyPage.evaluation.early.header",
                "dutyPage.evaluation.early.text", false);
        dutyConfirmDialog = new NonContentWindow("dutyConfirm");
        dutyEvaluateDialog = new NonContentWindow("dutyEvaluate");
        dutyStatusDialog = new NonContentWindow("dutyStatusDialog");
        personForDutySelectionWindow = new NavigationWindow("personSelection");

        this.currentUser = currentUser;
        this.floorId = floorId;
        retrieveCalendarFromDatabase();

        addModalWindowsToThePage();
        addTodayInfoToThePage();
        addCalendarToThePage();
        addPersonDutiesToThePage();
    }

    private void retrieveCalendarFromDatabase() {
        currentMonth = monthService.findFirstEnabledMonthByFloorId(floorId);
    }

    private ModalWindow createNotificationWindow(String id, String headerKey,
                                                 String contentKey, boolean showCancelButton) {
        return new NotificationWindow(id, headerKey, contentKey, showCancelButton) {
            @Override
            public void onCancel(AjaxRequestTarget target) {
                close(target);
            }

            @Override
            public void onConfirm(AjaxRequestTarget target) {
                close(target);
            }
        };
    }

    private void addModalWindowsToThePage() {
        add(personForDutySelectionWindow);
        add(dutyNumberForCurrentMonthExceededDialog);
        add(dutyNumberForPersonExceededDialog);
        add(dutyLockedDialog);
        add(dutyConfirmDialog);
        add(dutyEvaluateDialog);
        add(dutyStatusDialog);
        add(earlyEvaluationDialog);
    }

    private void addTodayInfoToThePage() {
        addTodayLabel();
        addTodayDate();
    }

    private void addTodayLabel() {
        add(new Label("todayLabel", new StringResourceModel("dutyPage.today.label", this, null)));
    }

    private void addTodayDate() {
        final IModel<CalendarDayModel> model = new Model<CalendarDayModel>(new CalendarDayModel(getCurrentLocale()));
        add(new Label("todayDate", new StringResourceModel(ResourceKeys.TODAY_DATE, this, model)));
    }

    private void addCalendarToThePage() {
        addCurrentMonthLabel();

        addCalendar();
        addCalendarNavigationButtons();
        add(calendarContainer);
    }

    private void addCurrentMonthLabel() {
        currentMonthLabel = new Label("currentMonth", new StringResourceModel(
                getResourceKeyForCurrentMonth(currentMonth.getMonth()), this, null) {
            @Override
            protected String load() {
                return getLocalizer()
                        .getString(getResourceKeyForCurrentMonth(currentMonth.getMonth()), CalendarPanel.this)
                        + "&nbsp;" + currentMonth.getYear();
            }
        });
        currentMonthLabel.setEscapeModelStrings(false);
        currentMonthLabel.setOutputMarkupPlaceholderTag(true);

        add(currentMonthLabel);
    }

    private WebMarkupContainer createCalendarContainer(String id) {
        WebMarkupContainer container = new WebMarkupContainer(id);
        container.add(new Behavior() {
            @Override
            public void onComponentTag(Component component, ComponentTag tag) {
                if (currentMonth.getEnabled()) {
                    tag.put("class", "");
                } else {
                    tag.put("class", "disabled");
                }
            }
        });
        container.setOutputMarkupPlaceholderTag(true);
        container.setVisible(true);
        return container;
    }

    private void addCalendar() {
        addCalendarHeader();
        GridView<CalendarCellDto> gridView = new GridView<CalendarCellDto>("rows", createDataProvider()) {
            @Override
            protected void populateEmptyItem(Item<CalendarCellDto> item) {
                throw new IllegalStateException("No empty items should be on calendar page!");
            }
            @Override
            protected void populateItem(final Item<CalendarCellDto> item) {
                final CalendarCellDto dto = item.getModelObject();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dto.getDate());
                item.add(new Label("day", new Model<String>(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)))));
                if (dto.isEnabled()) {
                    addLinkWithLabelToItem(FIRST_SHIFT_LINK, FIRST_SHIFT_LABEL, dto, DutyShiftDto.FIRST, item);
                    addLinkWithLabelToItem(SECOND_SHIFT_LINK, SECOND_SHIFT_LABEL, dto, DutyShiftDto.SECOND, item);
                } else {
                    addLinkWithEmptyLabelToItem(FIRST_SHIFT_LINK, FIRST_SHIFT_LABEL, item);
                    addLinkWithEmptyLabelToItem(SECOND_SHIFT_LINK, SECOND_SHIFT_LABEL, item);
                    item.add(new AttributeModifier("class", CssHelper.DISABLED_DAY_CLASS));
                }
            }
        };
        gridView.setColumns(CALENDAR_COLUMNS);
        gridView.setRows(CALENDAR_ROWS);
        gridView.setOutputMarkupPlaceholderTag(true);
        calendarContainer.add(gridView);
    }

    private void addLinkWithLabelToItem(String linkId, String labelId, final CalendarCellDto dto, final DutyShiftDto shift,
                                        final Item<CalendarCellDto> item) {
        Link link = new AjaxFallbackLink(linkId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                processShiftClick(dto, shift, item, target);
            }

            @Override
            public boolean isEnabled() {
                return currentMonth.getEnabled();
            }
        };
        if (shift.equals(DutyShiftDto.FIRST)) {
            addLabelToLink(labelId, link, dto.getFirstDuty().getStatus(), dto.getFirstDuty().getPerson());
            item.add(new DeleteShiftLink(linkId + "DeleteButton", dto, shift, dto.getFirstDuty(), item));
        } else {
            addLabelToLink(labelId, link, dto.getSecondDuty().getStatus(), dto.getSecondDuty().getPerson());
            item.add(new DeleteShiftLink(linkId + "DeleteButton", dto, shift, dto.getSecondDuty(), item));
        }
        item.add(link);
    }

    private void addLinkWithEmptyLabelToItem(String linkId, String labelId, Item<CalendarCellDto> item) {
        Link link = new AjaxFallbackLink(linkId) {
            @Override
            public void onClick(AjaxRequestTarget target) {
            }
        };
        link.add(new Label(labelId, new Model<String>("")));
        item.add(new AjaxFallbackLink<Void>(linkId + "DeleteButton") {
            @Override
            public boolean isVisible() {
                return false;
            }

            @Override
            public void onClick(AjaxRequestTarget target) {
            }
        });
        item.add(link);
    }

    private void processShiftClick(final CalendarCellDto dto, final DutyShiftDto shift,
                                   final Item<CalendarCellDto> item, AjaxRequestTarget target) {
        final CalendarDutyDto calendarDutyDto;
        if (shift.equals(DutyShiftDto.FIRST)) {
            calendarDutyDto = dto.getFirstDuty();
        } else {
            calendarDutyDto = dto.getSecondDuty();
        }
        if (calendarDutyDto.getStatus() == DutyStatusDto.FREE) {
            if (!dutyRecordingAllowed()) {
                return;
            }
            if (anyUserRecordingAllowed()) {
                showAnyUserDutyRecordingDialog(dto, shift, item, target, calendarDutyDto);
            } else if (maxDutiesForPersonExceeded()) {
                dutyNumberForPersonExceededDialog.show(target);
            } else if (maxDutiesForCurrentMonthExceeded()) {
                dutyNumberForCurrentMonthExceededDialog.show(target);
            } else {
                showCurrentUserDutyRecordingDialog(dto, shift, item, target, calendarDutyDto);
            }
        } else if (calendarDutyDto.getStatus() == DutyStatusDto.OCCUPIED) {
            if (dutyEvaluationAllowed()) {
                if (dto.getDate().before(new Date())) {
                    showDutyEvaluationDialog(dto, shift, item, target, calendarDutyDto);
                } else {
                    earlyEvaluationDialog.show(target);
                }
            }
        } else if (calendarDutyDto.getStatus() == DutyStatusDto.COMPLETED_BAD
                || calendarDutyDto.getStatus() == DutyStatusDto.COMPLETED_PUNISHED
                || calendarDutyDto.getStatus() == DutyStatusDto.SKIPPED) {
            if (badDutyViewingAllowed()) {
                dutyStatusDialog.setContent(new DutyStatusPanel(
                        dutyStatusDialog.getContentId(), dutyStatusDialog, calendarDutyDto.getStatusComment()));
                dutyStatusDialog.show(target);
            }
        }
    }

    private boolean anyUserRecordingAllowed() {
        return userHasFloorHeadRole() || isSuperUser();
    }

    private boolean badDutyViewingAllowed() {
        return userHasFloorHeadRole() || userHasCommandantRole() || userHasEducatorRole() || isSuperUser();
    }

    private boolean dutyEvaluationAllowed() {
        return userHasCommandantRole();
    }

    private boolean dutyRecordingAllowed() {
        return !userHasCommandantRole() && !userHasEducatorRole();
    }

    private void showDutyEvaluationDialog(final CalendarCellDto dto, final DutyShiftDto shift,
                                          final Item<CalendarCellDto> item, AjaxRequestTarget target,
                                          final CalendarDutyDto calendarDutyDto) {
        DutyEvaluatePanel dutyEvaluatePanel =
                new DutyEvaluatePanel(dutyEvaluateDialog.getContentId(), dutyEvaluateDialog) {
                    @Override
                    public void onSubmit(AjaxRequestTarget target) {
                        target.add(calendarContainer);
                        target.add(myDutiesPanel);
                        calendarDutyDto.setStatus(this.getSelected());
                        if (this.getSelected() == DutyStatusDto.COMPLETED_BAD
                                || this.getSelected() == DutyStatusDto.COMPLETED_PUNISHED) {
                            calendarDutyDto.setStatusComment(this.getComment());
                            notificationService.createNotification(calendarDutyDto.getPerson().getId(), NotificationKeys.DUTY_SKIPPED,
                                    new String[]{String.valueOf(dto.getDate())}, null);
                        }
                        if (this.getSelected() == DutyStatusDto.SKIPPED) {
                            calendarDutyDto.setStatusComment(SKIPPED_DUTY_LABEL);
                            notificationService.createNotification(calendarDutyDto.getPerson().getId(), NotificationKeys.DUTY_COMPLETED_BAD,
                                    new String[]{String.valueOf(dto.getDate())}, null);
                        }
                        CalendarDutyDto updatedDuty = dutyService.updateDuty(calendarDutyDto);
                        if (shift.equals(DutyShiftDto.FIRST)) {
                            dto.setFirstDuty(updatedDuty);
                        } else {
                            dto.setSecondDuty(updatedDuty);
                        }
                        item.setModelObject(dto);
                        myDutiesPanel.updatePersonDuties(new PersonDutyDataProvider(currentUser.getId()));
                    }
                };
        dutyEvaluateDialog.setContent(dutyEvaluatePanel);
        dutyEvaluateDialog.show(target);
    }

    private void showAnyUserDutyRecordingDialog(final CalendarCellDto dto, final DutyShiftDto shift,
                                                final Item<CalendarCellDto> item, AjaxRequestTarget target,
                                                final CalendarDutyDto calendarDutyDto) {
        PersonForDutySelectionPanel selectionPanel = new PersonForDutySelectionPanel(
                personForDutySelectionWindow.getContentId(), personForDutySelectionWindow, floorId) {
            @Override
            public void onPersonSelected(AjaxRequestTarget target) {
                target.add(calendarContainer);
                target.add(myDutiesPanel);
                notificationService.createNotification(
                        getSelectedPerson().getId(), NotificationKeys.FLOORHEAD_ASSIGN_DUTY,
                        new String[]{DateUtils.getFormattedDate(dto.getDate().getTime())}, null);
                occupyDuty(target, calendarDutyDto, shift, dto, item, getSelectedPerson().getId());
            }
        };
        personForDutySelectionWindow.setContent(selectionPanel);
        personForDutySelectionWindow.show(target);
    }

    private void showCurrentUserDutyRecordingDialog(final CalendarCellDto dto, final DutyShiftDto shift,
                                                    final Item<CalendarCellDto> item, AjaxRequestTarget target,
                                                    final CalendarDutyDto calendarDutyDto) {
        final ConfirmationAnswer answer = new ConfirmationAnswer();
        dutyConfirmDialog.setContent(new ConfirmationPanel(dutyConfirmDialog.getContentId(), answer, dutyConfirmDialog) {
            @Override
            public StringResourceModel getHeaderModel() {
                return new StringResourceModel("duty.confirm.header", this, null);
            }

            @Override
            public StringResourceModel getContentModel() {
                return new StringResourceModel("duty.confirm.content", this, null,
                        new Object[]{
                                getDateString(dto.getDate(), getLocale(), CalendarPanel.this),
                                getShiftString(shift)
                        });
            }
        });
        dutyConfirmDialog.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
            @Override
            public void onClose(AjaxRequestTarget target) {
                target.add(calendarContainer);
                target.add(myDutiesPanel);
                if (answer.isPositive()) {
                    occupyDuty(target, calendarDutyDto, shift, dto, item, currentUser.getId());
                }
            }
        });
        dutyConfirmDialog.show(target);
    }

    private void occupyDuty(AjaxRequestTarget target, CalendarDutyDto calendarDutyDto, DutyShiftDto shift,
                            CalendarCellDto dto, Item<CalendarCellDto> item, Long personId) {
        try {
            CalendarDutyDto updatedDto = dutyService.occupyDuty(calendarDutyDto, personId);
            if (shift.equals(DutyShiftDto.FIRST)) {
                dto.setFirstDuty(updatedDto);
            } else {
                dto.setSecondDuty(updatedDto);
            }
            item.setModelObject(dto);
        } catch (OptimisticLockException e) {
            dutyLockedDialog.show(target);
        }
        myDutiesPanel.updatePersonDuties(new PersonDutyDataProvider(currentUser.getId()));
    }

    public static String getDateString(Date date, Locale locale, Component parent) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        final IModel<CalendarDayModel> model = new Model<CalendarDayModel>(
                new CalendarDayModel(calendar, locale));
        return new StringResourceModel(ResourceKeys.TODAY_DATE, parent, model).getString();
    }

    private String getShiftString(DutyShiftDto shift) {
        if (shift.equals(DutyShiftDto.FIRST)) {
            return new StringResourceModel("duty.shift.first", this, null).getString();
        } else {
            return new StringResourceModel("duty.shift.second", this, null).getString();
        }
    }

    private boolean maxDutiesForCurrentMonthExceeded() {
        Long userId = currentUser.getId();
        int numberOfDuties = 0;
        for (CalendarCellDto cell : dataList) {
            CalendarPersonDto firstPerson = cell.getFirstDuty().getPerson();
            if (firstPerson != null && firstPerson.getId().equals(userId)) {
                numberOfDuties++;
            }
            CalendarPersonDto secondPerson = cell.getSecondDuty().getPerson();
            if (secondPerson != null && secondPerson.getId().equals(userId)) {
                numberOfDuties++;
            }
        }
        return numberOfDuties >= MAX_DUTIES_FOR_PERSON;
    }

    private boolean maxDutiesForPersonExceeded() {
        return myDutiesPanel.getPersonDutiesSize() >= currentUser.getMaxDuties();
    }

    private void addLabelToLink(String id, Link link, final DutyStatusDto dutyStatus, CalendarPersonDto person) {
        link.add(new Label(id, getModelForDutyStatus(dutyStatus, person)) {
            @Override
            protected void onComponentTag(ComponentTag tag) {
                super.onComponentTag(tag);
                tag.put("class", getCellLabelClassForDutyStatus(dutyStatus));
            }
        });
    }

    private IModel<String> getModelForDutyStatus(DutyStatusDto status, CalendarPersonDto person) {
        if (status.equals(DutyStatusDto.FREE)) {
            return new StringResourceModel(ResourceKeys.FREE_DUTY_STATE, this, null);
        } else {
            return new PropertyModel<String>(person, "name");
        }
    }

    private String getCellLabelClassForDutyStatus(DutyStatusDto status) {
        if (status == DutyStatusDto.FREE) {
            return CssHelper.FREE_DUTY_ITEM_CLASS;
        } else if (status == DutyStatusDto.OCCUPIED) {
            return CssHelper.OCCUPIED_DUTY_ITEM_CLASS;
        } else if (status == DutyStatusDto.COMPLETED_GOOD) {
            return CssHelper.GOOD_DUTY_ITEM_CLASS;
        } else if (status == DutyStatusDto.SKIPPED) {
            return CssHelper.SKIPPED_DUTY_ITEM_CLASS;
        } else {
            return CssHelper.BAD_DUTY_ITEM_CLASS;
        }
    }

    private void addCalendarHeader() {
        calendarContainer.add(new Label("monday", new StringResourceModel(ResourceKeys.DAY_OF_WEEK_MONDAY, this, null)));
        calendarContainer.add(new Label("tuesday", new StringResourceModel(ResourceKeys.DAY_OF_WEEK_TUESDAY, this, null)));
        calendarContainer.add(new Label("wednesday", new StringResourceModel(ResourceKeys.DAY_OF_WEEK_WEDNESDAY, this, null)));
        calendarContainer.add(new Label("thursday", new StringResourceModel(ResourceKeys.DAY_OF_WEEK_THURSDAY, this, null)));
        calendarContainer.add(new Label("friday", new StringResourceModel(ResourceKeys.DAY_OF_WEEK_FRIDAY, this, null)));
        calendarContainer.add(new Label("saturday", new StringResourceModel(ResourceKeys.DAY_OF_WEEK_SATURDAY, this, null)));
        calendarContainer.add(new Label("sunday", new StringResourceModel(ResourceKeys.DAY_OF_WEEK_SUNDAY, this, null)));
    }

    private void addCalendarNavigationButtons() {
        buttonNext = new AjaxFallbackLink("nextMonth") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                target.add(this);
                CalendarMonthDto nextMonth = getNextMonth();
                if (nextMonth.getId() != null) {
                    currentMonth = nextMonth;
                }
                dataList.clear();
                dataList.addAll(generateDutyList());

                target.add(calendarContainer);
                target.add(currentMonthLabel);
                target.add(buttonPrevious);
                target.add(enableMonthButton);
                target.add(disableMonthButton);
            }
        };

        buttonNext.setOutputMarkupPlaceholderTag(true);
        add(buttonNext);

        buttonPrevious = new AjaxFallbackLink("previousMonth") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                target.add(this);
                CalendarMonthDto previousMonth = getPreviousMonth();
                if (previousMonth.getId() != null) {
                    currentMonth = previousMonth;
                }
                dataList.clear();
                dataList.addAll(generateDutyList());

                target.add(currentMonthLabel);
                target.add(calendarContainer);
                target.add(buttonNext);
                target.add(enableMonthButton);
                target.add(disableMonthButton);
            }
        };

        buttonPrevious.setOutputMarkupPlaceholderTag(true);
        add(buttonPrevious);
    }

    private CalendarMonthDto getNextMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, currentMonth.getMonth());
        calendar.set(Calendar.YEAR, currentMonth.getYear());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        return monthService.findByMonthYearAndFloorId(
                calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR), floorId);
    }

    private CalendarMonthDto getPreviousMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, currentMonth.getMonth());
        calendar.set(Calendar.YEAR, currentMonth.getYear());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, -1);
        return monthService.findByMonthYearAndFloorId(
                calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR), floorId);
    }

    private void addPersonDutiesToThePage() {
        myDutiesPanel = new MyDutiesPanel("myDutiesPanel", currentUser);
        myDutiesPanel.setOutputMarkupPlaceholderTag(true);
        add(myDutiesPanel);
    }

    private IDataProvider<CalendarCellDto> createDataProvider() {
        dataList = generateDutyList();
        return new CalendarDataProvider(dataList);
    }

    private List<CalendarCellDto> generateDutyList() {
        int [] margins = CalendarUtils.getTopAndBottomMargins(currentMonth.getMonth(), currentMonth.getYear());
        List<CalendarCellDto> fullCellList = new ArrayList<CalendarCellDto>();

        fillPreviousMonthCells(margins[0], fullCellList, currentMonth);
        fillCurrentMonthCells(fullCellList, currentMonth.getCells());
        fillNextMonthCells(margins[1], fullCellList, currentMonth);

        return fullCellList;
    }

    private void fillPreviousMonthCells(int topMargin, List<CalendarCellDto> fullCellList, CalendarMonthDto monthDto) {
        for (int i = 0; i < topMargin; i++) {
            fullCellList.add(generatePreviousMonthCell(monthDto, topMargin, i));
        }
    }

    private void fillCurrentMonthCells(List<CalendarCellDto> fullCellList, List<CalendarCellDto> currentMonthCells) {
        fullCellList.addAll(currentMonthCells);
    }

    private void fillNextMonthCells(int bottomMargin, List<CalendarCellDto> fullCellList, CalendarMonthDto monthDto) {
        for (int i = 0; i < bottomMargin; i++) {
            fullCellList.add(generateNextMonthCell(monthDto, i));
        }
    }

    private CalendarCellDto generatePreviousMonthCell(CalendarMonthDto monthDto, int topMargin, int index) {
        CalendarCellDto dto = new CalendarCellDto();
        Calendar previousMonthCalendar = Calendar.getInstance();
        previousMonthCalendar.set(Calendar.MONTH, monthDto.getMonth() - 1);
        previousMonthCalendar.set(Calendar.DAY_OF_MONTH,
                previousMonthCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) - (topMargin - index) + 1);
        return fillDisabledCell(dto, previousMonthCalendar);
    }

    private CalendarCellDto generateNextMonthCell(CalendarMonthDto monthDto, int index) {
        CalendarCellDto dto = new CalendarCellDto();
        Calendar nextMonthCalendar = Calendar.getInstance();
        nextMonthCalendar.set(Calendar.MONTH, monthDto.getMonth() + 1);
        nextMonthCalendar.set(Calendar.DAY_OF_MONTH, index + 1);
        return fillDisabledCell(dto, nextMonthCalendar);
    }

    private CalendarCellDto fillDisabledCell(CalendarCellDto cell, Calendar calendar) {
        cell.setDate(calendar.getTime());
        cell.setEnabled(false);
        cell.setFirstDuty(new CalendarDutyDto());
        cell.setSecondDuty(new CalendarDutyDto());
        return cell;
    }

    private CalendarMonthDto getCurrentMonth() {
        return currentMonth;
    }

    private String getResourceKeyForCurrentMonth(int month) {
        return ResourceKeys.CURRENT_MONTH_RESOURCE_KEY + DateUtils.getMonthName(month, Locale.ENGLISH).toLowerCase();
    }

    private void freeDuty(CalendarCellDto dto, DutyShiftDto shift,
                          CalendarDutyDto calendarDutyDto, Item<CalendarCellDto> item) {
        CalendarDutyDto updatedDuty = dutyService.freeDuty(calendarDutyDto);
        if (shift.equals(DutyShiftDto.FIRST)) {
            dto.setFirstDuty(updatedDuty);
        } else {
            dto.setSecondDuty(updatedDuty);
        }
        item.setModelObject(dto);
        myDutiesPanel.updatePersonDuties(new PersonDutyDataProvider(currentUser.getId()));
    }

    @AuthorizeAction(action = Action.RENDER, roles = {Roles.FLOOR_HEAD})
    private class SwitchMonthAccessButton extends AjaxButton {

        public SwitchMonthAccessButton(String id) {
            super(id);
        }

        @Override
        protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
            currentMonth = monthService.switchMonthAccess(currentMonth);
            target.add(calendarContainer);
            target.add(disableMonthButton);
            target.add(enableMonthButton);
        }

        @Override
        protected void onError(AjaxRequestTarget target, Form<?> form) {
        }
    }

    @AuthorizeAction(action = Action.RENDER, roles = {Roles.FLOOR_HEAD})
    private class DeleteShiftLink extends AjaxFallbackLink<Void> {

        private CalendarCellDto dto;

        private DutyShiftDto shift;

        private CalendarDutyDto calendarDutyDto;

        private Item<CalendarCellDto> item;

        public DeleteShiftLink(String id, CalendarCellDto dto, DutyShiftDto shift,
                               CalendarDutyDto calendarDutyDto, Item<CalendarCellDto> item) {
            super(id);
            this.dto = dto;
            this.shift = shift;
            this.calendarDutyDto = calendarDutyDto;
            this.item = item;
        }

        @Override
        public void onClick(AjaxRequestTarget target) {
            target.add(calendarContainer);
            target.add(myDutiesPanel);
            freeDuty(dto, shift, calendarDutyDto, item);
        }

        @Override
        public boolean isVisible() {
            return calendarDutyDto.getStatus() == DutyStatusDto.OCCUPIED && getCurrentMonth().getEnabled();
        }
    }

    public void updateCalendar(Long floorId, AjaxRequestTarget target) {
        this.floorId = floorId;
        retrieveCalendarFromDatabase();
        dataList.clear();
        dataList.addAll(generateDutyList());

        target.add(calendarContainer);
        target.add(currentMonthLabel);
        target.add(buttonPrevious);
        target.add(buttonNext);
        target.add(enableMonthButton);
        target.add(disableMonthButton);
    }
}
