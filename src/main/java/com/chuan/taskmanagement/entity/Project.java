package com.chuan.taskmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "project")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Project extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @ElementCollection
    @Column
    private List<String> members;
    @Column(columnDefinition = "tinyint(0) default 0")
    private boolean isDeleted;
}
