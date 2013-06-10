package by.bsuir.suite.page.person.panel;

import by.bsuir.suite.domain.person.PenaltyOrRewardEnum;
import by.bsuir.suite.dto.person.PenaltyRewardDto;
import by.bsuir.suite.page.news.NewsPage;
import by.bsuir.suite.page.person.option.SelectOption;
import by.bsuir.suite.service.person.PenaltyRewardService;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * User: a.garelik
 * Date: 16/03/13
 * Time: 13:15
 */
public class PenaltyRewardPanel extends Panel {

    public enum Type {
        PENALTY,
        REWARD
    }

    public static final String CATEGORY_PREFIX = "label.category.";
    public static final String LABEL_SUFFIX = "_label";
    public static final String CATEGORY_ID = "category";
    public static final String ORDER_NUMBER_ID = "orderNumber";
    public static final String COMMENT_ID = "comment";
    public static final String DATE_ID = "date";
    public static final String REMOVE_BTN_ID = "remove";
    public static final String SAVE_BTN_ID = "save";
    public static final String CONTAINER_ID = "parentContainer";

    private WebMarkupContainer list;
    private WebMarkupContainer parentContainer;
    private WebMarkupContainer widgetContainer;
    private boolean isReadOnly = true;
    private Long personId;
    private Type type;

    @SpringBean
    private PenaltyRewardService service;

