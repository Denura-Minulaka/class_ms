package com.class_ms.service;

import com.class_ms.data.Rating;
import com.class_ms.repository.RatingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepo;

    @Transactional
    public Rating addOrUpdateRating(Integer studentId, Integer classId, Double ratingValue, String review) {

        Optional<Rating> existing = ratingRepo.findByStudentIdAndClassId(studentId, classId);

        if (existing.isPresent()) {
            Rating r = existing.get();
            r.setRating(ratingValue);
            r.setReview(review);
            return ratingRepo.save(r);
        }

        Rating r = new Rating();
        r.setStudentId(studentId);
        r.setClassId(classId);
        r.setRating(ratingValue);
        r.setReview(review);
        return ratingRepo.save(r);
    }

    public Map<Integer, Object[]> getAverageRatings() {

        List<Object[]> results = ratingRepo.findAverageRatingWithCount();

        Map<Integer, Object[]> map = new HashMap<>();

        for (Object[] row : results) {
            Integer classId = (Integer) row[0];
            Double avgRating = (Double) row[1];
            Long count = (Long) row[2];

            map.put(classId, new Object[]{avgRating, count});
        }
        return map;
    }
}
