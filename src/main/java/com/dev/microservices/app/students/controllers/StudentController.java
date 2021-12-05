package com.dev.microservices.app.students.controllers;

import com.dev.microservices.app.students.services.StudentService;
import com.dev.microservices.commons.controllers.CommonController;
import com.dev.microservices.commons.students.models.entity.Student;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@RestController
public class StudentController extends CommonController<Student, StudentService> {

    @GetMapping("/uploads/img/{id}")
    public ResponseEntity<?> viewPhoto(@PathVariable Long id) {

        Optional<Student> o = service.findById(id);

        if (o.isEmpty() || o.get().getPhoto() == null) {
            return ResponseEntity.notFound().build();
        }

        Resource image = new ByteArrayResource(o.get().getPhoto());

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@Valid @RequestBody Student student, BindingResult result, @PathVariable Long id) {

        if (result.hasErrors()) {
            return this.validate(result);
        }

        Optional<Student> o = service.findById(id);

        if (o.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Student studentDB = o.get();
        studentDB.setFirstName(student.getFirstName());
        studentDB.setLastName(student.getLastName());
        studentDB.setEmail(student.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(studentDB));
    }

    @GetMapping("/filter/{term}")
    public ResponseEntity<?> filter(@PathVariable String term) {
        return ResponseEntity.ok(service.findByFirstNameOrLastName(term));
    }

    @PostMapping("/create-with-photo")
    public ResponseEntity<?> createWithPhoto(
            @Valid Student student,
            BindingResult result,
            @RequestParam MultipartFile file
    ) throws IOException {
        if (!file.isEmpty()) {
            student.setPhoto(file.getBytes());
        }

        return super.create(student, result);
    }

    @PutMapping("/edit-with-photo/{id}")
    public ResponseEntity<?> editWithPhoto(
            @Valid Student student,
            BindingResult result,
            @PathVariable Long id,
            @RequestParam MultipartFile file
    ) throws IOException {

        if (result.hasErrors()) {
            return this.validate(result);
        }

        Optional<Student> o = service.findById(id);

        if (o.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Student studentDB = o.get();
        studentDB.setFirstName(student.getFirstName());
        studentDB.setLastName(student.getLastName());
        studentDB.setEmail(student.getEmail());

        if (!file.isEmpty()) {
            studentDB.setPhoto(file.getBytes());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(studentDB));
    }
}
