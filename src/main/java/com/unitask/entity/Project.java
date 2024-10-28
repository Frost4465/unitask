package com.unitask.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "project")
@AllArgsConstructor
@NoArgsConstructor
public class Project extends AuditedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column( nullable = false, unique = true)
    private String code;
    @Column
    private String description;
    @ManyToOne
    @JoinColumn(name = "leader_id")
    private AppUser leader;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "project")
    private Set<ProjectMember> projectMembers;
}
