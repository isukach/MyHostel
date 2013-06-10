package by.bsuir.suite.exporter.impl;

import by.bsuir.suite.domain.duty.Duty;
import by.bsuir.suite.domain.duty.DutyShift;
import by.bsuir.suite.domain.person.Person;
import by.bsuir.suite.exporter.BaseDutyExporter;
import by.bsuir.suite.exporter.DutyData;
import by.bsuir.suite.util.EntityUtils;
import jxl.SheetSettings;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Orientation;
import jxl.write.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.Boolean;
import java.util.*;

/**
 * @author a.garelik
 */
@Component
public class ExcelDutyExporter extends BaseDutyExporter {

    private static final Logger LOG = Logger.getLogger(ExcelDutyExporter.class);

    private WritableSheet sheet;
    private int lastDataRow;

    enum CellFormat {
        TIMES_16_BOLD,
        TIMES_16_NOBOLD,
        TIMES_14_BOLD,
        TIMES_14_NOBOLD,
        HEADER_FORMAT,
        HEADER_FORMAT_GREY,
        DATA_FORMAT_BORDER_ALL,
        DATA_FORMAT_BORDER_ALL_GREY,
        DATA_FORMAT_BORDER_ALL_BOLD,
        DATA_FORMAT_BORDER_ALL_BOLD_GRAY,
        FOOTHER_FORMAT_NOBOLD,
        FOOTHER_FORMAT_BOLD
    }

    @Override
    public void createHeader(DutyData data) {
        try {
            int headerRow = 0;
            int headerColumn = 4;

            sheet.mergeCells(headerColumn, headerRow, 6, headerRow);
            sheet.mergeCells(headerColumn, headerRow + 1, 6, headerRow + 1);
            sheet.mergeCells(headerColumn, headerRow + 2, 6, headerRow + 2);
            sheet.mergeCells(headerColumn, headerRow + 3, 6, headerRow + 3);

            sheet.setRowView(0, 800);
            sheet.setRowView(4, 0);

            int distCol = 1;

            sheet.setColumnView(distCol - 1, 9);
            sheet.setColumnView(distCol, 20);
            sheet.setColumnView(distCol + 1, 10);
            sheet.setColumnView(distCol + 2, 9);
            sheet.setColumnView(distCol + 3, 20);
            sheet.setColumnView(distCol + 4, 10);
            sheet.setColumnView(distCol + 5, 9);

            sheet.mergeCells(0, 5, 6, 5);
            sheet.mergeCells(0, 6, 6, 6);
            sheet.mergeCells(0, 7, 6, 7);

            sheet.setRowView(9, 250);

            addLabelToSheet(sheet, 0, 5, "График дежурств", CellFormat.TIMES_16_BOLD);
            addLabelToSheet(sheet, 0, 6, "по " + data.getFloor() + " этажу", CellFormat.TIMES_16_NOBOLD);
            addLabelToSheet(sheet, 0, 7, "на " + data.getMonth() + " " + data.getYear() + " года", CellFormat.TIMES_16_NOBOLD);

            sheet.setRowView(8, 0);

            addLabelToSheet(sheet, 4, 0, "У Т В Е Р Ж Д А Ю", CellFormat.TIMES_14_NOBOLD);
            addLabelToSheet(sheet, 4, 1, "Заведущая общежития № " + data.getHostel(), CellFormat.TIMES_14_NOBOLD);
            addLabelToSheet(sheet, 4, 2, "____________Наумова С.Л.", CellFormat.TIMES_14_NOBOLD);
            addLabelToSheet(sheet, 4, 3, "\"_____\"__________ " + data.getYear() + " год", CellFormat.TIMES_14_NOBOLD);

        } catch (WriteException ex) {
            LOG.error(ex);
        }
    }

