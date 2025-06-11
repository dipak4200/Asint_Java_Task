package RatingSystem.RatingSystem.controller;

import RatingSystem.RatingSystem.dto.JwtAuthenticationResponse;
import RatingSystem.RatingSystem.dto.UserLoginRequest;
import RatingSystem.RatingSystem.dto.UserRegistrationRequest;
import RatingSystem.RatingSystem.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        try {
            authService.registerUser(request);
            return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> loginUser(@Valid @RequestBody UserLoginRequest request) {
        try {
            JwtAuthenticationResponse response = authService.loginUser(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            // In a real application, you might want more specific error messages/codes
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}