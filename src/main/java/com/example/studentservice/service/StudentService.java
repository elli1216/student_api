package com.example.studentservice.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.studentservice.model.Student;
import com.example.studentservice.repository.StudentRepository;

@Service
public class StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        logger.info("Fetching all students");
        List<Student> students = studentRepository.findAll();
        logger.info("Founde {} students", students.size());
        return students;
    }

    public Student getStudentById(Long id) {
        logger.info("Fetching student by Id: {}", id);
        return studentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Student Id not found: {}", id);
                    return new RuntimeException("Student not found: " + id);
                });
    }

    public Student createStudent(Student student) {
        logger.info("Creating student: name={}, course={}", student.getName(), student.getCourse());
        Student saved = studentRepository.save(student);
        logger.info("Student created with id: {}", saved.getId());
        return saved;
    }

    public Student updateStudent(Long id, Student updatedStudent) {
        logger.info("Updating student: {}", id);
        Student existing = getStudentById(id);
        existing.setName(updatedStudent.getName());
        existing.setCourse(updatedStudent.getCourse());
        existing.setGrade(updatedStudent.getGrade());
        Student saved = studentRepository.save(existing);
        logger.info("Student updated: id={}, name={}", saved.getId(), saved.getName());
        return saved;
    }

    public void deleteStudent(Long id) {
        logger.info("Deleting student with id: {}", id);
        Student existing = getStudentById(id);
        studentRepository.deleteById(existing.getId());
        logger.info("Student deleted: {}", id);
    }

    public List<Student> getStudentsByName(String name) {
        logger.info("Getting students by name: {}", name);
        return studentRepository.findByName(name);
    }

    public List<Student> getStudentsByCourse(String course) {
        logger.info("Getting students by course: {}", course);
        return studentRepository.findByCourse(course);
    }

    public List<Student> getStudentsByNameContaining(String keyword) {
        logger.info("Getting students by name containing: {}", keyword);
        return studentRepository.findByNameContaining(keyword);
    }

    public List<Student> getStudentsWithGradeAbove(double grade) {
        logger.info("Getting students by grade above: {}", grade);
        return studentRepository.findByGradeGreaterThan(grade);
    }

    public List<Student> getStudentsWithGradeBelow(double grade) {
        logger.info("Getting students by grade below: {}", grade);
        return studentRepository.findByGradeLessThan(grade);
    }

    public long countStudentsByCourse(String course) {
        logger.info("Counting students in course: {}", course);
        long count = studentRepository.countByCourse(course);
        logger.info("Found {} students in course: {}", count, course);
        return count;
    }
}
