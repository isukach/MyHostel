package by.bsuir.suite.domain.setting;

import by.bsuir.suite.domain.BaseEntity;

import javax.persistence.*;

/**
 * User: Matveyenka Denis
 * Date: 14.09.13
 */
@javax.persistence.Entity
@Table(name = "system_setting")
public class SystemSetting extends BaseEntity {

    private boolean enabled;

    private SettingEnum name;

    @Column(name = "name")
    @Enumerated(value = EnumType.STRING)
    public SettingEnum getName() {
        return name;
    }

    public void setName(SettingEnum name) {
        this.name = name;
    }

    @Column(columnDefinition = "tinyint", name = "enabled")
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
