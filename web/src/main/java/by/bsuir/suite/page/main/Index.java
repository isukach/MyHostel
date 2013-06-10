package by.bsuir.suite.page.main;

import by.bsuir.suite.colors.ColorConstants;
import by.bsuir.suite.domain.news.NewsCategory;
import by.bsuir.suite.dto.news.NewsDto;
import by.bsuir.suite.page.base.BasePage;
import by.bsuir.suite.page.duty.DutyPage;
import by.bsuir.suite.page.duty.panel.CalendarPanel;
import by.bsuir.suite.page.lan.LanPage;
import by.bsuir.suite.page.news.NewsPage;
import by.bsuir.suite.page.news.components.EndlessListView;
import by.bsuir.suite.page.penalty.PenaltyPage;
import by.bsuir.suite.page.registration.RegistrationPage;
import by.bsuir.suite.page.registration.StaffRegistrationPage;
import by.bsuir.suite.page.work.WorkPage;
import by.bsuir.suite.service.lan.LanService;
import by.bsuir.suite.service.news.Filter;
import by.bsuir.suite.service.news.NewsService;
import by.bsuir.suite.session.HostelAuthenticatedWebSession;
import by.bsuir.suite.util.NewsInfo;
import by.bsuir.suite.util.Permissions;
import by.bsuir.suite.util.Roles;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author i.sukach
 * @author a.garelik
 */
@AuthorizeInstantiation({Roles.USER, Roles.ADMIN, Roles.FLOOR_HEAD, Roles.MANAGERESS, Roles.YOUTH_CENTER,
        Roles.COMMANDANT, Roles.SUPER_USER, Roles.EDUCATOR, Roles.REGISTRAR})
public class Index extends BasePage {

    private Filter filter;
    private List<String> selectedCategories = new ArrayList<String>(6);
    private EndlessListView<NewsDto> newsContainer;

    @SpringBean
    @SuppressWarnings({"UnusedDeclaration"})
    private NewsService newsService;

    @SpringBean
    @SuppressWarnings({"UnusedDeclaration"})
    private LanService lanService;

    public Index() {
        super(ColorConstants.GREY, false);
        init();
    }

    private void init() {
        Link dutyLink = new DutyLink("dutyLink");

        Link sehLink = new WorkLink("sehLink");

        Link lanLink = new LanLink("lanLink");

        Link penaltyLink = new PenaltyLink("penaltyLink");

        Link registrationLink = new RegistrationLink("registrationLink");

        Link staffRegistrationLink = new StaffRegistrationLink("staffRegistrationLink");

        Link newsLink = new NewsLink("newsLink");

        Label sehLinkLabel = new Label("sehLinkLabel", new StringResourceModel("linkTitle.work", this, null));
        sehLink.add(sehLinkLabel);
        add(sehLink);

        Label dutyLinkLabel = new Label("dutyLinkLabel", new StringResourceModel("linkTitle.duty", this, null));
        dutyLink.add(dutyLinkLabel);
        add(dutyLink);

        Label lanLinkLabel = new Label("lanLinkLabel", new StringResourceModel("linkTitle.lan", this, null));
        lanLink.add(lanLinkLabel);
        add(lanLink);

        Label registrationLinkLabel = new Label("registrationLinkLabel",
                new StringResourceModel("linkTitle.registration", this, null));
        registrationLink.add(registrationLinkLabel);
        add(registrationLink);

        Label staffRegistrationLinkLabel = new Label("staffRegistrationLinkLabel",
                new StringResourceModel("linkTitle.staffRegistration", this, null));
        staffRegistrationLink.add(staffRegistrationLinkLabel);
        add(staffRegistrationLink);

        Label penaltyLinkLabel = new Label("penaltyLinkLabel", new StringResourceModel("linkTitle.penalty", this, null));
        penaltyLink.add(penaltyLinkLabel);
        add(penaltyLink);

        Label newsLinkLabel = new Label("newsLinkLabel", new StringResourceModel("linkTitle.news", this, null));
        newsLink.add(newsLinkLabel);
        add(newsLink);

        HostelAuthenticatedWebSession session = (HostelAuthenticatedWebSession) getSession();
        filter = newsService.createNewsFilter(session.getUser().getPersonId());
        createNewsCheckboxes();

        newsContainer = new EndlessListView<NewsDto>("newsContainer") {
            @Override
            protected void populateListItem(ListItem<NewsDto> components) {
                final NewsDto item = components.getModelObject();
                WebComponent indicator = new WebComponent("newsIndicator");
                String resKey = NewsInfo.getCategoryHashMap().get(item.getCategory());
                resKey = resKey.substring(resKey.lastIndexOf('.')+1);
                indicator.add(new AttributeModifier("class", resKey){
                    protected String newValue(final String currentValue, final String replacementValue){
                        return currentValue +" "+ replacementValue+"-indicator";
                    }
                });
                components.add(indicator);
                components.add(new Label("newsCaption", item.getCaption()));
                components.add(new Label("newsDate", CalendarPanel.getDateString(new Date(item.getTimestamp().getTime()),getLocale(), this)));
                MultiLineLabel label = new MultiLineLabel("newsBody", item.getText());
                label.setEscapeModelStrings(false);
                components.add(label);
                if(item.getUserName() != null){
                    components.add(new Label("newsAutor", item.getPersonName()));
                }
                else {
                    components.add(new WebComponent("newsAutor"));
                }
            }

            @Override
            protected List<NewsDto> loadItems(int offset, int limit) {
                List<NewsCategory> categories = new ArrayList<NewsCategory>(6);
                for(NewsCategory category: NewsInfo.getCategoryHashMap().keySet()) {
                    if(selectedCategories.contains(NewsInfo.getCategoryHashMap().get(category))) {
                        categories.add(category);
                    }
                }
                return newsService.getNewsByFilter(filter, categories, offset, limit);
            }
        };
        add(newsContainer);
    }

