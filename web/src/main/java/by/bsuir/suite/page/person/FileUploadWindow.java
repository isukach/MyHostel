package by.bsuir.suite.page.person;

import by.bsuir.suite.page.base.NonContentWindow;
import by.bsuir.suite.page.person.panel.FileUploadPanel;

/**
 * @author i.sukach
 */
public class FileUploadWindow extends NonContentWindow{
    public FileUploadWindow(String id, String path, String login) {
        super(id);
        
        setContent(new FileUploadPanel("content", path, login));
    }
}
