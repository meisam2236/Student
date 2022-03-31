package com.student.app.model.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchoolDto implements Serializable {
    private static final long serialVersionUID = 2;
    private long id;
    private String name;
    private List<StudentDto> students;
    private Double lat;
    private Double lon;
}
