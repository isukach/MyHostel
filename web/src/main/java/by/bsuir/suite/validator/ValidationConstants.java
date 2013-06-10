package by.bsuir.suite.validator;

/**
 * @author i.sukach
 */
public final class ValidationConstants {

    public static final int FIRST_NAME_MAX_LENGTH = 30;
    public static final int LAST_NAME_MAX_LENGTH = 30;
    public static final int MIDDLE_NAME_MAX_LENGTH = 30;
    public static final int FACILITIES_MAX_LENGTH = 100;
    public static final int FROM_MAX_LENGTH = 100;
    public static final int PHONE_MAX_LENGTH = 20;
    public static final int ABOUT_MAX_LENGTH = 1000;
    public static final int PASSWORD_MIN_LENGTH = 6;
    public static final int PASSWORD_MAX_LENGTH = 20;
    public static final int USERNAME_MIN_LENGTH = 6;
    public static final int USERNAME_MAX_LENGTH = 20;
    public static final int DUTY_STATUS_COMMENT_MIN_LENGTH = 4;
    public static final int DUTY_STATUS_COMMENT_MAX_LENGTH = 150;

    public static final int DESCRIPTION_FIELD_MAX_LENGTH = 500;

    public static final String FIRST_NAME_LENGTH_ERROR_KEY = "info.first.name.maxlength";
    public static final String LAST_NAME_LENGTH_ERROR_KEY = "info.last.name.maxlength";
    public static final String MIDDLE_NAME_LENGTH_ERROR_KEY = "info.middle.name.maxlength";
    public static final String FACILITIES_LENGTH_ERROR_KEY = "info.facilities.maxlength";
    public static final String FROM_LENGTH_ERROR_KEY = "info.city.maxlength";
    public static final String JOB_DESCRIPTION_LENGTH_ERROR_KEY = "workPage.validator.description.maxLength";
    public static final String PHONE_NUMBER_LENGTH_ERROR_KEY = "info.phone.maxlength";
    public static final String ABOUT_LENGTH_ERROR_KEY = "info.about.maxlength";
    public static final String PASSWORD_MIN_LENGTH_ERROR_KEY = "info.password.minlength";
    public static final String PASSWORD_MAX_LENGTH_ERROR_KEY = "info.password.maxlength";
    public static final String USERNAME_MIN_LENGTH_ERROR_KEY = "info.username.minlength";
    public static final String USERNAME_MAX_LENGTH_ERROR_KEY = "info.username.maxlength";
    public static final String USERNAME_DIGITS_AND_LETTERS_ERROR_KEY = "info.username.digitsAndLetters";
    public static final String PASSWORD_DIGITS_AND_LETTERS_ERROR_KEY = "info.password.digitsAndLetters";
    public static final String DUTY_STATUS_COMMENT_MIN_LENGTH_ERROR_KEY ="info.duty.status.comment.minlength";
    public static final String DUTY_STATUS_COMMENT_MAX_LENGTH_ERROR_KEY ="info.duty.status.comment.maxlength";

    private ValidationConstants() {
    }
}
