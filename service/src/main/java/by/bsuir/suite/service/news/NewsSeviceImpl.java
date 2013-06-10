package by.bsuir.suite.service.news;

import by.bsuir.suite.assembler.news.NewsAssembler;
import by.bsuir.suite.comparator.NewsDtoByTimestampComparator;
import by.bsuir.suite.dao.news.NewsDao;
import by.bsuir.suite.dao.person.FloorDao;
import by.bsuir.suite.dao.person.HostelDao;
import by.bsuir.suite.dao.person.PersonDao;
import by.bsuir.suite.disassembler.news.NewsDtoDisassembler;
import by.bsuir.suite.domain.news.Course;
import by.bsuir.suite.domain.news.News;
import by.bsuir.suite.domain.news.NewsCategory;
import by.bsuir.suite.domain.person.Floor;
import by.bsuir.suite.domain.person.Person;
import by.bsuir.suite.dto.news.NewsDto;
import by.bsuir.suite.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author a.garelik
 */
@Service
@Transactional
public class NewsSeviceImpl implements NewsService{

    @Autowired
    private NewsDtoDisassembler disassembler;
    @Autowired
    private NewsDao newsDao;
    @Autowired
    private NewsAssembler assembler;
    @Autowired
    private HostelDao hostelDao;
    @Autowired
    private FloorDao floorDao;
    @Autowired
    private PersonDao personDao;


    @Override
    public List<NewsDto> getNewsByFilter(Filter filter, List<NewsCategory> selectedCategories, int offset, int limit) {
        List<NewsDto> dtoList = new ArrayList<NewsDto>();
        if(filter!= null){
            int floor = filter.getSelectedFloors().size()>0 ? filter.getSelectedFloors().get(0) : -1;
            int course = filter.getSelectedCourses().size()>0 ? filter.getSelectedCourses().get(0) : -1;
            List<News> newsList = newsDao.getNewsBy(selectedCategories, floor, course, offset, limit);
            for (News news: newsList) {
                dtoList.add(disassembler.disassemble(news));
            }
        }
        return dtoList;
    }

    public List<NewsDto> getNews(int offset, int limit, Long personId){
        List<NewsDto> dtoList = new ArrayList<NewsDto>();
        for (News news: newsDao.getNews(offset, limit, personId)){
            NewsDto disasembledDto = disassembler.disassemble(news);
            Filter filter = new Filter();
            for (Floor floor: news.getFloors()){
                filter.selectFloor(Integer.valueOf(floor.getNumber()));
            }
            for (Course course: news.getCourses()){
                filter.selectCourse(course.getCourse());
            }
            filter.setCategory(news.getCategory());
            dtoList.add(disasembledDto);
            disasembledDto.setFilter(filter);
        }

        NewsDtoByTimestampComparator comparator =
                new NewsDtoByTimestampComparator(NewsDtoByTimestampComparator.Order.DESC);
        Collections.sort(dtoList, comparator);
        return dtoList;
    }

    @Override
    public void saveNews(NewsDto dto) {
        News savedNews = assembler.assemble(dto);
        Filter filter = dto.getFilter();
        Set<Floor> hostelFloors = hostelDao.getHostelByNumber(filter.getHostel()).getFloors();
        for (Floor floor :hostelFloors){
            Integer floorNumber = Integer.valueOf(floor.getNumber());
            if (filter.getSelectedFloors().contains(floorNumber)) {
                savedNews.getFloors().add(floor);
            }
        }
        if(dto.getUserName() != null){
            Person person = personDao.getPersonByUsername(dto.getUserName());
            savedNews.setPerson(person);
        }

        Course courseToSave = null;
        for (Integer course : filter.getSelectedCourses()){
            courseToSave = new Course(course, savedNews);
            savedNews.getCourses().add(courseToSave);
        }
        newsDao.saveNews(savedNews);
    }

    public Filter createNewsFilter(long personId){
        Filter filter = new Filter();
        int floorNumber = floorDao.getFloorNumberByPersonId(personId);
        if(floorNumber > 0) {
            filter.selectFloor(floorNumber);
        }
        String group = personDao.get(personId).getUniversityGroup();
        if(group!=null && group.length() == 6) {
            filter.selectCourse(CommonUtils.getCourseByUniversityGroup(group));
        }
        return filter;
    }

    @Override
    public void removeNews(NewsDto dto) {
        News entity = assembler.assemble(dto);
        newsDao.delete(entity);
    }
}
