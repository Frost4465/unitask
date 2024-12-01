package com.unitask.entity;

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
@Table(name = "subject")
@AllArgsConstructor
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(name = "name")
    private String name;
    @Column(name = "code")
    private String code;
    @Column(name = "course")
    private String course;
    @Column(name = "creditHour")
    private Integer creditHour;
    @Column(name = "description")
    private String description;
    @Column(name = "learningOutcome")
    private String learningOutcome;
    @Column(name = "lecturerName")
    private String lecturerName;
    @Column(name = "lecturerContact")
    private String lecturerContact;
    @Column(name = "lecturerEmail")
    private String lecturerEmail;
    @Column(name = "lecturerOffice")
    private String lecturerOffice;
    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private GeneralStatus status;
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<Assessment> assessment;

}
