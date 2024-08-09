package swiftescaper.backend.swiftescaper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swiftescaper.backend.swiftescaper.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // Query Method 추가
    User findByPhoneNumber(String phoneNumber);
}
