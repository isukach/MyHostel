package by.bsuir.suite.page.person.panel;

import by.bsuir.suite.dto.person.*;
import by.bsuir.suite.page.base.panel.HostelPanel;
import by.bsuir.suite.validator.GroupFieldValidator;
import by.bsuir.suite.validator.MaxLengthValidator;
import by.bsuir.suite.validator.ValidationConstants;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;

/**
 * @author i.sukach
 */
public class InfoPanelEditByAdmin extends HostelPanel {

    private PersonDto personDto;

    private FacultySelectionPanel facultySelectionPanel;

    private FormComponent<String> firstNameTextField;

    public InfoPanelEditByAdmin(String id, PersonDto personDto) {
        super(id);
        setOutputMarkupId(true);

        this.personDto = personDto;

        firstNameTextField = new RequiredTextField<String>("firstName");
        firstNameTextField.add(new MaxLengthValidator(
                ValidationConstants.FIRST_NAME_MAX_LENGTH, ValidationConstants.FIRST_NAME_LENGTH_ERROR_KEY));
        add(firstNameTextField);

        FormComponent<String> lastNameTextField = new RequiredTextField<String>("lastName");
        lastNameTextField.add(new MaxLengthValidator(
                ValidationConstants.LAST_NAME_MAX_LENGTH, ValidationConstants.LAST_NAME_LENGTH_ERROR_KEY));
        add(lastNameTextField);

        FormComponent<String> middleNameTextField = new TextField<String>("middleName");
        middleNameTextField.add(new MaxLengthValidator(
                ValidationConstants.MIDDLE_NAME_MAX_LENGTH, ValidationConstants.MIDDLE_NAME_LENGTH_ERROR_KEY));
        add(middleNameTextField);

        FormComponent<String> groupField = new RequiredTextField<String>("group");
        groupField.add(new GroupFieldValidator());
        add(groupField);

        FormComponent<String> phoneNumberField = new TextField<String>("phoneNumber");
        phoneNumberField.add(new MaxLengthValidator(
                ValidationConstants.PHONE_MAX_LENGTH, ValidationConstants.PHONE_NUMBER_LENGTH_ERROR_KEY));
        add(phoneNumberField);

        FormComponent<String> fromField = new TextField<String>("from");
        fromField.add(new MaxLengthValidator(
                ValidationConstants.FROM_MAX_LENGTH, ValidationConstants.FROM_LENGTH_ERROR_KEY));
        add(fromField);

        FormComponent<String> facilitiesTextField = new TextField<String>("facilities");
        facilitiesTextField.add(new MaxLengthValidator(
                ValidationConstants.FACILITIES_MAX_LENGTH, ValidationConstants.FACILITIES_LENGTH_ERROR_KEY));
        add(facilitiesTextField);

        facultySelectionPanel = new FacultySelectionPanel("facultyPanel", personDto.getFaculty());
        add(facultySelectionPanel);
    }

    public void setRoom(RoomDto roomDto, FloorDto selectedFloor, HostelDto selectedHostel) {
        personDto.setRoom(roomDto);
        personDto.setFloor(selectedFloor);
        personDto.setHostel(selectedHostel);
    }

    public FacultyDto getFaculty() {
        return facultySelectionPanel.getSelectedFaculty();
    }
}
