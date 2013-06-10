package by.bsuir.suite.page.person.panel;

import by.bsuir.suite.dto.person.PersonDto;
import by.bsuir.suite.page.base.panel.HostelPanel;
import by.bsuir.suite.validator.MaxLengthValidator;
import by.bsuir.suite.validator.ValidationConstants;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextArea;

/**
 * @author i.sukach
 */
public class InfoPanelEditByUser extends HostelPanel {

    public InfoPanelEditByUser(String id, PersonDto personDto) {
        super(id);

        add(new Label("name", personDto.getFirstName() + " " + personDto.getLastName()));
        FormComponent<String> formComponent = new TextArea<String>("about");
        formComponent.add(new MaxLengthValidator(
                ValidationConstants.ABOUT_MAX_LENGTH, ValidationConstants.ABOUT_LENGTH_ERROR_KEY));
        add(formComponent);
    }
}
