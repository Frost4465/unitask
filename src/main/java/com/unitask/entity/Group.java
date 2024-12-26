package com.unitask.entity;

import com.unitask.entity.assessment.Assessment;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "`group`")
@AllArgsConstructor
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "assessment_id")
    private Assessment assessment;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private Set<StudentAssessment> studentAssessment;
}