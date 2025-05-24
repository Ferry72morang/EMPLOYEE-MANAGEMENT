package id.com.danamon.absensiproject.repository;

import id.com.danamon.absensiproject.model.entity.UserBackup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserBackup,Long> {

    List<UserBackup>findAll();

    Optional<UserBackup> findByUserName(String username);
}
