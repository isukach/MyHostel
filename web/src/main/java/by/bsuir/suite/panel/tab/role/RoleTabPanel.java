package by.bsuir.suite.panel.tab.role;

import by.bsuir.suite.dto.role.RoleDto;
import by.bsuir.suite.service.RoleService;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormValidatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.time.Duration;
import org.apache.wicket.validation.validator.StringValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: DenisKravchenko
 * Date: 14.07.12
 * Time: 16:20
 * To change this template use File | Settings | File Templates.
 */
public class RoleTabPanel extends Panel {
    private final FeedbackPanel feedBack = new FeedbackPanel("feedback");
    private AjaxFallbackDefaultDataTable<RoleDto> table;
    private Form<RoleDto> form;
    private RoleDto toUpdate;
    private RoleDto role = new RoleDto();
    private SaveButton saveButton;

    @SpringBean
    private RoleService roleService;

    class DeletePanel extends Panel {
        public DeletePanel(String id, IModel<RoleDto> model) {
            super(id, model);
            add(new AjaxLink("delete") {
                public void onClick(AjaxRequestTarget target) {
                    roleService.delete(((RoleDto) getParent().getDefaultModelObject()).getId());
                    target.add(table);
                }
            });
        }

    }

    class UpdatePanel extends Panel {
        public UpdatePanel(String id, IModel<RoleDto> model) {
            super(id, model);
            add(new AjaxLink("update") {
                public void onClick(AjaxRequestTarget target) {
                    toUpdate = (RoleDto) getParent().getDefaultModelObject();
                    role.setId(toUpdate.getId());
                    role.setName(toUpdate.getName());
                    role.setDescription(toUpdate.getDescription());
                    saveButton.setLabel(new ResourceModel("button.update"));
                    target.add(form);
                }
            });
        }
    }

    class SaveButton extends AjaxButton {
        private String value;

        public SaveButton(String name, String value, Form form) {
            super(name, new ResourceModel(value), form);
            this.value = value;
        }

        protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
            target.add(feedBack);
            RoleDto roleDto = new RoleDto();
            roleDto.setName(role.getName()).setDescription(role.getDescription());
            roleService.save(roleDto);
            role.setName("");
            role.setDescription("");
            if (this.value.equals("button.update")) {
                this.value = "button.add";
                this.setLabel(new ResourceModel("button.add"));
            }
            target.add(form);
            target.add(table);
        }

        protected void onError(AjaxRequestTarget target, Form<?> form) {
            target.add(feedBack);
        }
    }

    public RoleTabPanel(String id) {
        super(id);
        List<IColumn<RoleDto>> columns = new ArrayList<IColumn<RoleDto>>();
        columns.add(new PropertyColumn<RoleDto>(new ResourceModel("label.name"), "name"));
        columns.add(new PropertyColumn<RoleDto>(new ResourceModel("label.description"), "description"));
        columns.add(new AbstractColumn<RoleDto>(new ResourceModel("link.delete")) {
            public void populateItem(Item<ICellPopulator<RoleDto>> cellItem, String componentId, IModel<RoleDto> model) {
                cellItem.add(new DeletePanel(componentId, model));
            }
        });
        columns.add(new AbstractColumn<RoleDto>(new ResourceModel("link.update")) {
            public void populateItem(Item<ICellPopulator<RoleDto>> cellItem, String componentId, IModel<RoleDto> model) {
                cellItem.add(new UpdatePanel(componentId, model));
            }
        });

        table = new AjaxFallbackDefaultDataTable<RoleDto>("table", columns, new RoleDataProvider(), 10);
        add(table);
        add(feedBack);
        feedBack.setOutputMarkupId(true);

        form = new Form<RoleDto>("addRoleForm", new CompoundPropertyModel<RoleDto>(role));
        FormComponent<String> formComponent;
        formComponent = new RequiredTextField<String>("name");
        formComponent.add(StringValidator.minimumLength(4));
        formComponent.setLabel(new ResourceModel("label.name"));
        form.add(formComponent);
        form.add(new SimpleFormComponentLabel("name-label", formComponent));

        formComponent = new TextArea<String>("description");
        formComponent.setLabel(new ResourceModel("label.description"));
        form.add(formComponent);
        form.add(new SimpleFormComponentLabel("description-label", formComponent));

        AjaxFormValidatingBehavior.addToAllFormComponents(form, "onkeyup", Duration.ONE_SECOND);
        saveButton = new SaveButton("ajax-button", "button.add", form);
        form.add(saveButton);
        add(form);
        form.setOutputMarkupId(true);
    }
}
