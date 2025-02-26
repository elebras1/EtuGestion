package com.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "TeachingUnits")
public class TeachingUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
    private Boolean isRequired;
    private Short capacity;
    @ManyToOne
    @JoinColumn(name = "academic_year_id")
    private AcademicYear academicYear;
    private Long responsibleId;
    @ElementCollection
    private List<Long> studentsIds;
}
