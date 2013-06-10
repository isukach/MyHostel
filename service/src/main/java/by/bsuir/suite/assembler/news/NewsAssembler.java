package by.bsuir.suite.assembler.news;

import by.bsuir.suite.assembler.common.Assembler;
import by.bsuir.suite.dao.person.HostelDao;
import by.bsuir.suite.domain.news.News;
import by.bsuir.suite.domain.person.Floor;
import by.bsuir.suite.dto.news.NewsDto;
import by.bsuir.suite.service.news.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @autor a.garelik
 * Date: 03/01/13
 * Time: 00:16
 */
@Component
public class NewsAssembler implements Assembler<NewsDto, News> {


    @Autowired
    private HostelDao hostelDao;

    @Override
    public News assemble(NewsDto dto) {
        News news = new News();
        return updateEntityFields(dto, news);
    }

    @Override
    public News updateEntityFields(NewsDto dto, News entity) {
        entity.setCaption(dto.getCaption());

        entity.setText(dto.getText());
        entity.setCaption(dto.getCaption());
        entity.setTimestamp(dto.getTimestamp());
        entity.setId(dto.getId());
        entity.setFloors(new HashSet<Floor>());

        if(dto.getFilter() != null)
        {
            Filter filter = dto.getFilter();
            Set<Floor> hostelFloors = hostelDao.getHostelByNumber(filter.getHostel()).getFloors();
            for (Floor floor: hostelFloors) {
                for (Integer markedFloor: filter.getSelectedFloors()) {
                    if (floor.getNumber().equals(markedFloor.toString())) {
                        entity.getFloors().add(floor);
                    }
                }
            }

            entity.setCategory(filter.getCategory());
        }

        return entity;
    }

}
