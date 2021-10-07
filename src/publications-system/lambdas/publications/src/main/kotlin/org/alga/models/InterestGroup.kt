package org.alga.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*

@Entity
@Table(name = "interest_group", schema = "publications")
data class InterestGroup(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    var id: Long? = null,

    @Column(nullable = false, name = "title")
    var title: String,

    @ManyToMany(mappedBy = "interestGroups")
    @JsonIgnoreProperties("interestGroups")
    var lecturers: MutableList<Lecturer>  = mutableListOf()
)
