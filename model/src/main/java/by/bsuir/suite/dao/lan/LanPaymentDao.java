package by.bsuir.suite.dao.lan;

import by.bsuir.suite.domain.lan.LanPayment;

import java.io.Serializable;

/**
 * @author i.sukach
 */
public interface LanPaymentDao extends Serializable {

    LanPayment getByFirstNameLastNameAndGroupNumber(String firstName, String lastName, String groupNumber);
}
