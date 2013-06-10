package by.bsuir.suite.service.duty;

import by.bsuir.suite.dao.duty.MonthDao;
import by.bsuir.suite.disassembler.duty.CalendarMonthDtoDisassembler;
import by.bsuir.suite.domain.duty.Month;
import by.bsuir.suite.dto.duty.CalendarMonthDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

/**
 * @author i.sukach
 */
@Service
@Transactional
public class MonthServiceImpl implements MonthService {

    @Autowired
    private MonthDao monthDao;

    @Autowired
    private CalendarMonthDtoDisassembler calendarMonthDtoDisassembler;

    @Override
    public CalendarMonthDto findFirstEnabledMonthByFloorId(Long floorId) {
        Calendar calendar = Calendar.getInstance();
        int monthNumber = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        List<Month> enabledMonths = monthDao.findByEnabledAndFloorId(floorId);
        if (!enabledMonths.isEmpty()) {
            return calendarMonthDtoDisassembler.disassemble(enabledMonths.get(0));
        }
        return findByMonthYearAndFloorId(monthNumber, year, floorId);
    }

    @Override
    public CalendarMonthDto switchMonthAccess(CalendarMonthDto monthDto) {
        Month month = monthDao.get(monthDto.getId());
        month.setEnabled(!monthDto.getEnabled());
        Month updatedMonth = monthDao.update(month);
        return calendarMonthDtoDisassembler.disassemble(updatedMonth);
    }

    @Override
    public CalendarMonthDto findByMonthYearAndFloorId(int month, int year, Long floorId) {
        Month calendarMonth = monthDao.findMonthByMonthYearAndFloorId(month, year, floorId);
        if (calendarMonth != null) {
            return calendarMonthDtoDisassembler.disassemble(calendarMonth);
        } else {
            return new CalendarMonthDto();
        }
    }
}
