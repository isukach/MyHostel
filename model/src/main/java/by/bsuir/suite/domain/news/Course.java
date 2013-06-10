package by.bsuir.suite.domain.news;

import by.bsuir.suite.domain.BaseEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * @autor a.garelik
 * Date: 06/01/13
 * Time: 00:03
 */

@Entity
@Table(name = "course")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Course extends BaseEntity {

    private Integer course;
    private News news;

    public Course(Integer course, News news) {
        this.course = course;
        this.news = news;
    }

    public Course() {
    }

    @Column(name = "course")
    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id", referencedColumnName = "id")
    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

}
