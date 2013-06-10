package by.bsuir.suite.service.lan;

import by.bsuir.suite.dto.lan.LanDto;

/**
 * @author : d.shemiarey
 */
public interface LanService {

    LanDto getLanPaymentForPerson(Long personId);

    void changeLanPayment(LanDto dto, Long personId);

    boolean hasContract(Long personId);
}
