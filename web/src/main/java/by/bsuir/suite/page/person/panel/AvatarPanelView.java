package by.bsuir.suite.page.person.panel;

import by.bsuir.suite.page.person.image.AvatarImage;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import java.io.Serializable;

import static by.bsuir.suite.util.UploadUtils.generateFilePathForImage;

/**
 * @author i.sukach
 */
public class AvatarPanelView extends Panel {
    public AvatarPanelView(String id, String fileName) {
        super(id);
        String filePath = generateFilePathForImage(getApplication(), fileName);
        AvatarImage image = new AvatarImage("avatar", new Model<Serializable>(filePath));
        add(image);
    }
}
