package by.bsuir.suite.page.person.panel;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.ContextRelativeResource;

import java.util.HashSet;
import java.util.Set;

/**
 * @author i.sukach
 */
public class LinksPanelEdit extends Panel {

    private static final Set<String> LINKS = new HashSet<String>();
    private static final String IMAGE_FOLDER = "images/social/";

    static {
        LINKS.add("fb");
        LINKS.add("twitter");
        LINKS.add("vk");
        LINKS.add("skype");
        LINKS.add("deviant_art");
        LINKS.add("vimeo");
        LINKS.add("google");
        LINKS.add("youtube");
        LINKS.add("lastfm");
    }

    public LinksPanelEdit(String id) {
        super(id);
        
        add(new TextField<String>("facebookLink"));
        add(new TextField<String>("twitterLink"));
        add(new TextField<String>("vkLink"));
        add(new TextField<String>("skypeLink"));
        add(new TextField<String>("devartLink"));
        add(new TextField<String>("vimeoLink"));
        add(new TextField<String>("googleLink"));
        add(new TextField<String>("youtubeLink"));
        add(new TextField<String>("lastfmLink"));

        for (String link : LINKS) {
            add(new Image(link, new ContextRelativeResource(IMAGE_FOLDER + link + ".png")));
        }
    }
}
