package cn.edu.nju.StudentManagement.service;

import java.util.List;

import cn.edu.nju.StudentManagement.model.Student;

public interface StudentService {
    List<Student> findAll();
    Student findByStudentId(String studentId);
    void save(Student student);
    void delete(Student student);
}