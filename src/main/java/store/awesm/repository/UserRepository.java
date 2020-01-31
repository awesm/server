package store.awesm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import store.awesm.domain.User;

/**
 *
 * @author zhishangli
 * @time 21:26 2020-01-18
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(nativeQuery = true, value = "SELECT user.id FROM user WHERE username = ?1")
    Integer findIdByUsername(String username);
    @Query(nativeQuery = true, value = "SELECT user.id FROM user WHERE email = ?1")
    Integer findIdByEmail(String email);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE User u SET u.password = ?2 WHERE u.email = ?1")
    int updatePasswordByEmail(String email, String password);

    boolean existsByEmail(String email);
}
