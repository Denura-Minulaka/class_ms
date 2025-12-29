package com.class_ms.controller;

import com.class_ms.data.Class;
import com.class_ms.data.Rating;
import com.class_ms.dto.RatingRequest;
import com.class_ms.dto.RatingResponse;
import com.class_ms.security.JwtUtil;
import com.class_ms.service.ClassService;
import com.class_ms.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ratings")
@CrossOrigin(origins = "http://localhost:5173")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private ClassService classService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/rate/{classId}")
    public ResponseEntity<?> rateClass(
            @PathVariable Integer classId,
            @RequestHeader("Authorization") String authHeader,
            @RequestBody RatingRequest req
    ) {
        Integer studentId = jwtUtil.extractStudentId(authHeader);

        Rating r = ratingService.addOrUpdateRating(studentId, classId, req.getRating(), req.getReview());

        return ResponseEntity.ok("Rating saved Successfully");
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<List<RatingResponse>> getLeaderboard(
            @RequestParam(required = false) String district,
            @RequestParam(required = false) String town,
            @RequestParam(required = false) String grade,
            @RequestParam(required = false) String subject
    ) {
        Map<Integer, Object[]> avgMap = ratingService.getAverageRatings();

        List<RatingResponse> list = new ArrayList<>();

        for (Integer classId : avgMap.keySet()) {

            Class c = classService.getClassById(classId);

            // Apply filters
            if (district != null && !district.equals(c.getDistrict())) continue;
            if (town != null && !town.equals(c.getTown())) continue;
            if (grade != null && !grade.equals(c.getGrade())) continue;
            if (subject != null && !subject.equals(c.getSubject())) continue;

            Object[] ratingData = avgMap.get(classId);

            RatingResponse resp = new RatingResponse();
            resp.setClassId(classId);
            resp.setClassName(c.getClassName());
            resp.setAverageRating((Double) ratingData[0]);
            resp.setRatingCount((Long) ratingData[1]);

            list.add(resp);
        }

        // Sort - highest rating first
        list.sort((a, b) -> Double.compare(
                b.getAverageRating(),
                a.getAverageRating()
        ));

        return ResponseEntity.ok(list);
    }

}
