package com.class_ms.repository;

import com.class_ms.data.ClassSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface classScheduleRepository extends JpaRepository<ClassSchedule, Integer> {

    List<ClassSchedule> findByClassId(Integer classId);
}
