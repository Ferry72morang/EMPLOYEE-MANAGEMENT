package id.com.danamon.absensiproject.controller;

import id.com.danamon.absensiproject.model.dto.AttendanceDto;
import id.com.danamon.absensiproject.model.entity.Attendance;
import id.com.danamon.absensiproject.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    @GetMapping
    public ResponseEntity<?> getAttendance(@RequestParam(required = false) Long userId) {
        if (userId != null) {
            AttendanceDto attendance = attendanceService.findAttendanceByUserId(userId);
            return ResponseEntity.ok(attendance);
        } else {
            List<AttendanceDto> allAttendance = attendanceService.findAllAttendances();
            return ResponseEntity.ok(allAttendance);
        }
    }



    @PostMapping("/tap-in")
    public ResponseEntity<Attendance> tapIn(@RequestParam Long userId) throws Exception{
        Attendance attendance = attendanceService.tapIn(userId);
        return ResponseEntity.ok(attendance);
    }

    @PostMapping("/tap-out")
    public ResponseEntity<Attendance> tapOut(@RequestParam Long userId) {
        Attendance attendance = attendanceService.tapOut(userId);
        return ResponseEntity.ok(attendance);
    }
}
