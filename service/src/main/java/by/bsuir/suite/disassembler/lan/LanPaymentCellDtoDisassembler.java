package by.bsuir.suite.disassembler.lan;

import by.bsuir.suite.domain.lan.LanPayment;
import by.bsuir.suite.dto.lan.LanPaymentCellDto;
import by.bsuir.suite.util.MonthNames;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : d.shemiarey
 */
@Component
public class LanPaymentCellDtoDisassembler {

    public List<LanPaymentCellDto> disassembleToListCells(LanPayment lan) {
        List<LanPaymentCellDto> listDto = new ArrayList<LanPaymentCellDto>();
        listDto.add(createLanPaymentCellDto(MonthNames.SEPTEMBER, lan.isSeptember()));
        listDto.add(createLanPaymentCellDto(MonthNames.OCTOBER, lan.isOctober()));
        listDto.add(createLanPaymentCellDto(MonthNames.NOVEMBER, lan.isNovember()));
        listDto.add(createLanPaymentCellDto(MonthNames.DECEMBER, lan.isDecember()));
        listDto.add(createLanPaymentCellDto(MonthNames.JANUARY, lan.isJanuary()));
        listDto.add(createLanPaymentCellDto(MonthNames.FEBRUARY, lan.isFebruary()));
        listDto.add(createLanPaymentCellDto(MonthNames.MARCH, lan.isMarch()));
        listDto.add(createLanPaymentCellDto(MonthNames.APRIL, lan.isApril()));
        listDto.add(createLanPaymentCellDto(MonthNames.MAY, lan.isMay()));
        listDto.add(createLanPaymentCellDto(MonthNames.JUNE, lan.isJune()));
        listDto.add(createLanPaymentCellDto(MonthNames.JULY, lan.isJuly()));
        listDto.add(createLanPaymentCellDto(MonthNames.AUGUST, lan.isAugust()));

        return listDto;
    }

    private LanPaymentCellDto createLanPaymentCellDto(String monthName, boolean paid) {
        LanPaymentCellDto  dto = new LanPaymentCellDto();
        dto.setMonthName(monthName);
        dto.setPaid(paid);

        return dto;
    }
}
