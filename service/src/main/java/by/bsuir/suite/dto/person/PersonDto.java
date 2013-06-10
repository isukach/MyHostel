package by.bsuir.suite.dto.person;

import by.bsuir.suite.dto.common.Dto;
import org.apache.wicket.markup.html.form.upload.FileUpload;

/**
 * @author i.sukach
 */
public class PersonDto implements Dto {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private RoomDto room;
    private FloorDto floor;
    private HostelDto hostel;
    private FacultyDto faculty;
    private String group;
    private String course;
    private String role;
    private String from;
    private String about;
    private String facilities;
    private String phoneNumber;
    private String avatarPath;

    private String facebookLink;
    private String twitterLink;
    private String vkLink;
    private String skypeLink;
    private String devartLink;
    private String googleLink;
    private String youtubeLink;
    private String lastfmLink;
    private boolean passwordChanged;
    private boolean usernameChanged;

    private FileUpload fileUpload;

    public FileUpload getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(FileUpload fileUpload) {
        this.fileUpload = fileUpload;
    }

    public String getLastfmLink() {
        return lastfmLink;
    }

    public void setLastfmLink(String lastfmLink) {
        this.lastfmLink = lastfmLink;
    }

    public String getYoutubeLink() {

        return youtubeLink;
    }

    public void setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
    }

    public String getGoogleLink() {

        return googleLink;
    }

    public void setGoogleLink(String googleLink) {
        this.googleLink = googleLink;
    }

    public String getVimeoLink() {
        return vimeoLink;
    }

    public void setVimeoLink(String vimeoLink) {
        this.vimeoLink = vimeoLink;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getDevartLink() {

        return devartLink;
    }

    public void setDevartLink(String devartLink) {
        this.devartLink = devartLink;
    }

    public String getSkypeLink() {

        return skypeLink;
    }

    public void setSkypeLink(String skypeLink) {
        this.skypeLink = skypeLink;
    }

    public String getVkLink() {
        return vkLink;
    }

    public void setVkLink(String vkLink) {
        this.vkLink = vkLink;
    }

    public String getTwitterLink() {

        return twitterLink;
    }

    public void setTwitterLink(String twitterLink) {
        this.twitterLink = twitterLink;
    }

    public String getFacebookLink() {
        return facebookLink;
    }

    public void setFacebookLink(String facebookLink) {
        this.facebookLink = facebookLink;
    }

    public String getFacilities() {

        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public String getAbout() {

        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    private String vimeoLink;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getRole() {

        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCourse() {

        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getGroup() {

        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public FacultyDto getFaculty() {

        return faculty;
    }

    public void setFaculty(FacultyDto faculty) {
        this.faculty = faculty;
    }

    public HostelDto getHostel() {
        return hostel;
    }

    public void setHostel(HostelDto hostel) {
        this.hostel = hostel;
    }

    public RoomDto getRoom() {

        return room;
    }

    public void setRoom(RoomDto room) {
        this.room = room;
    }

    public String getLastName() {

        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {

        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FloorDto getFloor() {
        return floor;
    }

    public void setFloor(FloorDto floor) {
        this.floor = floor;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public boolean isPasswordChanged() {
        return passwordChanged;
    }

    public void setPasswordChanged(boolean passwordChanged) {
        this.passwordChanged = passwordChanged;
    }

    public boolean isUsernameChanged() {
        return usernameChanged;
    }

    public void setUsernameChanged(boolean usernameChanged) {
        this.usernameChanged = usernameChanged;
    }
}
