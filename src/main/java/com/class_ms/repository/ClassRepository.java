package com.class_ms.repository;

import com.class_ms.data.Class;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClassRepository extends JpaRepository<Class, Integer> {

    Optional<Class> findByIdAndTeacherId(Integer id, Integer teacherId);

    @Query("SELECT c FROM Class c WHERE " +
        "(:district IS NULL OR c.district = district) AND " +
        "(:town IS NULL OR c.town = town) AND " +
        "(:grade IS NULL OR c.grade = grade) AND " +
        "(:subject IS NULL OR c.subject = subject) AND " +
        "(:keyword IS NULL OR c.className LIKE %:keyword% OR c.description LIKE %:keyword% OR c.subject LIKE %:keyword%)")
    Page<Class> filterSearch(String district, String town, String grade, String subject, String keyword, Pageable pageable);
}
