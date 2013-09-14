package by.bsuir.suite.dao;

import by.bsuir.suite.dao.common.GenericDaoImpl;
import by.bsuir.suite.domain.setting.SettingEnum;
import by.bsuir.suite.domain.setting.SystemSetting;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * User: Matveyenka Denis
 * Date: 14.09.13
 */
@Repository
public class SystemSettingDaoImpl extends GenericDaoImpl<SystemSetting> implements SystemSettingDao {

    public SystemSettingDaoImpl() {
        super(SystemSetting.class);
    }

    @Override
    public SystemSetting getByName(SettingEnum name) {
        Criteria criteria = getSession().createCriteria(getPersistentClass(), "setting");
        criteria.add(Restrictions.eq("setting.name", name));
        return (SystemSetting) criteria.uniqueResult();
    }
}
