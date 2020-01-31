package store.awesm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.awesm.domain.Role;

/**
 *
 * @author zhishangli
 * @time 21:26 2020-01-18
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
