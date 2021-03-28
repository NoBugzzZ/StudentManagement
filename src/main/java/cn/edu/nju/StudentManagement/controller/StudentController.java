package cn.edu.nju.StudentManagement.controller;

import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.nju.StudentManagement.db.DAO;
import cn.edu.nju.StudentManagement.model.Student;

@Controller
public class StudentController {

    private String databaseName="src/main/resources/db/student";
    private String tableName="stuinfo";

    @GetMapping("/students/new")
	public String initCreationForm(Map<String, Object> model) {
		Student student = new Student();
		model.put("student", student);
		return "students/createStudent";
	}

	@PostMapping("/students/new")
	public String processCreationForm(Student student) {
        DAO dao=new DAO(databaseName, tableName);
        if(dao.findById(student.getId())==null){
            dao.insert(student);
            dao.closeConnection();
			return "redirect:/students/" + student.getId();
        }
		else {
            return "students/createStudent";
		}
	}

    @GetMapping("/students/{studentId}/modify")
	public String initModifyForm(@PathVariable("studentId") String studentId,Model model) {
        Student student=new Student();
        student.setId(studentId);
		model.addAttribute(student);
		return "students/modifyStudent";
	}

	@PostMapping("/students/{studentId}/modify")
	public String processModifyForm(@PathVariable("studentId") String studentId,Student student) {
        DAO dao=new DAO(databaseName,tableName);
        //student.setId(studentId);
        dao.modify(student);
        dao.closeConnection();
		return "redirect:/students/" + student.getId();
	}

    @GetMapping("/students/{studentId}/delete")
	public String processDelete(@PathVariable("studentId") String studentId,Map<String, Object> model) {
        DAO dao=new DAO(databaseName,tableName);
        dao.delete(studentId);
        LinkedList<Student> students=new LinkedList<Student>();
		students=dao.findAll();
        dao.closeConnection();
        model.put("students", students);
		return "students/studentsList";
	}

    @GetMapping("/students/find")
	public String initFindForm(Map<String, Object> model) {
        model.put("student", new Student());
		return "students/findStudents";
    }

    @GetMapping("/students")
	public String processFindForm(Student student,Map<String, Object> model) {

		// allow parameterless GET request for /owners to return all records
		if (student.getId() == "") {
            LinkedList<Student> students=new LinkedList<Student>();
		    DAO dao=new DAO(databaseName, tableName);
		    students=dao.findAll();
            dao.closeConnection();
            model.put("students", students);
			return "students/studentsList";
		}else{
            Student stu=new Student();
            DAO dao=new DAO(databaseName, tableName);
            stu=dao.findById(student.getId());
            dao.closeConnection();
            if(stu.getName().isEmpty()){
			    return "owners/findOwners";
            }else{
                return "redirect:/students/" + stu.getId();
            }
        }

	}

    @GetMapping("/students/{studentId}")
	public ModelAndView showOwner(@PathVariable("studentId") String studentId) {
		ModelAndView mav = new ModelAndView("students/studentDetails");
		Student student=new Student();
        DAO dao=new DAO(databaseName, tableName);
        student=dao.findById(studentId);
        dao.closeConnection();
		mav.addObject(student);
		return mav;
	}
}