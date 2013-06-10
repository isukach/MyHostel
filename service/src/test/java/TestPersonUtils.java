import by.bsuir.suite.domain.person.Person;
import by.bsuir.suite.util.EntityUtils;
import junit.framework.Assert;
import org.junit.Test;

/**
 * @author i.sukach
 */
public class TestPersonUtils {

    private static final String FIRST_NAME = "John";

    private static final String MIDDLE_NAME = "Vasilievich";

    private static final String LAST_NAME = "Doe";

    private static final String PATTERN = "Doe J.V.";

    @Test
    public void testPersonCalendarNameGeneration() {
        Person person = new Person();
        person.setFirstName(FIRST_NAME);
        person.setMiddleName(MIDDLE_NAME);
        person.setLastName(LAST_NAME);
        Assert.assertEquals(PATTERN, EntityUtils.generatePersonCalendarName(person));
    }
}
