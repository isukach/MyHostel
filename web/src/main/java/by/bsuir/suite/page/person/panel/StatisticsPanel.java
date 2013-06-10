package by.bsuir.suite.page.person.panel;

import by.bsuir.suite.dto.duty.DutyStatisticsDto;
import by.bsuir.suite.dto.work.WorkStatisticsDto;
import by.bsuir.suite.service.person.PersonService;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.resource.ContextRelativeResource;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author i.sukach
 */
public class StatisticsPanel extends Panel {
    @SpringBean
    private PersonService personService;

    private DutyStatisticsDto dutiesInfo;
    private WorkStatisticsDto worksInfo;
    private Boolean[] lan;

    public StatisticsPanel(String id, String login) {
        super(id);
        dutiesInfo = personService.getDutiesStatistics(login);
        worksInfo = personService.getWorkStatistics(login);
        lan = personService.getLanStatistics(login);

        int totalDuties = dutiesInfo.getDuties() + dutiesInfo.getExtraDuties();
        add(new Label("dutiesPc", dutiesInfo.getCompletedDuties() + "/" + totalDuties + "."));
        add(new Label("dutiesBar", "") {
            @Override
            protected void onComponentTag(final ComponentTag tag) {
                super.onComponentTag(tag);
                if (dutiesInfo.getCompletedDutiesPercentage() > 100) {
                    dutiesInfo.setCompletedDutiesPercentage(100);
                }
                tag.put("style", "width: " + dutiesInfo.getCompletedDutiesPercentage() + "%");
            }
        });
        add(new Label("extraDutiesLabel", new StringResourceModel("extra", this, null)));
        add(new Label("extraDutiesNumber", String.valueOf(dutiesInfo.getExtraDuties())));

        int totalHours = worksInfo.getRequiredHours() + worksInfo.getExtraHours();
        add(new Label("worksPc", worksInfo.getCompletedHours() + "/" + totalHours + "."));
        add(new Label("worksBar", ""){
            @Override
            protected void onComponentTag(final ComponentTag tag){
                super.onComponentTag(tag);
                tag.put("style", "width: " + worksInfo.getWorkCompletedPercentage() +"%");
            }
        });

        add(new Label("extraHoursLabel", new StringResourceModel("extra", this, null)));
        add(new Label("extraHoursNumber", String.valueOf(worksInfo.getExtraHours())));
        add(new LanMonthPanel("january"){
            @Override
            protected void onComponentTag(final ComponentTag tag){
                super.onComponentTag(tag);
                if (lan[0]) {
                    tag.put("class", "statistics_network_bar_item_fill");
                }
                else {
                    tag.put("class", "statistics_network_bar_item");
                }
            }
        });

        add(new LanMonthPanel("february"){
            @Override
            protected void onComponentTag(final ComponentTag tag){
                super.onComponentTag(tag);
                if(lan[1])   {
                    tag.put("class", "statistics_network_bar_item_fill");
                }
                else {
                    tag.put("class", "statistics_network_bar_item");
                }
            }
        });

        add(new LanMonthPanel("march"){
            @Override
            protected void onComponentTag(final ComponentTag tag){
                super.onComponentTag(tag);
                if(lan[2]){
                    tag.put("class", "statistics_network_bar_item_fill");
                }
                else {
                    tag.put("class", "statistics_network_bar_item");
                }
            }
        });

        add(new LanMonthPanel("april"){
            @Override
            protected void onComponentTag(final ComponentTag tag){
                super.onComponentTag(tag);
                if(lan[3])  {
                    tag.put("class", "statistics_network_bar_item_fill");
                }
                else {
                    tag.put("class", "statistics_network_bar_item");
                }
            }
        });

        add(new LanMonthPanel("may"){
            @Override
            protected void onComponentTag(final ComponentTag tag){
                super.onComponentTag(tag);
                if(lan[4]) {
                    tag.put("class", "statistics_network_bar_item_fill");
                }
                else {
                    tag.put("class", "statistics_network_bar_item");
                }
            }
        });

        add(new LanMonthPanel("june"){
            @Override
            protected void onComponentTag(final ComponentTag tag){
                super.onComponentTag(tag);
                if(lan[5]) {
                    tag.put("class", "statistics_network_bar_item_fill");
                }
                else {
                    tag.put("class", "statistics_network_bar_item");
                }
            }
        });

        add(new LanMonthPanel("july"){
            @Override
            protected void onComponentTag(final ComponentTag tag){
                super.onComponentTag(tag);
                if(lan[6]) {
                    tag.put("class", "statistics_network_bar_item_fill");
                }
                else {
                    tag.put("class", "statistics_network_bar_item");
                }
            }
        });

        add(new LanMonthPanel("august"){
            @Override
            protected void onComponentTag(final ComponentTag tag){
                super.onComponentTag(tag);
                if(lan[7]) {
                    tag.put("class", "statistics_network_bar_item_fill");
                }
                else {
                    tag.put("class", "statistics_network_bar_item");
                }
            }
        });

        add(new LanMonthPanel("september"){
            @Override
            protected void onComponentTag(final ComponentTag tag){
                super.onComponentTag(tag);
                if(lan[8]) {
                    tag.put("class", "statistics_network_bar_item_fill");
                }
                else {
                    tag.put("class", "statistics_network_bar_item");
                }
            }
        });

        add(new LanMonthPanel("october"){
            @Override
            protected void onComponentTag(final ComponentTag tag){
                super.onComponentTag(tag);
                if(lan[9]) {
                    tag.put("class", "statistics_network_bar_item_fill");
                }
                else {
                    tag.put("class", "statistics_network_bar_item");
                }
            }
        });

        add(new LanMonthPanel("november"){
            @Override
            protected void onComponentTag(final ComponentTag tag){
                super.onComponentTag(tag);
                if(lan[10]) {
                    tag.put("class", "statistics_network_bar_item_fill");
                }
                else {
                    tag.put("class", "statistics_network_bar_item");
                }
            }
        });

        add(new LanMonthPanel("december"){
            @Override
            protected void onComponentTag(final ComponentTag tag){
                super.onComponentTag(tag);
                if(lan[11]) {
                    tag.put("class", "statistics_network_bar_item_fill");
                }
                else {
                    tag.put("class", "statistics_network_bar_item");
                }
            }
        });

        Integer count = 0;
        for(Boolean value : lan){
            if(value) {
                count++;
            }
        }
        add(new Label("monthCount", count.toString()));
    }

    class LanMonthPanel extends Panel{
        public LanMonthPanel(String id) {
            super(id);
            add(new Image("squareImage", new ContextRelativeResource("images/square.png")));
        }
    }
}