    @Override
    public void createContent(DutyData data) {
        try {
            int distCol = 0;
            int distRow = 10;
            sheet.mergeCells(distCol + 1, distRow, distCol + 3, distRow);
            sheet.mergeCells(distCol + 4, distRow, distCol + 6, distRow);
            sheet.mergeCells(distCol, distRow, distCol, distRow + 1);

            addLabelToSheet(sheet, distCol + 1, distRow, "I смена (8:00 - 16:00)", CellFormat.HEADER_FORMAT);
            addLabelToSheet(sheet, distCol + 4, distRow, "II смена (16:00 - 24:00)", CellFormat.HEADER_FORMAT);
            addLabelToSheet(sheet, distCol, distRow, "Число", CellFormat.HEADER_FORMAT);
            addLabelToSheet(sheet, distCol + 1, distRow + 1, "ФИО", CellFormat.HEADER_FORMAT);
            addLabelToSheet(sheet, distCol + 2, distRow + 1, "Группа", CellFormat.HEADER_FORMAT);
            addLabelToSheet(sheet, distCol + 3, distRow + 1, "Комн.", CellFormat.HEADER_FORMAT);
            addLabelToSheet(sheet, distCol + 4, distRow + 1, "ФИО", CellFormat.HEADER_FORMAT);
            addLabelToSheet(sheet, distCol + 5, distRow + 1, "Группа", CellFormat.HEADER_FORMAT);
            addLabelToSheet(sheet, distCol + 6, distRow + 1, "Комн.", CellFormat.HEADER_FORMAT);

            sheet.setRowView(distRow + 1, 350);
            int dataRow = distRow + 2;
            CellFormat currentFormat = null;
            CellFormat currentFormatDay = CellFormat.DATA_FORMAT_BORDER_ALL_BOLD;


            List<Duty> duties = new ArrayList(data.getDuties());
            Collections.sort(duties, new Comparator<Duty>() {
                @Override
                public int compare(Duty o1, Duty o2) {
                    return o1.getDate().compareTo(o2.getDate());
                }
            });

            for (Iterator<Duty> it = duties.iterator(); it.hasNext(); ) {

                Duty dutyFirst = it.next(), dutySecond = it.next();
                if (dutyFirst.getShift() != DutyShift.FIRST) {
                    Duty buf = dutyFirst;
                    dutyFirst = dutySecond;
                    dutySecond = buf;
                }

                Calendar dutyDate = Calendar.getInstance();
                dutyDate.setTimeInMillis(dutyFirst.getDate().getTime());
                int dayOfWeek = dutyDate.get(Calendar.DAY_OF_WEEK);

                addLabelToSheet(sheet, distCol, dataRow, "" + dutyDate.get(Calendar.DAY_OF_MONTH), currentFormatDay);

                currentFormat = defineUserCellFormat(dayOfWeek, true, CellFormat.DATA_FORMAT_BORDER_ALL,
                        CellFormat.DATA_FORMAT_BORDER_ALL_GREY, CellFormat.DATA_FORMAT_BORDER_ALL_BOLD, CellFormat.DATA_FORMAT_BORDER_ALL_BOLD_GRAY);

                Person dutyPerson = dutyFirst.getPerson();
                String personName = dutyPerson != null ? EntityUtils.generatePersonCalendarName(dutyPerson) : "";

                addLabelToSheet(sheet, distCol + 1, dataRow, personName, currentFormat);
                String firstUSerGroup = dutyPerson == null ? "" : dutyPerson.getUniversityGroup();
                addLabelToSheet(sheet, distCol + 2, dataRow, firstUSerGroup, currentFormat);
                String firstUserRoom = dutyPerson == null ? "" : data.getFloor() + dutyPerson.getRoom().getNumber();
                addLabelToSheet(sheet, distCol + 3, dataRow, firstUserRoom, currentFormat);

                dutyPerson = dutySecond.getPerson();
                personName = dutyPerson != null ? EntityUtils.generatePersonCalendarName(dutyPerson) : "";
                addLabelToSheet(sheet, distCol + 4, dataRow, personName, currentFormat);
                String secondUserGroup = dutyPerson == null ? "" : dutyPerson.getUniversityGroup();
                addLabelToSheet(sheet, distCol + 5, dataRow, secondUserGroup, currentFormat);
                String secondUserRoom = dutyPerson == null ? "" : data.getFloor() + dutyPerson.getRoom().getNumber();
                addLabelToSheet(sheet, distCol + 6, dataRow, secondUserRoom, currentFormat);

                dataRow++;
            }

            lastDataRow = dataRow;
        } catch (WriteException ex) {
            LOG.error(ex);
        }
    }

