package cn.edu.nju.StudentManagement.Exception;

public class StudentNotFoundException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public StudentNotFoundException(Long id) {
        super("Could not find student " + id);
      }
}