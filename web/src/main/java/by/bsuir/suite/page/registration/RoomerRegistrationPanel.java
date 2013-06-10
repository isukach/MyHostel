package by.bsuir.suite.page.registration;

import by.bsuir.suite.dto.person.FacultyDto;
import by.bsuir.suite.dto.person.RoomDto;
import by.bsuir.suite.dto.registration.RoomerRegistrationDto;
import by.bsuir.suite.page.base.panel.HostelPanel;
import by.bsuir.suite.page.person.panel.FacultySelectionPanel;
import by.bsuir.suite.page.person.panel.RoomerRolePanel;
import by.bsuir.suite.service.registration.RegistrationService;
import by.bsuir.suite.util.Roles;
import by.bsuir.suite.validator.GroupFieldValidator;
import by.bsuir.suite.validator.MaxLengthValidator;
import by.bsuir.suite.validator.ValidationConstants;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormValidatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.time.Duration;

import static by.bsuir.suite.util.WicketUtils.addTextFieldWithLabel;

/**
 * @author i.sukach
 */
@AuthorizeAction(action = Action.RENDER, roles = {Roles.REGISTRAR, Roles.SUPER_USER, Roles.FLOOR_HEAD})
public class RoomerRegistrationPanel extends HostelPanel {

    private Form<RoomerRegistrationDto> roomerForm;

    @SpringBean
    private RegistrationService registrationService;

    private FacultySelectionPanel facultySelectionPanel;

    public RoomerRegistrationPanel(String id) {
        super(id);
        final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);

        RoomerRegistrationDto dto = new RoomerRegistrationDto();
        dto.setRole(Roles.USER); //default role in within set of checkboxes

        IModel<RoomerRegistrationDto> compoundModel = new CompoundPropertyModel<RoomerRegistrationDto>(dto);
        roomerForm = new Form<RoomerRegistrationDto>("roomerForm", compoundModel);
        add(roomerForm);
        roomerForm.setOutputMarkupId(true);

        addTextFieldWithLabel("firstName", roomerForm, true, this, new MaxLengthValidator(
                ValidationConstants.FIRST_NAME_MAX_LENGTH, ValidationConstants.FIRST_NAME_LENGTH_ERROR_KEY));
        addTextFieldWithLabel("lastName", roomerForm, true, this, new MaxLengthValidator(
                ValidationConstants.LAST_NAME_MAX_LENGTH, ValidationConstants.LAST_NAME_LENGTH_ERROR_KEY));
        addTextFieldWithLabel("middleName", roomerForm, false, this, new MaxLengthValidator(
                ValidationConstants.MIDDLE_NAME_MAX_LENGTH, ValidationConstants.MIDDLE_NAME_LENGTH_ERROR_KEY));
        addTextFieldWithLabel("groupNumber", roomerForm, true, this, new GroupFieldValidator());
        addTextFieldWithLabel("phoneNumber", roomerForm, false, this, new MaxLengthValidator(
                ValidationConstants.PHONE_MAX_LENGTH, ValidationConstants.PHONE_NUMBER_LENGTH_ERROR_KEY));
        addTextFieldWithLabel("city", roomerForm, false, this, new MaxLengthValidator(
                ValidationConstants.FROM_MAX_LENGTH, ValidationConstants.FROM_LENGTH_ERROR_KEY));

        facultySelectionPanel = new FacultySelectionPanel("facultyPanel", FacultyDto.FITU);
        RoomerRolePanel rolePanel = new RoomerRolePanel("rolePanel");
        roomerForm.add(facultySelectionPanel);
        roomerForm.add(rolePanel);

        AjaxFormValidatingBehavior.addToAllFormComponents(roomerForm, "onkeyup", Duration.ONE_SECOND);

        roomerForm.add(new AjaxButton("submitRoomerForm", roomerForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                RoomerRegistrationDto registrationDto = (RoomerRegistrationDto) form.getModelObject();
                registrationDto.setFaculty(facultySelectionPanel.getSelectedFaculty());
                registrationService.registerRoomer(registrationDto);
                PageParameters pageParameters = new PageParameters();
                onPageParametersAdded(pageParameters);
                setResponsePage(RegistrationPage.class, pageParameters);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(feedbackPanel);
            }
        });
    }

    protected void onPageParametersAdded(PageParameters pageParameters) {
    }

    public void setRoom(RoomDto room) {
        roomerForm.getModelObject().setRoom(room);
    }
}
