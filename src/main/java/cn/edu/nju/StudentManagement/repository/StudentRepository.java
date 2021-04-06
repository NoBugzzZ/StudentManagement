package cn.edu.nju.StudentManagement.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.nju.StudentManagement.model.Student;

public interface StudentRepository extends Repository<Student, Integer> {

    @Transactional(readOnly = true)
    public List<Student> findAll();

    @Transactional(readOnly = true)
    public Student findByStudentId(String studentId);

    void save(Student student) throws DataAccessException;

    void delete(Student student) throws DataAccessException;
}