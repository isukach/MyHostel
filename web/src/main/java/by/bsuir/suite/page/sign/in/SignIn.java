package by.bsuir.suite.page.sign.in;

import by.bsuir.suite.page.sign.in.panel.SignPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.WebPage;

import java.util.Locale;

/**
 * @author i.sukach
 */
public class SignIn extends WebPage {

    private SignPanel signPanel;

    public SignIn() {
        signPanel = new SignPanel("signInPanel");
        signPanel.setOutputMarkupId(true);
        add(signPanel);

        add(new AjaxFallbackLink<Void>("en") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                getSession().setLocale(Locale.ENGLISH);
                target.add(signPanel);
            }
        });
        add(new AjaxFallbackLink<Void>("ru") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                getSession().setLocale(new Locale("ru"));
                target.add(signPanel);
            }
        });
    }
}
