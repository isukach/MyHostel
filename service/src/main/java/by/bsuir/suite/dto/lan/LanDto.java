package by.bsuir.suite.dto.lan;

import by.bsuir.suite.dto.common.Dto;
import by.bsuir.suite.dto.common.Versioned;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : d.shemiarey
 */
public class LanDto implements Dto, Versioned {

    private static final int MONTH_NUMBER = 12;

    private static final String [] MONTHS = new String [] {
            "september", "october", "november", "december", "january", "february",
            "march", "april", "may", "june", "july", "august"
    };

    private Long id;

    private String ip;

    private boolean activated = false;

    private List<LanPaymentCellDto> listLanPaymentCellDtos = new ArrayList<LanPaymentCellDto>(MONTH_NUMBER);

    private Long version;

    public LanDto() {
        for (int i = 0; i < MONTH_NUMBER; i++) {
            LanPaymentCellDto dto = new LanPaymentCellDto(MONTHS[i]);
            listLanPaymentCellDtos.add(dto);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public List<LanPaymentCellDto> getListLanPaymentCellDtos() {
        return listLanPaymentCellDtos;
    }

    public void setListLanPaymentCellDtos(List<LanPaymentCellDto> listLanPaymentCellDtos) {
        this.listLanPaymentCellDtos = listLanPaymentCellDtos;
    }

    @Override
    public Long getVersion() {
        return version;
    }

    public LanDto setVersion(Long version) {
        this.version = version;
        return this;
    }
}
