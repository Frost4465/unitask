package com.unitask.entity;

import com.unitask.entity.assessment.Assessment;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false, unique = true)
    private String uuid;

    @Column
    private LocalDateTime fileCreatedDate;

    @Column
    private Boolean openForPublic = false;

    @Column
    private Boolean locked = false;

}