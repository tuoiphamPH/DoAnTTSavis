package com.example.test_spring_boot.RestController;

import com.example.test_spring_boot.Dto.Func.ProductRating;
import com.example.test_spring_boot.Dto.Func.ReivewUserDto;
import com.example.test_spring_boot.Service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/review")
public class RestReviewController {
    @Autowired
    ReviewService reviewService;

    @PostMapping("/staring")
    public ResponseEntity<?> staring(ProductRating productRating){
        reviewService.rating(productRating);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/review_post")
    public ResponseEntity<?> ReivewUserDto(ReivewUserDto reivewUserDto){
        reviewService.postReview(reivewUserDto);
        return ResponseEntity.ok(true);
    }
}
