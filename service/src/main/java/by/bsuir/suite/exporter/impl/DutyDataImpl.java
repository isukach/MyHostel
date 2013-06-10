package by.bsuir.suite.exporter.impl;


import by.bsuir.suite.domain.duty.Duty;
import by.bsuir.suite.exporter.DutyData;

import java.util.Set;
/**
 * @author a.garelik
 */
public class DutyDataImpl implements DutyData{

    private String month;
    private String year;
    private String floor;
    private String floorHead;
    private String educator;
    private String hostel;
    private Set<Duty> duties;

    public DutyDataImpl(String month, String year, String floor, String floorHead, String educator, String hostel, Set<Duty> duties) {
        this.month = month;
        this.year = year;
        this.floor = floor;
        this.floorHead = floorHead;
        this.educator = educator;
        this.hostel = hostel;
        this.duties = duties;
    }

    public DutyDataImpl() {
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getFlorHead() {
        return floorHead;
    }

    public void setFlorHead(String florHead) {
        this.floorHead = florHead;
    }

    public String getEducator() {
        return educator;
    }

    public void setEducator(String educator) {
        this.educator = educator;
    }

    public Set<Duty> getDuties() {
        return duties;
    }

    public void setDuties(Set<Duty> duties) {
        this.duties = duties;
    }

    public String getHostel() {
        return hostel;
    }

    public void setHostel(String hostel) {
        this.hostel = hostel;
    }
}
