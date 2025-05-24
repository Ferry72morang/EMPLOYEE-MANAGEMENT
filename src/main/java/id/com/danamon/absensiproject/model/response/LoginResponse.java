package id.com.danamon.absensiproject.model.response;

import lombok.Data;

public @Data class LoginResponse {
    private String token;

    private long expiresIn;
}
