package by.bsuir.suite.dto.news;

import by.bsuir.suite.domain.news.NewsCategory;
import by.bsuir.suite.dto.common.Dto;
import by.bsuir.suite.service.news.Filter;

import java.sql.Timestamp;

/**
 * @author a.garelik
 */
public class NewsDto implements Dto {

    private Long id;
    private Timestamp timestamp;
    private String caption;
    private String text;
    private NewsCategory category;
    private String userName;
    private String personName;

    private Filter filter;

    public NewsDto() {
        text = "";
        caption = "";
        filter = new Filter();
    }

    public NewsDto(String caption, String text, Filter filter) {
        this.caption = caption;
        this.text = text;
        this.filter = filter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }


    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public NewsCategory getCategory() {
        return category;
    }

    public void setCategory(NewsCategory category) {
        this.category = category;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

}
