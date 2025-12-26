package com.class_ms.service;

import com.class_ms.data.ClassSchedule;
import com.class_ms.dto.ClassScheduleRequest;
import com.class_ms.repository.ClassScheduleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ClassScheduleService {

    @Autowired
    private ClassScheduleRepository scheduleRepo;

    public ClassSchedule addSchedule(ClassScheduleRequest req) {
        ClassSchedule s = new ClassSchedule();
        s.setClassId(req.getClassId());
        s.setDayOfWeek(req.getDayOfWeek());
        s.setStartTime(req.getStartTime());
        s.setEndTime(req.getEndTime());
        return scheduleRepo.save(s);
    }

    @Transactional
    public ClassSchedule updateSchedule(Integer id, ClassScheduleRequest req) {
        ClassSchedule s = scheduleRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found"));
        s.setDayOfWeek(req.getDayOfWeek());
        s.setStartTime(req.getStartTime());
        s.setEndTime(req.getEndTime());
        return scheduleRepo.save(s);
    }

    public void deleteSchedule(Integer id) {
        ClassSchedule s = scheduleRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found"));
        scheduleRepo.delete(s);
    }

    public List<ClassSchedule> listSchedules(Integer classId) {
        return scheduleRepo.findByClassId(classId);
    }
}
