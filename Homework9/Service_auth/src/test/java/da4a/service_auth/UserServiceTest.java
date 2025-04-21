package da4a.service_auth;

import da4a.service_auth.model.User;
import da4a.service_auth.model.UserRole;
import da4a.service_auth.repository.UserRepository;
import da4a.service_auth.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void testRegisterUser_Success() {
        when(userRepository.findByUsername("newuser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encoded_password");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User user = userService.registerUser("newuser", "password");

        assertEquals("newuser", user.getUsername());
        assertEquals("encoded_password", user.getPassword());
        assertEquals(UserRole.ROLE_CUSTOMER, user.getRole());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testRegisterUser_UsernameExists() {
        when(userRepository.findByUsername("existinguser")).thenReturn(Optional.of(new User()));

        assertThrows(RuntimeException.class, () -> userService.registerUser("existinguser", "password"));
    }
}
