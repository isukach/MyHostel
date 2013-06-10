package by.bsuir.suite.util;

import by.bsuir.suite.dto.common.Dto;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.validation.IValidator;

/**
 * @author i.sukach
 */
public final class WicketUtils {

    private WicketUtils() {
    }

    public static <T extends Dto> FormComponent<String> addTextFieldWithLabel(String id, Form<T> form,
                                                        boolean required, Component parent, IValidator<String> validator) {
        FormComponent<String> textField;
        if (required) {
            textField = new RequiredTextField<String>(id);
        } else {
            textField = new TextField<String>(id);
        }
        textField.setLabel(new StringResourceModel("label." + id, null, parent));

        if (validator != null) {
            textField.add(validator);
        }
        form.add(textField);
        form.add(new SimpleFormComponentLabel(id + "Label", textField));
        return textField;
    }

    public static <T extends Dto> FormComponent<String> addPasswordFieldWithLabel(String id, Form<T> form,
                                                                                  Component parent) {
        FormComponent<String> passwordField = new PasswordTextField(id);
        passwordField.setLabel(new StringResourceModel("label." + id, null, parent));
        form.add(passwordField);
        form.add(new SimpleFormComponentLabel(id + "Label", passwordField));
        return passwordField;
    }
}
