package by.bsuir.suite.page.person.panel;

import by.bsuir.suite.dto.person.FacultyDto;
import by.bsuir.suite.page.base.panel.HostelPanel;
import by.bsuir.suite.page.registration.FacultyConverter;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.SimpleFormComponentLabel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.util.convert.IConverter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author i.sukach
 */
public class FacultySelectionPanel extends HostelPanel {

    private FacultyDto selectedFaculty;

    public FacultySelectionPanel(String id, FacultyDto defaultFaculty) {
        super(id);

        final IConverter<FacultyDto> converter = new FacultyConverter(createLocalizationMap());
        DropDownChoice<FacultyDto> faculties = new DropDownChoice<FacultyDto>("faculty",
                new PropertyModel<FacultyDto>(this, "selectedFaculty"), Arrays.asList(FacultyDto.values())) {
            @Override
            public <C> IConverter<C> getConverter(Class<C> type) {
                return (IConverter<C>) converter;
            }
        };
        faculties.setModelObject(defaultFaculty);
        faculties.setRequired(true);
        faculties.setLabel(new StringResourceModel("label.faculty", this, null));
        add(new SimpleFormComponentLabel("facultyLabel", faculties));
        add(faculties);
    }

    private Map<String, FacultyDto> createLocalizationMap() {
        Map<String, FacultyDto> facultiesLocalizationMap = new HashMap<String, FacultyDto>();
        for (FacultyDto faculty : FacultyDto.values()) {
            String localizedFaculty
                    = new StringResourceModel("faculty." + faculty.name().toLowerCase(), this, null).getString();
            facultiesLocalizationMap.put(localizedFaculty, faculty);
        }
        return facultiesLocalizationMap;
    }

    public FacultyDto getSelectedFaculty() {
        return selectedFaculty;
    }
}