    public PenaltyRewardPanel(String id, Long personId, boolean isReadOnly, Type panelType) {
        super(id);
        this.isReadOnly = isReadOnly;
        this.personId = personId;
        this.type = panelType;

        Label panelLabel = null;
        if(type != Type.REWARD)
            panelLabel = new Label("panel_label", new StringResourceModel("label.category.penalty", this, null));
        else
            panelLabel = new Label("panel_label", new StringResourceModel("label.category.reward", this, null));
        add(panelLabel);
        parentContainer = new WebMarkupContainer(CONTAINER_ID);
        WebMarkupContainer typeContainer = new WebMarkupContainer("type_head"+LABEL_SUFFIX);
        parentContainer.add(typeContainer);
        if(type != Type.REWARD)
            typeContainer.setVisibilityAllowed(false);
        parentContainer.setOutputMarkupId(true);
        parentContainer.setOutputMarkupPlaceholderTag(true);

        add(parentContainer);

        AjaxFallbackLink<String> addButton = new AjaxFallbackLink<String>("add") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                widgetContainer.setVisible(true);
                target.add(parentContainer);
            }
        };
        addButton.setVisibilityAllowed(isReadOnly);
        list = new ListView<PenaltyRewardDto>("list") {
            @Override
            protected void populateItem(ListItem<PenaltyRewardDto> item) {
                populatePenaltyRewardItem(item);
            }
        };
        widgetContainer = new WebMarkupContainer("widgetContainer");
        widgetContainer.setOutputMarkupId(true);
        widgetContainer.setVisible(false);
        widgetContainer.setVisibilityAllowed(isReadOnly);
        parentContainer.add(list);
        parentContainer.add(widgetContainer);
        updateRecords();
        add(addButton);
        createElements(widgetContainer);
    }
    private DropDownChoice createDropdown(String id){
        List<SelectOption> categories = new ArrayList<SelectOption>(2);
        String value = new StringResourceModel(CATEGORY_PREFIX+"penalty", this, null).getString();
        categories.add(new SelectOption(CATEGORY_PREFIX+"penalty",value));
        value = new StringResourceModel(CATEGORY_PREFIX+"reward", this, null).getString();
        categories.add(new SelectOption(CATEGORY_PREFIX+"reward",value));
        ChoiceRenderer<SelectOption> choiceRenderer = new ChoiceRenderer<SelectOption>("value", "key");
        DropDownChoice categoryChoice = new DropDownChoice(id, new Model(), categories, choiceRenderer);
        categoryChoice.setModelObject(categories.get(0));
        return categoryChoice;
    }

    private WebMarkupContainer createElements(WebMarkupContainer parent){
        WebMarkupContainer category = createDropdown(CATEGORY_ID);
        WebMarkupContainer typeContainer = new WebMarkupContainer("type_container");
        typeContainer.add(category);
        if(type != Type.REWARD)
            typeContainer.setVisibilityAllowed(false);
        parent.add(typeContainer);
        WebMarkupContainer order = new TextField<String>(ORDER_NUMBER_ID, new Model<String>());
        parent.add(order);
        WebMarkupContainer date = new TextField<String>(DATE_ID, new Model<String>());
        parent.add(date);
        WebMarkupContainer comment = new TextField<String>(COMMENT_ID, new Model<String>());
        parent.add(comment);
        WebMarkupContainer save =  new AjaxFallbackLink<String>(SAVE_BTN_ID, new Model<String>()){
            @Override
            public void onClick(AjaxRequestTarget target) {
                save(target);
            }
        };
        parent.add(save);
        return parent;
    }

    public void updateRecords(){
        ((ListView<PenaltyRewardDto>) parentContainer.get("list")).setList(service.getAll());
    }
    private void populatePenaltyRewardItem(ListItem<PenaltyRewardDto> item){
        final PenaltyRewardDto dto = item.getModelObject();
        Label wgt = new Label(CATEGORY_ID+LABEL_SUFFIX, new StringResourceModel(CATEGORY_PREFIX+dto.getType().name().toLowerCase(), this, null));
        WebMarkupContainer typeContainer = new WebMarkupContainer("type_container"+LABEL_SUFFIX);
        typeContainer.add(wgt);
        if(type != Type.REWARD)
            typeContainer.setVisibilityAllowed(false);
        item.add(typeContainer);
        wgt = new Label(ORDER_NUMBER_ID+LABEL_SUFFIX, dto.getOrderNumber());
        item.add(wgt);
        wgt = new Label(DATE_ID+LABEL_SUFFIX, dto.getDate().toString());
        item.add(wgt);
        wgt = new Label(COMMENT_ID+LABEL_SUFFIX, dto.getComment());
        item.add(wgt);
        WebMarkupContainer remove =  new AjaxFallbackLink<String>(REMOVE_BTN_ID, new Model<String>(String.valueOf(dto.getId()))){
            @Override
            public void onClick(AjaxRequestTarget target) {
                removeItem(target, dto.getId());
            }
        };
        remove.setVisibilityAllowed(isReadOnly);
        item.add(remove);
    }

    public void removeItem(AjaxRequestTarget target, Long entityId){
        service.remove(entityId);
        updateRecords();
        target.add(parentContainer);
    }

    public void save(AjaxRequestTarget target){
        widgetContainer.setVisible(false);

        String comment = ((TextField<String>) widgetContainer.get(COMMENT_ID)).getValue();
        String orderNumber = ((TextField<String>) widgetContainer.get(ORDER_NUMBER_ID)).getValue();
        PenaltyOrRewardEnum type;
        if(this.type != Type.REWARD){
             type = PenaltyOrRewardEnum.REWARD;
        }
        else {
            Object modelObj = ((DropDownChoice) widgetContainer.get(CATEGORY_ID)).getModelObject();
            String enumKey = ((SelectOption)modelObj).getKey();
            enumKey = enumKey.substring(enumKey.lastIndexOf('.')+1);
            type = PenaltyOrRewardEnum.valueOf(enumKey.toUpperCase());
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(NewsPage.DATE_FORMAT);
        Date date = null;
        try {
            date = new Date(dateFormat.parse(((TextField<String>) widgetContainer.get(COMMENT_ID)).getValue()).getTime());
        } catch (ParseException e) {
            date = new Date(Long.valueOf("0"));
        }
        PenaltyRewardDto dto = new PenaltyRewardDto();
        dto.setComment(comment);
        dto.setType(type);
        dto.setOrderNumber(orderNumber);
        dto.setDate(date);
        dto.setPersonId(personId);

        service.save(dto);
        updateRecords();
        target.add(parentContainer);
    }

    public boolean isReadOnly() {
        return isReadOnly;
    }

    public void setReadOnly(boolean readOnly) {
        isReadOnly = readOnly;
    }
}
