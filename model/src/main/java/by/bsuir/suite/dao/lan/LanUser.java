package by.bsuir.suite.dao.lan;

import by.bsuir.suite.domain.lan.LanPayment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author i.sukach
 */
@Entity
@Table(name = "hna_users")
public class LanUser {

    private Long id;

    private Long adminId;

    private int room;

    private boolean hasContract;

    private String firstName;

    private String lastName;

    private String middleName;

    private String groupNumber;

    private int hostelNumber;

    private List<LanPayment> lanPayments = new ArrayList<LanPayment>();

    @Id
    @Column(name = "user_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "admin_id")
    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    @Column(name = "block")
    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    @Column(name = "contract")
    public boolean isHasContract() {
        return hasContract;
    }

    public void setHasContract(boolean hasContract) {
        this.hasContract = hasContract;
    }

    @Column(name = "firstname")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "surname")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "lastname")
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Column(name = "group")
    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    @Column(name = "hostel")
    public int getHostelNumber() {
        return hostelNumber;
    }

    public void setHostelNumber(int hostelNumber) {
        this.hostelNumber = hostelNumber;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    public List<LanPayment> getLanPayments() {
        return lanPayments;
    }

    public void setLanPayments(List<LanPayment> lanPayments) {
        this.lanPayments = lanPayments;
    }
}
