package com.findme.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "POST")
public class Post {
    @Id
    @SequenceGenerator(name = "POST_SEQ", sequenceName = "POST_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POST_SEQ")
    @Column(name = "ID")
    private Long id;
    @Column(name = "MESSAGE")
    private String message;
    @Column(name = "DATE_POSTED")
    private Date datePosted;
    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "USER_ID")
    private User userPosted;
    //TODO
    //levels permissions
    //TODO
    //comments
}
