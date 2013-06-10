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
public class OldPasswordValidator implements IValidator<String> {

    @SpringBean
    private PersonService personService;

    private long personId;

    public OldPasswordValidator(long personId) {
        this.personId = personId;
        Injector.get().inject(this);
    }

    @Override
    public void validate(IValidatable<String> value) {
        String oldPassword = value.getValue();
        if (!personService.isPasswordCorrect(personId, oldPassword)) {
            error(value, "info.oldPassword.wrong");
        }
    }

    private void error(IValidatable<String> validatable, String messageKey) {
        ValidationError validationError = new ValidationError();
        validationError.addMessageKey(messageKey);
        validatable.error(validationError);
    }
}
