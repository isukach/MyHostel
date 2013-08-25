package by.bsuir.suite.validator;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

/**
 * User: Matveyenka Denis
 * Date: 25.08.13
 */
public class JobOfferNumberOfPeopleValidator implements IValidator<Integer> {

    @Override
    public void validate(IValidatable<Integer> validatable) {
        Integer numberOfPeople = validatable.getValue();
        if(numberOfPeople == null || numberOfPeople < 1 || numberOfPeople > 30) {
            error(validatable, ValidationConstants.JOB_OFFER_NUMBER_OF_PEOPLE_ERROR_KEY);
        }
    }

    private void error(IValidatable<Integer> validatable, String messageKey) {
        ValidationError validationError = new ValidationError();
        validationError.addMessageKey(messageKey);
        validatable.error(validationError);
    }
}
