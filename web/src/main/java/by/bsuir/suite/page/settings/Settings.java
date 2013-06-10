package by.bsuir.suite.page.settings;

import by.bsuir.suite.colors.ColorConstants;
import by.bsuir.suite.page.base.BasePage;
import by.bsuir.suite.util.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.http.WebResponse;

import javax.servlet.http.Cookie;
import java.util.*;

/**
 * @author i.sukach
 */
@AuthorizeInstantiation({Roles.USER, Roles.ADMIN, Roles.FLOOR_HEAD, Roles.MANAGERESS, Roles.COMMANDANT,
        Roles.EDUCATOR, Roles.SUPER_USER, Roles.REGISTRAR, Roles.YOUTH_CENTER})
public class Settings extends BasePage {

    private static final Map<String, String> LANGUAGE_MAP = new HashMap<String, String>();

    private String selected = receiveSelected();

    public static final String LANGUAGE_COOKIE_KEY = "language";

    static {
        LANGUAGE_MAP.put("English", "en");
        LANGUAGE_MAP.put("Русский", "ru");
    }

    public Settings() {
        super(ColorConstants.GREY);

        Form form = new Form<Void>("form") {
            @Override
            protected void onSubmit() {
                getSession().setLocale(new Locale(LANGUAGE_MAP.get(selected)));

                Cookie languageCookie = new Cookie(LANGUAGE_COOKIE_KEY, LANGUAGE_MAP.get(selected));
                languageCookie.setMaxAge(30 * 24 * 3600);
                ((WebResponse) getResponse()).addCookie(languageCookie);
            }
        };

        DropDownChoice<String> languageChoice = new DropDownChoice<String>(
                "languageSelect", new PropertyModel<String>(this, "selected"), getLanguageList());
        add(form);
        form.add(languageChoice);
        form.add(new Label("languageLabel", new StringResourceModel("settings.language", this, null)));
    }

    private List<String> getLanguageList() {
        List<String> languageList = new ArrayList<String>();
        languageList.addAll(LANGUAGE_MAP.keySet());
        return languageList;
    }

    private String receiveSelected() {
        for (Map.Entry<String, String> entry : LANGUAGE_MAP.entrySet()) {
            if (entry.getValue().equals(getSession().getLocale().getLanguage())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
