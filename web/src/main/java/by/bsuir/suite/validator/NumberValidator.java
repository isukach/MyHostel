package by.bsuir.suite.validator;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author i.sukach
 */
public class NumberValidator implements IValidator<String> {

    private final Pattern pattern;
    private String errorMessageKey;
    private int maxNumber;

    public NumberValidator(int maxDigits, int maxNumber, String errorMessageKey) {
        pattern = Pattern.compile(String.format("^[0-9]{1,%d}$", maxDigits));
        this.errorMessageKey = errorMessageKey;
        this.maxNumber = maxNumber;
    }

    @Override
    public void validate(IValidatable<String> validatable) {
        String value = validatable.getValue();
        Matcher matcher = pattern.matcher(value);
        if (!matcher.matches() || Integer.parseInt(value) == 0 || Integer.parseInt(value) > maxNumber) {
            error(validatable, errorMessageKey);
        }
    }

    private void error(IValidatable<String> validatable, String messageKey) {
        ValidationError validationError = new ValidationError();
        validationError.addMessageKey(messageKey);
        validatable.error(validationError);
    }
}
