package com.unitask.entity;

import com.unitask.constant.Enum.GeneralStatus;
import com.unitask.entity.User.AppUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "student_assessment")
@AllArgsConstructor
public class StudentAssessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @ManyToOne
    @JoinColumn
    private AppUser user;
    @ManyToOne
    @JoinColumn
    private Assessment assessment;
    private LocalDate enrollmentDate;
    @Enumerated(EnumType.STRING)
    private GeneralStatus status;
}
