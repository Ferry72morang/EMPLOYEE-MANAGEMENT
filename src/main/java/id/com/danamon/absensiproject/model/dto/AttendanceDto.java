package id.com.danamon.absensiproject.model.dto;

import id.com.danamon.absensiproject.model.entity.UserBackup;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

public @Data
class AttendanceDto {
    private Long userId;
    private String userName;
    private LocalDate date;
    private LocalDateTime tapIn;
    private LocalDateTime tapOut;
}
