package by.bsuir.suite.page.base.panel;

import by.bsuir.suite.page.base.BasePage;
import by.bsuir.suite.page.main.Index;
import by.bsuir.suite.page.person.PersonPage;
import by.bsuir.suite.page.settings.Settings;
import by.bsuir.suite.util.Roles;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.resource.ContextRelativeResource;

import javax.servlet.http.Cookie;

/**
 * @author i.sukach
 */
public class MenuPanel extends HostelPanel {

    private static final String HOME_BUTTON_WIDTH = "175px";

    private String color;

    public MenuPanel(String id, String color, final boolean showTitle, final BasePage basePage) {
        super(id);
        this.color = color;

        add(new Label("pageTitle", new StringResourceModel("pageTitle", this, null)) {
            @Override
            public boolean isVisible() {
                return showTitle;
            }
        });

        WebMarkupContainer separatorContainer = new WebMarkupContainer("separator") {
            @Override
            public boolean isVisible() {
                return showTitle;
            }
        };
        separatorContainer.add(new Image("separatorImage", new ContextRelativeResource("images/separator.png")));
        add(separatorContainer);

        add(new Behavior() {
            @Override
            public void onComponentTag(Component component, ComponentTag tag) {
                tag.put("style", "background-color: " + MenuPanel.this.color);
            }
        });

        AjaxFallbackLink<Void> homeLink = new AjaxFallbackLink<Void>("homeLink") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                setResponsePage(Index.class);
            }
        };

        if (!showTitle) {
            homeLink.add(new AttributeModifier("style", "width:" + HOME_BUTTON_WIDTH));
        }

        add(homeLink);

        add(new AjaxFallbackLink("settingsLink") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                setResponsePage(Settings.class);
            }
        });


        add(new AjaxFallbackLink("logoutLink") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                Cookie cookie = getWebRequest().getCookie("LoggedIn");
                cookie.setMaxAge(0);
                ((WebResponse) getResponse()).addCookie(cookie);
                getSession().invalidateNow();
                setResponsePage(Index.class);
            }
        });

        add(new AjaxFallbackLink("searchLink") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                basePage.updateSearchPanel(true);
                target.add(basePage.getPersonSearchPanel());
            }
        });

        ProfileLinkContainer profileLinkContainer = new ProfileLinkContainer("profileLinkContainer");
        AjaxFallbackLink profileLink = new AjaxFallbackLink("profileLink") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                setResponsePage(PersonPage.class);
            }
        };
        profileLinkContainer.add(profileLink);
        add(profileLinkContainer);
    }

    @AuthorizeAction(action = Action.RENDER, roles = {Roles.USER, Roles.ADMIN, Roles.COMMANDANT, Roles.FLOOR_HEAD})
    private static final class ProfileLinkContainer extends WebMarkupContainer {

        public ProfileLinkContainer(String id) {
            super(id);
        }
    }
}
