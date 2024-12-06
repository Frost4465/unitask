package com.unitask.entity.assessment;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.unitask.constant.Enum.GeneralStatus;
import com.unitask.entity.StudentAssessment;
import com.unitask.entity.Subject;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "assessment")
@AllArgsConstructor
public class Assessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "weightage")
    private String weightage;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    @ToString.Exclude
    private Subject subject;

    @Column
    @Enumerated(EnumType.STRING)
    private GeneralStatus generalStatus;

    @OneToMany(mappedBy = "assessment", orphanRemoval = true)
    List<StudentAssessment> studentAssessments;

    @Column
    private String assignmentMode;

    @Column
    private LocalDate dueDate;

    @Column(columnDefinition = "text")
    private String lecturerInstruction;

    @OneToMany(mappedBy = "assessment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AssessmentMarkingRubric> assessmentMarkingRubrics = new HashSet<>();

    @OneToMany(mappedBy = "assessment", orphanRemoval = true)
    private Set<AssessmentFile> attachedFile;

}
