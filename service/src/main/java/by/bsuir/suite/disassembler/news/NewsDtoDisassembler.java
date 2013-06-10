package by.bsuir.suite.disassembler.news;

import by.bsuir.suite.disassembler.common.BaseDisassembler;
import by.bsuir.suite.domain.news.News;
import by.bsuir.suite.dto.news.NewsDto;
import by.bsuir.suite.util.EntityUtils;
import by.bsuir.suite.util.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author : d.shemiarey
 */
@Component
public class NewsDtoDisassembler extends BaseDisassembler<NewsDto, News> {

    @Override
    public NewsDto disassemble(News object) {

        NewsDto dto = new NewsDto();
        if (object != null) {
            dto.setId(object.getId());
            dto.setCaption(object.getCaption());
            dto.setText(object.getText());
            dto.setCategory(object.getCategory());
            dto.setTimestamp(object.getTimestamp());
            if (object.getPerson() != null) {
                dto.setUserName(object.getPerson().getUser().getUsername());
                if (!StringUtils.notNullNotEmpty(object.getPerson().getMiddleName())) {
                    dto.setPersonName(object.getPerson().getFirstName() + " " + object.getPerson().getLastName());
                } else {
                    dto.setPersonName(EntityUtils.generatePersonCalendarName(object.getPerson()));
                }
            }

        }
        return dto;
    }
}
