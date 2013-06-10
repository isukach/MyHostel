package by.bsuir.suite.page.penalty;

import by.bsuir.suite.colors.ColorConstants;
import by.bsuir.suite.dto.penalty.ClosePenaltyDto;
import by.bsuir.suite.dto.penalty.PenaltyDto;
import by.bsuir.suite.dto.person.PersonSearchDto;
import by.bsuir.suite.page.base.BasePage;
import by.bsuir.suite.page.base.NonContentWindow;
import by.bsuir.suite.page.base.panel.NavigationPanel;
import by.bsuir.suite.page.penalty.panel.ChoicePenaltyPanel;
import by.bsuir.suite.page.penalty.panel.ClosePenaltyColumn;
import by.bsuir.suite.page.penalty.panel.ConfirmationPenaltyPanel;
import by.bsuir.suite.service.penalty.PenaltyService;
import by.bsuir.suite.service.person.PersonService;
import by.bsuir.suite.util.PenaltyUtils;
import by.bsuir.suite.util.Roles;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: CHEB
 */
@AuthorizeInstantiation({Roles.COMMANDANT, Roles.SUPER_USER})
public class PenaltyPage extends BasePage {

    private static final int NUMBER_ROWS = 10;

    public static final int MIN_DUTY_HOURS = 0;

    public static final int MAX_DUTY_HOURS = 10;

    public static final int MIN_WORK_HOURS = 0;

    public static final int MAX_WORK_HOURS = 20;

    private AjaxFallbackDefaultDataTable table;

    private boolean personSelected = false;

    private ClosePenaltyDto confirmationDto = null;

    @SpringBean
    @SuppressWarnings({"UnusedDeclaration"})
    private PenaltyService penaltyService;

    @SpringBean
    @SuppressWarnings({"UnusedDeclaration"})
    private PersonService personService;

    private ModalWindow choicePenaltyDialog;

    private ModalWindow confirmationPenaltyDialog;


    public PenaltyPage() {
        super(ColorConstants.BLACK);

        addNavigationPanel();
        addDialogs();
        addPenaltyTable();
    }

    private void addNavigationPanel() {
        NavigationPanel navigationPanel = new NavigationPanel("navigationPanel", false) {
            @Override
            protected void onClose(AjaxRequestTarget target) {
                if (personSelected) {
                    PenaltyDto dto = new PenaltyDto();
                    dto.setPersonId(getSelectedPersonId());
                    clickPunishment(target, dto);
                    personSelected = false;
                }
            }

            @Override
            protected void onPersonSelected(AjaxRequestTarget target) {
                personSelected = true;
            }

            @Override
            protected void onPersonDeselected(AjaxRequestTarget target) {
                personSelected = false;
            }
        };
        add(navigationPanel);
    }

    private void addDialogs() {
        choicePenaltyDialog = new NonContentWindow("choicePenaltyDialog");
        confirmationPenaltyDialog = new NonContentWindow("confirmationPenaltyDialog");
        add(confirmationPenaltyDialog);
        add(choicePenaltyDialog);
    }

    private void clickPunishment(AjaxRequestTarget target, final PenaltyDto penaltyDto) {
        confirmationDto = null;
        choicePenaltyDialog.setContent(new ChoicePenaltyPanel(choicePenaltyDialog.getContentId(), choicePenaltyDialog) {

            @Override
            public void onSubmit(AjaxRequestTarget target) {
                confirmationDto = new ClosePenaltyDto();
                confirmationDto.setPersonId(penaltyDto.getPersonId());
                confirmationDto.setDutyId(penaltyDto.getDutyId());
                if (this.isDutyChoice()) {
                    confirmationDto.setAdditionalDuties(Integer.parseInt(this.getDutyNumber()));
                }
                if (this.isWorkChoice()) {
                    confirmationDto.setAdditionalWorkHours(Integer.parseInt(this.getWorkNumber()));
                }
            }

            @Override
            protected void onClose(AjaxRequestTarget target) {
                if(confirmationDto != null)
                {
                    PersonSearchDto personDto = personService.getPersonSearch(confirmationDto.getPersonId());
                    confirmationPenaltyDialog.setContent(new ConfirmationPenaltyPanel(choicePenaltyDialog.getContentId(), choicePenaltyDialog,
                            personDto.getFirstAndLastName(), personDto.getRoom(), confirmationDto.getAdditionalDuties(),
                            confirmationDto.getAdditionalWorkHours()) {

                        @Override
                        public void onSubmit(AjaxRequestTarget target) {
                            target.add(table);
                            penaltyService.makePunishment(confirmationDto);
                        }
                    });
                    confirmationPenaltyDialog.show(target);
                }
            }
        });
        choicePenaltyDialog.show(target);
    }

