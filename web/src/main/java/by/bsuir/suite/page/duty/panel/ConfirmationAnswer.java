package by.bsuir.suite.page.duty.panel;

import java.io.Serializable;

/**
 * @author i.sukach
 */
public class ConfirmationAnswer implements Serializable {

    private boolean answer;

    public boolean isPositive() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }
}