    @Override
    public void createFooter(DutyData data) {
        int distCol = 0;
        int dataRow = lastDataRow + 2;
        try {
            sheet.setRowView(dataRow, 150);

            sheet.mergeCells(distCol, dataRow + 1, distCol + 2, dataRow + 1);
            sheet.mergeCells(distCol + 4, dataRow + 1, distCol + 6, dataRow + 1);
            sheet.setRowView(dataRow + 2, 140);

            sheet.mergeCells(distCol, dataRow + 3, distCol + 2, dataRow + 3);
            sheet.mergeCells(distCol, dataRow + 4, distCol + 2, dataRow + 4);
            sheet.mergeCells(distCol + 4, dataRow + 4, distCol + 6, dataRow + 4);

            addLabelToSheet(sheet, distCol, dataRow + 1, "Староста этажа", CellFormat.FOOTHER_FORMAT_NOBOLD);
            addLabelToSheet(sheet, distCol + 4, dataRow + 1, "_____________" + (data.getFlorHead() == null ? "" : data.getFlorHead()), CellFormat.FOOTHER_FORMAT_NOBOLD);
            addLabelToSheet(sheet, distCol, dataRow + 3, "Согласовано:", CellFormat.FOOTHER_FORMAT_BOLD);
            addLabelToSheet(sheet, distCol, dataRow + 4, "Воспитатель общежития №" + data.getHostel(), CellFormat.FOOTHER_FORMAT_NOBOLD);
            addLabelToSheet(sheet, distCol + 4, dataRow + 4, "_____________" + (data.getEducator() == null ? "" : data.getEducator()), CellFormat.FOOTHER_FORMAT_NOBOLD);
        } catch (WriteException e) {
            LOG.error(e);
        }
    }

    @Override
    public String build(DutyData data) {
        String floor = data.getFloor();

        StringBuilder fileName = new StringBuilder("duties_");
        fileName.append(floor).append("-floor").append(".xls");

        WorkbookSettings ws = new WorkbookSettings();
        ws.setIgnoreBlanks(true);
        ws.setSuppressWarnings(true);
        ws.setRefreshAll(true);
        try {
            File file = new File(fileName.toString());
            WritableWorkbook workbook = Workbook.createWorkbook(file, ws);
            sheet = workbook.createSheet("Duty", 0);
            SheetSettings settings = sheet.getSettings();
            settings.setTopMargin(0);
            settings.setBottomMargin(0);

            createTemplate(data);

            workbook.write();
            workbook.close();

        } catch (FileNotFoundException e) {
            LOG.error(e);
        } catch (IOException e) {
            LOG.error(e);
        } catch (WriteException e) {
            LOG.error(e);
        }
        return fileName.toString();
    }

    private void addLabelToSheet(WritableSheet sheet, int col, int row, String text, CellFormat format) throws WriteException {
        Label content = new Label(col, row, text, createFormat(format));
        sheet.addCell(content);
    }


    private CellFormat defineUserCellFormat(int dayOfWeek, Boolean isOwnDuty,
                                            CellFormat formatNoBold, CellFormat formatNoBoldGrey,
                                            CellFormat formatBold, CellFormat formatBoldGrey) {
        if ((dayOfWeek == Calendar.SATURDAY) || (dayOfWeek == Calendar.SUNDAY)) {
            if (isOwnDuty != null && !isOwnDuty) {
                return formatBoldGrey;
            } else {
                return formatNoBoldGrey;
            }
        } else {
            if (isOwnDuty != null && !isOwnDuty) {
                return formatBold;
            } else {
                return formatNoBold;
            }
        }
    }

