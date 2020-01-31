package store.awesm.service;

import store.awesm.domain.User;

/**
 *
 * @author zhishangli
 * @time 14:13 2020-01-23
 */
public interface UserService {

    Integer register(User user) throws Exception;

    User load(Integer id);

    boolean updatePasswordByEmail(String email, String password);

    boolean existsByEmail(String email);
}
