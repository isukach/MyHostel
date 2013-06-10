package by.bsuir.suite.page.sign.in.panel;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.authentication.IAuthenticationStrategy;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 * @author i.sukach
 */
public class SignPanel extends Panel {

    private static final String SIGN_IN_FORM = "signInForm";

    private String password;

    private String username;

    public SignPanel(final String id) {
        super(id);
        add(new FeedbackPanel("feedback"));
        add(new SignInForm(SIGN_IN_FORM));
    }

    protected SignInForm getForm() {
        return (SignInForm) get(SIGN_IN_FORM);
    }

    @Override
    protected void onBeforeRender() {
        if (!isSignedIn()) {
            IAuthenticationStrategy authenticationStrategy = getApplication().getSecuritySettings()
                    .getAuthenticationStrategy();
            String[] data = authenticationStrategy.load();

            if ((data != null) && (data.length > 1)) {
                // try to sign in the user
                if (signIn(data[0], data[1])) {
                    username = data[0];
                    password = data[1];

                    // logon successful. Continue to the original destination
                    if (!continueToOriginalDestination()) {
                        // Ups, no original destination. Go to the home page
                        throw new RestartResponseException(getSession().getPageFactory().newPage(
                                getApplication().getHomePage()));
                    }
                } else {
                    // the loaded credentials are wrong. erase them.
                    authenticationStrategy.remove();
                }
            }
        }

        // don't forget
        super.onBeforeRender();
    }

    private boolean signIn(String username, String password) {
        return AuthenticatedWebSession.get().signIn(username, password);
    }

    private boolean isSignedIn() {
        return AuthenticatedWebSession.get().isSignedIn();
    }

    protected void onSignInFailed() {
        error(getLocalizer().getString("signInFailed", this, "Sign in failed"));
    }

    protected void onSignInSucceeded() {
        if (!continueToOriginalDestination()) {
            setResponsePage(getApplication().getHomePage());
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public final class SignInForm extends StatelessForm<SignPanel> {
        private static final long serialVersionUID = 1L;

        public SignInForm(final String id) {
            super(id);

            setModel(new CompoundPropertyModel<SignPanel>(SignPanel.this));

            add(new RequiredTextField<String>("username"));
            add(new PasswordTextField("password"));
        }

        @Override
        public void onSubmit() {
            IAuthenticationStrategy strategy = getApplication().getSecuritySettings()
                    .getAuthenticationStrategy();

            if (signIn(getUsername(), getPassword())) {
                strategy.save(username, password);
                onSignInSucceeded();
            } else {
                onSignInFailed();
                strategy.remove();
            }
        }
    }
}
