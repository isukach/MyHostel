package by.bsuir.suite.dao;

import by.bsuir.suite.dao.common.GenericDao;
import by.bsuir.suite.domain.setting.SettingEnum;
import by.bsuir.suite.domain.setting.SystemSetting;

/**
 * User: Matveyenka Denis
 * Date: 14.09.13
 */
public interface SystemSettingDao extends GenericDao<SystemSetting> {

    SystemSetting getByName(SettingEnum name);
}
