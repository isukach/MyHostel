package by.bsuir.suite.domain.person;

import by.bsuir.suite.domain.BaseEntity;

import javax.persistence.*;

/**
 * @author i.sukach
 */
@Entity
@Table(name = "links")
public class Link extends BaseEntity {
    private String facebook;
    private String twitter;
    private String vk;
    private String skype;
    private String devart;
    private String vimeo;
    private Person person;
    private String google;
    private String youtube;
    private String lastfm;

    @Column(name = "lastfm")
    public String getLastfm() {
        return lastfm;
    }

    public void setLastfm(String lastfm) {
        this.lastfm = lastfm;
    }

    @Column(name = "youtube")
    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    @Column(name = "google")
    public String getGoogle() {
        return google;
    }

    public void setGoogle(String google) {
        this.google = google;
    }

    @Column(name = "facebook")
    public String getFacebook() {

        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    @Column(name = "twitter")
    public String getTwitter() {

        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    @Column(name = "vk")
    public String getVk() {

        return vk;
    }

    public void setVk(String vk) {
        this.vk = vk;
    }

    @Column(name = "skype")
    public String getSkype() {

        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    @Column(name = "devart")
    public String getDevart() {

        return devart;
    }

    public void setDevart(String devart) {
        this.devart = devart;
    }

    @Column(name = "vimeo")
    public String getVimeo() {

        return vimeo;
    }

    public void setVimeo(String vimeo) {
        this.vimeo = vimeo;
    }

    @JoinColumn(name = "person_id", referencedColumnName = "id")
    @OneToOne(optional = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}

