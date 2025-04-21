package da4a.service_auth.service;

import da4a.service_auth.model.User;
import da4a.service_auth.model.UserRole;
import da4a.service_auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(UserRole.ROLE_CUSTOMER); // Роль по умолчанию
        return userRepository.save(user);
    }
}
