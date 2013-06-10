package by.bsuir.suite.validator;

import by.bsuir.suite.dto.work.WorkProgressBarDto;
import by.bsuir.suite.page.work.panel.FormForUserWorkPanel;
import by.bsuir.suite.service.work.WorkService;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import static by.bsuir.suite.util.CommonUtils.isNumber;

/**
 * @author d.matveenko
 */
public class HoursValidator implements IValidator<String> {

    private String hours;

    private FormForUserWorkPanel panel;

    private WorkService workService;

    public HoursValidator(FormForUserWorkPanel panel) {
        this.workService = panel.getWorkService();
        this.panel = panel;
    }

    @Override
    public void validate(IValidatable<String> validatable) {
        hours = validatable.getValue();
        if(!isNumber(hours)) {
            error(validatable, "workPage.validator.hours.number");
        } else {
            if(validateMaxTotalHours()) {
                error(validatable, "workPage.validator.hours.maxResult");
            }
        }
    }

    private void error(IValidatable<String> validatable, String messageKey) {
        ValidationError validationError = new ValidationError();
        validationError.addMessageKey(messageKey);
        validatable.error(validationError);
    }

    private boolean validateMaxTotalHours() {
        WorkProgressBarDto dto = workService.getProgressBarDtoByPersonId(panel.getPersonId());
        int tmpPersonHours = Integer.parseInt(hours);
        return (dto.getTotalHours() + tmpPersonHours) > dto.getRequiredHours();
    }
}
