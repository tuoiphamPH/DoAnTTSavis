package com.example.test_spring_boot.Repository;

import com.example.test_spring_boot.Dto.ReceiptDto;
import com.example.test_spring_boot.Entity.ReceiptEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ReceiptRepository extends JpaRepository<ReceiptEntity, Long> {

    @Query("select new com.example.test_spring_boot.Dto.ReceiptDto(r) from ReceiptEntity r ")
    List<ReceiptDto> getAll();
    @Query("select new com.example.test_spring_boot.Dto.ReceiptDto(r) from ReceiptEntity r where r.status = ?1")
    List<ReceiptDto> getAllByStatus(Integer Status);
    @Query("select r from ReceiptEntity r where r.name like concat('%',?1,'%') and r.CreateDate > ?2 and r.CreateDate < ?3 and r.status = ?4")
    Page<ReceiptEntity> pageSearchByAll(String name ,Date fromDate,Date toDate ,Integer status,  Pageable pageable );
    @Query("select r from ReceiptEntity r where r.name like concat('%',?1,'%') and  r.status = ?2" )
    Page<ReceiptEntity> pageSearchByTextSearch(String name ,Integer status,Pageable pageable);
    @Query("select new com.example.test_spring_boot.Dto.ReceiptDto(r) from ReceiptEntity r where r.name like concat('%',?1,'%') and  r.status = ?2")
    List<ReceiptDto> listSearchByName(String name,Integer status);

    @Query("select r from ReceiptEntity r where r.name like concat('%',?1,'%') and r.CreateDate > ?2 and r.CreateDate < ?3 and r.status = ?4")
    List<ReceiptDto> listSearchByAll(String name ,Date fromDate,Date toDate,Integer status);

    @Query("select r from ReceiptEntity r where r.status = ?1")
    Page<ReceiptEntity> pageSearch(Integer status,Pageable pageable);
    @Query("select r from ReceiptEntity r")
    Page<ReceiptEntity> pageSearch(Pageable pageable);

    @Query("select r from ReceiptEntity r where r.id in ?1")
    List<ReceiptEntity> getAllById(List<Long> id);

    @Query("SELECT r from ReceiptEntity r where DATEDIFF (?1, r.CreateDate) > 3 and status = 3")
    List<ReceiptEntity> getAllByTimeNow(Date timeNow);
    @Query("SELECT new com.example.test_spring_boot.Dto.ReceiptDto(r) from ReceiptEntity r where DATEDIFF (month,r.CreateDate, ?1) = 0")
    List<ReceiptDto> getAllByMonthEx(Date timeNow) ;
    @Query("SELECT new com.example.test_spring_boot.Dto.ReceiptDto(r) from ReceiptEntity r where DATEDIFF (month,r.CreateDate, ?1) = -1")
    List<ReceiptDto> getAllByPrevMonthEx(Date date) ;

    @Query("select new com.example.test_spring_boot.Dto.ReceiptDto(r) from ReceiptEntity r where r.CreateBy like ?1")
    List<ReceiptDto> getAllByUsername(String username);


    @Query("select new com.example.test_spring_boot.Dto.ReceiptDto(r) from ReceiptEntity r join r.ProductHistorys p where p.userEntity.username like ?1")
    List<ReceiptDto> getAllByUsernameJoin(String username);

    @Query("select new com.example.test_spring_boot.Dto.ReceiptDto(r) from ReceiptEntity r where r.CreateBy like ?1 order by r.CreateDate desc")
    List<ReceiptDto> findByUserName(String username);
}
