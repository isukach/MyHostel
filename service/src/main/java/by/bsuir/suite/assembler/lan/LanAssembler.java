package by.bsuir.suite.assembler.lan;

import by.bsuir.suite.assembler.common.Assembler;
import by.bsuir.suite.domain.lan.Lan;
import by.bsuir.suite.dto.lan.LanDto;
import by.bsuir.suite.dto.lan.LanPaymentCellDto;
import by.bsuir.suite.util.MonthNames;
import org.springframework.stereotype.Component;

/**
 * @author : d.shemiarey
 */
@Component
public class LanAssembler implements Assembler<LanDto, Lan>{
    @Override
    public Lan assemble(LanDto dto) {
        Lan lan = new Lan();
        lan.setIp(dto.getIp());
        lan.setActivated(dto.isActivated());

        for (LanPaymentCellDto cellDto : dto.getListLanPaymentCellDtos()) {
            updateMonthField(cellDto, lan);
        }

        return lan;
    }

    @Override
    public Lan updateEntityFields(LanDto dto, Lan lan) {
        lan.setIp(dto.getIp());
        lan.setActivated(dto.isActivated());

        for (LanPaymentCellDto cellDto : dto.getListLanPaymentCellDtos()) {
            updateMonthField(cellDto, lan);
        }

        return lan;
    }

    private void updateMonthField(LanPaymentCellDto cellDto, Lan lan) {
        if (MonthNames.SEPTEMBER.equals(cellDto.getMonthName())) {
            lan.setSeptember(cellDto.isPaid());
        } else if (MonthNames.SEPTEMBER.equals(cellDto.getMonthName())) {
            lan.setSeptember(cellDto.isPaid());
        } else if (MonthNames.OCTOBER.equals(cellDto.getMonthName())) {
            lan.setOctober(cellDto.isPaid());
        } else if (MonthNames.NOVEMBER.equals(cellDto.getMonthName())) {
            lan.setNovember(cellDto.isPaid());
        } else if (MonthNames.DECEMBER.equals(cellDto.getMonthName())) {
            lan.setDecember(cellDto.isPaid());
        } else if (MonthNames.JANUARY.equals(cellDto.getMonthName())) {
            lan.setJanuary(cellDto.isPaid());
        } else if (MonthNames.FEBRUARY.equals(cellDto.getMonthName())) {
            lan.setFebruary(cellDto.isPaid());
        } else if (MonthNames.MARCH.equals(cellDto.getMonthName())) {
            lan.setMarch(cellDto.isPaid());
        } else if (MonthNames.APRIL.equals(cellDto.getMonthName())) {
            lan.setApril(cellDto.isPaid());
        } else if (MonthNames.MAY.equals(cellDto.getMonthName())) {
            lan.setMay(cellDto.isPaid());
        } else if (MonthNames.JUNE.equals(cellDto.getMonthName())) {
            lan.setJune(cellDto.isPaid());
        } else if (MonthNames.JULY.equals(cellDto.getMonthName())) {
            lan.setJuly(cellDto.isPaid());
        } else if (MonthNames.AUGUST.equals(cellDto.getMonthName())) {
            lan.setAugust(cellDto.isPaid());
        } else {
            throw new IllegalArgumentException("No such month name!");
        }
    }

}
