package by.bsuir.suite.validator;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import static by.bsuir.suite.util.CommonUtils.isNumber;

/**
 * User: Matveyenka Denis
 * Date: 25.08.13
 */
public class JobOfferHoursValidator implements IValidator<Integer> {

    @Override
    public void validate(IValidatable<Integer> validatable) {
        Integer hours = validatable.getValue();
        if(hours == null || hours < 1 || hours > 40) {
            error(validatable, ValidationConstants.JOB_OFFER_HOURS_ERROR_KEY);
        }
    }

    private void error(IValidatable<Integer> validatable, String messageKey) {
        ValidationError validationError = new ValidationError();
        validationError.addMessageKey(messageKey);
        validatable.error(validationError);
    }
}
