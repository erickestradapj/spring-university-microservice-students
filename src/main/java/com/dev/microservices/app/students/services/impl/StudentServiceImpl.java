package com.dev.microservices.app.students.services.impl;

import com.dev.microservices.app.students.repository.StudentRepository;
import com.dev.microservices.app.students.services.StudentService;
import com.dev.microservices.commons.services.impl.CommonServiceImpl;
import com.dev.microservices.commons.students.models.entity.Student;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServiceImpl extends CommonServiceImpl<Student, StudentRepository> implements StudentService {

    @Override
    @Transactional(readOnly = true)
    public List<Student> findByFirstNameOrLastName(String term) {
        return repository.findByFirstNameOrLastName(term);
    }
}
