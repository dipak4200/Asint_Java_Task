package RatingSystem.RatingSystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "ratings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Integer ambiance; // Rating 1-5
    private Integer food;     // Rating 1-5
    private Integer service;  // Rating 1-5
    private Integer cleanliness; // Rating 1-5
    private Integer drinks;   // Rating 1-5
}