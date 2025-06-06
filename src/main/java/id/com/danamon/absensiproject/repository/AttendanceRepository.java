package id.com.danamon.absensiproject.repository;

import id.com.danamon.absensiproject.model.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance,Long> {

    Optional<Attendance>findByUserId(Long userId);

    Optional<Attendance> findByUserIdAndDate(Long userId, LocalDate date);
}
