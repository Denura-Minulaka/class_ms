package com.class_ms.repository;

import com.class_ms.data.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Integer> {

    List<Rating> findByClassId(Integer classId);

    Optional<Rating> findByStudentIdAndClassId(Integer studentId, Integer classId);

    //classId, avgRating, ratingCount
    @Query("SELECT r.classId, AVG(r.rating), COUNT(r.rating) FROM Rating r GROUP BY r.classId")
    List<Object[]> findAverageRatingWithCount();
}
