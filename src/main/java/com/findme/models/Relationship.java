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
    @JsonProperty("status ")
    private Status status;
    @ManyToOne
    @JoinColumn(name="ID", nullable=false, insertable=false, updatable=false)
    private User userFrom;
    @ManyToOne
    @JoinColumn(name="ID", nullable=false, insertable=false, updatable=false)
    private User userTo;
}
