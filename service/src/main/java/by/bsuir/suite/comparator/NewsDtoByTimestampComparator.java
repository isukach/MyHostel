package by.bsuir.suite.comparator;

import by.bsuir.suite.dto.news.NewsDto;

import java.io.Serializable;
import java.util.Comparator;

/**
 * @autor a.garelik
 * Date: 05/01/13
 * Time: 20:18
 */

public class NewsDtoByTimestampComparator implements Comparator<NewsDto>, Serializable {

    public enum Order {
        ASC,
        DESC
    }

    private Order oreder;

    public NewsDtoByTimestampComparator(Order oreder) {
        this.oreder = oreder;
    }

    @Override
    public int compare(NewsDto o1, NewsDto o2) {
        if(oreder == Order.DESC) {
            return o2.getTimestamp().compareTo(o1.getTimestamp());
        }
        else {
            return o1.getTimestamp().compareTo(o2.getTimestamp());
        }
    }
}
