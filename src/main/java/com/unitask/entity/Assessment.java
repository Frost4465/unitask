package com.unitask.entity;

import com.unitask.constant.Enum.GeneralStatus;
import jakarta.persistence.*;
import lombok.*;

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
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private Subject subject;
    @Column
    @Enumerated(EnumType.STRING)
    private GeneralStatus generalStatus;

}
