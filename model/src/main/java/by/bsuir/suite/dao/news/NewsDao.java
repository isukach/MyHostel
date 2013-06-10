package by.bsuir.suite.dao.news;

import by.bsuir.suite.dao.common.GenericDao;
import by.bsuir.suite.domain.news.News;
import by.bsuir.suite.domain.news.NewsCategory;

import java.util.List;

/**
 * @author  a.garelik
 */
public interface NewsDao extends GenericDao<News> {
    List<News> getNewsBy(List<NewsCategory> categories, int floor, int course, int offset, int limit);
    List<News> getNews(int offset, int limit, Long personId);
    void saveNews(News news);
}
