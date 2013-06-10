package by.bsuir.suite.page.news;

import by.bsuir.suite.colors.ColorConstants;
import by.bsuir.suite.dto.news.NewsDto;
import by.bsuir.suite.page.base.BasePage;
import by.bsuir.suite.page.base.NotificationWindow;
import by.bsuir.suite.page.news.components.EndlessListView;
import by.bsuir.suite.service.news.NewsService;
import by.bsuir.suite.session.HostelAuthenticatedWebSession;
import by.bsuir.suite.session.SessionUser;
import by.bsuir.suite.util.NewsInfo;
import by.bsuir.suite.util.Roles;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author a.garelik
 * @author i.sukach
 */
@AuthorizeInstantiation(value = {Roles.ADMIN, Roles.FLOOR_HEAD, Roles.EDUCATOR, Roles.MANAGERESS, Roles.COMMANDANT,
        Roles.SUPER_USER, Roles.YOUTH_CENTER, Roles.REGISTRAR})
public class NewsPage extends BasePage {

    private static final int DISPLAYED_NEWS_BODY_WORDS = 20;
    private static final int DISPLAYED_NEWS_CAPTION_LENGTH = 20;
    public static final String DATE_FORMAT = "yyyy.MM.dd HH:mm ";
    private static final int MIN_CAPTION = 6;
    private static final int MAX_CAPTION = 100;
    private static final int MIN_TEXT = 20;
    private static final int MAX_TEXT = (int) (Math.pow(2, 16)-10); //length of MySql type (TEXT)

    @SpringBean
    @SuppressWarnings({"UnusedDeclaration"})
    private NewsService newsService;

    private TextField<String> editedItemCaption;
    private TextArea<String> editedItemText;
    private NewsSettingsPanel settingsPanel;
    private EndlessListView<NewsDto> newsList;

    private UnsavedNewsNotificationWindow unsavedNewsNotificationWindow;
    private NotificationWindow removeNewsNotificationWindow;
    private NewsPageSaveDialog saveNewsNotificationWindow;
    private ModalWindow invalidCaptionData;
    private ModalWindow invalidContentData;

    private NewsDto currentSelectedDto = null;

    private String text="";
    private String caption="";

