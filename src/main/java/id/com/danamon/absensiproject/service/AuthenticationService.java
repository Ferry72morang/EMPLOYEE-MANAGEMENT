package id.com.danamon.absensiproject.service;

import id.com.danamon.absensiproject.model.dto.UserDto;
import id.com.danamon.absensiproject.model.entity.UserBackup;
import id.com.danamon.absensiproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;



    public UserBackup signup(UserDto userDto) {
        UserBackup user = new UserBackup();
        user.setUserName(userDto.getUserName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setName(userDto.getName());
        user.setDesignation(userDto.getDesignation());
        user.setRole(userDto.getRole());
        user.setStatus(userDto.getStatus());
        return userRepository.save(user);
    }

    public UserBackup authenticate(UserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUserName(),
                        input.getPassword()
                )
        );

        return userRepository.findByUserName(input.getUserName()).orElseThrow();
    }

//    public List<User> allUsers() {
//        List<User> users = new ArrayList<>();
//
//        userRepository.findAll().forEach(users::add);
//
//        return users;
//    }
}

