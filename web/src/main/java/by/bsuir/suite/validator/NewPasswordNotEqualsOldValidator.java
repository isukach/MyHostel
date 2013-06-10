package by.bsuir.suite.validator;

import by.bsuir.suite.service.person.PersonService;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

/**
 * @author i.sukach
 */
public class NewPasswordNotEqualsOldValidator implements IValidator<String> {

    private long personId;

    @SpringBean
    private PersonService personService;

    public NewPasswordNotEqualsOldValidator(long personId) {
        Injector.get().inject(this);
        this.personId = personId;
    }

    @Override
    public void validate(IValidatable<String> validatable) {
        String newPassword = validatable.getValue();
        if (personService.isPasswordCorrect(personId, newPassword)) {
            error(validatable, "info.password.newEqualsOld");
        }
    }

    private void error(IValidatable<String> validatable, String messageKey) {
        ValidationError validationError = new ValidationError();
        validationError.addMessageKey(messageKey);
        validatable.error(validationError);
    }
}
