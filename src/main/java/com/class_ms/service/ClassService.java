package com.class_ms.service;

import com.class_ms.data.Class;
import com.class_ms.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassService {

    @Autowired
    private ClassRepository classRepo;

    public Class addClass()
}
