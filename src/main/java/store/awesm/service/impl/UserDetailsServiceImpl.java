package store.awesm.service.impl;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import store.awesm.domain.Role;
import store.awesm.domain.User;
import store.awesm.domain.UserRole;
import store.awesm.repository.UserRepository;
import store.awesm.repository.UserRoleRepository;
import store.awesm.service.UserService;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

/**
 *
 * @author zhishangli
 * @time 00:44 2020-01-19
 */
@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService, UserService {

    @PersistenceContext
    private EntityManager entityManager;
    @Resource
    private UserRepository userRepository;
    @Resource
    private UserRoleRepository userRoleRepository;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public User loadUserByUsername(String s) throws UsernameNotFoundException {
        entityManager.clear();
        Integer id = userRepository.findIdByUsername(s);
        if (ObjectUtils.isEmpty(id)) {
            id = userRepository.findIdByEmail(s);
        }
        if (ObjectUtils.isEmpty(id)) {
            throw new UsernameNotFoundException("User doesn't exist");
        }
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    public Integer register(User user) throws Exception {
        if (ObjectUtils.isEmpty(user)) throw new Exception("Miss param");
        if (StringUtils.isEmpty(user.getUsername())) throw new Exception("Miss param");
        if (StringUtils.isEmpty(user.getEmail())) throw new Exception("Miss param");
        if (StringUtils.isEmpty(user.getPassword())) throw new Exception("Miss param");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.saveAndFlush(user);
        userRoleRepository.saveAndFlush(new UserRole(user.getId(), Role.ROLE_USER));
        return user.getId();
    }

    @Override
    public User load(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public boolean updatePasswordByEmail(String email, String password) {
        int i = userRepository.updatePasswordByEmail(email, password);
        return i != 0;
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
