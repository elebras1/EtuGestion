package com.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "Groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    @ManyToOne
    @JoinColumn(name = "academic_year_id")
    private AcademicYear academicYear;
    @ElementCollection
    private List<Long> studentsIds;
}
