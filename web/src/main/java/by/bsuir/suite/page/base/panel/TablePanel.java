package by.bsuir.suite.page.base.panel;

import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * User: User
 * Date: 15/12/12
 */
public class TablePanel<T> extends Panel {

    private AjaxFallbackDefaultDataTable<T> table;

    public TablePanel(String name, AjaxFallbackDefaultDataTable<T> table){
        super(name);
        this.table = table;
        this.add(table);
    }

    public void refreshTable(AjaxFallbackDefaultDataTable<T> table){
        this.remove(this.table);
        this.table = table;
        this.add(table);
    }
}
