package da4a.authentication.service;

import da4a.authentication.model.TokenAuth;
import da4a.authentication.model.User;
import da4a.authentication.repository.TokenAuthRepository;
import da4a.authentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final TokenAuthRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> authenticateUser(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()));
    }

    @Transactional
    public String createToken(User user) {
        tokenRepository.deleteByUser_Id(user.getId());

        TokenAuth token = new TokenAuth();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(LocalDateTime.now().plusHours(3));

        tokenRepository.save(token);
        return token.getToken();
    }

    public Optional<User> validateToken(String tokenValue) {
        return tokenRepository.findByToken(tokenValue)
                .filter(token -> !token.isExpired())
                .map(TokenAuth::getUser);
    }

    @Transactional
    public void invalidateToken(String tokenValue) {
        tokenRepository.findByToken(tokenValue)
                .ifPresent(tokenRepository::delete);
    }
}
