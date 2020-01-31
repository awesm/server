package store.awesm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.awesm.domain.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

}
