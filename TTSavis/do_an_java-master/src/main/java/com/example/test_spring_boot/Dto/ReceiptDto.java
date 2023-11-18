package com.example.test_spring_boot.Dto;

import com.example.test_spring_boot.Entity.ProductEntity;
import com.example.test_spring_boot.Entity.ProductHistory;
import com.example.test_spring_boot.Entity.ReceiptEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceiptDto {
    private List<ProductHistoryDto> listProductDTO;
    private Double price;
    private int total;
    private String name;
    private String createTime;
    private String fullname;
    private Long id;
    private String phoneNumber;
    private String address;
    private Double priceShip;
    private Integer status;
    public ReceiptDto(ReceiptEntity receiptEntity){
        if (receiptEntity != null){
            this.id = receiptEntity.getId();
            this.name = receiptEntity.getName();
            this.phoneNumber = receiptEntity.getPhoneNumber();
            this.address = receiptEntity.getAddress();
            this.status = receiptEntity.getStatus();
            if(receiptEntity.getCreateDate() != null){
                this.createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(receiptEntity.getCreateDate());
            }else {
                if(receiptEntity.getModifierDate() != null){
                    this.createTime =  new SimpleDateFormat("yyyy-MM-dd HH:mm").format(receiptEntity.getModifierDate());
                }
            }
            if(receiptEntity.getProductHistorys() != null){
                this.listProductDTO = receiptEntity.getProductHistorys().stream().map(x -> new ProductHistoryDto(x)).collect(Collectors.toList());
            }
            if(receiptEntity.getProductHistorys() != null && receiptEntity.getProductHistorys().get(0) != null && receiptEntity.getProductHistorys().get(0).getUserEntity().getEmail() != null ){
                this.fullname = receiptEntity.getProductHistorys().get(0).getUserEntity().getEmail();
            }
            this.priceShip = receiptEntity.getPriceShip();
            this.price = receiptEntity.getPriceShip();
            this.total = 0;
            for (ProductHistory p : receiptEntity.getProductHistorys()){
                if (p.getProductEntity() != null && p.getSale() != null){
                    this.price += p.getProductEntity().getPrice()*p.getTotalItem()-p.getSale()*p.getTotalItem() * p.getProductEntity().getPrice()/100;
                }else {
                    this.price += p.getProductEntity().getPrice()*p.getTotalItem();
                }
                this.total += p.getTotalItem();
            }
        }
    }
}