    public NewsPage() {
        super(ColorConstants.BLUE);
        Form<Void> form = new Form<Void>("form");

        //settings panel
        settingsPanel = createSettingsPanel();
        settingsPanel.setOutputMarkupId(true);
        form.add(settingsPanel);

        //notification dialogs
        unsavedNewsNotificationWindow = new UnsavedNewsNotificationWindow();
        add(unsavedNewsNotificationWindow);
        saveNewsNotificationWindow = createSaveDialog();
        add(saveNewsNotificationWindow);
        removeNewsNotificationWindow = createRemoveDialog();
        add(removeNewsNotificationWindow);
        invalidCaptionData = createNotificationDialog("captionErrorDialog", "newsPage.message.caption_length");
        add(invalidCaptionData);
        invalidContentData = createNotificationDialog("contentErrorDialog", "newsPage.message.text_length");
        add(invalidContentData);

        //text fields
        PropertyModel<String> textPropertyModel = new PropertyModel<String>(this, "text");
        PropertyModel<String> captionPropertyModel = new PropertyModel<String>(this, "caption");
        editedItemCaption = new TextField<String>("editedItemCaption", captionPropertyModel);
        editedItemText = new TextArea<String>("editedItemText", textPropertyModel);
        editedItemText.add(new OnChangeAjaxBehavior(){
            @Override
            protected void onUpdate(AjaxRequestTarget target) { }
        });
        editedItemCaption.add(new OnChangeAjaxBehavior(){
            @Override
            protected void onUpdate(AjaxRequestTarget target) {}
        });
        editedItemCaption.setOutputMarkupId(true);
        editedItemText.setOutputMarkupId(true);
        form.add(editedItemText);
        form.add(editedItemCaption);

        //news list
        newsList = new EndlessListView<NewsDto>("newsContainer") {
            @Override
            protected void populateListItem(ListItem<NewsDto> components) {
                populateNewsItem(components);
            }
            @Override
            protected List<NewsDto> loadItems(int offset, int limit) {
                return newsService.getNews(offset,limit, getCurrentPersonId());
            }
        };

        AjaxButton createNewButton = new AjaxButton("addNewArticleButton") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                 addNewArticle(target);
            }
            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
            }
        };
        form.add(newsList);
        form.add(createNewButton);
        add(form);
        setEnabledAll(false);
    }

    private void addNewArticle(AjaxRequestTarget target){
        if(!settingsPanel.isEnabled()){
            setEnabledAll(true);
        }
        if(text != null && caption != null){
            currentSelectedDto = new NewsDto();
            settingsPanel.setFilter(currentSelectedDto.getFilter());
            if(isModified()){
                unsavedNewsNotificationWindow.setDto(currentSelectedDto);
                unsavedNewsNotificationWindow.show(target);
            }
            else {
                clearAll();
            }
        }
        updateAll(target);
    }

    private void populateNewsItem(ListItem<NewsDto> components){
        final NewsDto item = components.getModelObject();
        AjaxFallbackLink<Void> newsButton = new AjaxFallbackLink<Void>("itemButton") {
            private NewsDto dto = item;
            @Override
            public void onClick(AjaxRequestTarget target) {
                if(!settingsPanel.isEnabled()){
                    setEnabledAll(true);
                }
                if(isModified()){
                    unsavedNewsNotificationWindow.setDto(dto);
                    unsavedNewsNotificationWindow.show(target);
                }
                else {
                    text = dto.getText();
                    caption = dto.getCaption();
                    settingsPanel.setFilter(dto.getFilter());
                    currentSelectedDto = dto;
                    updateAll(target);
                }
            }
        };
        if(DISPLAYED_NEWS_CAPTION_LENGTH < item.getCaption().length()){
            String displayedCaption = item.getCaption().substring(0, DISPLAYED_NEWS_CAPTION_LENGTH)+"...";
            newsButton.add(new Label("newsCaption", displayedCaption));
        }
        else {
            newsButton.add(new Label("newsCaption", item.getCaption()));
        }

        newsButton.add(new Label("newsBody", NewsInfo.getWordsInTextByCount(item.getText(), DISPLAYED_NEWS_BODY_WORDS)));
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        newsButton.add(new Label("newsTimestamp", dateFormat.format(item.getTimestamp())));

        WebComponent indicator = new WebComponent("newsIndicator");
        String resKey = NewsInfo.getCategoryHashMap().get(item.getCategory());
        indicator.add(new AttributeModifier("class", resKey){
            protected String newValue(final String currentValue, final String replacementValue){
                return currentValue +" "+ replacementValue+"-indicator";
            }
        });
        newsButton.add(indicator);
        components.add(newsButton);

    }

    private void setEnabledAll(boolean status){
        editedItemText.setEnabled(status);
        editedItemCaption.setEnabled(status);
        settingsPanel.setEnabled(status);
    }

    public final void updateAll(AjaxRequestTarget target){
        target.add(editedItemText);
        target.add(editedItemCaption);
        target.add(settingsPanel);
    }

    public final void clearAll(){
        text = "";
        caption = "";
        settingsPanel.clear();
    }

    public boolean isModified(){
        if (text == null && caption == null) {
            return false;
        }
        return !(text.isEmpty() && caption.isEmpty()) &&
                !(text.equals(currentSelectedDto.getText()) &&
                        caption.equals(currentSelectedDto.getCaption())
                        && !settingsPanel.isModified());
    }

    private NewsSettingsPanel createSettingsPanel(){
        return new NewsSettingsPanel("newsSettingsPanel") {
            @Override
            protected void onSave(AjaxRequestTarget target){
                if (text != null && text.length() > MIN_TEXT && text.length() < MAX_TEXT)
                    if (caption != null && caption.length() > MIN_CAPTION && caption.length() < MAX_CAPTION) {
                        saveNewsNotificationWindow.show(target);
                    }
                    else {
                        invalidCaptionData.show(target);
                    }
                else {
                    invalidContentData.show(target);
                }
            }
            @Override
            protected void onClear(AjaxRequestTarget target){
                if(currentSelectedDto.getId() == null) {
                    clearAll();
                }
                else {
                    removeNewsNotificationWindow.show(target);
                }
                setEnabledAll(false);
                updateAll(target);
            }
        };
    }

    private NewsPageSaveDialog createSaveDialog(){

        return new NewsPageSaveDialog("saveDialog", "newsPage.warning", "newsPage.message.save"){
            @Override
            public void onCancel(AjaxRequestTarget target) {
            }

            @Override
            public void onConfirm(AjaxRequestTarget target) {
                save(false);
                target.add(newsList);
                newsList.reset(target);
                updateAll(target);
            }

            @Override
            public void onConfirmAndUpdate(AjaxRequestTarget target) {
                save(true);
                target.add(newsList);
                newsList.reset(target);
                updateAll(target);
            }

            @Override
            public boolean isConfirmAndUpdateVisible() {
                return currentSelectedDto.getTimestamp() != null;
            }

            public final void save(boolean updateTime){
                currentSelectedDto.setText(text);
                currentSelectedDto.setCaption(caption);
                SessionUser currentUser = ((HostelAuthenticatedWebSession) getSession()).getUser();
                currentSelectedDto.setUserName(currentUser.getUserName());
                if (updateTime || currentSelectedDto.getTimestamp() == null) {
                    currentSelectedDto.setTimestamp(new Timestamp(new Date().getTime()));
                }
                currentSelectedDto.setFilter(settingsPanel.getFilter());
                newsService.saveNews(currentSelectedDto);
                currentSelectedDto = null;
                clearAll();
                setEnabledAll(false);
            }
        };
    }

    private NotificationWindow createNotificationDialog(String id, String resKey){
        return new NotificationWindow(id,"newsPage.error", resKey , false){
            @Override
            public void onCancel(AjaxRequestTarget target) {
                close(target);
            }
            @Override
            public void onConfirm(AjaxRequestTarget target) {
                close(target);
            }
        };
    }

    private NotificationWindow createRemoveDialog(){
        return new NotificationWindow("removeDialog","newsPage.warning", "newsPage.message.remove", true) {
            @Override
            public void onCancel(AjaxRequestTarget target) {
                close(target);
            }
            @Override
            public void onConfirm(AjaxRequestTarget target) {
                if(currentSelectedDto.getId()!= null){
                    newsService.removeNews(currentSelectedDto);
                    clearAll();
                    newsList.reset(target);
                    setEnabledAll(false);
                    updateAll(target);
                }
                close(target);
            }
        };
    }


    private class UnsavedNewsNotificationWindow extends NotificationWindow{

        private NewsDto dto;
        public UnsavedNewsNotificationWindow() {
            super("unsavedDialog", "newsPage.warning", "newsPage.unsaved", true);
        }

        void setDto(NewsDto dto){
            this.dto = dto;
        }

        @Override
        public void onCancel(AjaxRequestTarget target) {
            close(target);
        }

        @Override
        public void onConfirm(AjaxRequestTarget target) {

            if(dto != null){
                settingsPanel.setFilter(dto.getFilter());
                caption = dto.getCaption();
                text = dto.getText();
                setEnabledAll(true);
                updateAll(target);
                currentSelectedDto = dto;
                dto = null;
            }
            else {
                clearAll();
            }
            close(target);
        }

    }
}
