package by.bsuir.suite.page.person.panel;

import by.bsuir.suite.page.person.image.AvatarImage;
import by.bsuir.suite.util.UploadUtils;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import java.io.Serializable;

/**
 * @author i.sukach
 */
public class AvatarPanelEdit extends Panel {
    private FileUploadField fileUpload;
    
    public AvatarPanelEdit(String id, String fileName) {
        super(id);
        String imagePath = UploadUtils.generateFilePathForImage(getApplication(), fileName);
        AvatarImage image = new AvatarImage("avatar", new Model<Serializable>(imagePath));
        image.setOutputMarkupId(true);
        add(image);

        fileUpload = new FileUploadField("fileUpload", new Model());
        add(fileUpload);
    }

    public FileUploadField getFileUpload() {
        return fileUpload;
    }
}
