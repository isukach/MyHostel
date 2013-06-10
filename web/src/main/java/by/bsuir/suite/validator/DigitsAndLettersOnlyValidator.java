package by.bsuir.suite.validator;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author i.sukach
 */
public class DigitsAndLettersOnlyValidator implements IValidator<String> {

    private static final String REGEX = "^[A-Za-z0-9-._]*$";
    private String messageKey;

    public DigitsAndLettersOnlyValidator(String messageKey) {
        this.messageKey = messageKey;
    }

    @Override
    public void validate(IValidatable<String> validatable) {
        Pattern pattern = Pattern.compile(REGEX);
        String value = validatable.getValue();
        Matcher matcher = pattern.matcher(value);
        if (!matcher.matches()) {
            error(validatable, messageKey);
        }
    }

    private void error(IValidatable<String> validatable, String messageKey) {
        ValidationError validationError = new ValidationError();
        validationError.addMessageKey(messageKey);
        validatable.error(validationError);
    }
}
