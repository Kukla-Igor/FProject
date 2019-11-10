package com.findme.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;


@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "RELATIONSHIP")
public class Relationship extends IdEntity{
    @Id
    @SequenceGenerator(name = "RELATIONSHIP_SEQ", sequenceName = "RELATIONSHIP_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RELATIONSHIP_SEQ")
    @Column(name = "ID")
    private Long id;
    @Column(name = "STATUS")
    @JsonProperty("status")
    private Status status;
    @OneToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "USER_ID_FROM")
    private User userFrom;
    @OneToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "USER_ID_TO")
    private User userTo;

    public Relationship(Status status, User userFrom, User userTo) {
        this.status = status;
        this.userFrom = userFrom;
        this.userTo = userTo;
    }
}
