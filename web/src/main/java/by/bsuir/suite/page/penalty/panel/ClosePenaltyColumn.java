package by.bsuir.suite.page.penalty.panel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

/**
 * User: CHEB
 */
public abstract class ClosePenaltyColumn<T> extends AbstractColumn<T> {

    public ClosePenaltyColumn(IModel<String> displayModel) {
        super(displayModel);
    }

    public void populateItem(Item<ICellPopulator<T>> cellItem, String componentId, IModel<T> rowModel) {
        cellItem.add(new ButtonPanel(componentId, rowModel));
    }

    protected abstract void onClick(IModel<T> clicked, AjaxRequestTarget ajaxRequestTarget);

    private class ButtonPanel extends Panel {
        public ButtonPanel(String id, IModel<T> rowModel) {
            super(id);
            Link<T> link = new AjaxFallbackLink<T>("closePenalytButton", rowModel) {
                @Override
                public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                    ClosePenaltyColumn.this.onClick(getModel(), ajaxRequestTarget);
                }
            };
            add(link);
        }
    }


}
