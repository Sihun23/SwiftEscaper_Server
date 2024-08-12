package swiftescaper.backend.swiftescaper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import swiftescaper.backend.swiftescaper.domain.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    // Query Method 추가
    User findByPhoneNumber(String phoneNumber);
}
