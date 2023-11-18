package com.example.test_spring_boot.Service;

import com.example.test_spring_boot.Dto.Func.ProductRating;
import com.example.test_spring_boot.Dto.Func.ReivewUserDto;

public interface ReviewService {
    void rating(ProductRating productRating);
    void postReview(ReivewUserDto reivewUserDto);
}
