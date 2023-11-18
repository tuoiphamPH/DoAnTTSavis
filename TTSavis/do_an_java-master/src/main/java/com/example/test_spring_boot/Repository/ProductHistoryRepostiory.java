package com.example.test_spring_boot.Repository;

import com.example.test_spring_boot.Entity.ProductHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductHistoryRepostiory extends JpaRepository<ProductHistory, Long> {
    @Query("SELECT p FROM ProductHistory p GROUP BY p.CreateDate,p.userEntity")
    List<ProductHistory> getALlGroupByProductHistory();
    @Query("select p from ProductHistory p where p.CreateDate = ?1 and p.userEntity.id = ?2")
    List<ProductHistory> getProductHistoryByTimeCreateAndIdUser(Date timeCreate, Long idUser);
}
