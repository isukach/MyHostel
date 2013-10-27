package by.bsuir.suite.page.help.panel;

import by.bsuir.suite.page.help.Help;
import by.bsuir.suite.session.HostelAuthenticatedWebSession;
import by.bsuir.suite.util.UploadUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.InlineFrame;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.ByteArrayResource;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * User: CHEB
 */
public class HelpPanel extends Panel {

    public HelpPanel(String id) {
        super(id);
        WebMarkupContainer wmc = null;

        try
        {
            String[] roles = ((HostelAuthenticatedWebSession) getSession()).getRoles().toArray(new String[((HostelAuthenticatedWebSession) getSession()).getRoles().size()]);
            final String currentUserRole = roles[0];
            String filePath = UploadUtils.getHeplFilePath(getApplication(), currentUserRole);
            File filePdf;
            FileInputStream fin;
            final byte fileContent[];
            try
            {
                filePdf = new File(filePath);
                fin = new FileInputStream(filePdf);
            } catch (FileNotFoundException e) {
                filePath = UploadUtils.getHeplFilePath(getApplication(), UploadUtils.DEFUALT_HELP_NAME);
                filePdf = new File(filePath);
                fin = new FileInputStream(filePdf);
            }
            fileContent = new byte[(int)filePdf.length()];
            fin.read(fileContent);
            fin.close();
            final ResourceReference pdfResource = new ResourceReference(filePath) {
                private static final long serialVersionUID = 1L;

                @Override
                public IResource getResource() {
                    return new ByteArrayResource("application/pdf", fileContent);
                }
            };

            if (pdfResource.canBeRegistered()) {
                getApplication().getResourceReferenceRegistry().registerResourceReference(pdfResource);
            }

            final String relativeFilePath = UploadUtils.getHelpFileRelativePath(filePath);

            wmc = new InlineFrame("helppdf", Help.class) {
                @Override
                protected CharSequence getURL() {
                    return relativeFilePath;
                }
            };

        } catch (IOException e) {
            e.printStackTrace();
        }

        add(wmc);
    }
}
