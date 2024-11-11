package ozdemir0zdemir.userservice.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
