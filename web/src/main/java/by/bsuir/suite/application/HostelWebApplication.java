package by.bsuir.suite.application;

import by.bsuir.suite.page.duty.DutyPage;
import by.bsuir.suite.page.help.Help;
import by.bsuir.suite.page.lan.LanPage;
import by.bsuir.suite.page.main.Index;
import by.bsuir.suite.page.news.NewsPage;
import by.bsuir.suite.page.penalty.PenaltyPage;
import by.bsuir.suite.page.person.PersonPage;
import by.bsuir.suite.page.registration.StaffRegistrationPage;
import by.bsuir.suite.page.registration.RegistrationPage;
import by.bsuir.suite.page.settings.Settings;
import by.bsuir.suite.page.sign.in.SignIn;
import by.bsuir.suite.page.work.WorkPage;
import by.bsuir.suite.session.HostelAuthenticatedWebSession;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.settings.IResourceSettings;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import javax.servlet.http.Cookie;
import java.util.List;
import java.util.Locale;

/**
 * @author i.sukach
 */
public class HostelWebApplication extends AuthenticatedWebApplication {

    private boolean isInitialized = false;

    public static final String LANGUAGE_COOKIE_KEY = "language";

    @Override
    protected void init() {
        if (!isInitialized) {
            super.init();
            setListeners();
            isInitialized = true;

            IResourceSettings settings = getResourceSettings();
            settings.addResourceFolder("pages");

            getMarkupSettings().setAutomaticLinking(true);
            getMarkupSettings().setDefaultMarkupEncoding("UTF-8");

            getApplicationSettings().setUploadProgressUpdatesEnabled(true);
        }
        changeUrls();
    }

    private void changeUrls() {
        mount(new MountedMapperWithoutPageComponentInfo("/login", SignIn.class));
        mount(new MountedMapperWithoutPageComponentInfo("/duty", DutyPage.class));
        mount(new MountedMapperWithoutPageComponentInfo("/settings", Settings.class));
        mount(new MountedMapperWithoutPageComponentInfo("/help", Help.class));
        mount(new MountedMapperWithoutPageComponentInfo("/index", Index.class));
        mount(new MountedMapperWithoutPageComponentInfo("/network", LanPage.class));
        mount(new MountedMapperWithoutPageComponentInfo("/work", WorkPage.class));
        mount(new MountedMapperWithoutPageComponentInfo("/profile", PersonPage.class));
        mount(new MountedMapperWithoutPageComponentInfo("/news", NewsPage.class));
        mount(new MountedMapperWithoutPageComponentInfo("/penalty", PenaltyPage.class));
        mount(new MountedMapperWithoutPageComponentInfo("/registration", RegistrationPage.class));
        mount(new MountedMapperWithoutPageComponentInfo("/adminRegistration", StaffRegistrationPage.class));
    }

    /**
     * SpringComponentInjector should be added to listeners to enable spring bean
     * injection in non-page Wicket classes.
     */
    private void setListeners() {
        getComponentInstantiationListeners().add(new SpringComponentInjector(this));
    }

    @Override
    public Session newSession(Request request, Response response) {
        Session session = super.newSession(request, response);
        session = tryToSetLanguageFromCookie(session, request, response);

        return session;
    }

    @Override
    protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
        return HostelAuthenticatedWebSession.class;
    }

    @Override
    protected Class<? extends WebPage> getSignInPageClass() {
        return SignIn.class;
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return Index.class;
    }

    private Session tryToSetLanguageFromCookie(Session session, Request request, Response response) {
        List<Cookie> cookies = ((WebRequest) request).getCookies();
        if (cookies == null || cookies.isEmpty()) {
            return session;
        }

        for (Cookie cookie : cookies) {
            if (LANGUAGE_COOKIE_KEY.equals(cookie.getName())) {
                session.setLocale(new Locale(cookie.getValue()));

                cookie.setMaxAge(30 * 24 * 3600);
                ((WebResponse) response).addCookie(cookie);
                break;
            }
        }
        return session;
    }
}
