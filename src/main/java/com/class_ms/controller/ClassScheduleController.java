package com.class_ms.controller;

import com.class_ms.data.ClassSchedule;
import com.class_ms.dto.ClassScheduleRequest;
import com.class_ms.dto.ClassScheduleResponse;
import com.class_ms.service.ClassScheduleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/schedules")
@CrossOrigin(origins = "http://localhost:5173")
public class ClassScheduleController {

    @Autowired
    private ClassScheduleService scheduleService;

    @PostMapping("/add")
    public ResponseEntity<ClassScheduleResponse> addSchedule(@Valid @RequestBody ClassScheduleRequest req) {
        return ResponseEntity.ok(mapToResponse(scheduleService.addSchedule(req)));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ClassScheduleResponse> updateSchedule(@PathVariable Integer id,
                                                                 @Valid @RequestBody ClassScheduleRequest req) {
        return ResponseEntity.ok(mapToResponse(scheduleService.updateSchedule(id, req)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Integer id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/list/{classId}")
    public ResponseEntity<List<ClassScheduleResponse>> listSchedule(@PathVariable Integer classId) {
        List<ClassScheduleResponse> list = scheduleService.listSchedules(classId)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassScheduleResponse> getSchedule(@PathVariable Integer id) {
        return ResponseEntity.ok(mapToResponse(scheduleService.getScheduleById(id)));
    }

    private ClassScheduleResponse mapToResponse(ClassSchedule s) {
        ClassScheduleResponse r = new ClassScheduleResponse();
        r.setId(s.getId());
        r.setClassId(s.getClassId());
        r.setDayOfWeek(s.getDayOfWeek());
        r.setStartTime(s.getStartTime());
        r.setEndTime(s.getEndTime());
        return r;
    }
}
