package com.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "AcademicYears")
public class AcademicYear {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
    private Short praticalWorkSize;
    private Short directedWorkSize;
    private Short numberOptionalTeachingUnit;
    private Long responsibleId;
    @OneToMany(mappedBy = "academicYear")
    private List<Group> groups;
    @OneToMany(mappedBy = "academicYear")
    private List<TeachingUnit> teachingUnits;
}
