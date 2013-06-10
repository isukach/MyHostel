package by.bsuir.suite.util;


import by.bsuir.suite.domain.news.NewsCategory;

import java.util.HashMap;
import java.util.Map;

/**
 * @autor a.garelik
 * Date: 30/12/12
 * Time: 23:44
 */

public final class NewsInfo {

    private NewsInfo() {
    }

    private static String[] categories = {
            "network",
            "duty",
            "events",
            "questions",
            "work",
            "other"
    };

    public static final String DEFAULT_CATEGORY = "other";
    public static final String CATEGORY_RESOURCE_KEY = "newsPage.category.";

    public static String[] getCategoriesKeys() {
        return categories;
    }

    private static Map<NewsCategory,String> categoryHashMap;

    static {
        categoryHashMap = new HashMap<NewsCategory,String>(6);
        categoryHashMap.put(NewsCategory.NETWORK,"network");
        categoryHashMap.put(NewsCategory.DUTY,"duty");
        categoryHashMap.put(NewsCategory.EVENTS,"events");
        categoryHashMap.put(NewsCategory.LIVE_QUESTIONS,"questions");
        categoryHashMap.put(NewsCategory.WORK,"work");
        categoryHashMap.put(NewsCategory.OTHER,"other");
    }

    public static String getWordsInTextByCount(String text, int count){
        char prevChar = 0;
        for (int i = 0; i < text.length(); i++){
            if(text.charAt(i) == ' ' && prevChar != ' ') {
                count--;
            }
            if( count <= 0) {
                return text.substring(0 , i) + "...";
            }
            prevChar = text.charAt(i);
        }
        return text;
    }

    public static Map<NewsCategory, String> getCategoryHashMap() {
        return categoryHashMap;
    }
}
