package by.bsuir.suite.page.lan;

import by.bsuir.suite.dto.lan.LanDto;
import by.bsuir.suite.dto.lan.LanPaymentCellDto;
import by.bsuir.suite.exception.OptimisticLockException;
import by.bsuir.suite.page.base.NonContentWindow;
import by.bsuir.suite.page.base.NotificationWindow;
import by.bsuir.suite.page.base.panel.ConfirmationPanel;
import by.bsuir.suite.page.duty.panel.ConfirmationAnswer;
import by.bsuir.suite.service.lan.LanService;
import by.bsuir.suite.session.HostelAuthenticatedWebSession;
import by.bsuir.suite.util.CssHelper;
import by.bsuir.suite.util.ResourceKeys;
import by.bsuir.suite.util.Roles;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.GridView;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author : d.shemiarey
 */
@AuthorizeAction(action = Action.ENABLE, roles = {Roles.ADMIN, Roles.SUPER_USER})
public abstract class LanPaymentPanel extends Panel {

    public static final String HEADER_PAID_KEY = "switch.payment.dialog.header.paid";
    public static final String HEADER_NOT_PAID_KEY = "switch.payment.dialog.header.notPaid";
    public static final String CONTENT_PAID_KEY = "switch.payment.dialog.content.paid";
    public static final String CONTENT_NOT_PAID_KEY = "switch.payment.dialog.content.notPaid";

    private static final Set<String> ADMINISTRATED_ROLES = new HashSet<String>();

    @SpringBean
    @SuppressWarnings({"UnusedDeclaration"})
    private LanService lanService;

    private LanDto lanDto;
    private List<LanPaymentCellDto> gridCells = new ArrayList<LanPaymentCellDto>();

    public static final int PAYMENT_COLUMNS = 11;
    public static final int PAYMENT_ROWS = 3;

    private ModalWindow lanPaymentLockedDialog;
    private ModalWindow switchPaymentConfirmDialog;

    static {
        /**
         * add roles for adding payment here
         * for example: ADMINISTRATED_ROLES.add(Roles.SUPER_USER);
         */
    }

    public LanPaymentPanel(String id) {
        super(id);

        createSwitchWindow();
        createLockWindow();

        initializePanel();
        createPaymentGridView();
    }

    public abstract Long getPersonId();

    private void initializePanel() {
        lanDto = lanService.getLanPaymentForPerson(getPersonId());
        gridCells = createGridCells(lanDto.getListLanPaymentCellDtos());
    }

    public void updateCells() {
        LanDto dto = lanService.getLanPaymentForPerson(getPersonId());
        lanDto.setVersion(dto.getVersion());
        lanDto.setActivated(dto.isActivated());
        lanDto.setIp(dto.getIp());
        lanDto.setId(dto.getId());
        for (int i = 0; i < 12; i++) {
            lanDto.getListLanPaymentCellDtos().get(i).setMonthName(dto.getListLanPaymentCellDtos().get(i).getMonthName());
            lanDto.getListLanPaymentCellDtos().get(i).setPaid(dto.getListLanPaymentCellDtos().get(i).isPaid());
        }
    }

    private List<LanPaymentCellDto> createGridCells(List<LanPaymentCellDto> cellDtos) {
        List<LanPaymentCellDto> listCells = new ArrayList<LanPaymentCellDto>();

        listCells.add(cellDtos.get(0));
        for (LanPaymentCellDto dto : cellDtos.subList(1, 6)) {
            listCells.add(new LanPaymentCellDto());
            listCells.add(dto);
        }

        for (int i = 0; i < 11; i++) {
            listCells.add(new LanPaymentCellDto());
        }

        listCells.add(cellDtos.get(6));
        for (LanPaymentCellDto dto : cellDtos.subList(7, 12)) {
            listCells.add(new LanPaymentCellDto());
            listCells.add(dto);
        }

        return listCells;
    }

    private void createSwitchWindow() {
        switchPaymentConfirmDialog = new NonContentWindow("switchPaymentDialog");
        add(switchPaymentConfirmDialog);
    }

