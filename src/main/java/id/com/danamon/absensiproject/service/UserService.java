package id.com.danamon.absensiproject.service;

import id.com.danamon.absensiproject.model.dto.UserDto;
import id.com.danamon.absensiproject.model.entity.UserBackup;
import id.com.danamon.absensiproject.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    public Page<UserDto> findAllUser(String userName, Pageable pageable) {
        if (userName == null || userName.isEmpty()) {
            Page<UserBackup> pageData = userRepository.findAll(pageable);
            return pageData.map(userData -> {
                UserDto user = new UserDto();
                user.setId(userData.getId());
                user.setUserName(userData.getUsername());
                user.setName(userData.getName());
                user.setDesignation(userData.getDesignation());
                user.setRole(userData.getRole());
                return user;
            });
        } else {
            UserBackup userData = findByUserName(userName);
            if (userData != null) {
                UserDto user = new UserDto();
                user.setId(userData.getId());
                user.setUserName(userData.getUsername());
                user.setName(userData.getName());
                user.setDesignation(userData.getDesignation());
                user.setRole(userData.getRole());
                return new PageImpl<>(List.of(user), pageable, 1);
            } else {
                return Page.empty(pageable); // ini akan mengembalikan page kosong, bukan null
            }
        }
    }




    public UserBackup findByUserName(String userName) {
        return userRepository.findByUserName(userName).orElse(null);
    }


    public void deleteByUserId(String userId){
         userRepository.deleteById(Long.valueOf(userId));
    }

    public void updateDataUser(UserDto userDto){
        Optional<UserBackup> userOptional = userRepository.findById(userDto.getId());

        if (userOptional.isPresent()) {
            UserBackup user = userOptional.get();
            user.setUserName(userDto.getUserName());
            user.setName(userDto.getName());
            user.setRole(userDto.getRole());
            userRepository.save(user);
        } else {
            throw new RuntimeException("User dengan ID " + userDto.getId() + " tidak ditemukan");
        }
    }



}
