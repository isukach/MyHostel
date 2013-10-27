package by.bsuir.suite.initializer;

import by.bsuir.suite.dao.SystemSettingDao;
import by.bsuir.suite.dao.person.PersonDao;
import by.bsuir.suite.domain.person.Person;
import by.bsuir.suite.domain.setting.SettingEnum;
import by.bsuir.suite.domain.setting.SystemSetting;
import by.bsuir.suite.util.CommonUtils;
import by.bsuir.suite.util.HostelConstants;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author i.sukach
 */
public class JobHoursInitializer {

    @Autowired
    private SystemSettingDao systemSettingDao;

    @Autowired
    private PersonDao personDao;

    public void init() {
        SystemSetting jobInitializedSetting = systemSettingDao.getByName(SettingEnum.JOB_HOURS_INITIALIZED);
        if (jobInitializedSetting == null) {
            createJobInitializedSetting();
            jobInitializedSetting = systemSettingDao.getByName(SettingEnum.JOB_HOURS_INITIALIZED);
        }
        if (!jobInitializedSetting.isEnabled()) {
            for (Person person : personDao.findAll()) {
                if (person.getRoom() != null) {
                    int personCourse = CommonUtils.getCourseByUniversityGroup(person.getUniversityGroup());
                    if (personCourse == 1) {
                        person.getWork().setRequiredHours(HostelConstants.FIRST_COURSE_HOURS);
                        personDao.update(person);
                    } else if (personCourse == 5) {
                        person.getWork().setRequiredHours(HostelConstants.LAST_COURSE_HOURS);
                        personDao.update(person);
                    }
                }
            }
            jobInitializedSetting.setEnabled(true);
            systemSettingDao.update(jobInitializedSetting);
        }
    }

    private void createJobInitializedSetting() {
        SystemSetting systemSetting = new SystemSetting();
        systemSetting.setName(SettingEnum.JOB_HOURS_INITIALIZED);
        systemSetting.setEnabled(false);
        systemSettingDao.create(systemSetting);
    }
}
