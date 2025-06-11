package RatingSystem.RatingSystem.controller;

import RatingSystem.RatingSystem.dto.AdminReportResponse;
import RatingSystem.RatingSystem.dto.RatingRequest;
import RatingSystem.RatingSystem.dto.RatingResponse;
import RatingSystem.RatingSystem.entity.Role;
import RatingSystem.RatingSystem.service.RatingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    // Helper to get current user's email
    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            return userDetails.getUsername(); // Email is the username
        }
        return null; // Or throw an exception
    }

    // Helper to get current user ID (you'll need to fetch user by email)
    // For simplicity, we'll assume a service method to get User by email
    // In a real app, you might inject a UserService or UserRepository here.
    // For now, let's assume we can get it or pass the email.
    // **Important:** This helper should be part of a utility or a dedicated service
    // to get the current authenticated user's ID securely.
    // For this example, we'll simulate getting the user object for ID.
    // A better approach is to store user ID in JWT claims or fetch from DB once per request.
    private Long getCurrentUserId() {
        // This is a simplified way. In a real app, you'd fetch the User entity
        // using the email from SecurityContextHolder and then get its ID.
        // For brevity, we'll assume you have a way to get the User from email.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof RatingSystem.RatingSystem.entity.User user) {
            return user.getId();
        }
        return null;
    }


    @PostMapping
    @PreAuthorize("hasRole('USER')") // Only users can rate
    public ResponseEntity<RatingResponse> submitRating(@Valid @RequestBody RatingRequest ratingRequest) {
        // Get the current authenticated user's ID
        Long userId = getCurrentUserId(); // Implement this securely in a real application
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Or handle appropriately
        }
        RatingResponse response = ratingService.submitRating(userId, ratingRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN') ") // Only admins can view all ratings
    public ResponseEntity<List<RatingResponse>> getAllRatingsForAdmin() {
        List<RatingResponse> ratings = ratingService.getAllRatings();
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/admin/filter")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RatingResponse>> getFilteredRatings(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer ratingValue,
            @RequestParam(required = false) Long userId) {
        List<RatingResponse> ratings = ratingService.getFilteredRatings(category, ratingValue, userId);
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/admin/report")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminReportResponse> getAdminReport() {
        AdminReportResponse report = ratingService.generateAdminReport();
        return ResponseEntity.ok(report);
    }

    // User can view their own ratings
    @GetMapping("/my-ratings")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<RatingResponse>> getMyRatings() {
        Long userId = getCurrentUserId(); // Implement this securely
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<RatingResponse> myRatings = ratingService.getRatingsByUserId(userId);
        return ResponseEntity.ok(myRatings);
    }
}