package by.bsuir.suite.dao.news;

import by.bsuir.suite.dao.common.GenericDaoImpl;
import by.bsuir.suite.domain.news.News;
import by.bsuir.suite.domain.news.NewsCategory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author a.garelik
 */
@Repository
public class NewsDaoImpl extends GenericDaoImpl<News> implements NewsDao {

    public NewsDaoImpl() {
        super(News.class);
    }

    @Override
    public List<News> getNewsBy(List<NewsCategory> categories, int floor, int course, int offset, int limit) {
        Criteria criteria = getSession().createCriteria(getPersistentClass(),"news")
            .add(Restrictions.in("news.category", categories));
        if (course >= 1 && course <= 5) {
            criteria.createAlias("news.courses", "newsCourses")
                    .add(Restrictions.eq("newsCourses.course",course));
        }
        if (floor >= 1) {
            criteria.createAlias("news.floors", "newsFloors")
                    .add(Restrictions.eq("newsFloors.number", String.valueOf(floor)));
        }
        if (limit > 0 && offset > 0 ) {
            criteria.setMaxResults(limit);
            criteria.setFirstResult(offset);
        }

        criteria.addOrder(Order.desc("news.timestamp"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    @Override
    public List<News> getNews(int offset, int limit, Long personId) {
        Criteria criteria = getSession().createCriteria(getPersistentClass(),"news");
        if(limit > 0 && offset > 0 ){
            criteria.setMaxResults(limit);
            criteria.setFirstResult(offset);
        }
        criteria.createAlias("news.person", "newsPerson");
        criteria.add(Restrictions.eq("newsPerson.id", personId));
        return criteria.list();
    }

    @Override
    public void saveNews(News news) {
        if(news.getId() != null) {
            getSession().createSQLQuery("delete from course where news_id="+news.getId()).executeUpdate();
        }
        update(news);
    }
}
