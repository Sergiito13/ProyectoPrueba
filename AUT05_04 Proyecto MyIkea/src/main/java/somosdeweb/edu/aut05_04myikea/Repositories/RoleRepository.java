package somosdeweb.edu.aut05_04myikea.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import somosdeweb.edu.aut05_04myikea.Models.Roles;


import java.util.Optional;

public interface RoleRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByRole(String roleName);
}
