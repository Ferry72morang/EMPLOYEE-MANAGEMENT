package id.com.danamon.absensiproject.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "attendance", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "date"}))
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public UserBackup user;

    private LocalDate date;

    @Column(name = "tap_in")
    private LocalDateTime tapIn;

    @Column(name = "tap_out")
    private LocalDateTime tapOut;
}
