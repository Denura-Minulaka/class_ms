package com.class_ms.controller;

import com.class_ms.data.Class;
import com.class_ms.dto.ClassRequest;
import com.class_ms.dto.ClassResponse;
import com.class_ms.security.JwtUtil;
import com.class_ms.service.ClassService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/classes")
@CrossOrigin(origins = "http://localhost:5173")
public class ClassController {

    @Autowired
    private ClassService classService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/add")
    public ResponseEntity<ClassResponse> addClass(@Valid @RequestBody ClassRequest req,
                                                  @RequestHeader("Authorization") String authHeader) {
        Integer teacherId = jwtUtil.extractTeacherId(authHeader);
        Class c = classService.addClass(req, teacherId);
        return ResponseEntity.ok(mapToResponse(c));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ClassResponse> updateClass(@PathVariable Integer id,
                                                     @Valid @RequestBody ClassRequest req,
                                                     @RequestHeader("Authorization") String authHeader) {

        Integer teacherId = jwtUtil.extractTeacherId(authHeader);
        Class c = classService.updateClass(id, req, teacherId);
        return ResponseEntity.ok(mapToResponse(c));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable Integer id,
                                            @RequestHeader("Authorization") String authHeader) {

        Integer teacherId = jwtUtil.extractTeacherId(authHeader);
        classService.deleteClass(id, teacherId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassResponse> getClassById(@PathVariable Integer id) {
        return ResponseEntity.ok(mapToResponse(classService.getClassById(id)));
    }

    @GetMapping("/list")
    public ResponseEntity<?> listClasses(
            @RequestParam(required = false) String district,
            @RequestParam(required = false) String town,
            @RequestParam(required = false) String grade,
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return ResponseEntity.ok(
                classService.listClasses(district, town, grade, subject, keyword, page, size, sortBy)
                        .stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/my-classes")
    public ResponseEntity<List<ClassResponse>> getClassesByTeacher(@RequestHeader("Authorization") String authHeader) {
        Integer teacherId = jwtUtil.extractTeacherId(authHeader);
        return ResponseEntity.ok(
                classService.getClassesByTeacher(teacherId)
                        .stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList())
        );
    }

    private ClassResponse mapToResponse(Class c) {
        ClassResponse r = new ClassResponse();
        r.setId(c.getId());
        r.setClassName(c.getClassName());
        r.setDescription(c.getDescription());
        r.setSubject(c.getSubject());
        r.setGrade(c.getGrade());
        r.setLanguage(c.getLanguage());
        r.setMode(c.getMode());
        r.setDistrict(c.getDistrict());
        r.setTown(c.getTown());
        r.setPrice(c.getPrice());
        r.setTeacherId(c.getTeacherId());
        return r;
    }
}
