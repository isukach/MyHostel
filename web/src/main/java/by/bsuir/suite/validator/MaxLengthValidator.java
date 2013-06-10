package by.bsuir.suite.validator;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

/**
 * @author d.matveenko
 */
public class MaxLengthValidator implements IValidator<String>{

    private int maxLength;
    private String messageKey;

    public MaxLengthValidator(int maxLength, String messageKey) {
        this.maxLength = maxLength;
        this.messageKey = messageKey;
    }

    @Override
    public void validate(IValidatable<String> validatable) {
        String value = validatable.getValue();
        if(value != null && value.length() > maxLength) {
            error(validatable, messageKey);
        }
    }

    private void error(IValidatable<String> validatable, String messageKey) {
        ValidationError validationError = new ValidationError();
        validationError.addMessageKey(messageKey);
        validatable.error(validationError);
    }
}
