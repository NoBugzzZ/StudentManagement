package cn.edu.nju.StudentManagement.db;

import java.sql.*;
import java.util.LinkedList;

import cn.edu.nju.StudentManagement.model.Student;

public class DAO {
    private Connection conn = null;
    private String tableName;

    public DAO(String databaseName, String tableName) {
        this.tableName = tableName;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + databaseName + ".db");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
            System.exit(0);
        }
    }

    public LinkedList<Student> findAll() {
        LinkedList<Student> students = new LinkedList<Student>();
        try {
            String sql = "SELECT * FROM " + tableName + ";";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getString("id"));
                student.setName(rs.getString("name"));
                student.setSex(rs.getString("sex"));
                student.setBirthday(rs.getString("birthday"));
                student.setNativePlace(rs.getString("nativePlace"));
                student.setDepartment(rs.getString("department"));
                students.add(student);
            }
            stmt.close();
            rs.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
            System.exit(0);
        }

        return students;
    }

    public Student findById(String id) {
        boolean flag=false;
        Student student=new Student();
        try {
            String sql = "SELECT * FROM " + tableName + " WHERE id='" + id + "';";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                flag=true;
                student.setId(rs.getString("id"));
                student.setName(rs.getString("name"));
                student.setSex(rs.getString("sex"));
                student.setBirthday(rs.getString("birthday"));
                student.setNativePlace(rs.getString("nativePlace"));
                student.setDepartment(rs.getString("department"));
            }
            stmt.close();
            rs.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
            System.exit(0);
        }
        if(flag==false){
            return null;
        }
        return student;
    }

    public boolean insert(Student student) {
        boolean isSuccess = true;
        try {
            String sql = "INSERT INTO " + tableName + " (id,name,sex,birthday,nativePlace,department) " + "VALUES ('"
                    + student.getId() + "','" + student.getName() + "','" + student.getSex() + "','"
                    + student.getBirthday() + "','" + student.getNativePlace() + "','" + student.getDepartment()
                    + "');";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
            System.exit(0);
        }
        return isSuccess;
    }

    public boolean delete(String id) {
        boolean isSuccess = true;
        try {
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM "+tableName+" where id='" + id + "';";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
            System.exit(0);
        }
        return isSuccess;
    }

    public boolean modify(Student student) {
        boolean isSuccess = true;
        try {
            Statement stmt = conn.createStatement();
            String sql = "UPDATE "+tableName+" SET name='" + student.getName() + "',sex='" + student.getSex() + "',birthday='"
                    + student.getBirthday() + "',nativePlace='" + student.getNativePlace() + "',department='"
                    + student.getDepartment() + "' " + "WHERE id='" + student.getId() + "';";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
            System.exit(0);
        }
        return isSuccess;
    }

    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}