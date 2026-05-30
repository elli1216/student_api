package com.example.studentservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.studentservice.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByName(String name);

    List<Student> findByCourse(String course);

    List<Student> findByNameContaining(String keyword);

    List<Student> findByGradeLessThan(double grade);

    List<Student> findByGradeGreaterThan(double grade);

    long countByCourse(String course);

    @Query("SELECT s FROM Student s WHERE LOWER(s.course) = LOWER(:course)")
    List<Student> findByCourseIgnoreCase(@Param("course") String course);

    @Query("SELECT s FROM Student s WHERE s.name LIKE %:keyword% OR s.course LIKE %:keyword%")
    List<Student> searchByKeyword(@Param("keyword") String keyword);
}
