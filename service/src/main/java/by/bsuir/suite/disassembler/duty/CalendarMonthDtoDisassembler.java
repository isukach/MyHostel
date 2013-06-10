package by.bsuir.suite.disassembler.duty;

import by.bsuir.suite.disassembler.common.BaseDisassembler;
import by.bsuir.suite.domain.duty.Duty;
import by.bsuir.suite.domain.duty.DutyShift;
import by.bsuir.suite.domain.duty.Month;
import by.bsuir.suite.dto.duty.CalendarCellDto;
import by.bsuir.suite.dto.duty.CalendarMonthDto;
import by.bsuir.suite.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author i.sukach
 */
@Component
public class CalendarMonthDtoDisassembler extends BaseDisassembler<CalendarMonthDto, Month> {

    @Autowired
    private CalendarCellDtoDisassembler calendarCellDtoDisassembler;

    @Override
    public CalendarMonthDto disassemble(Month month) {
        CalendarMonthDto dto = new CalendarMonthDto()
                .setId(month.getId())
                .setEnabled(month.isEnabled())
                .setMonth(month.getMonth())
                .setYear(month.getYear());
        dto.setCells(disassembleMonthDuties(month.getDuties()));
        return dto;
    }

    public List<CalendarCellDto> disassembleMonthDuties(Set<Duty> duties) {
        List<Duty> dutyList = new ArrayList<Duty>(duties);
        List<CalendarCellDto> cellDtos = new ArrayList<CalendarCellDto>();
        List<Long> checkedDuties = new ArrayList<Long>();
        for (int i = 0; i < dutyList.size(); i++) {
            Duty duty = dutyList.get(i);
            for (int j = i + 1; j < dutyList.size(); j++) {
                Duty secondDuty = dutyList.get(j);
                if (DateUtils.datesEqual(duty.getDate(), secondDuty.getDate())) {
                    checkedDuties.add(duty.getId());
                    checkedDuties.add(secondDuty.getId());
                    cellDtos.add(duty.getShift().equals(DutyShift.FIRST)
                                    ? calendarCellDtoDisassembler.disassemble(duty, secondDuty)
                                    : calendarCellDtoDisassembler.disassemble(secondDuty, duty));

                }
            }
            if (!checkedDuties.contains(duty.getId())) {
                checkedDuties.add(duty.getId());
                cellDtos.add(duty.getShift().equals(DutyShift.FIRST)
                        ? calendarCellDtoDisassembler.disassemble(duty, new Duty())
                        : calendarCellDtoDisassembler.disassemble(new Duty(), duty));
            }
        }
        Collections.sort(cellDtos);
        return cellDtos;
    }
}
