package cn.edu.nju.StudentManagement.batch;

import org.springframework.batch.extensions.excel.RowMapper;
import org.springframework.batch.extensions.excel.support.rowset.RowSet;

import cn.edu.nju.StudentManagement.model.Student;

public class StudentExcelRowMapper implements RowMapper<Student> {
    @Override
    public Student mapRow(RowSet rowSet) throws Exception {
        Student student = new Student();
        String[] row=rowSet.getCurrentRow();
        student.setStudentId(row[0]);
        student.setName(row[1]);
        student.setDepartment(row[2]);
        student.setPhone(row[3]);
        student.setBirthday("null");
        student.setNativePlace("null");
        student.setSex("null");
        //System.out.println(student.toString());
        return student;
    }
}