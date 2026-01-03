package com.class_ms.service;

import com.class_ms.data.Class;
import com.class_ms.dto.ClassRequest;
import com.class_ms.repository.ClassRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ClassService {

    @Autowired
    private ClassRepository classRepo;

    public Class addClass(ClassRequest req, Integer teacherId) {
        Class c = new Class();
        c.setClassName(req.getClassName());
        c.setDescription(req.getDescription());
        c.setSubject(req.getSubject());
        c.setGrade(req.getGrade());
        c.setLanguage(req.getLanguage());
        c.setMode(req.getMode());
        c.setDistrict(req.getDistrict());
        c.setTown(req.getTown());
        c.setPrice(req.getPrice());
        c.setTeacherId(teacherId);
        return classRepo.save(c);
    }

    @Transactional
    public Class updateClass(Integer classId, ClassRequest req, Integer teacherId) {
        Class c = classRepo.findByIdAndTeacherId(classId, teacherId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Not owner or class not found"));
        c.setClassName(req.getClassName());
        c.setDescription(req.getDescription());
        c.setSubject(req.getSubject());
        c.setGrade(req.getGrade());
        c.setLanguage(req.getLanguage());
        c.setMode(req.getMode());
        c.setDistrict(req.getDistrict());
        c.setTown(req.getTown());
        c.setPrice(req.getPrice());
        return classRepo.save(c);
    }

    public void deleteClass(Integer classId, Integer teacherId) {
        Class c = classRepo.findByIdAndTeacherId(classId, teacherId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Not owner or class not found"));
        classRepo.delete(c);
    }

    public Class getClassById(Integer id) {
        return classRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Class not found"));
    }

    public Page<Class> listClasses(String district, String town, String grade, String subject, String keyword, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return classRepo.filterSearch(district, town, grade, subject, keyword, pageable);
    }

    public List<Class> getClassesByTeacher(Integer teacherId) {
        return classRepo.findByTeacherId(teacherId);
    }
}
