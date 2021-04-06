package cn.edu.nju.StudentManagement.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.nju.StudentManagement.model.Student;
import cn.edu.nju.StudentManagement.service.StudentService;

@Controller
public class StudentController {

	@Autowired
	private StudentService studentService;

    @GetMapping("/students/new")
	public String initCreationForm(Map<String, Object> model) {
		Student student = new Student();
		model.put("student", student);
		return "students/createStudent";
	}

	@PostMapping("/students/new")
	public String processCreationForm(Student student) {
        Student stu=studentService.findByStudentId(student.getStudentId());
        if(stu==null){
            studentService.save(student);
			return "redirect:/students/" + student.getStudentId();
        }
		else {
            return "students/createStudent";
		}
	}

    @GetMapping("/students/{studentId}/modify")
	public String initModifyForm(@PathVariable("studentId") String studentId,Model model) {
        Student student=new Student();
        student.setStudentId(studentId);
		model.addAttribute(student);
		return "students/modifyStudent";
	}

	@PostMapping("/students/{studentId}/modify")
	public String processModifyForm(@PathVariable("studentId") String studentId,Student student) {
        student.setId(studentService.findByStudentId(studentId).getId());
        studentService.save(student);
		return "redirect:/students/" + student.getStudentId();
	}

    @GetMapping("/students/{studentId}/delete")
	public String processDelete(@PathVariable("studentId") String studentId,Map<String, Object> model) {
        studentService.delete(studentService.findByStudentId(studentId));
        List<Student> students=studentService.findAll();
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

		if (student.getStudentId() == "") {
            List<Student> students=studentService.findAll();
            model.put("students", students);
			return "students/studentsList";
		}else{
            Student stu=studentService.findByStudentId(student.getStudentId());
            if(stu==null){
			    return "students/findStudents";
            }else{
                return "redirect:/students/" + stu.getStudentId();
            }
        }

	}

    @GetMapping("/students/{studentId}")
	public ModelAndView showOwner(@PathVariable("studentId") String studentId) {
		ModelAndView mav = new ModelAndView("students/studentDetails");
        Student student=studentService.findByStudentId(studentId);
		mav.addObject(student);
		return mav;
	}
}