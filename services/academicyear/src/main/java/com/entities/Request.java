package com.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long studentId;
    private Long academicYearId;
}
