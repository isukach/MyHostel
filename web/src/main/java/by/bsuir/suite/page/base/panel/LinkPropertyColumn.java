package by.bsuir.suite.page.base.panel;

import by.bsuir.suite.dto.common.Dto;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

/**
 * @author d.matveenko
 */
public abstract class LinkPropertyColumn<T extends Dto> extends PropertyColumn<T> {

    private IModel<T> labelModel;

    public LinkPropertyColumn(IModel<String> displayModel, String propertyExpression) {
        super(displayModel, propertyExpression);
    }

    public abstract void onClick(Item item, String componentId, IModel<T> model);

    @Override
    public void populateItem(Item item, String componentId, IModel model) {
        item.add(new LinkPanel(item, componentId, model));
    }

    public class LinkPanel extends Panel {
        public LinkPanel(final Item item, final String componentId, final IModel<T> model) {
            super(componentId);
            setOutputMarkupId(true);
            Link link = new Link("link") {
                public void onClick() {
                    LinkPropertyColumn.this.onClick(item, componentId, model);
                }
            };

            add(link);

            IModel tmpLabelModel = labelModel;

            if (labelModel == null) {
                tmpLabelModel = createLabelModel(model);
            }

            link.add(new Label("label", tmpLabelModel));
        }
    }
}
