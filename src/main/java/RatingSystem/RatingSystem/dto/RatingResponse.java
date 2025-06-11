package RatingSystem.RatingSystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingResponse {
    private Long id;
    private Long userId;
    private String userName; // To show who rated
    private Integer ambiance;
    private Integer food;
    private Integer service;
    private Integer cleanliness;
    private Integer drinks;
}