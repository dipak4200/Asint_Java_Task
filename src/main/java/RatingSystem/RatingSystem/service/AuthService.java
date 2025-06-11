package RatingSystem.RatingSystem.service;

import RatingSystem.RatingSystem.dto.JwtAuthenticationResponse;
import RatingSystem.RatingSystem.dto.UserLoginRequest;
import RatingSystem.RatingSystem.dto.UserRegistrationRequest;
import RatingSystem.RatingSystem.entity.Role;
import RatingSystem.RatingSystem.entity.User;
import RatingSystem.RatingSystem.exception.DuplicateEmailException;
import RatingSystem.RatingSystem.repository.UserRepository;
import RatingSystem.RatingSystem.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public User registerUser(UserRegistrationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("Email " + request.getEmail() + " is already registered.");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(Role.ADMIN)); // Default role for new registrations



        return userRepository.save(user);
    }

    public JwtAuthenticationResponse loginUser(UserLoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid email or password", e); // More specific exception can be handled
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found after authentication (this should not happen)"));
        String token = jwtUtil.generateToken(user);
        return new JwtAuthenticationResponse(token);
    }
}