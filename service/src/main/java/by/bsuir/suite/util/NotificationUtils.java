package by.bsuir.suite.util;

import by.bsuir.suite.domain.NotificationType;

/**
 * @autor a.garelik
 * Date: 27/07/13
 * Time: 18:17
 */

public class NotificationUtils {

    private static NotificationUtils instance;

    private static final String PARAM_SPLITTER = "\u00ae";

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
            case WORK: theRes = "work-indicator"; break;
            case NETWORK: theRes = "network-indicator"; break;
            case OTHER: theRes = "other-indicator"; break;
        }
        return theRes;
    }

    public NotificationType getNotificationTypeFromNotificationKey(String notificationKey){
        NotificationType theRes = NotificationType.OTHER;
        try {
            String s = notificationKey.split("\\.")[1];
            if (s.equals("duty")) {
                theRes = NotificationType.DUTY;
            } else if (s.equals("network")) {
                theRes = NotificationType.NETWORK;
            } else if (s.equals("work")) {
                theRes = NotificationType.WORK;
            }
        } catch (Exception ex){
            //skipp
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
            return params.split(PARAM_SPLITTER);
        return null;
    }

    public String getParametersAsString(Object[] params) {
        if(params != null && params.length != 0) {
            StringBuilder buffer = new StringBuilder();
            for (Object parameter: params){
                buffer.append(parameter).append(PARAM_SPLITTER);
            }
            return buffer.toString();
        }
        return null;
    }

    public static String getTextKey(String notificationType) {
        return notificationType+".text";
    }

    public static String getHeaderKey(String notificationType) {
        return notificationType+".header";
    }
}
