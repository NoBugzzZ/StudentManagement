package cn.edu.nju.StudentManagement.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.nju.StudentManagement.Exception.StudentNotFoundException;
import cn.edu.nju.StudentManagement.assembler.StudentModelAssembler;
import cn.edu.nju.StudentManagement.model.Student;
import cn.edu.nju.StudentManagement.repository.StudentRepository;

@RestController
public class StudentController {

	private final StudentRepository repository;
	private final StudentModelAssembler assembler;

	public StudentController(StudentRepository repository, StudentModelAssembler assembler) {
		this.repository = repository;
		this.assembler = assembler;
	}

	@GetMapping("/students")
	public CollectionModel<EntityModel<Student>> all() {
		List<EntityModel<Student>> students = repository.findAll().stream()
			.map(assembler::toModel)
			.collect(Collectors.toList());
		return CollectionModel.of(students, linkTo(methodOn(StudentController.class).all()).withSelfRel());
	}

	@PostMapping("/students")
	public ResponseEntity<?> newStudent(@RequestBody Student newStudent) {
		EntityModel<Student> entityModel = assembler.toModel(repository.save(newStudent));
		return ResponseEntity //
			.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
			.body(entityModel);
	}

	@GetMapping("/students/{id}")
	public EntityModel<Student> one(@PathVariable Long id) {
		Student student = repository.findById(id) //
      		.orElseThrow(() -> new StudentNotFoundException(id));

		return assembler.toModel(student);
	}

	@PutMapping("/students/{id}")
	public ResponseEntity<?> replaceStudent(@RequestBody Student newStudent, @PathVariable Long id) {
		Student updatedStudent = repository.findById(id) //
			.map(student -> {
				student.setStudentId(newStudent.getStudentId());
				student.setName(newStudent.getName());
				student.setSex(newStudent.getSex());
				student.setBirthday(newStudent.getBirthday());
				student.setNativePlace(newStudent.getNativePlace());
				student.setDepartment(newStudent.getDepartment());
				return repository.save(student);
			}) //
			.orElseGet(() -> {
				newStudent.setId(id);
				return repository.save(newStudent);
			});

		EntityModel<Student> entityModel = assembler.toModel(updatedStudent);

		return ResponseEntity //
			.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
			.body(entityModel);
  }

	@DeleteMapping("/students/{id}")
	public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
		repository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}