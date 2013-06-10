package by.bsuir.suite.page.news;

import by.bsuir.suite.domain.news.NewsCategory;
import by.bsuir.suite.page.person.option.SelectOption;
import by.bsuir.suite.service.news.Filter;
import by.bsuir.suite.util.NewsInfo;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;

import java.util.ArrayList;
import java.util.List;

/**
 *@author a.garelik
 *
 */
public abstract class NewsSettingsPanel extends Panel {

    private Filter filter = new Filter();

    private DropDownChoice categoryChoice;
    private SelectOption defaultCategory = null;
    private FormComponent controlGroup;

    private List<CheckBox> courseChoice;
    private List<CheckBox> floorChoice;

    public NewsSettingsPanel(String id) {
        super(id);
        
        controlGroup = new FormComponent("controlGroup", new Model()) {};
        controlGroup.add(new OnChangeAjaxBehavior() {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
               target.add(controlGroup);
            }
        } );

        createDropdown();
        createCourseChoice();
        createFloorChoice();

        AjaxFallbackLink<String> saveButton = new AjaxFallbackLink<String>("saveButton") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                onSave(target);
            }
        };

        AjaxFallbackLink<String> clearButton = new AjaxFallbackLink<String>("clearButton") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                onClear(target);
            }
        };

        add(saveButton);
        add(clearButton);
        add(controlGroup);
    }

    protected abstract void onSave(AjaxRequestTarget target);
    protected abstract void onClear(AjaxRequestTarget target);

    private void createDropdown(){
        List<SelectOption> categories = new ArrayList<SelectOption>(6);
        for (String key: NewsInfo.getCategoriesKeys()){
            String value = new StringResourceModel(NewsInfo.CATEGORY_RESOURCE_KEY+key, this, null).getString();
            SelectOption selectOption = new SelectOption(key, value);
            if (key.equalsIgnoreCase(NewsInfo.DEFAULT_CATEGORY)) {
                defaultCategory = selectOption;
            }
            categories.add(selectOption);
        }
        ChoiceRenderer<SelectOption> choiceRenderer = new ChoiceRenderer<SelectOption>("value", "key");
        categoryChoice = new DropDownChoice("categories", new Model(), categories, choiceRenderer);
        controlGroup.add(categoryChoice);
        categoryChoice.setModelObject(defaultCategory);
        categoryChoice.add(new OnChangeAjaxBehavior(){
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                target.add(categoryChoice);
            }
        });
    }

    private void createCourseChoice(){
        courseChoice = new ArrayList<CheckBox>(5);
        for (int i=1; i<6; i++){
            final CheckBox checkBox = new CheckBox("checkbox"+i, Model.of(Boolean.TRUE));
            checkBox.setOutputMarkupId(true);
            checkBox.add(new OnChangeAjaxBehavior() {
                @Override
                protected void onUpdate(AjaxRequestTarget target) {
                    int selectedCheckboxesCount = 0;
                    for (CheckBox cBox : courseChoice) {
                        if(cBox.getModelObject()) {
                            selectedCheckboxesCount++;
                        }
                    }
                    if(selectedCheckboxesCount == 0) {
                        checkBox.setModelObject(true);
                    }
                    target.add(checkBox);
                }
            });
            FormComponentLabel wrapLabel = new FormComponentLabel("checkBoxLabel"+i, checkBox);
            wrapLabel.add(checkBox);
            controlGroup.add(wrapLabel);
            courseChoice.add(checkBox);
        }
    }

    private void createFloorChoice(){
        floorChoice = new ArrayList<CheckBox>(12);
        for (int i = 2; i < 13; i++){
            final CheckBox checkBox = new CheckBox("floorCheckbox"+i, Model.of(Boolean.TRUE));
            checkBox.setOutputMarkupId(true);
            checkBox.add(new OnChangeAjaxBehavior() {
                @Override
                protected void onUpdate(AjaxRequestTarget target) {
                    int selectedCheckboxesCount = 0;
                    for (CheckBox cBox : floorChoice) {
                        if(cBox.getModelObject()) {
                            selectedCheckboxesCount++;
                        }
                    }
                    if(selectedCheckboxesCount == 0) {
                        checkBox.setModelObject(true);
                    }
                    target.add(checkBox);
                }
            });
            FormComponentLabel wrapLabel = new FormComponentLabel("floorLabel"+i, checkBox);
            wrapLabel.add(checkBox);
            controlGroup.add(wrapLabel);
            floorChoice.add(checkBox);
        }
    }

    public Filter getFilter() {
        filter.setCategory(getCategoryChoice());
        filter.setSelectedCourses(getSelectedCourses());
        filter.setSelectedFloors(getSelectedFloors());
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;

        if(filter.getCategory() != null ){
            setSelection(false);
            setCategoryChoice(filter.getCategory());
        }
        else{
            setSelection(true);
            setCategoryChoice(NewsCategory.OTHER);
        }
        for (Integer course: filter.getSelectedCourses()) {
            setSelectedCourse(course, true);
        }

        for (Integer floor: filter.getSelectedFloors()) {
            setSelectedFloor(floor, true);
        }
    }

    public void setSelectedFloor(int floor, boolean status){
        if( floor < 1 || floor > floorChoice.size() + 1) {
            throw new IndexOutOfBoundsException("In hostel № " + filter.getHostel()+" no floor " + floor );
        }
        floorChoice.get(floor-2).setModelObject(status);
    }

    public void setSelectedCourse(int course, boolean status){
        if (course < 1 || course > 5) {
            return;
        }
        courseChoice.get(course - 1).setModelObject(status);
    }

    public void setCategoryChoice(NewsCategory category){
        if(NewsInfo.getCategoryHashMap().containsKey(category))
        {
            String selectedResourceKey = NewsInfo.getCategoryHashMap().get(category);
            for (Object choiseVariant: categoryChoice.getChoices()){
                String resourceKey = ((SelectOption)choiseVariant).getKey();
                if (resourceKey.equals(selectedResourceKey)) {
                    categoryChoice.setModelObject(choiseVariant);
                }
            }
        }
    }

    public List<Integer> getSelectedFloors(){
        List<Integer> selectedFloors = new ArrayList<Integer>(12);
        for (CheckBox checkBox: floorChoice) {
            if(Boolean.valueOf(checkBox.getValue())) {
                selectedFloors.add(floorChoice.indexOf(checkBox)+2);
            }
        }
        return selectedFloors;
    }

    public List<Integer> getSelectedCourses(){
        List<Integer> selectedCourses = new ArrayList<Integer>(5);
        for (CheckBox checkBox: courseChoice) {
            if(Boolean.valueOf(checkBox.getValue())) {
                selectedCourses.add(courseChoice.indexOf(checkBox)+1);
            }
        }
        return selectedCourses;
    }

    public NewsCategory getCategoryChoice(){
        String key = ((SelectOption) categoryChoice.getModelObject()).getKey();
        for (NewsCategory category:NewsInfo.getCategoryHashMap().keySet()) {
            if(key.equals(NewsInfo.getCategoryHashMap().get(category))) {
                return category;
            }
        }
        return null;
    }

    public void setSelection(boolean selection){
        for (CheckBox choise: floorChoice) {
            choise.setModelObject(selection);
        }
        for (CheckBox choise: courseChoice) {
            choise.setModelObject(selection);
        }
    }

    public void clear() {
        setFilter(new Filter());
    }

    public boolean isModified() {
        return filter.getSelectedCourses().size() != getSelectedCourses().size()
                && filter.getSelectedFloors().size() != getSelectedCourses().size() ||
                (filter.getCategory() != getCategoryChoice() || !(filter.getSelectedCourses().containsAll(getSelectedCourses()) &&
                filter.getSelectedFloors().containsAll(getSelectedFloors())));
    }
}


