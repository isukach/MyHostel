package by.bsuir.suite.util;

import by.bsuir.suite.domain.NotificationType;

/**
 * @autor a.garelik
 * Date: 27/07/13
 * Time: 18:17
 */

public class NotificationUtils {

    private static NotificationUtils instance;

    private NotificationUtils() {
    }

    public static NotificationUtils getInstance()
    {
        if (instance == null)
        {
            instance = new NotificationUtils();
        }
        return instance;
    }

    public String getCategoryAsString(NotificationType entityType) {
        String theRes = "other-indicator";
        switch (entityType){
            case DUTY: theRes = "duty-indicator"; break;
            case NETWORK: theRes = "network-indicator"; break;
            case OTHER: theRes = "other-indicator"; break;
        }
        return theRes;
    }

    public String getVisibleHeader(String header) {
        //TODO fix It
        return header;
    }

    public String getVisibleText(String text){
       return text;
    }

    public  Object[] getParamsFromString(String params){
        if(params != null)
            return params.split("\\?");
        return null;
    }

    public String getParametersAsString(Object[] params) {
        if(params != null) {
            StringBuilder buffer = new StringBuilder();
            for (Object parameter: params){
                buffer.append(parameter).append("?");
            }
            buffer.deleteCharAt(buffer.length());
            return buffer.toString();
        }
        return null;
    }
}
