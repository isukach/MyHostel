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
public class OldUsernameValidator implements IValidator<String> {

    @SpringBean
    private PersonService personService;

    private long personId;

    public OldUsernameValidator(long personId) {
        this.personId = personId;
        Injector.get().inject(this);
    }

    @Override
    public void validate(IValidatable<String> validatable) {
        String oldUsername = validatable.getValue();
        if (!personService.isUsernameCorrect(personId, oldUsername)) {
            error(validatable, "info.oldUsername.wrong");
        }
    }

    private void error(IValidatable<String> validatable, String messageKey) {
        ValidationError validationError = new ValidationError();
        validationError.addMessageKey(messageKey);
        validatable.error(validationError);
    }
}
