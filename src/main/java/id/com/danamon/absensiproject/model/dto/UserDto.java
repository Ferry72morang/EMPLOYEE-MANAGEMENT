package id.com.danamon.absensiproject.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

public @Data class UserDto {
    private Long id;
    private String userName;
    private String name;
    private String designation;
    private String role;
    @JsonIgnore
    private String password;

}
