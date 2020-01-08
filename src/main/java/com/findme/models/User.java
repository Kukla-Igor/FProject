package com.findme.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter @Getter  @NoArgsConstructor
@Entity
@Table (name = "USERS")
public class User extends IdEntity {
    @Id
    @SequenceGenerator(name = "USER_SEQ", sequenceName = " USER_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
    @Column(name = "ID")
    @JsonProperty("id")
    private Long id;

    @Column(name = "FIRST_NAME")
    @JsonProperty("firstName")
    private String firstName;

    @Column(name = "LAST_NAME")
    @JsonProperty("lastName")
    private String lastName;

    @Column(name = "PHONE")
    @JsonProperty("phone")
    private String phone;

    //TODO from existed data
    @Column(name = "COUNTRY")
    @JsonProperty("country")
    private String country;

    @Column(name = "CITY")
    @JsonProperty("city")
    private String city;

    @Column(name = "AGE")
    @JsonProperty("age")
    private Integer age;

    @Column(name = "DATE_REGISTERED")
    @JsonProperty("dateRegistered")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateRegistered;

    @Column(name = "DATE_LAST_ACTIVE")
    @JsonProperty("dateLastActive")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateLastActive;

    //TODO enum
    @Column(name = "RELATIONSHIP_STATUS")
    @JsonProperty("relationshipStatus")
    private String relationshipStatus;

    @Column(name = "RELIGION")
    @JsonProperty("religion")
    private String religion;
    //TODO from existed data

    @Column(name = "SCHOOL")
    @JsonProperty("school")
    private String school;

    @Column(name = "UNIVERSITY")
    @JsonProperty("university")
    private String university;
    //private String[] interests;

    @Column(name = "PASSWORD")
    @JsonProperty("password")
    private String password;

    @Column(name = "EMAIL")
    @JsonProperty("email")
    private String eMail;


    @OneToMany(mappedBy = "userPosted", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Post> userPosted;

    @OneToMany(mappedBy = "userPagePosted", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Post> userPagePosted;

//    @OneToMany(mappedBy = "userTo", cascade = CascadeType.ALL,  fetch = FetchType.LAZY)
//    private List<Message> messagesSent;
//
//    @OneToMany(mappedBy = "userFrom", cascade = CascadeType.ALL,  fetch = FetchType.LAZY)
//    private List<Message> messagesReceived;

    @ManyToMany(mappedBy = "usersTagget")
    private List<Post> posts = new ArrayList<>();


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", age=" + age +
                ", dateRegistered=" + dateRegistered +
                ", dateLastActive=" + dateLastActive +
                ", relationshipStatus='" + relationshipStatus + '\'' +
                ", religion='" + religion + '\'' +
                ", school='" + school + '\'' +
                ", university='" + university + '\'' +
                '}';
    }
}
