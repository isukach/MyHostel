import by.bsuir.suite.util.StringUtils;
import junit.framework.Assert;
import org.junit.Test;

/**
 * @author i.sukach
 */
public class TestStringUtils {

    private static final String WORD_TO_CAPITALIZE = "wOrD";

    private static final String EXPECTED_CAPITALIZE_RESULT = "Word";

    @Test
    public void testCapitalize() {
        Assert.assertEquals(StringUtils.capitalize(WORD_TO_CAPITALIZE), EXPECTED_CAPITALIZE_RESULT);
    }
}
