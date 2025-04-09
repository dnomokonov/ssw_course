package da4a.authentication.controller;

import da4a.authentication.dto.AuthRequest;
import da4a.authentication.dto.AuthResponse;
import da4a.authentication.dto.Register;
import da4a.authentication.model.Customer;
import da4a.authentication.service.AuthService;
import da4a.authentication.service.CustomerService;
import da4a.authentication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> createUser(@RequestBody Register request) {
        Customer customer = customerService.createCustomer(request.getName(), request.getAddress());
        userService.createCustomerUser(request.getUsername(), request.getPassword(), customer);
        return authService.authenticateUser(request.getUsername(), request.getPassword())
                .map(user -> {
                    String token = authService.createToken(user);
                    return ResponseEntity.ok(new AuthResponse(token, user.getRole().name()));
                })
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return authService.authenticateUser(request.getUsername(), request.getPassword())
                .map(user -> {
                    String token = authService.createToken(user);
                    return ResponseEntity.ok(new AuthResponse(token, user.getRole().name()));
                })
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authorization) {
        if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.substring(7);
            authService.invalidateToken(token);
        }
        return ResponseEntity.ok().build();
    }
}
