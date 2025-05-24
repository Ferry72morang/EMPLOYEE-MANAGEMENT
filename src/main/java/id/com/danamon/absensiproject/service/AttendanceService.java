package id.com.danamon.absensiproject.service;

import id.com.danamon.absensiproject.model.dto.AttendanceDto;
import id.com.danamon.absensiproject.model.entity.Attendance;
import id.com.danamon.absensiproject.model.entity.UserBackup;
import id.com.danamon.absensiproject.repository.AttendanceRepository;
import id.com.danamon.absensiproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private UserRepository userRepository;

    public AttendanceDto findAttendanceByUserId(Long userId) {
        Attendance attendance = attendanceRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User ID tidak ditemukan"));

        AttendanceDto result = new AttendanceDto();
        result.setUserId(attendance.getUser().getId());
        result.setDate(attendance.getDate());
        result.setTapIn(attendance.getTapIn());
        result.setTapOut(attendance.getTapOut());

        return result;
    }

    public List<AttendanceDto> findAllAttendances() {
        List<Attendance> attendances = attendanceRepository.findAll();
        return attendances.stream().map(att -> {
            AttendanceDto dto = new AttendanceDto();
            dto.setUserId(att.getUser().getId());
            dto.setDate(att.getDate());
            dto.setTapIn(att.getTapIn());
            dto.setTapOut(att.getTapOut());
            return dto;
        }).toList();
    }


    public Attendance tapIn(Long userId) throws Exception {
        LocalDate today = LocalDate.now();
        Attendance attendance = attendanceRepository.findByUserIdAndDate(userId, today)
                .orElseGet(() -> {
                    Attendance newAttendance = new Attendance();
                    newAttendance.setUser(userRepository.findById(userId).orElseThrow());
                    newAttendance.setDate(today);
                    return newAttendance;
                });

        if (attendance.getTapIn() == null) {
            attendance.setTapIn(LocalDateTime.now());
            return attendanceRepository.save(attendance);
        } else {
            throw new Exception("User has already tapped in today.");
        }
    }

    public Attendance tapOut(Long userId) {
        LocalDate today = LocalDate.now();
        Attendance attendance = attendanceRepository.findByUserIdAndDate(userId, today)
                .orElseThrow(() -> new IllegalStateException("User has not tapped in today."));

        if (attendance.getTapOut() == null) {
            attendance.setTapOut(LocalDateTime.now());
            return attendanceRepository.save(attendance);
        } else {
            throw new IllegalStateException("User has already tapped out today.");
        }
    }
}
