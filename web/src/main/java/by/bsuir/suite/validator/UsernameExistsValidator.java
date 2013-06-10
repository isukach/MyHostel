package by.bsuir.suite.validator;

import by.bsuir.suite.service.UserService;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

/**
 * @author i.sukach
 */
public class UsernameExistsValidator implements IValidator<String> {

    @SpringBean
    private UserService userService;

    public UsernameExistsValidator() {
        Injector.get().inject(this);
    }

    @Override
    public void validate(IValidatable<String> validatable) {
        String username = validatable.getValue();
        if (userService.userNameExists(username)) {
            error(validatable, "info.username.exists");
        }
    }

    private void error(IValidatable<String> validatable, String messageKey) {
        ValidationError validationError = new ValidationError();
        validationError.addMessageKey(messageKey);
        validatable.error(validationError);
    }
}
