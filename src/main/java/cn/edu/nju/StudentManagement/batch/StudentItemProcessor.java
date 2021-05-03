package cn.edu.nju.StudentManagement.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import cn.edu.nju.StudentManagement.model.Student;

public class StudentItemProcessor implements ItemProcessor<Student, Student> {
    private static final Logger log = LoggerFactory.getLogger(StudentItemProcessor.class);

    @Override
    public Student process(final Student student) throws Exception {

        final String studentId = student.getStudentId();
        final String name = student.getName();
        final String sex = student.getSex();
        final String birthday = student.getBirthday();
        final String nativePlace = student.getNativePlace();
        final String department = student.getDepartment();
        final String phone = student.getPhone();

        final Student transformedStudent = new Student(studentId, name, sex, birthday, nativePlace, department, phone);

        log.info("Converting (" + student + ") into (" + transformedStudent + ")");

        return transformedStudent;
    }
}