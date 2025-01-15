package cz.sda.java.remotesk1.invoices.service;

import cz.sda.java.remotesk1.invoices.exception.NotFoundException;
import cz.sda.java.remotesk1.invoices.model.User;
import cz.sda.java.remotesk1.invoices.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.StreamSupport;

@Slf4j
@Service
class UserServiceBean implements UserService {

    private static final String USER_ROLE = "USER";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    UserServiceBean(UserRepository UserRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = UserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User addUser(String username, String password) {
        if (!StringUtils.hasText(username)) {
            throw new IllegalArgumentException("Username must not be empty");
        }
        if (!StringUtils.hasText(password)) {
            throw new IllegalArgumentException("Password must not be empty");
        }
        var User = new User(username, encryptPassword(password), USER_ROLE);
        if (userRepository.existsById(User.getUsername())) {
            throw new IllegalArgumentException("User " + User.getUsername() + " already exists");
        }
        userRepository.save(User);
        log.info("User added: {}", User);
        return User;
    }

    @Override
    public void removeUser(String id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User with id " + id + " does not exist");
        }
        userRepository.deleteById(id);
        log.info("User removed: {}", id);
    }

    @Override
    public User getUser(String id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " does not exist"));
    }

    @Override
    public User updateUser(String username, String password) {

        var user = userRepository
                .findById(username)
                .orElseThrow(() -> new NotFoundException("User " + username + " does not exist"));
        var updated = new User();
        updated.setUsername(username);
        if (StringUtils.hasText(username)) {
            updated.setUsername(username);
        } else {
            updated.setUsername(user.getUsername());
        }
        if (StringUtils.hasText(password)) {
            updated.setPassword(encryptPassword(password));
        } else {
            updated.setPassword(user.getPassword());
        }
        userRepository.save(updated);
        log.info("User updated: {}", updated);
        return updated;
    }

    @Override
    public List<User> getAllUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false).toList();
    }

    @Override
    public List<User> findUsersByName(String username) {
        return userRepository.findByUsernameContainingIgnoreCase(username);
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
