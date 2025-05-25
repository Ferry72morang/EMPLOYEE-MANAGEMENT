package id.com.danamon.absensiproject.controller;

import id.com.danamon.absensiproject.model.dto.UserDto;
import id.com.danamon.absensiproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserDto>> findUsers(
            @RequestParam(required = false) String userName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserDto> result = userService.findAllUser(userName, pageable);

        return ResponseEntity.ok(result); // Tidak perlu cek null lagi
    }


    @DeleteMapping("/deleteByUserId")
    public void deleteByUserId(@RequestParam(required = true) String userId){
         userService.deleteByUserId(userId);
    }

    @PutMapping("/update-user")
    public void updateUser(@RequestBody UserDto userDto){
        userService.updateDataUser(userDto);
    }



}