    private void closeClick(AjaxRequestTarget target, final PenaltyDto penaltyDto) {
        confirmationDto = null;
        choicePenaltyDialog.setContent(new ChoicePenaltyPanel(choicePenaltyDialog.getContentId(), choicePenaltyDialog) {

            @Override
            public void onSubmit(AjaxRequestTarget target) {
                confirmationDto = new ClosePenaltyDto();
                confirmationDto.setPersonId(penaltyDto.getPersonId());
                confirmationDto.setDutyId(penaltyDto.getDutyId());
                if (this.isDutyChoice()) {
                    confirmationDto.setAdditionalDuties(Integer.parseInt(this.getDutyNumber()));
                }
                if (this.isWorkChoice()) {
                    confirmationDto.setAdditionalWorkHours(Integer.parseInt(this.getWorkNumber()));
                }
            }

            @Override
            protected void onClose(AjaxRequestTarget target) {
                if(confirmationDto != null)
                {
                    PersonSearchDto personDto = personService.getPersonSearch(confirmationDto.getPersonId());
                    confirmationPenaltyDialog.setContent(new ConfirmationPenaltyPanel(choicePenaltyDialog.getContentId(), choicePenaltyDialog,
                            personDto.getFirstAndLastName(), personDto.getRoom(), confirmationDto.getAdditionalDuties(),
                            confirmationDto.getAdditionalWorkHours()) {

                        @Override
                        public void onSubmit(AjaxRequestTarget target) {
                            target.add(table);
                            penaltyService.closePenalty(confirmationDto);
                            penaltyService.makePunishment(confirmationDto);
                        }
                    });
                    confirmationPenaltyDialog.show(target);
                }
            }
        });
        choicePenaltyDialog.show(target);
    }

    private void addPenaltyTable() {
        List<IColumn<PenaltyDto>> columns = new ArrayList<IColumn<PenaltyDto>>();

        columns.add(new ClosePenaltyColumn<PenaltyDto>(Model.of("")) {
            @Override
            protected void onClick(IModel<PenaltyDto> clicked, AjaxRequestTarget ajaxRequestTarget) {
                closeClick(ajaxRequestTarget, clicked.getObject());
            }
        });
        columns.add(new PropertyColumn<PenaltyDto>(new StringResourceModel("tableTitle.name", this, null),
                PenaltyUtils.DUTY_SORT_BY_PERSON_NAME, "fullName"));
        columns.add(new PropertyColumn<PenaltyDto>(new StringResourceModel("tableTitle.room", this, null),
                PenaltyUtils.DUTY_SORT_BY_FLOOR, "room"));
        columns.add(new PropertyColumn<PenaltyDto>(new StringResourceModel("tableTitle.date", this, null),
                PenaltyUtils.DUTY_SORT_BY_DATE, "date"));
        columns.add(new PropertyColumn<PenaltyDto>(new StringResourceModel("tableTitle.description", this, null),
                "description"));

        table = new AjaxFallbackDefaultDataTable<PenaltyDto>("penaltyTable", columns,
                new PenaltyDataProvider(), NUMBER_ROWS);
        table.setOutputMarkupId(true);
        add(table);
    }

    private class PenaltyDataProvider extends SortableDataProvider<PenaltyDto> {

        public PenaltyDataProvider() {
            Injector.get().inject(this);
            setSort(PenaltyUtils.DUTY_SORT_BY_PERSON_NAME, SortOrder.ASCENDING);
        }


        @Override
        public Iterator<PenaltyDto> iterator(int first, int count) {
            return penaltyService.findPenalty(first, count, getSort().getProperty()).iterator();
        }

        @Override
        public int size() {
            return penaltyService.getPenaltyCount().intValue();
        }

        @Override
        public IModel<PenaltyDto> model(PenaltyDto penaltyDto) {
            return new DetachablePenaltyTableModel(penaltyDto);
        }
    }
}
