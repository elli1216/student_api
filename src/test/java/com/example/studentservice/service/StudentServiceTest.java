package com.example.studentservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.studentservice.model.Student;
import com.example.studentservice.repository.StudentRepository;

public class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllStudents() {
        Student s1 = new Student("LeBron James", "Information Technology", 88.76);
        Student s2 = new Student("Stephen Curry", "Computer Science", 91.2);
        List<Student> fakStudents = Arrays.asList(s1, s2);

        when(studentRepository.findAll()).thenReturn(fakStudents);
        List<Student> result = studentService.getAllStudents();

        assertEquals(2, result.size());
        assertEquals("LeBron James", result.get(0).getName());
        assertEquals("Stephen Curry", result.get(1).getName());

        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void testGetStudentById_Found() {
        Student s1 = new Student("LeBron James", "Information Technology", 88.76);
        s1.setId(1L);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(s1));
        Student result = studentService.getStudentById(1L);

        assertNotNull(result);
        assertEquals("LeBron James", result.getName());
        assertEquals(1L, result.getId());

        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetStudentById_NotFound() {
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            studentService.getStudentById(99L);
        });

        assertEquals("Student not found: 99", exception.getMessage());
        verify(studentRepository, times(1)).findById(99L);
    }

    @Test
    void testCreatStudent() {
        Student toSave = new Student("Jayson Tatum", "Computer Science", 78.2);
        Student saved = new Student("Jayson Tatum", "Computer Science", 78.2);
        saved.setId(1L);

        when(studentRepository.save(toSave)).thenReturn(saved);
        Student result = studentService.createStudent(toSave);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Jayson Tatum", result.getName());

        verify(studentRepository, times(1)).save(toSave);
    }

    @Test
    void updateStudent() {
        Student existing = new Student("LeBron James", "Information Technology", 88.76);
        existing.setId(1L);

        Student updatedData = new Student("LeBron James", "Computer Science", 85.76);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(studentRepository.save(existing)).thenReturn(existing);
        Student result = studentService.updateStudent(1L, updatedData);

        assertEquals("Computer Science", result.getCourse());
        assertEquals(85.76, result.getGrade());

        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).save(existing);
    }

    @Test
    void deleteStudent() {
        Student s1 = new Student("LeBron James", "Information Technology", 88.76);
        s1.setId(1L);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(s1));
        doNothing().when(studentRepository).deleteById(1L);
        studentService.deleteStudent(1L);

        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).deleteById(1L);
    }

}
