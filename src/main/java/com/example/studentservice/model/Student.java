package com.example.studentservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Name is required!")
    @Size(min = 2, max = 100, message = "Min length of name is 2.")
    private String name;

    @NotBlank(message = "Course is required!")
    private String course;

    @NotNull(message = "Grade is required!")
    @Positive(message = "Only positive numbers are allowed.")
    @DecimalMin(value = "60.0", message = "Grade must be at least 60.0")
    @DecimalMax(value = "100.0", message = "Grade must be at most 100.0")
    private Double grade;

    public Student() {
    }

    public Student(String name, String course, double grade) {
        this.name = name;
        this.course = course;
        this.grade = grade;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

}
