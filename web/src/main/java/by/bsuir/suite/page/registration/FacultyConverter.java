package by.bsuir.suite.page.registration;

import by.bsuir.suite.dto.person.FacultyDto;
import org.apache.wicket.util.convert.IConverter;

import java.util.Locale;
import java.util.Map;

/**
 * @author i.sukach
 */
public class FacultyConverter implements IConverter<FacultyDto> {

    private Map<String, FacultyDto> facultiesLocalizationMap;

    public FacultyConverter(Map<String, FacultyDto> facultiesLocalizationMap) {
        this.facultiesLocalizationMap = facultiesLocalizationMap;
    }

    @Override
    public FacultyDto convertToObject(String s, Locale locale) {
        return facultiesLocalizationMap.get(s);
    }

    @Override
    public String convertToString(FacultyDto facultyDto, Locale locale) {
        for (Map.Entry<String, FacultyDto> entry : facultiesLocalizationMap.entrySet()) {
            if (entry.getValue() == facultyDto) {
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException("Cannot convert faculty to string");
    }
}
