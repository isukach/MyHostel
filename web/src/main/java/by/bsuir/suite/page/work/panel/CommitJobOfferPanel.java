package by.bsuir.suite.page.work.panel;

import by.bsuir.suite.dto.work.CommitJobOfferDto;
import by.bsuir.suite.page.base.NotificationWindow;
import by.bsuir.suite.page.duty.panel.ConfirmationAnswer;
import by.bsuir.suite.validator.JobOfferHoursValidator;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormValidatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
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
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.util.time.Duration;

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

    private AjaxFallbackDefaultDataTable<CommitJobOfferDto> table;

    private FeedbackPanel feedbackPanel;

    public CommitJobOfferPanel(String id, List<CommitJobOfferDto> commitJobOfferDtos,
                                   final ConfirmationAnswer answer, final ModalWindow modalWindow) {
        super(id);
        feedbackPanel = new FeedbackPanel("commitJobOfferValidationPanel");
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);

        if (commitJobOfferDtos != null) {
            this.commitJobOfferDtos = commitJobOfferDtos;
        }
        createHeader();
        addConfirmDialog();
        addJobOfferForm();
        addTable();
        createButtons(answer, modalWindow);
        setOutputMarkupId(true);

        AjaxFormValidatingBehavior.addToAllFormComponents(jobOfferForm, "onkeyup", Duration.ONE_SECOND);
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
        jobOfferForm.setOutputMarkupId(true);
        add(jobOfferForm);
    }

    private void addTable() {
        table = new AjaxFallbackDefaultDataTable<CommitJobOfferDto>("commitJobOfferTable", getTableColumns(),
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
        columns.add(new AbstractColumn<CommitJobOfferDto>(new StringResourceModel("commit.table.column.delete", this, null)) {
            @Override
            public void populateItem(Item<ICellPopulator<CommitJobOfferDto>> components, String componentId, IModel<CommitJobOfferDto> jobOfferDtoIModel) {
                DeletePersonPanel panel = new DeletePersonPanel(componentId, jobOfferDtoIModel);
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
                answer.setAnswer(false);
                modalWindow.close(ajaxRequestTarget);
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
                ajaxRequestTarget.add(feedbackPanel);
            }

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> components) {
                answer.setAnswer(true);
                modalWindow.close(target);
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
            TextField<Integer> hoursField = new TextField<Integer>("hoursField", model);
            hoursField.add(new JobOfferHoursValidator());
            hoursField.setRequired(true);
            add(hoursField);
        }
    }

    public class DeletePersonPanel extends Panel {

        private IModel<CommitJobOfferDto> jobOfferDtoIModel;

        public DeletePersonPanel(String id, IModel<CommitJobOfferDto> jobOfferDtoIModel) {
            super(id);
            this.jobOfferDtoIModel = jobOfferDtoIModel;
            addDeleteButton();
        }

        private void addDeleteButton() {
            AjaxFallbackLink<Void> deleteButton = new AjaxFallbackLink<Void>("deleteButton") {

                @Override
                public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                    commitJobOfferDtos.remove(jobOfferDtoIModel.getObject());
                    ajaxRequestTarget.add(CommitJobOfferPanel.this);
                }
            };
            add(deleteButton);
        }
    }
}
