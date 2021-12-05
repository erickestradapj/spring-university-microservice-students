package com.dev.microservices.app.students.services;

import com.dev.microservices.commons.services.CommonService;
import com.dev.microservices.commons.students.models.entity.Student;

import java.util.List;

public interface StudentService extends CommonService<Student> {
    
    List<Student> findByFirstNameOrLastName(String term);
}
