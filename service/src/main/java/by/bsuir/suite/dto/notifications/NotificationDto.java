package by.bsuir.suite.dto.notifications;

import by.bsuir.suite.dto.common.Dto;

/**
 * @autor a.garelik
 * Date: 27/07/13
 * Time: 14:34
 */

public class NotificationDto implements Dto {

    private String colorDecorator;
    private String date;
    private String header;
    private String text;
    private Boolean isViewed;
    private Object[] headerParams;
    private Object[] textParams;

    public Object[] getHeaderParams() {
        return headerParams;
    }

    public void setHeaderParams(Object[] headerParams) {
        this.headerParams = headerParams;
    }

    public Object[] getTextParams() {
        return textParams;
    }

    public void setTextParams(Object[] textParams) {
        this.textParams = textParams;
    }

    public String getColorDecorator() {
        return colorDecorator;
    }

    public void setCategory(String colorDecorator) {
        this.colorDecorator = colorDecorator;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setIsViewed(Boolean viewed) {
        isViewed = viewed;
    }

    public Boolean isViewed() {
        return isViewed;
    }

}
