package com.unitask.entity;

import com.unitask.constant.Enum.GeneralStatus;
import com.unitask.entity.User.AppUser;
import jakarta.persistence.*;
import lombok.*;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecturer")
    private AppUser lecturer;
    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private GeneralStatus status;


}
