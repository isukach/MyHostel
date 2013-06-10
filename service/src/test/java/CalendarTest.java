import by.bsuir.suite.util.CalendarUtils;
import junit.framework.Assert;
import org.junit.Test;

/**
 * @author i.sukach
 */
public class CalendarTest {

    private static final int AUGUST_NUMBER = 7;

    private static final int JANUARY_NUMBER = 0;

    private static final int FEBRUARY_NUMBER = 1;

    private static final int YEAR_2012 = 2012;

    private static final int YEAR_2013 = 2013;

    @Test
    public void testAugust2012() {
        int [] margins = CalendarUtils.getTopAndBottomMargins(AUGUST_NUMBER, YEAR_2012);
        Assert.assertEquals(margins[0], 2);
        Assert.assertEquals(margins[1], 9);
    }

    @Test
    public void testJanuary2013() {
        int [] margins = CalendarUtils.getTopAndBottomMargins(JANUARY_NUMBER, YEAR_2013);
        Assert.assertEquals(margins[0], 1);
        Assert.assertEquals(margins[1], 10);
    }

    @Test
    public void testFebruary2013() {
        int [] margins = CalendarUtils.getTopAndBottomMargins(FEBRUARY_NUMBER, YEAR_2013);
        Assert.assertEquals(margins[0], 4);
        Assert.assertEquals(margins[1], 10);
    }
}
