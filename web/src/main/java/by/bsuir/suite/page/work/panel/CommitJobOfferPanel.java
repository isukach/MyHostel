package by.bsuir.suite.page.work.panel;

import by.bsuir.suite.dto.work.CommitJobOfferDto;
import by.bsuir.suite.page.base.NotificationWindow;
import by.bsuir.suite.page.base.panel.ConfirmationPanel;
import by.bsuir.suite.page.duty.panel.ConfirmationAnswer;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * User: Matveyenka Denis
 * Date: 21.07.13
 */
public class CommitJobOfferPanel extends Panel {

    private static final int NUMBER_ROWS = 15;

    private List<CommitJobOfferDto> commitJobOfferDtos = new LinkedList<CommitJobOfferDto>();

    private Form<Void> jobOfferForm;

    private ModalWindow jobOfferConfirmDialog;

    public CommitJobOfferPanel(String id, List<CommitJobOfferDto> commitJobOfferDtos,
                                   final ConfirmationAnswer answer, final ModalWindow modalWindow) {
        super(id);
        if (commitJobOfferDtos != null) {
            this.commitJobOfferDtos = commitJobOfferDtos;
        }
        createHeader();
        addConfirmDialog();
        addJobOfferForm();
        addTable();
        createButtons(answer, modalWindow);
    }

    private void addConfirmDialog() {
        jobOfferConfirmDialog = new NotificationWindow("jobOfferConfirmDialog", "ConfirmDialog.header",
                "ConfirmDialog.content", false) {
            @Override
            public void onCancel(AjaxRequestTarget target) {
                close(target);
            }

            @Override
            public void onConfirm(AjaxRequestTarget target) {
                close(target);
            }
        };
        add(jobOfferConfirmDialog);
    }

    private void createHeader() {
        add(new Label("header", new StringResourceModel("commit.jobOfferPanel.header", this, null)));
    }

    private void addJobOfferForm() {
        jobOfferForm = new Form<Void>("jobOfferForm");
        add(jobOfferForm);
    }

    private void addTable() {
        AjaxFallbackDefaultDataTable<CommitJobOfferDto> table = new AjaxFallbackDefaultDataTable<CommitJobOfferDto>("commitJobOfferTable", getTableColumns(),
                new CommitJobOfferDataProvider(), NUMBER_ROWS);
        table.setOutputMarkupId(true);
        add(table);
        jobOfferForm.add(table);
    }

    private List<IColumn<CommitJobOfferDto>> getTableColumns() {
        List<IColumn<CommitJobOfferDto>> columns = new ArrayList<IColumn<CommitJobOfferDto>>();
        columns.add(getTableColumn("commit.table.column.fullName", "work-description", "personJobOfferDto.fullName"));
        columns.add(new AbstractColumn<CommitJobOfferDto>(new StringResourceModel("commit.table.column.hours", this, null)) {
            @Override
            public void populateItem(Item<ICellPopulator<CommitJobOfferDto>> components, String componentId, IModel<CommitJobOfferDto> jobOfferDtoIModel) {
                components.add(new AttributeModifier("class", "work-hours"));
                EditablePanel panel = new EditablePanel(componentId, new PropertyModel(jobOfferDtoIModel, "hours"));
                components.add(panel);
            }
        });

        return columns;
    }

    private AbstractColumn<CommitJobOfferDto> getTableColumn(String resourceModelKey, final String columnStyle, String propertyExpression) {
        return new PropertyColumn<CommitJobOfferDto>(new StringResourceModel(resourceModelKey, this, null),
                propertyExpression) {
            @Override
            public void populateItem(Item<ICellPopulator<CommitJobOfferDto>> item, String componentId, IModel<CommitJobOfferDto> rowModel) {
                item.add(new AttributeModifier("class", columnStyle));
                super.populateItem(item, componentId, rowModel);
            }
        };
    }

    private void createButtons(final ConfirmationAnswer answer, final ModalWindow modalWindow) {
        AjaxFallbackButton cancelButton = new AjaxFallbackButton("cancel", jobOfferForm) {


            @Override
            protected void onError(AjaxRequestTarget ajaxRequestTarget, Form<?> components) {
            }

            @Override
            protected void onSubmit(AjaxRequestTarget ajaxRequestTarget, Form<?> components) {
                answer.setAnswer(false);
                modalWindow.close(ajaxRequestTarget);
            }
        };
        add(cancelButton);
        jobOfferForm.add(cancelButton);
        AjaxFallbackButton okButton = new AjaxFallbackButton("ok", jobOfferForm) {

            @Override
            protected void onError(AjaxRequestTarget ajaxRequestTarget, Form<?> components) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> components) {
//                final ConfirmationAnswer answer = new ConfirmationAnswer();
//                jobOfferConfirmDialog.setContent(new ConfirmationPanel(jobOfferConfirmDialog.getContentId(), answer, jobOfferConfirmDialog) {
//                    @Override
//                    public StringResourceModel getHeaderModel() {
//                        return new StringResourceModel("confirmWindow.header", this, null);
//                    }
//
//                    @Override
//                    public StringResourceModel getContentModel() {
//                        return new StringResourceModel("confirmWindow.content", this, null);
//                    }
//                });
//                jobOfferConfirmDialog.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
//                    @Override
//                    public void onClose(AjaxRequestTarget target) {
//                        if(answer.isPositive()) {
                            answer.setAnswer(true);
                            modalWindow.close(target);
//                        }
//                    }
//                });
//                jobOfferConfirmDialog.show(target);
            }
        };
        add(okButton);
        jobOfferForm.add(okButton);
    }

    public List<CommitJobOfferDto> getCommitJobOfferDtos() {
        return commitJobOfferDtos;
    }

    private class CommitJobOfferDataProvider extends SortableDataProvider<CommitJobOfferDto> {

        public CommitJobOfferDataProvider() {
            Injector.get().inject(this);
        }


        @Override
        public Iterator<CommitJobOfferDto> iterator(int first, int count) {
            return commitJobOfferDtos.iterator();
        }

        @Override
        public int size() {
            return commitJobOfferDtos.size();
        }

        @Override
        public IModel<CommitJobOfferDto> model(final CommitJobOfferDto jobOfferDto) {
            return new LoadableDetachableModel<CommitJobOfferDto>() {
                @Override
                protected CommitJobOfferDto load() {
                    return jobOfferDto;
                }
            };
        }
    }

    public class EditablePanel extends Panel {

        public EditablePanel(String id, IModel  model) {
            super(id);
            addHoursField(model);
        }

        private void addHoursField(IModel  model) {
            TextField<String> hoursField = new TextField<String>("hoursField", model);
            //TODO: add field validation    jobHoursField.add(new HoursValidator(this));
            hoursField.setRequired(true);
            add(hoursField);
        }
    }
}
