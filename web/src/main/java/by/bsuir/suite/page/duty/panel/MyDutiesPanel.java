package by.bsuir.suite.page.duty.panel;

import by.bsuir.suite.disassembler.duty.DutyStatusDto;
import by.bsuir.suite.dto.duty.PersonDutyDto;
import by.bsuir.suite.dto.person.CalendarPersonDto;
import by.bsuir.suite.page.base.NonContentWindow;
import by.bsuir.suite.page.duty.CalendarDayModel;
import by.bsuir.suite.page.duty.DutyNumberModel;
import by.bsuir.suite.page.duty.PersonDutyDataProvider;
import by.bsuir.suite.util.CssHelper;
import by.bsuir.suite.util.Roles;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author i.sukach
 */
@AuthorizeAction(action = Action.RENDER, roles = {Roles.USER, Roles.ADMIN, Roles.FLOOR_HEAD})
public class MyDutiesPanel extends Panel {

    private final List<IModel<PersonDutyDto>> personDuties = new ArrayList<IModel<PersonDutyDto>>();

    private final CalendarPersonDto currentUser;

    private final ModalWindow dutyStatusWindow;

    private PersonDutyDataProvider provider;

    public MyDutiesPanel(String id, final CalendarPersonDto currentUser) {
        super(id);
        this.currentUser = currentUser;
        dutyStatusWindow = new NonContentWindow("dutyStatusWindow");
        add(dutyStatusWindow);
        provider = new PersonDutyDataProvider(currentUser.getId());
        updatePersonDuties(provider);
        addDutyView();
        addDutiesLabel(provider);
    }

    private void addDutyView() {
        RefreshingView<PersonDutyDto> dutyView = new RefreshingView<PersonDutyDto>("personDutyList") {

            @Override
            protected Iterator<IModel<PersonDutyDto>> getItemModels() {
                return personDuties.iterator();
            }

            @Override
            protected void populateItem(Item<PersonDutyDto> item) {
                final PersonDutyDto dto = item.getModelObject();
                final IModel<CalendarDayModel> model = new Model<CalendarDayModel>(
                        new CalendarDayModel(dto.getDutyDate(), getSession().getLocale()));
                AjaxFallbackLink<Void> dutyLink = new AjaxFallbackLink<Void>("dutyLink") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        if (dto.getStatus() == DutyStatusDto.COMPLETED_BAD
                                || dto.getStatus() == DutyStatusDto.COMPLETED_PUNISHED
                                || dto.getStatus() == DutyStatusDto.SKIPPED) {
                            dutyStatusWindow.setContent(new DutyStatusPanel(
                                    dutyStatusWindow.getContentId(), dutyStatusWindow, dto.getComment()));
                            dutyStatusWindow.show(target);
                        }
                    }
                };
                dutyLink.add(new Label("personDuty", new StringResourceModel("myDutiesPanel.today.date", this, model)));
                item.add(dutyLink);

                if (dto.getStatus() == DutyStatusDto.COMPLETED_GOOD) {
                    item.add(new AttributeModifier("class", CssHelper.GOOD_DUTY_LIST_ITEM_CLASS));
                } else if (dto.getStatus() == DutyStatusDto.COMPLETED_BAD ||
                        dto.getStatus() == DutyStatusDto.COMPLETED_PUNISHED) {
                    item.add(new AttributeModifier("class", CssHelper.BAD_DUTY_LIST_ITEM_CLASS));
                } else if (dto.getStatus() == DutyStatusDto.SKIPPED) {
                    item.add(new AttributeModifier("class", CssHelper.SKIPPED_DUTY_LIST_ITEM_CLASS));
                } else {
                    item.add(new AttributeModifier("class", ""));
                }

            }
        };
        dutyView.setOutputMarkupPlaceholderTag(true);

        add(dutyView);
    }

    private void addDutiesLabel(final PersonDutyDataProvider provider) {
        final Model<DutyNumberModel> dutyModel = new Model<DutyNumberModel>(
                new DutyNumberModel(personDuties.size(), currentUser.getMaxDuties()));
        Label dutiesLabel = new Label("dutyListLabel", new StringResourceModel("myDutiesPanel.duties.label", this, dutyModel) {
            @Override
            protected String load() {
                return getLocalizer().getString("myDutiesPanel.duties.label", MyDutiesPanel.this,
                        new Model<DutyNumberModel>(new DutyNumberModel(provider.size(), currentUser.getMaxDuties())));
            }
        });
        dutiesLabel.setOutputMarkupPlaceholderTag(true);
        add(dutiesLabel);
    }

    public final void updatePersonDuties(PersonDutyDataProvider personDutyDataProvider) {
        personDuties.clear();
        Iterator<PersonDutyDto> iterator = personDutyDataProvider.iterator(0, personDutyDataProvider.size());
        while (iterator.hasNext()) {
            personDuties.add(personDutyDataProvider.model(iterator.next()));
        }
    }

    public int getPersonDutiesSize() {
        return provider.size();
    }
}
