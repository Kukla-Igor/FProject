package com.findme.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
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


    @Column(name = "LOCATION")
    @JsonProperty("location")
    private String location;
//(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @ManyToMany ( fetch = FetchType.EAGER)
    @JoinTable(
            name = "USERS_POSTS",
            joinColumns = { @JoinColumn(name = "POST_ID")},
            inverseJoinColumns = { @JoinColumn(name = "USER_ID")}
    )
    private List<User> usersTagget = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name="USER_POSTED", nullable=false)
    private User userPosted;

    @ManyToOne
    @JoinColumn(name="USER_PAGE_POSTED", nullable=false)
    private User userPagePosted;

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", datePosted=" + datePosted +
                ", location='" + location + '\'' +
                //", usersTagget=" + usersTagget +
                ", userPosted=" + userPosted.getFirstName() + " " + userPosted.getLastName() +
                ", userPagePosted=" + userPagePosted.getFirstName() + " " + userPagePosted.getLastName() +
                '}';
    }

    //TODO
    //levels permissions
    //TODO
    //comments
}
