package org.alga.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.hibernate.annotations.LazyCollection
import org.hibernate.annotations.LazyCollectionOption
import javax.persistence.*

@Entity
@Table(name = "publication", schema = "publications")
data class Publication(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    var id: Long? = null,

    @Column(nullable = false, name = "title")
    var title: String,

    @Column(nullable = false, name = "text")
    var text: String,

    @ManyToMany(cascade = [CascadeType.MERGE])
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
        name = "lecturer_publication",
        joinColumns = [JoinColumn(name = "lecturer_id")],
        inverseJoinColumns = [JoinColumn(name = "publication_id")]
    )
    @JsonIgnoreProperties("publications")
    var lecturers: MutableList<Lecturer> = mutableListOf(),

    @ManyToMany(cascade = [CascadeType.MERGE])
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
        name = "citations",
        joinColumns = [JoinColumn(name = "lecturer_id")],
        inverseJoinColumns = [JoinColumn(name = "publication_id")]
    )
    @JsonIgnoreProperties("publicationCitations")
    var citations: MutableList<Lecturer> = mutableListOf()
)
