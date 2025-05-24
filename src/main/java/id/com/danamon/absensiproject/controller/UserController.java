package id.com.danamon.absensiproject.controller;

import id.com.danamon.absensiproject.model.dto.AttendanceDto;
import id.com.danamon.absensiproject.model.dto.UserDto;
import id.com.danamon.absensiproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> findUsers(@RequestParam(required = false) String userName) {
            return ResponseEntity.ok(userService.findAllUser(userName));
    }

}
