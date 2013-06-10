package by.bsuir.suite.page.person.panel;

import by.bsuir.suite.page.person.FileUploadWindow;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.ResourceModel;

import java.io.File;

/**
 * @author i.sukach
 */
public class FileUploadPanel extends Panel {
    private String path;
    private String login;
    private FileUploadField fileUpload = new FileUploadField("fileUpload");
    
    public FileUploadPanel(String id, String path, String login) {
        super(id);
        
        this.path = path;
        this.login = login;
        
        Form form = new Form("uploadForm");
        form.setMultiPart(true);
        add(form);

        FeedbackPanel feedBack = new FeedbackPanel("feedback");
        form.add(feedBack);
        form.add(fileUpload);
        
        form.add(new AjaxButton("submit", new ResourceModel("upload"), form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                FileUpload uploadedFile = fileUpload.getFileUpload();
                if (uploadedFile != null) {
                    File newFile = new File(FileUploadPanel.this.path + "/" + FileUploadPanel.this.login +
                            uploadedFile.getClientFileName().substring(uploadedFile.getClientFileName().lastIndexOf(".")));

                    if (newFile.exists()) {
                        newFile.delete();
                    }
                    try {
                        newFile.createNewFile();
                        uploadedFile.writeTo(newFile);
                    } catch (Exception e) {
                        throw new IllegalStateException("Error uploading file");
                    }
                }
                ((FileUploadWindow) FileUploadPanel.this.getParent()).close(target);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                error("Error uploading file");
            }
        });

        form.add(new AjaxButton("cancel", new ResourceModel("cancel"), form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                ((FileUploadWindow) FileUploadPanel.this.getParent()).close(target);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                error("Error closing window");
            }
        });
    }
}
