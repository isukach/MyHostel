package by.bsuir.suite.page.base.panel;

import by.bsuir.suite.dao.notificaation.NotificationDao;
import by.bsuir.suite.dto.notifications.NotificationDto;
import by.bsuir.suite.page.news.components.EndlessListView;
import by.bsuir.suite.service.notifications.NotificationService;
import by.bsuir.suite.session.HostelAuthenticatedWebSession;
import by.bsuir.suite.session.SessionUser;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @autor Aliaksandr Harelik
 * Date: 22/07/13
 * Time: 21:32
 */

public class NoticePanel extends Panel {

    @SpringBean
    private NotificationService service;

    @SpringBean
    private NotificationDao dao;

    private WebMarkupContainer container;

    public NoticePanel(String id) {
        super(id);

        container = new WebMarkupContainer("notifications");
        container.setOutputMarkupId(true);
        container.setOutputMarkupPlaceholderTag(true);
        container.setVisible(false);
        add(container);
        container.add(new AjaxFallbackLink<Void>("closeButton") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                hide(target);
            }
        });
        container.add(new EndlessListView<NotificationDto>("notificationsList") {
            @Override
            protected void populateListItem(ListItem<NotificationDto> components) {
                final NotificationDto item = components.getModelObject();
                if(item !=null)
                {
                    WebComponent indicator = new WebComponent("indicator");
                    indicator.add(new AttributeModifier("class", item.getColorDecorator()){
                        protected String newValue(final String currentValue, final String replacementValue){
                            return currentValue +" "+ replacementValue;
                        }
                    });
                    components.add(indicator);
                    components.add(new Label("date", item.getDate()));
                    components.add(new Label("header", item.getHeader()));
                    components.add(new Label("text", item.getText()));
                    if(!item.isViewed())
                        components.add(new AttributeModifier("class", "new_notification"){
                            protected String newValue(final String currentValue, final String replacementValue){
                                return currentValue +" "+ replacementValue;
                            }
                        });

                }
            }

            @Override
            protected List<NotificationDto> loadItems(int offset, int limit) {
                SessionUser currentUser = ((HostelAuthenticatedWebSession) getSession()).getUser();
                return service.loadPartOfNotifications(currentUser.getPersonId(), offset, limit, getSession().getLocale());
            }
        });

    }

    public void show(AjaxRequestTarget target) {
        container.setVisible(true);
        target.add(container);
//        service.createDutyNotification(Long.valueOf(1));
    }
    public void hide(AjaxRequestTarget target) {
        container.setVisible(false);
        dao.markAsRead(((HostelAuthenticatedWebSession) getSession()).getUser().getPersonId());
        ((EndlessListView)container.get("notificationsList")).reset(target);
        target.add(container);
    }

    public int getNotificationsCount() {
        return dao.notificationsCount(((HostelAuthenticatedWebSession) getSession()).getUser().getPersonId());
    }

}
