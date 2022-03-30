package com.student.app.model.repr;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolRepr implements Serializable {
    private static final long serialVersionUID = 2;
    private long id;
    private String name;
    private List<StudentRepr> students;
    private Double lat;
    private Double lon;
}
