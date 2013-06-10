package by.bsuir.suite.dao.news;

import by.bsuir.suite.dao.common.GenericDaoImpl;
import by.bsuir.suite.domain.news.Course;
import org.springframework.stereotype.Repository;

/**
 * @author a.garelik
 */
@Repository
public class CourseDaoImpl extends GenericDaoImpl<Course> implements CourseDao {

    public CourseDaoImpl() {
        super(Course.class);
    }
}
