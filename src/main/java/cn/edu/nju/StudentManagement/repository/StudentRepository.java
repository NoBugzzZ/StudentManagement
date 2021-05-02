package cn.edu.nju.StudentManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cn.edu.nju.StudentManagement.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

    // @Transactional(readOnly = true)
    // public List<Student> findAll();

    // @Transactional(readOnly = true)
    // public Student findById(Long id);

    // @Transactional(readOnly = true)
    // public Student findByStudentId(String studentId);

    // public Student save(Student student) throws DataAccessException;

    // void delete(Student student) throws DataAccessException;
}