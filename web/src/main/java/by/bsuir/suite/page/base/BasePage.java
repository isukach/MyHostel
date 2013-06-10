package by.bsuir.suite.page.base;

import by.bsuir.suite.page.base.panel.MenuPanel;
import by.bsuir.suite.page.base.panel.PersonSearchPanel;
import by.bsuir.suite.session.HostelAuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;

/**
 * @author hector21
 */
public abstract class BasePage extends WebPage {

    private static boolean isSearchPanelVisible;

    private PersonSearchPanel searchPanel;

    public BasePage(String color) {
        this(color, true);
    }

    public BasePage(String color, boolean showTitle) {
        add(new MenuPanel("menuPanel", color, showTitle, this));
        addSearchPanel();
    }

    private void addSearchPanel() {
        searchPanel = new PersonSearchPanel("personSearchPanel") {
            @Override
            public boolean isVisible() {
                return isSearchPanelVisible;
            }
        };
        searchPanel.setVisible(isSearchPanelVisible);
        add(searchPanel);
        searchPanel.setOutputMarkupPlaceholderTag(true);
    }

    public void updateSearchPanel(boolean visibility) {
        setSearchVisibility(visibility);
        searchPanel.setVisible(isSearchPanelVisible);
    }

    public static void setSearchVisibility(boolean visibility) {
        isSearchPanelVisible = visibility;
    }

    public PersonSearchPanel getPersonSearchPanel() {
        return this.searchPanel;
    }

    public Long getCurrentPersonId() {
        return ((HostelAuthenticatedWebSession) getSession()).getUser().getPersonId();
    }
}
