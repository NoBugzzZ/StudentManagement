package cn.edu.nju.StudentManagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import cn.edu.nju.StudentManagement.model.Student;
import cn.edu.nju.StudentManagement.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    private StudentRepository studentRepository;

    @Override
    @Cacheable(value = "students")
    public List<Student> findAll(){
        return studentRepository.findAll();
    }

    @Override
    @Cacheable(value = "students",key = "#studentId")
    public Student findByStudentId(String studentId){
        return studentRepository.findByStudentId(studentId);
    }

    @Override
    @CacheEvict(value = "students", allEntries = true)
    public void save(Student student){
        studentRepository.save(student);
    }

    @Override
    @CacheEvict(value = "students", allEntries = true)
    public void delete(Student student){
        studentRepository.delete(student);
    }

}