package somosdeweb.edu.aut05_04myikea.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import somosdeweb.edu.aut05_04myikea.Models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserMail(String userMail);

    Optional<User> findByUsername(String userName);
}
