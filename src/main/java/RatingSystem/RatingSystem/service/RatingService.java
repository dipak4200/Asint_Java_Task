package RatingSystem.RatingSystem.service;

import RatingSystem.RatingSystem.dto.AdminReportResponse;
import RatingSystem.RatingSystem.dto.RatingRequest;
import RatingSystem.RatingSystem.dto.RatingResponse;
import RatingSystem.RatingSystem.entity.Rating;
import RatingSystem.RatingSystem.entity.User;
import RatingSystem.RatingSystem.repository.RatingRepository;
import RatingSystem.RatingSystem.repository.UserRepository;
import RatingSystem.RatingSystem.exception.ResourceNotFoundException;
// import org.springframework.data.jpa.domain.Specification; // <--- REMOVE THIS IMPORT if not used elsewhere
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;

    public RatingService(RatingRepository ratingRepository, UserRepository userRepository) {
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
    }

    private RatingResponse mapToRatingResponse(Rating rating) {
        RatingResponse response = new RatingResponse();
        response.setId(rating.getId());
        response.setUserId(rating.getUser().getId());
        response.setUserName(rating.getUser().getName());
        response.setAmbiance(rating.getAmbiance());
        response.setFood(rating.getFood());
        response.setService(rating.getService());
        response.setCleanliness(rating.getCleanliness());
        response.setDrinks(rating.getDrinks());
        return response;
    }

    @Transactional // Ensures this operation runs within a database transaction
    public RatingResponse submitRating(Long userId, RatingRequest ratingRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        Rating rating = new Rating();
        rating.setUser(user);
        rating.setAmbiance(ratingRequest.getAmbiance());
        rating.setFood(ratingRequest.getFood());
        rating.setService(ratingRequest.getService());
        rating.setCleanliness(ratingRequest.getCleanliness());
        rating.setDrinks(ratingRequest.getDrinks());

        Rating savedRating = ratingRepository.save(rating);
        return mapToRatingResponse(savedRating);
    }

    @Transactional(readOnly = true)
    public List<RatingResponse> getAllRatings() {
        return ratingRepository.findAllWithUser().stream()
                .map(this::mapToRatingResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RatingResponse> getRatingsByUserId(Long userId) {
        return ratingRepository.findByUserIdWithUser(userId).stream()
                .map(this::mapToRatingResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RatingResponse> getFilteredRatings(String category, Integer ratingValue, Long userId) {
        return ratingRepository.findFilteredWithUser(category, ratingValue, userId).stream()
                .map(this::mapToRatingResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AdminReportResponse generateAdminReport() {
        // --- CRITICAL CHANGE HERE ---
        // Change findAll() to findAllWithUser() to eagerly fetch associated Users
        List<Rating> allRatings = ratingRepository.findAllWithUser(); // <--- THIS LINE WAS CHANGED

        Map<String, Double> averageRatings = new HashMap<>();
        double totalSum = 0;
        int totalCount = 0;

        if (allRatings.isEmpty()) {
            return new AdminReportResponse(new HashMap<>(), 0.0);
        }

        // Using reflection to dynamically get field values
        try {
            Field[] fields = Rating.class.getDeclaredFields();
            for (Field field : fields) {
                if (field.getType() == Integer.class &&
                        !field.getName().equals("id") && !field.getName().equals("user")) {
                    field.setAccessible(true); // Allow access to private fields
                    double sum = 0;
                    int count = 0;
                    for (Rating rating : allRatings) {
                        Integer value = (Integer) field.get(rating);
                        if (value != null) {
                            sum += value;
                            count++;
                        }
                    }
                    if (count > 0) {
                        averageRatings.put(field.getName(), sum / count);
                        totalSum += sum;
                        totalCount += count;
                    } else {
                        averageRatings.put(field.getName(), 0.0); // No ratings for this category
                    }
                }
            }
        } catch (IllegalAccessException e) {
            // Handle exception, e.g., log it or throw a custom exception
            System.err.println("Error accessing rating fields via reflection: " + e.getMessage());
            e.printStackTrace();
        }

        Double overallAverage = totalCount > 0 ? totalSum / totalCount : 0.0;

        return new AdminReportResponse(averageRatings, overallAverage);
    }
}