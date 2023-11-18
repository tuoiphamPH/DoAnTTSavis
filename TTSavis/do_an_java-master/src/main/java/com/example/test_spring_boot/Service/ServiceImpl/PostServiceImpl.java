package com.example.test_spring_boot.Service.ServiceImpl;

import com.example.test_spring_boot.Service.PostService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PostServiceImpl implements PostService {
}
