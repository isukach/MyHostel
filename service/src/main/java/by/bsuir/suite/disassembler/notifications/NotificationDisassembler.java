package by.bsuir.suite.disassembler.notifications;

import by.bsuir.suite.disassembler.common.BaseDisassembler;
import by.bsuir.suite.domain.Notification;
import by.bsuir.suite.dto.notifications.NotificationDto;
import by.bsuir.suite.util.DateUtils;
import by.bsuir.suite.util.NotificationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * @autor a.garelik
 * Date: 27/07/13
 * Time: 16:21
 */
@Component
public class NotificationDisassembler extends BaseDisassembler<NotificationDto, Notification> {

    private Locale locale = Locale.ROOT;

    @Autowired
    private ResourceBundleMessageSource resourceBundleMessageSource;


    @Override
    public NotificationDto disassemble(Notification object) {
        NotificationUtils notificationUtils = NotificationUtils.getInstance();
        NotificationDto theDto = new NotificationDto();
        theDto.setText(notificationUtils.getVisibleText(object.getText()));
        theDto.setHeader(notificationUtils.getVisibleHeader(object.getHeader()));
        theDto.setIsViewed(object.getViewed());
        theDto.setDate(DateUtils.getFormattedDate(object.getDate().getTime()));
        theDto.setCategory(notificationUtils.getCategoryAsString(object.getEntityType()));
        theDto.setHeader(resourceBundleMessageSource
                .getMessage(object.getHeader(), notificationUtils.getParamsFromString(object.getHeaderParams()), locale));
        theDto.setText(resourceBundleMessageSource
                .getMessage(object.getText(), notificationUtils.getParamsFromString(object.getTextParams()), locale));
        return theDto;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
