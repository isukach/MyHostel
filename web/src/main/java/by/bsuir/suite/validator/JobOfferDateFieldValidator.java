package by.bsuir.suite.validator;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import java.util.Calendar;
import java.util.Date;

/**
 * User: Matveyenka Denis
 * Date: 25.08.13
 */
public class JobOfferDateFieldValidator implements IValidator<Date> {

    @Override
    public void validate(IValidatable<Date> validatable) {
        Date date = validatable.getValue();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar currentDate = Calendar.getInstance();
        if(calendar.get(Calendar.YEAR) < currentDate.get(Calendar.YEAR)) {
            error(validatable, ValidationConstants.JOB_OFFER_DATE_ERROR_KEY);
        } else if (calendar.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR)) {
            if (calendar.get(Calendar.DAY_OF_YEAR) < currentDate.get(Calendar.DAY_OF_YEAR)) {
                error(validatable, ValidationConstants.JOB_OFFER_DATE_ERROR_KEY);
            }
        }
    }

    private void error(IValidatable<Date> validatable, String messageKey) {
        ValidationError validationError = new ValidationError();
        validationError.addMessageKey(messageKey);
        validatable.error(validationError);
    }
}