    private void createLockWindow() {
        lanPaymentLockedDialog = new NotificationWindow("lanPaymentLocked", "lanPaymentPage.exceeded.header",
                "lanPayment.error.locked", false) {
            @Override
            public void onCancel(AjaxRequestTarget target) {
                close(target);
            }

            @Override
            public void onConfirm(AjaxRequestTarget target) {
                close(target);
            }
        };
        add(lanPaymentLockedDialog);
    }

    private void createPaymentGridView() {
        GridView<LanPaymentCellDto> gridView = new GridView<LanPaymentCellDto>("rows", new LanPaymentDataProvider() {
            @Override
            public List<LanPaymentCellDto> getCells() {
                return gridCells;
            }
        }) {

            @Override
            protected void populateEmptyItem(Item<LanPaymentCellDto> item) {
                throw new IllegalStateException("No empty items should be on calendar page!");
            }

            @Override
            protected void populateItem(final Item<LanPaymentCellDto> item) {
                final LanPaymentCellDto dto = item.getModelObject();
                final WebMarkupContainer container = new WebMarkupContainer("container");
                container.setOutputMarkupPlaceholderTag(true);
                if (dto.getMonthName() != null) {
                    if (ADMINISTRATED_ROLES.containsAll(
                            ((HostelAuthenticatedWebSession) LanPaymentPanel.this.getSession()).getUser().getRoles())) {
                        createMonthButton(container, dto);
                    } else {
                        createStaticIcon(container, dto);
                    }
                } else {
                    container.add(new Label("label"));
                }
                item.add(container);
            }
        };
        gridView.setColumns(PAYMENT_COLUMNS);
        gridView.setRows(PAYMENT_ROWS);
        gridView.setOutputMarkupPlaceholderTag(true);
        add(gridView);
    }

    private void createStaticIcon(final WebMarkupContainer container, final LanPaymentCellDto dto) {
        container.add(new Label("label",
                new StringResourceModel(
                        ResourceKeys.CURRENT_MONTH_RESOURCE_KEY + dto.getMonthName(), this, null)));

        container.add(new AttributeModifier("class",
                dto.isPaid() ? CssHelper.PAID_MONTH_CLASS : CssHelper.NOT_PAID_MONTH_CLASS));
        container.add(new AttributeModifier("disabled", "disabled"));
    }

    private void createMonthButton(final WebMarkupContainer container, final LanPaymentCellDto dto) {
        container.add(new Label("label",
                new StringResourceModel(
                        ResourceKeys.CURRENT_MONTH_RESOURCE_KEY + dto.getMonthName(), this, null)));

        container.add(new AttributeModifier("class",
                dto.isPaid() ? CssHelper.PAID_MONTH_CLASS : CssHelper.NOT_PAID_MONTH_CLASS));
        container.add(new AjaxEventBehavior("onclick") {
            @Override
            protected void onEvent(AjaxRequestTarget ajaxRequestTarget) {
                final ConfirmationAnswer answer = new ConfirmationAnswer();
                switchPaymentConfirmDialog.setContent(new ConfirmationPanel(
                        switchPaymentConfirmDialog.getContentId(), answer, switchPaymentConfirmDialog) {
                    @Override
                    public StringResourceModel getHeaderModel() {
                        return new StringResourceModel(
                                dto.isPaid() ? HEADER_PAID_KEY : HEADER_NOT_PAID_KEY, this, null);
                    }

                    @Override
                    public StringResourceModel getContentModel() {
                        return new StringResourceModel(
                                dto.isPaid() ? CONTENT_PAID_KEY : CONTENT_NOT_PAID_KEY, this, null);
                    }
                });
                switchPaymentConfirmDialog.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
                    @Override
                    public void onClose(AjaxRequestTarget target) {
                        target.add(container);
                        if (answer.isPositive()) {
                            try {
                                dto.setPaid(!dto.isPaid());
                                lanService.changeLanPayment(lanDto, getPersonId());
                                container.add(new AttributeModifier("class",
                                        dto.isPaid() ? CssHelper.PAID_MONTH_CLASS
                                                : CssHelper.NOT_PAID_MONTH_CLASS));
                            } catch (OptimisticLockException e) {
                                lanPaymentLockedDialog.show(target);
                            }

                        }
                    }
                });
                switchPaymentConfirmDialog.show(ajaxRequestTarget);
            }
        });
    }
}


