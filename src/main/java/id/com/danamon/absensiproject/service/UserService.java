package id.com.danamon.absensiproject.service;

import id.com.danamon.absensiproject.model.dto.UserDto;
import id.com.danamon.absensiproject.model.entity.UserBackup;
import id.com.danamon.absensiproject.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    public List<UserDto>findAllUser(String userName){
        List<UserDto> resultList = new ArrayList<>();
        if(userName == null || userName.equals("")){
            List<UserBackup>listData = userRepository.findAll();
            for(UserBackup userData : listData){
                UserDto user = new UserDto();
                user.setId(userData.getId());
                user.setUserName(userData.getUsername());
                user.setName(userData.getName());
                user.setDesignation(userData.getDesignation());
                user.setRole(userData.getRole());
                resultList.add(user);
            }
        }else{
            UserBackup userData = new UserBackup();
            userData = this.findByUserName(userName);
            if(userData != null){
                UserDto user = new UserDto();
                user.setId(userData.getId());
                user.setUserName(userData.getUsername());
                user.setName(userData.getName());
                user.setDesignation(userData.getDesignation());
                user.setRole(userData.getRole());
                resultList.add(user);
            }
        }

        return resultList;
    }

    public UserBackup findByUserName(String userName){
        UserBackup userData = new UserBackup();
        userData = userRepository.findByUserName(userName).orElseThrow();
        return userData;
    }

}
