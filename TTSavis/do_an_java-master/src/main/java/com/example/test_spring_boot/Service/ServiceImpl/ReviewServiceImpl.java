package com.example.test_spring_boot.Service.ServiceImpl;

import com.example.test_spring_boot.Configuration.Security.UserDetailCustom;
import com.example.test_spring_boot.Dto.Func.ProductRating;
import com.example.test_spring_boot.Dto.Func.ReivewUserDto;
import com.example.test_spring_boot.Entity.*;
import com.example.test_spring_boot.Repository.*;
import com.example.test_spring_boot.Service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    ReceiptRepository receiptRepository;


    @Override
    public void rating(ProductRating productRating) {
        Long id = (long) productRating.getIdProduct();
        List<Long> ids = new ArrayList<>();
        try{
            ReceiptEntity receiptEntity = receiptRepository.findById(id).get();
            receiptEntity.getProductHistorys().stream().forEach(x -> ids.add(x.getProductEntity().getId()));
        }catch (Exception e){
            System.out.println("lỗi"
            );
        }
        for (Long idProduct : ids){
            try{
                ProductEntity productEntity = productRepository.findById(idProduct).get();
                Integer ratingProduct = 0;
                if(productEntity.getReviewEntity() == null){
                    ReviewEntity reviewEntity = new ReviewEntity();
                    reviewEntity.setRating(productRating.getRate());
                    productEntity.setReviewEntity(reviewEntity);
                }else{
                    int a = (int) ((productRating.getRate() + productRating.getRate()) / 2);
                    productEntity.getReviewEntity().setRating(a);
                }
                productRepository.save(productEntity);
            }catch (Exception  e){
                System.out.printf("lỗi");
            }
        }
    }

    @Override
    public void postReview(ReivewUserDto reivewUserDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String roleA = authentication.getPrincipal().toString();
        if(roleA.equals("anonymousUser")){
            ProductEntity p = productRepository.getById(reivewUserDto.getIdProduct());
            ReviewEntity reviewEntity = p.getReviewEntity();
            if(reviewEntity == null){
                reviewEntity = new ReviewEntity();
                reviewEntity.setRating(0);
                p.setReviewEntity(reviewEntity);
                p = productRepository.save(p);
            }
            UserEntity userEntity = new UserEntity();
            userEntity.setFullname(reivewUserDto.getFullname());
            userEntity.setEmail(reivewUserDto.getEmail());
            userEntity = userRepository.save(userEntity);

            PostEntity postEntity = new PostEntity();
            postEntity.setContent(reivewUserDto.getContent());
            postEntity.setUserEntity(userEntity);
            postEntity.setReviewEntity(p.getReviewEntity());
            postRepository.save(postEntity);
        }
        try{
            if(!roleA.equals("anonymousUser")){
                UserDetailCustom ud =(UserDetailCustom) authentication.getPrincipal();
                ProductEntity p = productRepository.getById(reivewUserDto.getIdProduct());
                ReviewEntity reviewEntity = p.getReviewEntity();
                if(reviewEntity == null){
                    reviewEntity = new ReviewEntity();
                    reviewEntity.setRating(0);
                    p.setReviewEntity(reviewEntity);
                    p = productRepository.save(p);
                }
                UserEntity userEntity = userRepository.getByUsername(ud.getUsername());
                PostEntity postEntity = new PostEntity();
                postEntity.setContent(reivewUserDto.getContent());
                postEntity.setUserEntity(userEntity);
                postEntity.setReviewEntity(p.getReviewEntity());
                postRepository.save(postEntity);
            }
        }catch (Exception e){
            ProductEntity p = productRepository.getById(reivewUserDto.getIdProduct());
            ReviewEntity reviewEntity = p.getReviewEntity();
            if(reviewEntity == null){
                reviewEntity = new ReviewEntity();
                reviewEntity.setRating(0);
                p.setReviewEntity(reviewEntity);
                p = productRepository.save(p);
            }
            String a = authentication.getName();
            UserEntity userEntity = userRepository.getByUsername(authentication.getName());
            PostEntity postEntity = new PostEntity();
            postEntity.setContent(reivewUserDto.getContent());
            postEntity.setUserEntity(userEntity);
            postEntity.setReviewEntity(p.getReviewEntity());
            postRepository.save(postEntity);
        }
    }
}
