package com.example.test_spring_boot.Repository;

import com.example.test_spring_boot.Dto.UserDto;
import com.example.test_spring_boot.Entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("select u from UserEntity u where u.username = ?1")
    UserEntity getByUsername(String username);
    @Query("select u from UserEntity u where u.CreateBy = ?1")
    UserEntity getByCreateBy(String username);
    @Query("select u from UserEntity u where u.email like ?1")
    UserEntity getByEmail(String email);

    @Query("select u from UserEntity u where u.email like ?1 and u.token = ?2")
    UserEntity getByEmailAndToken(String email,int token);
    @Query("select u from UserEntity u where u.username like concat('%' , ?1,'%') and u.active =1 order by u.CreateDate desc")
    Page<UserEntity> getAllByName(String name , Pageable pageable);

    @Query("select u from UserEntity u where u.active = 1 order by u.CreateDate desc")
    Page<UserEntity> getAll(Pageable pageable);

    @Query("select new com.example.test_spring_boot.Dto.UserDto(u) from UserEntity u order by u.CreateDate desc")
    List<UserDto> getAllDto();
}
