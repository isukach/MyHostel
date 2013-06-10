package by.bsuir.suite.domain.duty;

/**
 * @author i.sukach
 */
public enum DutyShift {
    FIRST(1),
    SECOND(2);

    private int number;

    DutyShift(int number) {
        this.number = number;
    }

    public static DutyShift fromNumber(int number) {
        for (DutyShift shift : DutyShift.values()) {
            if (shift.number == number) {
                return shift;
            }
        }
        throw new IllegalArgumentException("Could not find shift by provided number.");
    }
}
