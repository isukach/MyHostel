package by.bsuir.suite.service.news;

import by.bsuir.suite.domain.news.NewsCategory;
import by.bsuir.suite.dto.news.NewsDto;

import java.util.List;

/**
 * @author a.garelik
 */

public interface NewsService {

    List<NewsDto> getNewsByFilter(Filter filter, List<NewsCategory> selectedCategories, int offset, int limit);
    List<NewsDto> getNews(int offset, int limit, Long personId);
    void saveNews(NewsDto dto);
    Filter createNewsFilter(long personId);
    void removeNews(NewsDto dto);
}
