package com.student.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedisStudent  implements Serializable {
    private long id;
    private String firstName;
    private String lastName;
    private float grade;
}
