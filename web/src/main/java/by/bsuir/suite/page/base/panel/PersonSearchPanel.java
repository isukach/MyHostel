package by.bsuir.suite.page.base.panel;

import by.bsuir.suite.constants.PersonSearchConstants;
import by.bsuir.suite.dto.person.PersonSearchDto;
import by.bsuir.suite.page.base.BasePage;
import by.bsuir.suite.page.person.PersonPage;
import by.bsuir.suite.service.person.PersonService;
import by.bsuir.suite.util.PersonSearchDataProvider;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.LinkedList;
import java.util.List;

/**
 * @author d.matveenko
 */
public class PersonSearchPanel extends Panel {

    @SpringBean
    private PersonService personService;

    private List<IColumn<PersonSearchDto>> columns;

    private AjaxFallbackDefaultDataTable<PersonSearchDto> table;

    private TextField<String> searchField;

    private TablePanel<PersonSearchDto> tablePanel;

    private String search;

    private boolean isTableVisible;

    private PersonSearchDataProvider personSearchDataProvider;

    public PersonSearchPanel(String id) {
        super(id);
        columns = new LinkedList<IColumn<PersonSearchDto>>();
        setOutputMarkupPlaceholderTag(true);

        PropertyColumn<PersonSearchDto> fioColumn = new LinkPropertyColumn<PersonSearchDto>(
                new StringResourceModel("search.no_column_name", this, null), "firstAndLastName") {
            @Override
            public void onClick(Item item, String componentId, IModel<PersonSearchDto> personSearchDtoIModel) {
                PageParameters pageParameters = new PageParameters();
                BasePage.setSearchVisibility(false);
                pageParameters.add(PersonPage.USERNAME_KEY, personSearchDtoIModel.getObject().getUserName());
                setResponsePage(PersonPage.class, pageParameters);
            }
        };

        PropertyColumn<PersonSearchDto> roomColumn = new PropertyColumn<PersonSearchDto>(
                new StringResourceModel("search.no_column_name", this, null), "room") {
            public void populateItem(Item item, String componentId, IModel model) {
                item.add(new AttributeModifier("class", "room"));
                item.add(new Label(componentId, createLabelModel(model)));
            }
        };
        columns.add(fioColumn);
        columns.add(roomColumn);

        personSearchDataProvider = new PersonSearchDataProvider(null, personService);
        table = new AjaxFallbackDefaultDataTable<PersonSearchDto>("resultTable", columns,
                personSearchDataProvider, PersonSearchConstants.NUMBER_ITEMS_ON_PAGE);
        table.setVisible(isTableVisible);
        tablePanel = new TablePanel<PersonSearchDto>("tablePanel", table);
        tablePanel.setOutputMarkupId(true);
        add(tablePanel);

        AjaxFallbackLink link = new AjaxFallbackLink("closeLink") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                isTableVisible = false;
                BasePage.setSearchVisibility(false);
                PersonSearchPanel.this.setVisible(isTableVisible);
                target.add(PersonSearchPanel.this);
            }
        };
        add(link);

        searchField = new TextField<String>("search");
        searchField.setModel(new Model(search));
        searchField.add(new AjaxFormComponentUpdatingBehavior("onkeyup") {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                String value = searchField.getValue();
                personSearchDataProvider = new PersonSearchDataProvider(value, personService);
                table = new AjaxFallbackDefaultDataTable<PersonSearchDto>("resultTable", columns,
                        personSearchDataProvider, PersonSearchConstants.NUMBER_ITEMS_ON_PAGE) {
                    @Override
                    public boolean isVisible() {
                       return isTableVisible;
                    }
                };

                isTableVisible = value != null && !"".equals(value) && personService.searchForPersons(0, value).size() > 0;
                tablePanel.refreshTable(table);
                target.add(tablePanel);
            }
        });
        add(searchField);
    }


}
