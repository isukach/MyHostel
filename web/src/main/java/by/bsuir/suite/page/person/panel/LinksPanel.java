package by.bsuir.suite.page.person.panel;

import by.bsuir.suite.dto.person.PersonDto;
import by.bsuir.suite.page.person.image.AvatarImage;
import by.bsuir.suite.util.Link;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static by.bsuir.suite.util.LinkUtils.generateHrefForLinkName;
import static by.bsuir.suite.util.LinkUtils.getImagePath;
import static by.bsuir.suite.util.StringUtils.notNullNotEmpty;

/**
 * @author i.sukach
 */
public class LinksPanel extends Panel {
    private List<LinkHolder> list = new ArrayList<LinkHolder>();

    public LinksPanel(String id, PersonDto personDto) {
        super(id);

        addLinksToRender(personDto);

        ListView<LinkHolder> linkList = new ListView<LinkHolder>("listview", list) {
            protected void populateItem(ListItem<LinkHolder> item) {
                String link = item.getModelObject().getLink();
                Link linkName = item.getModelObject().getLinkName();
                String href = generateHrefForLinkName(linkName, link);

                ExternalLink externalLink = new ExternalLink("link", href);
                AvatarImage image = new AvatarImage("image",
                        new Model<String>(getImagePath(linkName)));
                externalLink.add(image);
                item.add(externalLink);
            }
        };
        add(linkList);
    }

    private void addLinksToRender(PersonDto personDto) {
        addLinkToRender(Link.VK, personDto.getVkLink());
        addLinkToRender(Link.DEVIANT_ART, personDto.getDevartLink());
        addLinkToRender(Link.FACEBOOK, personDto.getFacebookLink());
        addLinkToRender(Link.GOOGLE, personDto.getGoogleLink());
        addLinkToRender(Link.LAST_FM, personDto.getLastfmLink());
        addLinkToRender(Link.SKYPE, personDto.getSkypeLink());
        addLinkToRender(Link.TWITTER, personDto.getTwitterLink());
        addLinkToRender(Link.VIMEO, personDto.getVimeoLink());
        addLinkToRender(Link.YOUTUBE, personDto.getYoutubeLink());
    }

    private void addLinkToRender(Link linkName, String link) {
        if (notNullNotEmpty(link)) {
            list.add(new LinkHolder(linkName, link));
        }
    }

    public boolean isEmptyPanel() {
        return list.size() == 0;
    }

    private static final class LinkHolder implements Serializable {
        private String link;
        private Link linkName;

        private LinkHolder(Link linkName, String link) {
            this.linkName = linkName;
            this.link = link;
        }

        public Link getLinkName() {
            return linkName;
        }

        public String getLink() {
            return link;
        }
    }
}
