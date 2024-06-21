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
public class Ticket extends BaseEntity {

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
    @Column(columnDefinition = "tinyint(0) default 0")
    private boolean isDeleted;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private AppUser author;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_person_id")
    private AppUser assignedPerson;

}
