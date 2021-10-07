package org.alga.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.hibernate.annotations.LazyCollection
import org.hibernate.annotations.LazyCollectionOption
import javax.persistence.*

@Entity
@Table(name = "lecturer", schema = "publications")
data class Lecturer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    var id: Long? = null,

    @Column(nullable = false, name = "first_name")
    var firstName: String,

    @Column(nullable = false, name = "last_name")
    var lastName: String,

    @ManyToMany(mappedBy = "lecturers")
    @JsonIgnoreProperties("lecturers")
    var publications: MutableList<Publication> = mutableListOf(),

    @ManyToMany(mappedBy = "citations")
    @JsonIgnoreProperties("citations")
    var publicationCitations: MutableList<Publication> = mutableListOf(),

    @ManyToMany(cascade = [CascadeType.MERGE])
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
        name = "lecturer_interest_group",
        joinColumns = [JoinColumn(name = "lecturer_id")],
        inverseJoinColumns = [JoinColumn(name = "interest_group_id")]
    )
    @JsonIgnoreProperties("lecturers")
    var interestGroups: MutableList<InterestGroup> = mutableListOf()
)
