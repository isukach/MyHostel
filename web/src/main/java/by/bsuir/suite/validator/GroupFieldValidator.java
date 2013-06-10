package by.bsuir.suite.validator;

import by.bsuir.suite.util.CommonUtils;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import static by.bsuir.suite.util.CommonUtils.isNumber;

/**
 * @author i.sukach
 */
public class GroupFieldValidator implements IValidator<String> {

    private static final int GROUP_NUMBER_LENGTH = 6;
    private static final int MAX_COURSE_NUMBER = 5;
    private static final int MIN_COURSE_NUMBER = 1;

    @Override
    public void validate(IValidatable<String> validatable) {
        String group = validatable.getValue();
        ValidationError error = new ValidationError();
        int errors = 0;
        if (group != null) {
            if (group.length() != GROUP_NUMBER_LENGTH) {
                error.addMessageKey("info.group.length");
                errors++;
            }
            if (!isNumber(group)) {
                error.addMessageKey("info.group.digits");
                errors++;
            } else {
                int courseNumber = CommonUtils.getCourseByUniversityGroup(group);
                if (courseNumber < MIN_COURSE_NUMBER || courseNumber > MAX_COURSE_NUMBER) {
                    error.addMessageKey("info.group.wrong.course");
                    errors++;
                }
            }
            if (errors > 0) {
                validatable.error(error);
            }
        }
    }
}
