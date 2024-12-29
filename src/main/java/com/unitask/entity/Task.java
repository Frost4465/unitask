package com.unitask.entity;

import com.unitask.entity.User.AppUser;
import com.unitask.entity.assessment.Assessment;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "task")
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn
    private Assessment assessment;
    @ManyToOne
    @JoinColumn
    private AppUser user;
    @Column(name = "checked", columnDefinition = "0")
    private boolean checked;

}
