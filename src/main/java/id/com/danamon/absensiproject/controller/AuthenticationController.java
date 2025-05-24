package id.com.danamon.absensiproject.controller;

import id.com.danamon.absensiproject.model.dto.UserDto;
import id.com.danamon.absensiproject.model.entity.UserBackup;
import id.com.danamon.absensiproject.model.response.LoginResponse;
import id.com.danamon.absensiproject.service.AuthenticationService;
import id.com.danamon.absensiproject.service.JwtService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/signup")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserBackup> register(@RequestBody UserDto registerUserDto) {
        UserBackup registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody UserDto registerUserDto
    ) {
        LinkedHashMap<String, Object> res = new LinkedHashMap<>();
        try{
            String token = authHeader.replace("Bearer ", "");

            // Validasi dan ekstrak klaim dari token
            Claims claims = jwtService.extractAllClaims(token);
            List<String> roles = claims.get("roles", List.class);
            String userName = claims.getSubject();

            // Periksa apakah peran ADMIN ada dalam daftar peran
            if (roles == null || !roles.contains("ADMIN")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // Lanjutkan dengan proses pendaftaran
            UserBackup registeredUser = authenticationService.signup(registerUserDto);

            res.put("status", true);
            res.put("message", "success");
            res.put("data",registeredUser);
            return ResponseEntity.ok().body(res);
        }catch (Exception ex){
            ex.printStackTrace();
            res.put("status", false);
            res.put("message", ex.getMessage());

            return ResponseEntity.badRequest().body(res);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody UserDto loginUserDto) {
        UserBackup authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}