package by.bsuir.suite.initializer.property;

import java.util.List;

/**
 * @author i.sukach
 */
public class PersonProperty {

    private String firstName;
    private String lastName;
    private String middleName;
    private String faculty;
    private String universityGroup;
    private String about;
    private String email;
    private String tel;
    private int maxDuties;

    private String username;
    private String password;
    private List<RoleProperty> roles;

    private int hostel;
    private String floor;
    private String room;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getUniversityGroup() {
        return universityGroup;
    }

    public void setUniversityGroup(String universityGroup) {
        this.universityGroup = universityGroup;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<RoleProperty> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleProperty> roles) {
        this.roles = roles;
    }

    public int getHostel() {
        return hostel;
    }

    public void setHostel(int hostel) {
        this.hostel = hostel;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getMaxDuties() {
        return maxDuties;
    }

    public void setMaxDuties(int maxDuties) {
        this.maxDuties = maxDuties;
    }
}
