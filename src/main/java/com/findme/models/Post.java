package com.findme.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "POST")
public class Post extends IdEntity {
    @Id
    @SequenceGenerator(name = "POST_SEQ", sequenceName = "POST_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POST_SEQ")
    @Column(name = "ID")
    @JsonProperty("id")
    private Long id;

    @Column(name = "MESSAGE")
    @JsonProperty("message")
    private String message;

    @Column(name = "DATE_POSTED")
    @JsonProperty("datePosted")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date datePosted;

    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "ID")
    @JsonProperty("user")
    private User userPosted;
    //TODO
    //levels permissions
    //TODO
    //comments
}
