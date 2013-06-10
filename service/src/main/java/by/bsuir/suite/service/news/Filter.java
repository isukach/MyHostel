package by.bsuir.suite.service.news;

import by.bsuir.suite.domain.news.NewsCategory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @autor a.garelik
 * Date: 30/12/12
 * Time: 23:38
 */

public class Filter implements Serializable {

    private Integer hostel = 1;
    private NewsCategory category;
    private Map<Integer, Boolean> courses = new HashMap<Integer, Boolean>(5);
    private Map<Integer, Boolean> floors = new HashMap<Integer, Boolean>(12);

    public NewsCategory getCategory() {
        return category;
    }

    public void setCategory(NewsCategory category) {
        this.category = category;
    }

    public Integer getHostel() {
        return hostel;
    }

    public void setHostel(Integer hostel) {
        this.hostel = hostel;
    }

    public void selectCourse(int floor){
        courses.put(floor, true);
    }

    public void deselectCourse(int floor){
        courses.put(floor, false);
    }

    public List<Integer> getSelectedCourses(){
        List<Integer> selectedCourses = new ArrayList<Integer>(5);
        for(Integer key: courses.keySet()) {
            if(courses.get(key)) {
                selectedCourses.add(key);
            }
        }

        return selectedCourses;
    }

    public void setSelectedFloors(List<Integer> list){
        floors.clear();
        for (Integer floor: list) {
            selectFloor(floor);
        }
    }

    public void setSelectedCourses(List<Integer> list){
        courses.clear();
        for (Integer course: list) {
            selectCourse(course);
        }
    }
    public void selectFloor(int floor){
        floors.put(floor, true);
    }

    public void deselectFloor(int floor){
        floors.put(floor, false);
    }

    public List<Integer> getSelectedFloors(){
        List<Integer> selectedCourses = new ArrayList<Integer>(5);
        for(Integer key: floors.keySet()) {
            if(floors.get(key)) {
                selectedCourses.add(key);
            }
        }

        return selectedCourses;
    }

}