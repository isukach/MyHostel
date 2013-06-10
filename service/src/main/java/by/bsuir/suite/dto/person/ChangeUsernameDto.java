package by.bsuir.suite.dto.person;

import by.bsuir.suite.dto.common.Dto;

/**
 * @author i.sukach
 */
public class ChangeUsernameDto implements Dto {

    private String oldUsername;

    private String newUsername;

    public String getOldUsername() {
        return oldUsername;
    }

    public void setOldUsername(String oldUsername) {
        this.oldUsername = oldUsername;
    }

    public String getNewUsername() {
        return newUsername;
    }

    public void setNewUsername(String newUsername) {
        this.newUsername = newUsername;
    }
}
