package com.example.test_spring_boot.Service.ServiceImpl;

import com.example.test_spring_boot.Dto.CategoryDto;
import com.example.test_spring_boot.Dto.SearchDto.SearchReportDto;
import com.example.test_spring_boot.Dto.UserDto;
import com.example.test_spring_boot.Entity.RoleEntity;
import com.example.test_spring_boot.Entity.UserEntity;
import com.example.test_spring_boot.Repository.RoleRepository;
import com.example.test_spring_boot.Repository.UserRepository;
import com.example.test_spring_boot.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Override
    public UserEntity checkExistUserOauth(String username, String method ,String email) {
               UserEntity u = null;
               if(userRepository.getByEmail(email) != null){
                   u = userRepository.getByEmail(email);
                  if(u.getMethodLogin() != null &&!u.getMethodLogin().contains(method) || u.getActive() == 0){
                      return null;
                  }
               }else {
                   u = new UserEntity();
               }
               u.setUsername(username);
               u.setEmail(email);
               u.setMethodLogin(method);
               u.setFullname(username);
               RoleEntity role = roleRepository.findById(2L).get();
               Set<RoleEntity> roleSet = new HashSet<>();
               roleSet.add(role);
               if(u.getRoles() != null && u.getRoles().size() > 0){
                   u.getRoles().clear();
               }
               u.setRoles(roleSet);
               u.setActive(1);
               u = userRepository.save(u);
               return u;
    }

    @Override
    public UserDto registerAcc(UserDto userDto, BCryptPasswordEncoder bCryptPasswordEncoder) {
        UserEntity userEntity = userRepository.getByUsername(userDto.getUsername());
        if(userEntity != null){
            return null;
        } else {
            userEntity = userRepository.getByEmail(userDto.getEmail());
            if(userEntity != null){
                return null;
            }else {
                userEntity = new UserEntity();
                userEntity.setUsername(userDto.getUsername());
                userEntity.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
                userEntity.setEmail(userDto.getEmail());
                RoleEntity role =roleRepository.findById(2L).get();
                Set<RoleEntity> roleEntities = new HashSet<>();
                roleEntities.add(role);
                userEntity.setRoles(roleEntities);
                userEntity.setFullname(userDto.getFullname());
                userEntity.setActive(1);
                userEntity = userRepository.save(userEntity);
            }
        }
        return new UserDto(userEntity);
    }

    @Override
    public UserDto updateAcc(UserDto userDto, BCryptPasswordEncoder bCryptPasswordEncoder) {
        UserEntity userEntity = userRepository.getById(userDto.getId());
        if(userDto.getPassword() != ""){
            userEntity.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        }
        userEntity.setEmail(userDto.getEmail());
        userEntity.setFullname(userDto.getFullname());
        return new UserDto(userRepository.save(userEntity));
    }

    @Override
    public Page<UserDto> findPage(SearchReportDto searchReportDto) {
        int pageSize = 5;
        int pageIndex = searchReportDto.getPageIndex();
        Pageable pageable = PageRequest.of(pageIndex,pageSize);
        Page<UserDto> list;
        if(searchReportDto.getTextSearch() != null){
            list = userRepository.getAllByName(searchReportDto.getTextSearch(),pageable).map(x -> new UserDto(x));
        }else {
            list = userRepository.getAll(pageable).map(x -> new UserDto(x));
        }
        return list;
    }

}
