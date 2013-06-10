package by.bsuir.suite.service.duty;

import by.bsuir.suite.dao.duty.MonthDao;
import by.bsuir.suite.domain.duty.Month;
import by.bsuir.suite.domain.person.Floor;
import by.bsuir.suite.exporter.DutyData;
import by.bsuir.suite.exporter.impl.DutyDataImpl;
import by.bsuir.suite.exporter.impl.ExcelDutyExporter;
import by.bsuir.suite.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author a.garelik
 */
@Component
@Transactional
public class ExportServiceImpl implements ExportService {


    @Qualifier("excelDutyExporter")
    @Autowired
    private ExcelDutyExporter exporter;

    @Autowired
    private MonthDao monthDao;

    @Override
    public String export(Long monthId) {

        Month monthEntity = monthDao.get(monthId);
        Floor floorEnity = monthEntity.getFloor();
        DutyData data = new DutyDataImpl(
                DateUtils.getMonthNameRus(monthEntity.getMonth()),
                String.valueOf(monthEntity.getYear()),
                floorEnity.getNumber(),
                floorEnity.getFloorHead(),
                floorEnity.getEducator(),
                String.valueOf(floorEnity.getHostel().getNumber()),
                monthDao.get(monthId).getDuties()
        );
        return exporter.build(data);
    }

}
