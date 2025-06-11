package RatingSystem.RatingSystem.repository;

import RatingSystem.RatingSystem.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor; // Keep if still using Specification elsewhere
import org.springframework.data.jpa.repository.Query; // <--- ADD THIS IMPORT
import org.springframework.data.repository.query.Param; // <--- ADD THIS IMPORT
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long>, JpaSpecificationExecutor<Rating> {

    // Keep this if you have other uses for it, but for populating DTOs, use findByUserIdWithUser
    List<Rating> findByUserId(Long userId);

    // Custom query to fetch Ratings and their associated User eagerly
    @Query("SELECT r FROM Rating r JOIN FETCH r.user WHERE r.user.id = :userId")
    List<Rating> findByUserIdWithUser(@Param("userId") Long userId);

    // Custom query to fetch all Ratings and their associated User eagerly
    @Query("SELECT r FROM Rating r JOIN FETCH r.user")
    List<Rating> findAllWithUser();

    // Custom query for filtered ratings that includes user data
    @Query("SELECT r FROM Rating r JOIN FETCH r.user " +
            "WHERE (:category IS NULL OR " +
            "  (:category = 'ambiance' AND r.ambiance = :ratingValue) OR " +
            "  (:category = 'food' AND r.food = :ratingValue) OR " +
            "  (:category = 'service' AND r.service = :ratingValue) OR " +
            "  (:category = 'cleanliness' AND r.cleanliness = :ratingValue) OR " +
            "  (:category = 'drinks' AND r.drinks = :ratingValue)) " +
            "AND (:userId IS NULL OR r.user.id = :userId)")
    List<Rating> findFilteredWithUser(
            @Param("category") String category,
            @Param("ratingValue") Integer ratingValue,
            @Param("userId") Long userId);
}