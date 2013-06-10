package by.bsuir.suite.validator;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

/**
 * @author i.sukach
 */
public class MinLengthValidator implements IValidator<String> {

    private int minLength;
    private String messageKey;

    public MinLengthValidator(int minLength, String messageKey) {
        this.minLength = minLength;
        this.messageKey = messageKey;
    }

    @Override
    public void validate(IValidatable<String> value) {
        String description = value.getValue();
        if(description != null && description.length() < minLength) {
            error(value, messageKey);
        }
    }

    private void error(IValidatable<String> validatable, String messageKey) {
        ValidationError validationError = new ValidationError();
        validationError.addMessageKey(messageKey);
        validatable.error(validationError);
    }
}
