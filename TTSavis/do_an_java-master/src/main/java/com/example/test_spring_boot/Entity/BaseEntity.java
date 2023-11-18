package com.example.test_spring_boot.Entity;

import com.example.test_spring_boot.Configuration.Oauth2.CustomOauth2User;
import com.example.test_spring_boot.Configuration.Security.UserDetailCustom;
import com.example.test_spring_boot.Dto.UserDto;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import javax.servlet.http.HttpSession;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntity {


    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Column(name = "Create_date")
    private Date CreateDate;

    @Column(name = "Create_By")
    private String CreateBy;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Column(name = "Modifier_date")
    private Date ModifierDate;

    @Column(name = "Modifier_by")
    private String ModifierBy;

    @PrePersist
    public void prePersist( ){

        String createBy ="unknowUser";
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailCustom userDetailsCustom = (UserDetailCustom) authentication.getPrincipal();
            createBy = userDetailsCustom.getUsername();
        }catch (Exception ex){

            try {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                authentication = SecurityContextHolder.getContext().getAuthentication();
                CustomOauth2User oAuth2User = (CustomOauth2User) authentication.getPrincipal();
                createBy = oAuth2User.getName();
            }catch (Exception e){

            }
        }

        this.CreateBy =createBy;
    }

    @PreUpdate
    public void preUpdate(){
        String modifyBy ="unknowUser";
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailCustom userDetailsCustom = (UserDetailCustom) authentication.getPrincipal();
            modifyBy = userDetailsCustom.getUsername();
        }catch (Exception ex){
            try {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                authentication = SecurityContextHolder.getContext().getAuthentication();
                CustomOauth2User oAuth2User = (CustomOauth2User) authentication.getPrincipal();
                modifyBy = oAuth2User.getName();
            }catch (Exception e){

            }
        }
        this.ModifierBy = modifyBy;
    }

    public Date getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(Date createDate) {
        CreateDate = createDate;
    }

    public String getCreateBy() {
        return CreateBy;
    }

    public void setCreateBy(String createBy) {
        CreateBy = createBy;
    }

    public Date getModifierDate() {
        return ModifierDate;
    }

    public void setModifierDate(Date modifierDate) {
        ModifierDate = modifierDate;
    }

    public String getModifierBy() {
        return ModifierBy;
    }

    public void setModifierBy(String modifierBy) {
        ModifierBy = modifierBy;
    }
}