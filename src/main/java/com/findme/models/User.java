package com.findme.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Setter @Getter @ToString @NoArgsConstructor
@Entity
@Table (name = "USERS")
public class User {
    @Id
    @SequenceGenerator(name = "USER_SEQ", sequenceName = " USER_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
    @Column(name = "ID")
    private Long id;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "PHONE")
    private String phone;
    //TODO from existed data
    @Column(name = "COUNTRY")
    private String country;
    @Column(name = "CITY")
    private String city;
    @Column(name = "AGE")
    private Integer age;
    @Column(name = "DATE_REGISTERED")
    private Date dateRegistered;
    @Column(name = "DATE_LAST_ACTIVE")
    private Date dateLastActive;
    //TODO enum
    @Column(name = "RELATIONSHIP_STATUS")
    private String relationshipStatus;
    @Column(name = "RELIGION")
    private String religion;
    //TODO from existed data
    @Column(name = "SCHOOL")
    private String school;
    @Column(name = "UNIVERSITY")
    private String university;
    //private String[] interests;

    @OneToMany(mappedBy = "userTo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Message> messagesSent;
    @OneToMany(mappedBy = "userFrom", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Message> messagesReceived;
}
