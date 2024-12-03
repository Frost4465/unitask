package com.unitask.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.unitask.constant.Enum.GeneralStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    private Long Id;
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
    @OneToMany(mappedBy = "assessment", cascade = CascadeType.ALL)
    List<StudentAssessment> studentAssessments;

}
