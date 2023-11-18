package com.example.test_spring_boot.Controller;

import com.example.test_spring_boot.Dto.Func.ProductRating;
import com.example.test_spring_boot.Dto.Func.ReivewUserDto;
import com.example.test_spring_boot.Entity.ProductEntity;
import com.example.test_spring_boot.Repository.ProductRepository;
import com.example.test_spring_boot.Repository.ReviewRepository;
import com.example.test_spring_boot.Service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/review")
@Controller
public class ReviewController {
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
