package by.bsuir.suite.comparator;

import java.io.Serializable;
import java.util.Comparator;

import static by.bsuir.suite.util.StringUtils.isNumeric;
/**
 * @author i.sukach
 */
public class HostelNumberComparator implements Comparator<String>, Serializable {

    @Override
    public int compare(String o1, String o2) {
        boolean isFirstNumeric = isNumeric(o1);
        boolean isSecondNumeric = isNumeric(o2);
        if (isFirstNumeric && !isSecondNumeric) {
            return -1;
        } else if (isSecondNumeric && !isFirstNumeric) {
            return 1;
        } else if (isFirstNumeric && isSecondNumeric) {
            Integer firstNumber = Integer.parseInt(o1);
            Integer secondNumber = Integer.parseInt(o2);
            return firstNumber.compareTo(secondNumber);
        } else {
            return o1.compareTo(o2);
        }
    }
}