    private WritableCellFormat createFormat(CellFormat format) {
        WritableCellFormat result = null;
        try {
            WritableFont font;
            switch (format) {
                case TIMES_14_BOLD:
                    font = new WritableFont(WritableFont.TIMES, 14, WritableFont.BOLD);
                    result = new WritableCellFormat(font);
                    result.setAlignment(Alignment.LEFT);
                    result.setWrap(false);
                    break;
                case TIMES_14_NOBOLD:
                    font = new WritableFont(WritableFont.TIMES, 14, WritableFont.NO_BOLD);
                    result = new WritableCellFormat(font);
                    result.setAlignment(Alignment.LEFT);
                    result.setWrap(false);
                    break;
                case TIMES_16_BOLD:
                    font = new WritableFont(WritableFont.TIMES, 16, WritableFont.BOLD);
                    result = new WritableCellFormat(font);
                    result.setAlignment(Alignment.CENTRE);
                    result.setWrap(false);
                    break;
                case TIMES_16_NOBOLD:
                    font = new WritableFont(WritableFont.TIMES, 16, WritableFont.NO_BOLD);
                    result = new WritableCellFormat(font);
                    result.setAlignment(Alignment.CENTRE);
                    result.setWrap(false);
                    result.setOrientation(Orientation.HORIZONTAL);
                    break;
                case HEADER_FORMAT:
                    font = new WritableFont(WritableFont.TIMES, 11, WritableFont.BOLD);
                    result = new WritableCellFormat(font);
                    result.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
                    result.setVerticalAlignment(VerticalAlignment.CENTRE);
                    result.setAlignment(Alignment.CENTRE);
                    result.setWrap(true);
                    break;
                case HEADER_FORMAT_GREY:
                    font = new WritableFont(WritableFont.TIMES, 14, WritableFont.BOLD);
                    result = new WritableCellFormat(font);
                    result.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
                    result.setVerticalAlignment(VerticalAlignment.CENTRE);
                    result.setAlignment(Alignment.CENTRE);
                    result.setWrap(true);
                    result.setBackground(Colour.GREY_25_PERCENT);
                    break;
                case DATA_FORMAT_BORDER_ALL:
                    font = new WritableFont(WritableFont.TIMES, 11, WritableFont.NO_BOLD);
                    result = new WritableCellFormat(font);
                    result.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
                    result.setAlignment(Alignment.CENTRE);
                    result.setVerticalAlignment(VerticalAlignment.CENTRE);
                    result.setWrap(true);
                    break;
                case DATA_FORMAT_BORDER_ALL_GREY:
                    font = new WritableFont(WritableFont.TIMES, 11, WritableFont.NO_BOLD);
                    result = new WritableCellFormat(font);
                    result.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
                    result.setAlignment(Alignment.CENTRE);
                    result.setVerticalAlignment(VerticalAlignment.CENTRE);
                    result.setWrap(true);
                    result.setBackground(Colour.GREY_25_PERCENT);
                    break;
                case DATA_FORMAT_BORDER_ALL_BOLD_GRAY:
                    font = new WritableFont(WritableFont.TIMES, 11, WritableFont.BOLD);
                    result = new WritableCellFormat(font);
                    result.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
                    result.setAlignment(Alignment.CENTRE);
                    result.setVerticalAlignment(VerticalAlignment.CENTRE);
                    result.setWrap(true);
                    break;
                case DATA_FORMAT_BORDER_ALL_BOLD:
                    font = new WritableFont(WritableFont.TIMES, 11, WritableFont.BOLD);
                    result = new WritableCellFormat(font);
                    result.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
                    result.setAlignment(Alignment.CENTRE);
                    result.setVerticalAlignment(VerticalAlignment.CENTRE);
                    result.setWrap(true);
                    result.setBackground(Colour.GREY_25_PERCENT);
                    break;
                case FOOTHER_FORMAT_NOBOLD:
                    font = new WritableFont(WritableFont.TIMES, 14, WritableFont.NO_BOLD);
                    result = new WritableCellFormat(font);
                    break;
                case FOOTHER_FORMAT_BOLD:
                    font = new WritableFont(WritableFont.TIMES, 14, WritableFont.NO_BOLD);
                    result = new WritableCellFormat(font);
                    break;
                default:
                    font = new WritableFont(WritableFont.TIMES, 14, WritableFont.BOLD);
                    result = new WritableCellFormat(font);
                    result.setAlignment(Alignment.LEFT);
                    result.setWrap(false);
                    break;
            }
        } catch (WriteException ex) {
            LOG.error(ex);
        }
        return result;
    }
}
