package by.bsuir.suite.page.news;



import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.ResourceReference;


/**
 *@author a.garelik
 */

abstract class  NewsPageSavePanel extends Panel{

    public NewsPageSavePanel(String id, String headerKey, String contentKey) {
        super(id);

        add(new Label("header", new StringResourceModel(headerKey,  this, null)));
        add(new Label("content", new StringResourceModel(contentKey, this, null)));

        add(new AjaxFallbackLink<Void>("cancel") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                onCancel(target);
            }
        });

        add(new AjaxFallbackLink<Void>("ok") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                onConfirm(target);
            }
        });

        add(new AjaxFallbackLink<Void>("okAndUpdate") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                onConfirmAndUpdate(target);
            }

            @Override
            public boolean isVisible() {
                return isConfirmAndUpdateVisible();
            }
        });

    }

    public abstract void onCancel(AjaxRequestTarget target);

    public abstract void onConfirm(AjaxRequestTarget target);

    public abstract void onConfirmAndUpdate(AjaxRequestTarget target);

    public abstract boolean isConfirmAndUpdateVisible();
}

public abstract class NewsPageSaveDialog extends ModalWindow {



    public NewsPageSaveDialog(String id, String headerKey, String contentKey) {
        super(id);
        setTitle("");
        setCssClassName("modal.css");

        setContent(new NewsPageSavePanel(this.getContentId(), headerKey, contentKey) {
            @Override
            public void onCancel(AjaxRequestTarget target) {
                NewsPageSaveDialog.this.onCancel(target);
                close(target);
            }

            @Override
            public void onConfirm(AjaxRequestTarget target) {
                NewsPageSaveDialog.this.onConfirm(target);
                close(target);
            }

            @Override
            public void onConfirmAndUpdate(AjaxRequestTarget target) {
                NewsPageSaveDialog.this.onConfirmAndUpdate(target);
                close(target);
            }

            @Override
            public boolean isConfirmAndUpdateVisible() {
                return NewsPageSaveDialog.this.isConfirmAndUpdateVisible();
            }
        });
    }

    @Override
    protected ResourceReference newCssResource() {
        return new CssResourceReference(NewsPageSaveDialog.class, "styles/modal.css");
    }


    public abstract void onCancel(AjaxRequestTarget target);

    public abstract void onConfirm(AjaxRequestTarget target);

    public abstract void onConfirmAndUpdate(AjaxRequestTarget target);

    public abstract boolean isConfirmAndUpdateVisible();
}


