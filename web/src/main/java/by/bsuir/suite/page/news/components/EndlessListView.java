package by.bsuir.suite.page.news.components;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * User: a.garelik
 * Date: 22/01/13
 * Time: 16:32
 */
public abstract class EndlessListView<T> extends WebMarkupContainer{

    public static final int LIMIT = 5;

    private int offset = 0;
    private boolean lockMode = false;

    private AbstractDefaultAjaxBehavior requestHandler;
    private ListView<T> list;
    private List<T> itemsList = new ArrayList<T>(LIMIT+1);


    public EndlessListView(String id) {
        super(id);

        setOutputMarkupId(true);
        add(new Behavior() {
            @Override
            public void renderHead(Component component, IHeaderResponse response) {
                super.renderHead(component, response);
                response.renderJavaScriptReference("scripts/EndlessList.js");
            }
        });

        requestHandler = new AbstractDefaultAjaxBehavior() {
            protected void respond(final AjaxRequestTarget target) {
                if(!lockMode){
                    List<T> items = loadItems(offset, LIMIT);
                    if(items.size() < LIMIT) {
                        lockMode = true;
                    }
                    itemsList.addAll(items);
                    offset += LIMIT;
                    target.add(EndlessListView.this);
                }

            }
        };
        add(requestHandler);

        itemsList = loadItems(offset, LIMIT);
        if(itemsList.size() < LIMIT) {
            lockMode = true;
        }
        offset += LIMIT;

        list = new ListView<T>("list", itemsList) {
            @Override
            protected void populateItem(ListItem<T> components) {
                populateListItem(components);
            }
        };
        add(list);

    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);

        String id = tag.getAttribute("id");
        if(id.isEmpty()){
            id = getId();
            tag.put("id", id);
        }
        StringBuilder script = new StringBuilder("scroll(\"");
        script.append(requestHandler.getCallbackUrl()).append("\",\"");
        script.append(id).append("\");");
        tag.put("onscroll", script.toString());
    }

    public void reset(AjaxRequestTarget target){
        offset = 0;
        itemsList.clear();

        itemsList = loadItems(offset, LIMIT);
        if(itemsList.size() < LIMIT) {
            lockMode = true;
        }
        offset += LIMIT;

        list.setList(itemsList);
        if(target != null) {
            target.add(this);
        }
    }

    protected abstract void populateListItem(ListItem<T> components);

    protected abstract List<T> loadItems(int offset, int limit);
}
