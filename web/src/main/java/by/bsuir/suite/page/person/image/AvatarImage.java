package by.bsuir.suite.page.person.image;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.model.IModel;

/**
 * @author i.sukach
 */
public class AvatarImage extends WebComponent{
    public AvatarImage(String id, IModel<?> model) {
        super(id, model);
    }
    
    protected void onComponentTag(ComponentTag tag){
        super.onComponentTag(tag);
        checkComponentTag(tag, "img");
        tag.put("src", getDefaultModelObjectAsString());
    }
}
