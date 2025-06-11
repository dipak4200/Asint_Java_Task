package RatingSystem.RatingSystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminReportResponse {
    private Map<String, Double> averageRatings; // e.g., "ambiance": 4.5
    private Double overallAverage;
}