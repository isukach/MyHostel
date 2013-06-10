package by.bsuir.suite.dto.person;

import by.bsuir.suite.dto.common.Dto;

/**
 * @author i.sukach
 */
public class ChangePasswordDto implements Dto {

    private String oldPassword;

    private String newPassword;

    private String newPasswordConfirm;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordConfirm() {
        return newPasswordConfirm;
    }

    public void setNewPasswordConfirm(String newPasswordConfirm) {
        this.newPasswordConfirm = newPasswordConfirm;
    }
}
