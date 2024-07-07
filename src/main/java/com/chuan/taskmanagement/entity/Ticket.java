package com.chuan.taskmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "ticket")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ticket extends AuditedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private String status;
    @Column
    private Integer storyPoints;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private AppUser author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assigned_person_id")
    private AppUser assignedPerson;

    @ManyToOne(optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

}