    private void createNewsCheckboxes(){
        for (String key: NewsInfo.getCategoriesKeys()){
            final CheckBox checkBox = new CheckBox("filter."+key, Model.of(Boolean.TRUE));
            final String finalKey = key;
            checkBox.add(new OnChangeAjaxBehavior() {
                @Override
                protected void onUpdate(AjaxRequestTarget target) {
                    if(checkBox.getModelObject()) {
                        selectedCategories.add(finalKey);
                    } else if (selectedCategories.size() > 1) {
                        selectedCategories.remove(finalKey);
                    }
                    else {
                        checkBox.setModelObject(true);
                    }
                    newsContainer.reset(target);
                    target.add(checkBox);
                }
            });
            add(checkBox);
            selectedCategories.add(key);
        }
    }

    @AuthorizeAction(action = Action.RENDER, roles = {Permissions.LAN_VIEWING_PERMISSION, Roles.SUPER_USER, Roles.ADMIN})
    private static class LanLink extends AjaxFallbackLink<Void> {
        public LanLink(String id) {
            super(id);
        }

        @Override
        public void onClick(AjaxRequestTarget ajaxRequestTarget) {
            setResponsePage(LanPage.class);
        }
    }


    @AuthorizeAction(action = Action.RENDER, roles = {Roles.USER, Roles.ADMIN, Roles.FLOOR_HEAD,
            Roles.EDUCATOR, Roles.SUPER_USER, Roles.COMMANDANT})
    private static class DutyLink extends AjaxFallbackLink<Void> {
        public DutyLink(String id) {
            super(id);
        }

        @Override
        public void onClick(AjaxRequestTarget target) {
            setResponsePage(DutyPage.class);
        }
    }

    @AuthorizeAction(action = Action.RENDER, roles = {Roles.ADMIN, Roles.USER, Roles.FLOOR_HEAD,
            Roles.SUPER_USER, Roles.MANAGERESS})private static class WorkLink extends AjaxFallbackLink<Void> {
        public WorkLink(String id) {
            super(id);
        }

        @Override
        public void onClick(AjaxRequestTarget ajaxRequestTarget) {
            setResponsePage(WorkPage.class);
        }
    }

    @AuthorizeAction(action = Action.RENDER, roles = {Roles.REGISTRAR, Roles.SUPER_USER, Roles.FLOOR_HEAD})
    private static class RegistrationLink extends AjaxFallbackLink<Void> {

        public RegistrationLink(String id) {
            super(id);
        }

        @Override
        public void onClick(AjaxRequestTarget target) {
            setResponsePage(RegistrationPage.class);
        }
    }

    @AuthorizeAction(action = Action.RENDER, roles = {Roles.COMMANDANT, Roles.SUPER_USER})
    private static class PenaltyLink extends AjaxFallbackLink<Void> {

        public PenaltyLink(String id) {
            super(id);
        }
        @Override
        public void onClick(AjaxRequestTarget ajaxRequestTarget) {
            setResponsePage(PenaltyPage.class);
        }
    }

    @AuthorizeAction(action = Action.RENDER, roles = {Roles.REGISTRAR, Roles.SUPER_USER})
    private static class StaffRegistrationLink extends AjaxFallbackLink<Void> {

        public StaffRegistrationLink(String id) {
            super(id);
        }

        @Override
        public void onClick(AjaxRequestTarget target) {
            setResponsePage(StaffRegistrationPage.class);
        }
    }

    @AuthorizeAction(action = Action.RENDER,
            roles = {Roles.ADMIN, Roles.FLOOR_HEAD, Roles.EDUCATOR, Roles.MANAGERESS, Roles.COMMANDANT,
                    Roles.SUPER_USER, Roles.YOUTH_CENTER, Roles.REGISTRAR})
    private static class NewsLink extends AjaxFallbackLink<Void> {

        public NewsLink(String id) {
            super(id);
        }

        @Override
        public void onClick(AjaxRequestTarget target) {
            setResponsePage(NewsPage.class);
        }
    }
}
