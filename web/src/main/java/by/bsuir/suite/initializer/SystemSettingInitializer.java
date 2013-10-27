package by.bsuir.suite.initializer;

import by.bsuir.suite.dao.SystemSettingDao;
import by.bsuir.suite.domain.setting.SettingEnum;
import by.bsuir.suite.domain.setting.SystemSetting;
import by.bsuir.suite.dto.person.FacultyDto;
import by.bsuir.suite.dto.person.RoomDto;
import by.bsuir.suite.dto.registration.RoomerRegistrationDto;
import by.bsuir.suite.service.person.RoomService;
import by.bsuir.suite.service.registration.RegistrationService;
import by.bsuir.suite.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Properties;

/**
 * User: Matveyenka Denis
 * Date: 14.09.13
 */
public class SystemSettingInitializer {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private SystemSettingDao systemSettingDao;

    public void init() {
        SystemSetting systemSetting = systemSettingDao.getByName(SettingEnum.PEOPLE_REGISTERED);
        if (systemSetting == null) {
            createSystemSetting();
            systemSetting = systemSettingDao.getByName(SettingEnum.PEOPLE_REGISTERED);
        }
        if (!systemSetting.isEnabled()) {
            try {
                URL url = getClass().getClassLoader().getResource("/2013-2014.mdb");
                String absolutePath = url.getPath();
                absolutePath = absolutePath.replaceFirst("/", "");
                Class.forName("sun.jdbc.odbc.JdbcOdbcDriver").newInstance();
                Connection connection = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=" + absolutePath + ";Pwd=5310103", getDBProperties());
                Statement statement = connection.createStatement();
                statement.execute("SELECT Основной.*, Фак.Код_фак FROM Основной where общ = 1");
                ResultSet rs = statement.getResultSet();
                while (rs.next()) {
                    String block = rs.getString(8);
                    if (block.length() < 3) {
                        continue;
                    }
                    RoomerRegistrationDto dto = new RoomerRegistrationDto();
                    dto.setLastName(rs.getString(2));
                    dto.setFirstName(rs.getString(3));
                    dto.setMiddleName(rs.getString(4));
                    dto.setGroupNumber(rs.getString(6));
                    dto.setRoom(getRoomDto(block));
                    dto.setFaculty(getFaculty(rs));
                    dto.setCity(capitalizeCity(rs.getString(11)));
                    dto.setRole(Roles.USER);
                    registrationService.registerRoomer(dto);
                }
                systemSetting.setEnabled(true);
                systemSettingDao.update(systemSetting);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private String capitalizeCity(String city) {
        String result = city;
        if (result != null  && !"".equals(result)) {
            result = StringUtils.capitalize(result);
        }
        return result;
    }

    private void createSystemSetting() {
        SystemSetting systemSetting = new SystemSetting();
        systemSetting.setName(SettingEnum.PEOPLE_REGISTERED);
        systemSetting.setEnabled(false);
        systemSettingDao.create(systemSetting);
    }

    private FacultyDto getFaculty(ResultSet rs) throws SQLException {
        String facultyDb = rs.getString(10);
        FacultyDto faculty = null;
        if ("КП".equals(facultyDb)) {
            faculty = FacultyDto.FKP;
        } else if ("ИТиУ".equals(facultyDb)) {
            faculty = FacultyDto.FITU;
        } else if ("ТК".equals(facultyDb)) {
            faculty = FacultyDto.FTK;
        } else if ("РЭ".equals(facultyDb)) {
            faculty = FacultyDto.FRE;
        } else if ("КСиС".equals(facultyDb)) {
            faculty = FacultyDto.FKSIS;
        } else if ("ИЭФ".equals(facultyDb)) {
            faculty = FacultyDto.IEF;
        } else if ("Аспирант".equals(facultyDb)) {
            faculty = FacultyDto.ASPIRANT;
        } else if ("ПО".equals(facultyDb)) {
            faculty = FacultyDto.PO;
        } else if ("Семейные".equals(facultyDb)) {
            faculty = FacultyDto.FAMILY;
        } else if ("Мил".equals(facultyDb)) {
            faculty = FacultyDto.MILITIA;
        } else if ("+++".equals(facultyDb)) {
            faculty = FacultyDto.OTHER;
        } else if ("Магистрант".equals(facultyDb)) {
            faculty = FacultyDto.MAGISTRANT;
        } else if ("ВФ".equals(facultyDb)) {
            faculty = FacultyDto.MILITARY;
        } else if ("МОС_МГЭУ".equals(facultyDb)) {
            faculty = FacultyDto.MOS_MGEU;
        } else if ("ЭМ_МГЭУ".equals(facultyDb)) {
            faculty = FacultyDto.EM_MGEU;
        }
        return faculty;
    }

    private RoomDto getRoomDto(String block) throws SQLException {
        String floorNumber;
        String roomNumber;
        if (block.length() == 3) {
            floorNumber = block.substring(0, 1);
            roomNumber = block.substring(1, 3);
        } else {
            floorNumber = block.substring(0, 2);
            roomNumber = block.substring(2, 4);
        }

        return roomService.getRoomByHostelFloorAndNumber(1, floorNumber, roomNumber);
    }

    private Properties getDBProperties() {
        Properties p = new Properties();
        p.put("charSet", "Windows-1251");
        p.put("lc_ctype", "Windows-1251");
        p.put("encoding", "Windows-1251");
        return p;
    }
}
