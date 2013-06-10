package by.bsuir.suite.exporter;

/**
 * @author a.garelik
 */
public abstract class BaseDutyExporter {
    public void createTemplate(DutyData data) {
        createHeader(data);
        createContent(data);
        createFooter(data);
    }

    public abstract void createHeader(DutyData data);

    public abstract void createContent(DutyData data);

    public abstract void createFooter(DutyData data);

    public abstract String build(DutyData data);
}
